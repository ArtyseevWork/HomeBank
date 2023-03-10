package com.mordansoft.homebank.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.usecase.GetMainPurchasesUc
import com.mordansoft.homebank.domain.usecase.GetSumDaughterPurchaseUc


class MainViewModel(private val getMainPurchasesUc      : GetMainPurchasesUc,
                    private val getSumDaughterPurchaseUc: GetSumDaughterPurchaseUc) : ViewModel() {

    private val mainPurchasesMutableLiveData: MutableLiveData<ArrayList<Purchase>>

    init{
        mainPurchasesMutableLiveData = MutableLiveData()
    }


    fun getMainPurchasesMutableLiveData(): MutableLiveData<ArrayList<Purchase>> {
        mainPurchasesMutableLiveData.value = getMainPurchasesUc.execute()
        return mainPurchasesMutableLiveData
    }

//Метод для получения данных ()

    //Метод для получения данных ()
    /*fun getMainPurchases() {
        //mainPurchasesMutableLiveData.setValue(getMainPurchasesUc.execute())

        mainPurchasesMutableLiveData.value = getMainPurchasesUc.execute()

    }*/


}