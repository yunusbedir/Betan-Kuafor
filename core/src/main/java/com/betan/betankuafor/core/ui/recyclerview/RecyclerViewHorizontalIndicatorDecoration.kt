package com.betan.betankuafor.core.ui.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.betan.betankuafor.core.R

class RecyclerViewHorizontalIndicatorDecoration(
    private val context: Context
) : RecyclerView.ItemDecoration() {

    private val indicatorHeight =
        context.resources.getDimensionPixelOffset(R.dimen.indicator_height)
    private val indicatorStrokeWidth =
        context.resources.getDimensionPixelOffset(R.dimen.indicator_stroke_width)
    private val indicatorItemLength =
        context.resources.getDimensionPixelOffset(R.dimen.indicator_length_selected)
    private val indicatorItemLengthUnselected =
        context.resources.getDimensionPixelOffset(R.dimen.indicator_length_unselected)
    private val indicatorItemPadding =
        context.resources.getDimensionPixelOffset(R.dimen.indicator_padding)
    private val interpolator = AccelerateDecelerateInterpolator()
    private val paint = Paint()

    init {
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = indicatorStrokeWidth.toFloat()
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)

        val itemCount = parent.adapter?.itemCount ?: 1
        val totalLength =
            indicatorItemLengthUnselected * itemCount - indicatorItemLengthUnselected + indicatorItemLength
        val paddingBetweenItems = Math.max(0, itemCount - 1) * indicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f
        val indicatorPositionY = parent.height - indicatorHeight / 2f
        val layoutManager = parent.layoutManager as LinearLayoutManager
        val activePosition = layoutManager.findFirstVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }
        if (itemCount <= 1) {
            return
        }

        with(layoutManager.findViewByPosition(activePosition)) {
            let {
                val progress =
                    interpolator.getInterpolation(it?.left as Int * -1 / it.width.toFloat())
                val lineDrawModel = LineDrawModel(
                    canvas,
                    indicatorStartX,
                    indicatorPositionY,
                    itemCount,
                    activePosition,
                    progress
                )
                drawInactiveIndicators(lineDrawModel, activePosition)
                drawHighlights(lineDrawModel)
            }
        }
    }

    private fun drawInactiveIndicators(lineDrawModel: LineDrawModel, activePosition: Int) {
        paint.color = context.getColor(R.color.greyscale300)
        with(lineDrawModel) {
            var start = indicatorStartX
            for (i in 0 until itemCount) {
                if (activePosition == i) {
                    canvas.drawLine(
                        start,
                        indicatorPositionY,
                        start + indicatorItemLength,
                        indicatorPositionY,
                        paint
                    )
                    start += indicatorItemLength + indicatorItemPadding
                } else {
                    canvas.drawLine(
                        start,
                        indicatorPositionY,
                        start + indicatorItemLengthUnselected,
                        indicatorPositionY,
                        paint
                    )
                    start += indicatorItemLengthUnselected + indicatorItemPadding
                }
            }
        }
    }

    private fun drawHighlights(lineDrawModel: LineDrawModel) {
        paint.color = context.getColor(R.color.primary500)
        with(lineDrawModel) {
            val highlightStart =
                indicatorStartX + (indicatorItemLengthUnselected + indicatorItemPadding) * highlightPosition
            if (progress == 0f) {
                canvas.drawLine(
                    highlightStart,
                    indicatorPositionY,
                    highlightStart + indicatorItemLength,
                    indicatorPositionY,
                    paint
                )
            } else {
                canvas.drawLine(
                    highlightStart + indicatorItemLengthUnselected * progress,
                    indicatorPositionY,
                    highlightStart + indicatorItemLength,
                    indicatorPositionY,
                    paint
                )
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = indicatorHeight
    }

    class LineDrawModel(
        val canvas: Canvas,
        val indicatorStartX: Float,
        val indicatorPositionY: Float,
        val itemCount: Int,
        val highlightPosition: Int,
        val progress: Float
    )
}
