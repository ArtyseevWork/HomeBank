package com.mordansoft.homebank.ui.profits

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mordansoft.homebank.R
import com.mordansoft.homebank.domain.model.Profit
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Int
import kotlin.Long

class ProfitAdapter(listProfit: ArrayList<Profit>, mContext: Context) :
    RecyclerView.Adapter<ProfitAdapter.ViewHolder>() {
    private val listProfit: ArrayList<Profit>
    private val mContext: Context
    private var listener: Listener? = null

    init {
        this.listProfit = listProfit
        this.mContext = mContext
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.profit_card, parent, false) as CardView
        return ViewHolder(v)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int
    ) {  //download data from list to THE item\profit
        //download data from list to THE ite
        val profit = listProfit[position]
        val cardView = viewHolder.cardView
        viewHolder.v_amount.setText(String.valueOf(profit.amount))
        viewHolder.v_name.setText(String.valueOf(profit.name))
        viewHolder.v_statusId.setText(profit.statusId.toString())
            //viewHolder.v_statusId.setText(Status.getProfitNameById(mContext, profit.getStatus()))

        val unixTime: Long = profit.date
        val normalTime = Date(unixTime * 1000)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        viewHolder.v_date.setText(dateFormat.format(normalTime))

        if (profit.statusId == 100) {
            viewHolder.v_border.background =
                ContextCompat.getDrawable(mContext, R.drawable.ic_fill_100)
        } else if (profit.statusId == 200) {
            viewHolder.v_border.background =
                ContextCompat.getDrawable(mContext, R.drawable.ic_fill_200)
        } else if (profit.statusId == 300) {
            viewHolder.v_border.background =
                ContextCompat.getDrawable(mContext, R.drawable.ic_fill_300)
        } else if (profit.statusId  == 400) {
            viewHolder.v_border.background =
                ContextCompat.getDrawable(mContext, R.drawable.ic_fill_400)
        }

        cardView.setOnClickListener {
            if (listener != null) {
                listener!!.onClick(it, profit.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return listProfit.size
    }

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        val v_name  : TextView
        val v_amount: TextView
        val v_date  : TextView
        val v_statusId: TextView
        val v_border: View

        init {
            v_name =   cardView.findViewById<TextView>(R.id.profit_card_name)
            v_amount = cardView.findViewById<TextView>(R.id.profit_card_amount)
            v_date =   cardView.findViewById<TextView>(R.id.profit_card_date)
            v_statusId = cardView.findViewById<TextView>(R.id.profit_card_status)
            v_border = cardView.findViewById<View>(R.id.profit_card_border)
        }
    }

    interface Listener {
        fun onClick(view: View, position: Long)
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }
}
