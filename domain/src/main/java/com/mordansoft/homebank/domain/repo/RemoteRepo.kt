package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.Preferences
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.usecase.auth.StartSyncUc

interface RemoteRepo {

    fun getAllData(startSyncUc: StartSyncUc)

    fun getUserId(): String?

}