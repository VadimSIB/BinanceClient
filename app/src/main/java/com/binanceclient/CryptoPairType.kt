package com.binanceclient

enum class CryptoPairType (val _name:String) {
    BTCUSDT("BTC/USDT"),
    BNBBTC("BNB/BTC"),
    ETHBTC("ETH/BTC");
    companion object {
        private val lookup = values().associateBy(CryptoPairType::_name)
        fun fromPairName(value: String): CryptoPairType = requireNotNull(lookup[value]) { "No CryptoPairType with _name $value" }
        fun getNames(): ArrayList<String>{ return lookup.keys.toList() as ArrayList<String> }
    }
}