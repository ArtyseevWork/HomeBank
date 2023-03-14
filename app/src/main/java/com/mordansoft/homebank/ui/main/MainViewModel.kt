package com.mordansoft.homebank.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.usecase.GetMainPurchasesUc
import com.mordansoft.homebank.domain.usecase.GetSumDaughterPurchaseUc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MainViewModel(private val getMainPurchasesUc      : GetMainPurchasesUc) : ViewModel() {

    private val mainPurchasesMutableLiveData: MutableLiveData<ArrayList<Purchase>> = MutableLiveData()

    fun getMainPurchasesMutableLiveData(): MutableLiveData<ArrayList<Purchase>> {
        mainPurchasesMutableLiveData.value = getMainPurchasesUc.execute()
        return mainPurchasesMutableLiveData
    }

}