package com.example.kotlinexample.screens.start

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mordansoft.homebank.R
import com.mordansoft.homebank.domain.model.Currency
import com.mordansoft.homebank.domain.model.Profit
import com.mordansoft.homebank.ui.profits.ProfitAdapter
import java.util.ArrayList

class CurrencyAdapter(listStart : List<Currency>) : RecyclerView.Adapter<CurrencyAdapter.StartViewHolder>() {

    private val listStart : List<Currency>


    init {
        this.listStart = listStart
    }

    class StartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_card,parent,false)
        return StartViewHolder(view)
    }


    override fun getItemCount(): Int {
        return listStart.size
    }

    override fun onBindViewHolder(holder: StartViewHolder, position: Int) {

        val tv_name_a  = holder.itemView.findViewById<TextView>(R.id.currency_a_name)
        val tv_name_b  = holder.itemView.findViewById<TextView>(R.id.currency_b_name)
        val tv_count_a = holder.itemView.findViewById<TextView>(R.id.currency_a_count)
        val tv_count_b = holder.itemView.findViewById<TextView>(R.id.currency_b_count)

        tv_name_a.text = listStart[position].currencyNameA
        tv_name_b.text = listStart[position].currencyNameB
        tv_count_a.text = listStart[position].currencyCountA.toString()
        tv_count_b.text = listStart[position].currencyCountB.toString()

    }

    /*fun setList(list: List<Currency>){
        listStart = list
        notifyDataSetChanged()
    }*/
}