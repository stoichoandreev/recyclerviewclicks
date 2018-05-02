package com.sniper.rcdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.annotations.NotNull
import java.util.*

class CardViewActivity : AppCompatActivity() {

    companion object {
        const val MAX_ITEMS = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cardview)

        setup(recycler_view)
    }

    private fun setup(@NotNull recyclerView: RecyclerView) {
        recyclerView.adapter = CardViewAdapter()
        recyclerView.itemAnimator = DefaultItemAnimator()
        with(recyclerView.adapter as CardViewAdapter) {
            setItems(generateDummyData())
        }
        recyclerView.addOnItemTouchListener(CardViewRecyclerViewItemTouchListener(recyclerView,
                intArrayOf(R.id.card_view_action_button),
                object : CardViewRecyclerViewItemTouchListener.CarViewClickListener {
                    override fun onButtonClick(view: View, position: Int) {
                        Toast.makeText(view.context, "CardView Button click action on position = " + position, Toast.LENGTH_LONG).show()
                    }

                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(view.context, "CardView single click action on position = " + position, Toast.LENGTH_LONG).show()
                    }

                    override fun onLongClick(view: View, position: Int) {
                        Toast.makeText(view.context, "CardView long click action on position = " + position, Toast.LENGTH_LONG).show()
                    }
                })
        )
    }

    @NotNull
    private fun generateDummyData(): ArrayList<CustomData> =
            (0..MAX_ITEMS).mapTo(ArrayList(MAX_ITEMS)) {
                CustomData(it,
                        String.format(resources.getString(R.string.card_view_title_pattern), it),
                        String.format(resources.getString(R.string.card_view_description_pattern), it))
            }
}