package com.sniper.rcdemo

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

open class CustomRecyclerViewItemTouchListener(@NotNull recycleView: RecyclerView,
                                               @Nullable @IdRes specialIds: IntArray?,
                                               @NotNull clickListener: MyCustomClickListener)
    : BaseRecyclerViewItemTouchListener<CustomRecyclerViewItemTouchListener.MyCustomClickListener>(recycleView, specialIds, clickListener) {

    companion object {
        private const val SPECIAL_VIEW_CLICK_AREA_EXTENSION = 5//this is gonna be converted to 5dp
    }

    private var clickPadding: Int

    init {
        clickPadding = (SPECIAL_VIEW_CLICK_AREA_EXTENSION * recycleView.resources.displayMetrics.density).toInt()
    }

    interface MyCustomClickListener : BaseRecyclerViewItemTouchListener.ClickListener {

        fun onBackupClick(view: View, position: Int)

        fun onBlockClick(view: View, position: Int)

    }

    override fun onSpecialViewClick(@NotNull specialChildView: View,
                                    listPosition: Int) {
        when (specialChildView.id) {
            R.id.backup_view -> mClickListener.onBackupClick(specialChildView, listPosition)
            R.id.block_view -> mClickListener.onBlockClick(specialChildView, listPosition)
            else -> mClickListener.onClick(specialChildView, listPosition)
        }
    }

    override fun getSpecialViewClickPadding(): Int = clickPadding
}