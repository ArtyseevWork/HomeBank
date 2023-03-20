package com.mordansoft.homebank.ui.purchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.Purchase
import com.mordansoft.homebank.domain.usecase.period.GetPeriodUc
import com.mordansoft.homebank.domain.usecase.purchase.*
import kotlinx.coroutines.launch

class PurchaseViewModel(private val getPurchaseByIdUc       : GetPurchaseByIdUc,
                        private val getDaughterPurchasesUc  : GetDaughterPurchasesUc,
                        private val getSumDaughterPurchaseUc: GetSumDaughterPurchaseUc,
                        private val setPurchaseUc           : SetPurchaseUc,
                        private val deletePurchaseUc        : DeletePurchaseUc,
                        private val getPeriodUc            : GetPeriodUc
) : ViewModel() {


    private val _period = MutableLiveData<Period>()
    var period: LiveData<Period> = _period

    private val _daughterPurchases = MutableLiveData<ArrayList<Purchase>>()
    var daughterPurchases: LiveData<ArrayList<Purchase>> = _daughterPurchases

    private val _purchase = MutableLiveData<Purchase>()
    var purchase: LiveData<Purchase> = _purchase

    private val _daughterPurchaseSum = MutableLiveData<Float>()
    var daughterPurchaseSum: LiveData<Float> = _daughterPurchaseSum


    fun getDaughterPurchases(parentId : Long){
        viewModelScope.launch {
            _daughterPurchases.value = getDaughterPurchasesUc.execute(parentId = parentId )
        }
    }

    fun getDaughterPurchasesSum(parentId  : Long){
        viewModelScope.launch {
            _daughterPurchaseSum.value = getSumDaughterPurchaseUc.execute(parentId  = parentId )
        }
    }

    fun getPurchase(purchaseId : Long){
        viewModelScope.launch {
            _purchase.value = getPurchaseByIdUc.execute(purchaseId = purchaseId)
        }
    }

    fun setPurchase(purchase : Purchase){
        viewModelScope.launch {
            setPurchaseUc.execute(purchase = purchase)
            _purchase.value = purchase
        }
    }

    fun getPeriod(periodId : Int?){
        viewModelScope.launch {
            _period.value = getPeriodUc.execute( periodId = periodId)
        }
    }

    fun deletePurchase(purchase : Purchase){
        viewModelScope.launch {
            deletePurchaseUc.execute(purchase = purchase)
        }
    }
}

