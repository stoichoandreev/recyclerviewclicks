package com.sniper.rcdemo

import android.graphics.Rect
import android.support.annotation.CallSuper
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

abstract class BaseRecyclerViewItemTouchListener<out T : BaseRecyclerViewItemTouchListener.ClickListener>(
        @NotNull val recycleView: RecyclerView,
        @Nullable @IdRes private val specialViewIDs: IntArray?,
        @NotNull protected val mClickListener: T)
    : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector by lazy {
        GestureDetector(recycleView.context, object : GestureDetector.SimpleOnGestureListener() {

            override fun onSingleTapUp(event: MotionEvent): Boolean = true

            override fun onLongPress(event: MotionEvent) {
                with(recycleView) {
                    val child: View? = findChildViewUnder(event.x, event.y)
                    child?.let { mClickListener.onLongClick(child, getChildAdapterPosition(child)) }
                }
            }
        })
    }

    interface ClickListener {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View, position: Int)
    }

    @CallSuper
    override fun onInterceptTouchEvent(recyclerView: RecyclerView, event: MotionEvent): Boolean {
        if (gestureDetector.onTouchEvent(event)) {
            val view: View? = recyclerView.findChildViewUnder(event.x, event.y)
            view?.let {
                val listPosition = recyclerView.getChildAdapterPosition(view)
                val specialChildView: View? = findExactChild(view,
                        event.rawX,
                        event.rawY,
                        getSpecialViewClickPadding())
                if (listPosition != RecyclerView.NO_POSITION) {
                    if (specialChildView != null) {
                        onSpecialViewClick(specialChildView, listPosition)
                    } else {
                        mClickListener.onClick(view, listPosition)
                    }
                    return true
                }
            }
        }

        return false
    }

    /**
     * This method will try to match the coordinates of the MotionEvent(x and y) and the absolute boundaries of all special views
     *
     * @param view                    - This should be the view which receive the touch event (usually the parent element in the ViewHolder)
     * @param x                       - The X coordinate of the touch event
     * @param y                       - The Y coordinate of the touch event
     * @param specialViewClickPadding - some padding if we want to increase the special View clickable area
     * @return - Will return the special child View found under the X and Y coordinates of the touch event. This View must be specified in the array of special view ids.
     * If the specialViewClickIds array is empty or Null, the method will return Null, which means that special view click will never be triggered
     */
    @Nullable
    private fun findExactChild(@Nullable view: View?,
                               x: Float,
                               y: Float,
                               specialViewClickPadding: Int): View? {
        if (view == null || view !is ViewGroup) {
            return view
        }

        if (specialViewIDs != null && specialViewIDs.isNotEmpty()) {
            for (specialViewId in specialViewIDs) {
                val specialView: View? = view.findViewById(specialViewId)
                specialView?.let {
                    val viewBounds = Rect()
                    specialView.getGlobalVisibleRect(viewBounds)
                    if (x >= viewBounds.left - specialViewClickPadding &&
                            x <= viewBounds.right + specialViewClickPadding &&
                            y >= viewBounds.top - specialViewClickPadding &&
                            y <= viewBounds.bottom + specialViewClickPadding) {
                        return specialView
                    }
                }
            }
        }
        return null
    }

    /**
     * Send notification to the listener about special views click
     * The child class which implement this method needs to handle the callback to all special Views
     *
     * @param specialChildView - the SpecialView found under the touch event
     * @param listPosition     - the position in the list
     */
    protected abstract fun onSpecialViewClick(specialChildView: View, listPosition: Int)

    /**
     * @return - If we want to have bigger clickable area around the special views we can return some padding here.
     * If this method returns 0 the clickable area will be exactly in the special View boundaries.
     * This value will not effect the the View visible padding, it is just for the click events
     */
    protected abstract fun getSpecialViewClickPadding(): Int

    @CallSuper
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        //unused
    }

    @CallSuper
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        //unused
    }

}