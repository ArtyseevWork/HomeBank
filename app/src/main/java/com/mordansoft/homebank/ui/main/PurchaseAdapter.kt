package com.mordansoft.homebank.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mordansoft.homebank.R
import com.mordansoft.homebank.domain.model.Purchase

class PurchaseAdapter(listPurchase: ArrayList<Purchase>, mContext: Context) :
    RecyclerView.Adapter<PurchaseAdapter.ViewHolder>() {
    private val listPurchase: ArrayList<Purchase>
    private val mContext: Context
    private var listener: Listener? = null

    init { //constructor
        this.listPurchase = listPurchase
        this.mContext = mContext
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder { //view for one item \ Purchase
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.purchase_card2, parent, false) as CardView
        return ViewHolder(v)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        i: Int
    ) {  //download data from list to THE item\purchase
        val purchase: Purchase = listPurchase[i]
        val cardView = viewHolder.cardView
        viewHolder.v_price.text = (purchase.price * purchase.count).toString()
        if (purchase.status === 100) {
            viewHolder.v_border.background =
                ContextCompat.getDrawable(mContext, R.drawable.ic_fill_100)
        } else if (purchase.status === 200) {
            viewHolder.v_border.background =
                ContextCompat.getDrawable(mContext, R.drawable.ic_fill_200)
        } else if (purchase.status === 300) {
            viewHolder.v_border.background =
                ContextCompat.getDrawable(mContext, R.drawable.ic_fill_300)
        } else if (purchase.status === 400) {
            viewHolder.v_border.background =
                ContextCompat.getDrawable(mContext, R.drawable.ic_fill_400)
        }
        //viewHolder.v_status.setText(Status.getPurchaseNameById(mContext, purchase.status))
        //val countOfChildren: Int = Purchase.getCountOfChildren(mContext, purchase.id)
        val countOfChildren: Int =0
        if (countOfChildren > 0) {
            viewHolder.v_name.setText(purchase.name + " (" + countOfChildren + ")")
        } else {
            viewHolder.v_name.setText(java.lang.String.valueOf(purchase.name))
        }
        if (purchase.count > 1) {
            viewHolder.v_count.text = (purchase.count.toString() + " * " + purchase.price.toString())
        } else {
            val x = viewHolder.v_count.lineHeight + viewHolder.v_name.lineHeight
            //viewHolder.v_price.setHeight(x * 2);
            viewHolder.v_count.height = 0
            viewHolder.v_count.text = null
            viewHolder.v_name.height = x + 15
        }
        cardView.setOnClickListener {
            if (listener != null) {
                listener!!.onClick(it, purchase.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return listPurchase.size
    }

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(
        cardView
    ) {
        val v_price: TextView
        val v_count: TextView
        val v_status: TextView
        val v_name: TextView
        val v_border: View

        init {
            v_price = cardView.findViewById<TextView>(R.id.profit_card_amount)
            v_count = cardView.findViewById<TextView>(R.id.count)
            v_status = cardView.findViewById<TextView>(R.id.profit_card_status)
            v_name = cardView.findViewById<TextView>(R.id.profit_card_name)
            v_border = cardView.findViewById<View>(R.id.purchase_card_border)
        }
    }

    interface Listener {
        fun onClick(view: View, position: Long)
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }
}
