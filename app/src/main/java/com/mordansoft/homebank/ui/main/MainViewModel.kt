package com.mordansoft.homebank.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.usecase.purchase.GetMainPurchasesUc
import kotlinx.coroutines.launch


class MainViewModel(private val getMainPurchasesUc      : GetMainPurchasesUc) : ViewModel() {

    private val _purchases = MutableLiveData<ArrayList<Purchase>>()
    var purchases: LiveData<ArrayList<Purchase>> = _purchases


    fun getPurchases(){
        viewModelScope.launch {
            _purchases.value = getMainPurchasesUc.execute()
        }


    }

}

