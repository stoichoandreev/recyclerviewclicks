package com.sniper.rcdemo

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import org.jetbrains.annotations.NotNull

class CustomViewHolder(@NotNull itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: CustomData) = with(itemView) {
        with(itemView.findViewById<TextView>(R.id.title_text_view)) {
            text = item.title
        }
        with(itemView.findViewById<TextView>(R.id.description_text_view)) {
            text = item.description
        }
    }
}