package com.binanceclient

sealed class CryptoPairType (val name: String, val symbol: String) {

    object BTCUSDT: CryptoPairType("BTC/USDT", "BTCUSDT")
    object BNBBTC: CryptoPairType("BNB/BTC", "BNBBTC")
    object ETHBTC: CryptoPairType("ETH/BTC", "ETHBTC")

    companion object {
        fun fromPairName(possibleName: String): CryptoPairType? {
            return CryptoPairType::class.sealedSubclasses
                .firstOrNull() { it.objectInstance?.name == possibleName }
                ?.objectInstance
        }
    }
}

