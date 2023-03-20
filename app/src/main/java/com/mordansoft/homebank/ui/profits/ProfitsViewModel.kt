package com.mordansoft.homebank.ui.profits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mordansoft.homebank.domain.model.Period
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.domain.model.ProfitsAccounting
import com.mordansoft.homebank.domain.usecase.period.GetPeriodUc
import com.mordansoft.homebank.domain.usecase.profit.GetMainProfitsUc
import com.mordansoft.homebank.domain.usecase.profit.GetProfitsAccountingUc
import kotlinx.coroutines.launch


class ProfitsViewModel(private val getMainProfitsUc       : GetMainProfitsUc,
                       private val getProfitsAccountingUc : GetProfitsAccountingUc,
                       private val getPeriodUc            : GetPeriodUc
) : ViewModel() {


    private val _period = MutableLiveData<Period>()
    var period: LiveData<Period> = _period

    private val _profits = MutableLiveData<ArrayList<Profit>>()
    var profits: LiveData<ArrayList<Profit>> = _profits

    private val _accounting = MutableLiveData<ProfitsAccounting>()
    var accounting: LiveData<ProfitsAccounting> = _accounting



    fun getPeriodsData(periodId : Int?){
        viewModelScope.launch {
            val newPeriod : Period = getPeriodUc.execute(periodId)
            _period.value      = newPeriod
            _accounting.value  = getProfitsAccountingUc.execute(newPeriod.id) //todo async
            _profits.value     = getMainProfitsUc.execute(newPeriod.id)
        }
    }

}

