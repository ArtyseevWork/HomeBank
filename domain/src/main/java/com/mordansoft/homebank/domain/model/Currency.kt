package com.mordansoft.homebank.domain.model

data class Currency(
    val currencyCodeA : Int    = UAH_ISO_4217,
    val currencyNameA : String = UAH_CODE,
    val currencyCountA: Float  = 1F,
    val currencyCodeB : Int    = UAH_ISO_4217,
    val currencyNameB : String = UAH_CODE,
    val currencyCountB: Float  = 1F

){
    companion object{
        const val UAH_ISO_4217 : Int    = 980    //Українська гривня
        const val USD_ISO_4217 : Int    = 840    //Долар США
        const val EUR_ISO_4217 : Int    = 978    //Євро
        const val PLN_ISO_4217 : Int    = 985    //Російський рубль
        const val GBP_ISO_4217 : Int    = 826    //Фунт стерлінгів Великобританії
        const val JPY_ISO_4217 : Int    = 392    //Японська єна
        const val CHF_ISO_4217 : Int    = 756    //Швейцарський франк
        const val CNY_ISO_4217 : Int    = 156    //Китайський юань
        const val RUB_ISO_4217 : Int    = 643    //Польський злотий

        const val UAH_CODE : String    = "UAH"    //Українська гривня
        const val USD_CODE : String    = "USD"    //Долар США
        const val EUR_CODE : String    = "EUR"    //Євро
        const val PLN_CODE : String    = "PLN"    //Російський рубль
        const val GBP_CODE : String    = "GBP"    //Фунт стерлінгів Великобританії
        const val JPY_CODE : String    = "JPY"    //Японська єна
        const val CHF_CODE : String    = "CHF"    //Швейцарський франк
        const val CNY_CODE : String    = "CNY"    //Китайський юань
        const val RUB_CODE : String    = "RUB"    //Польський злотий

        val map = mapOf(
            UAH_ISO_4217 to UAH_CODE,
            USD_ISO_4217 to USD_CODE,
            EUR_ISO_4217 to EUR_CODE,
            PLN_ISO_4217 to PLN_CODE,
            GBP_ISO_4217 to GBP_CODE,
            JPY_ISO_4217 to JPY_CODE,
            CHF_ISO_4217 to CHF_CODE,
            CNY_ISO_4217 to CNY_CODE,
            RUB_ISO_4217 to RUB_CODE
        )
    }



}