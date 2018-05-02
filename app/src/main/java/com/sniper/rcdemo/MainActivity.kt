package com.sniper.rcdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.annotations.NotNull
import java.util.*
import android.support.v7.widget.DividerItemDecoration



class MainActivity : AppCompatActivity() {

    companion object {
        const val MAX_ITEMS = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setup(recycler_view)
    }

    private fun setup(@NotNull recyclerView: RecyclerView) {
        recyclerView.adapter = CustomAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        with(recyclerView.adapter as CustomAdapter) {
            setItems(generateDummyData())
        }
        recyclerView.addOnItemTouchListener(CustomRecyclerViewItemTouchListener(recyclerView,
                intArrayOf(R.id.backup_view, R.id.block_view),
                object : CustomRecyclerViewItemTouchListener.MyCustomClickListener {
                    override fun onBackupClick(view: View, position: Int) {
                        Toast.makeText(view.context, "Backup action on position = " + position, Toast.LENGTH_LONG).show()
                    }

                    override fun onBlockClick(view: View, position: Int) {
                        Toast.makeText(view.context, "Block action on position = " + position, Toast.LENGTH_LONG).show()
                    }

                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(view.context, "Single click action on position = " + position, Toast.LENGTH_LONG).show()
                    }

                    override fun onLongClick(view: View, position: Int) {
                        Toast.makeText(view.context, "Long click action on position = " + position, Toast.LENGTH_LONG).show()
                        openCardViewScreen()
                    }
                })
        )
    }

    private fun openCardViewScreen() {
        startActivity(Intent(this, CardViewActivity::class.java))
    }

    @NotNull
    private fun generateDummyData(): ArrayList<CustomData> =
            (0..MAX_ITEMS).mapTo(ArrayList(MAX_ITEMS)) {
                CustomData(it ,
                        String.format(resources.getString(R.string.title_pattern), it),
                        String.format(resources.getString(R.string.description_pattern), it))
            }
}