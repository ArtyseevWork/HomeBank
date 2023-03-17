package com.mordansoft.homebank.ui.profits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.usecase.profit.GetMainProfitsUc
import kotlinx.coroutines.launch


class ProfitsViewModel(private val getMainProfitsUc      : GetMainProfitsUc) : ViewModel() {

    private val _profits = MutableLiveData<ArrayList<Profit>>()
    var profits: LiveData<ArrayList<Profit>> = _profits


    fun getProfits(){
        viewModelScope.launch {
            _profits.value = getMainProfitsUc.execute()
        }


    }

}

