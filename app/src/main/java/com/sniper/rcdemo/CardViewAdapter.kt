package com.sniper.rcdemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class CardViewAdapter: RecyclerView.Adapter<CardViewHolder>() {

    private var itemsList = ArrayList<CustomData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CardViewHolder(layoutInflater.inflate(R.layout.item_card_view, parent, false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int =
        itemsList.size

    fun setItems(list: ArrayList<CustomData>) {
        itemsList.addAll(list)
    }
}