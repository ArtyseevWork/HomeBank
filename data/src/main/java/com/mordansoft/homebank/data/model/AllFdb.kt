package com.mordansoft.homebank.data.model

import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.Preferences
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.model.Purchase

data class AllFdb(
    var profit       :    Array<Profit>
)
