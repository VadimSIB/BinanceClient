package com.binanceclient

import androidx.lifecycle.MutableLiveData
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiWebSocketClient
import com.binance.api.client.domain.event.DepthEvent
import com.binance.api.client.domain.market.OrderBook
import com.binance.api.client.domain.market.OrderBookEntry
import java.io.Closeable
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Comparator
import kotlin.collections.HashMap

@Singleton
class BinanceRepository @Inject constructor() {

    private val BIDS = "BIDS"
    private val ASKS = "ASKS"
    val asks: NavigableMap<BigDecimal, BigDecimal>
        get() = depthCache[ASKS]!!
    val bids: NavigableMap<BigDecimal, BigDecimal>
        get() = depthCache[BIDS]!!
    private lateinit var depthCache: MutableMap<String, NavigableMap<BigDecimal, BigDecimal>>

    private var lastUpdateId: Long = 0
    @Volatile
    private var symbolVolatile: String? = ""
    @Volatile
    private var startDepthCacheVolatile = false
    var _bidOrderBook = MutableLiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>>()
    var _askOrderBook = MutableLiveData<List<MutableMap.MutableEntry<BigDecimal, BigDecimal>>>()
    var socketClient: BinanceApiWebSocketClient?=null
    var runningSocket: Closeable? = null

    fun initializeDepthCache(symbol: String?) {
        val factory = BinanceApiClientFactory.newInstance()
        val client = factory.newAsyncRestClient()
        client.getOrderBook(symbol?.uppercase(Locale("english")), 100) { response: OrderBook ->
            depthCache = HashMap()
            val asks: NavigableMap<BigDecimal, BigDecimal> = TreeMap(Comparator.reverseOrder())
            val bids: NavigableMap<BigDecimal, BigDecimal> = TreeMap(Comparator.reverseOrder())
            for (ask in response.asks) {
                asks[BigDecimal(ask.price)] = BigDecimal(ask.qty)
            }
            for (bid in response.bids) {
                bids[BigDecimal(bid.price)] = BigDecimal(bid.qty)
            }
            depthCache[ASKS] = asks
            depthCache[BIDS] = bids
            startDepthCacheVolatile = true
        }
    }

    fun startDepthEventStreaming(symbol: String?){
        val factory = BinanceApiClientFactory.newInstance()
        if (runningSocket!=null) {
            runningSocket?.close()
            runningSocket = null
            lastUpdateId = 0
            asks.clear()
            bids.clear()
            _bidOrderBook.postValue(ArrayList(bids.entries))
            _askOrderBook.postValue(ArrayList(asks.entries).asReversed())
        }
        socketClient = factory.newWebSocketClient()
        symbolVolatile = symbol
        runningSocket = socketClient?.onDepthEvent(symbol?.lowercase(Locale("english"))) { response: DepthEvent ->
            if (startDepthCacheVolatile) {
                if (response.symbol == symbolVolatile) {
                    if (response.finalUpdateId > lastUpdateId) {
                        lastUpdateId = response.finalUpdateId
                        updateOrderBook(asks, response.asks, ASKS)
                        updateOrderBook(bids, response.bids, BIDS)
                        _bidOrderBook.postValue(ArrayList(bids.entries))
                        _askOrderBook.postValue(ArrayList(asks.entries).asReversed())
                    }
                }
            }
        }
    }

    private fun updateOrderBook(lastOrderBookEntries: NavigableMap<BigDecimal, BigDecimal>, orderBookDeltas: List<OrderBookEntry>, type: String) {
        for (orderBookDelta in orderBookDeltas) {
            val price = BigDecimal(orderBookDelta.price)
            val qty = BigDecimal(orderBookDelta.qty)
            if (qty.compareTo(BigDecimal.ZERO) == 0) {
                lastOrderBookEntries.remove(price)
            } else {
                lastOrderBookEntries[price] = qty
                if (lastOrderBookEntries.size>100){
                    val currenKey = when(type){
                        ASKS->lastOrderBookEntries.firstEntry()?.key
                        BIDS->lastOrderBookEntries.lastEntry()?.key
                        else->0
                    }
                    lastOrderBookEntries.remove(currenKey)
                }
            }
        }
    }
}