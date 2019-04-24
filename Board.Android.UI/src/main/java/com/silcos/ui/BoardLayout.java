package com.silcos.ui;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * {@code BoardLayout} can be used to reflect the position of views in
 * a {@code Board} (from Board library). It divides a rectangular area
 * into rows & columns where each child {@link View} is placed into a
 * specific cell. Each view is placed with a cell (whose coordinates
 * are given in its {@link LayoutParams}. Margins are not currently
 * supported for this layout.
 *
 * Child views coordinates can be transformed during positioning to
 * change the way a user views a board. For example, using the
 * {@link InversionBoardTransformer}, you can display the board to the user
 * from the opponent's perspective.
 *
 * By default, child views are placed in the top-left corner of each
 * cell. This position can be modified using parameters like margin,
 * cell-padding, and offset.
 *
 * NOTE: BoardLayout doesn't currently support XML. Try using it in
 * a {@link android.widget.FrameLayout} placed in XML and add it
 * programmatically.
 */
public class BoardLayout extends ViewGroup {

    private int mCols;
    private int mRows;
    private float mPercentPadding;
    public float offsetBackPercent;
    public BoardTransformer mBoardTransformer;

    private CellParams mCellParams[][];
    private CellParams mDefaultCellParams = null;

    /**
     * Handles any request to change the dimension of this {@link BoardLayout}
     * and copies all the cell-params to a new 2-D array.
     *
     * Subclasses should extend this method to update any of their state
     * caches.
     *
     * @param newRows new no. of rows requested
     * @param newCols new no. of columns requested
     */
    @CallSuper
    private void onDimenChanged(int newRows, int newCols) {
        final CellParams oldCellParams[][] = mCellParams;
        final CellParams newCellsParams[][] = new CellParams[newRows][newCols];

        final int copyRows = Math.min(mRows, newRows);
        final int copyCols = Math.min(mCols, newCols);

        for (int row = 0; row < copyRows; row++) {
            for (int col = 0; col < copyCols; col++) {
                newCellsParams[row][col] = oldCellParams[row][col];
            }
        }

        mRows = newRows;
        mCols = newCols;
        mCellParams = newCellsParams;
    }

    public BoardLayout(Context context) {
        super(context);
    }

    public BoardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getColumns() {
        return mCols;
    }

    public void setColumns(int columns) {
        onDimenChanged(mRows, columns);
    }

    public int getRows() {
        return mRows;
    }

    public void setRows(int rows) {
        onDimenChanged(rows, mCols);
    }

    public CellParams getCellParams(int r, int c) {
        return mCellParams[r][c];
    }

    public void setCellParams(int r, int c, CellParams cellParams) {
        mCellParams[r][c] = cellParams;
    }

    public CellParams getDefaultCellParams() {
        return mDefaultCellParams;
    }

    public BoardLayout setDefaultCellParams(CellParams defaultCellParams) {
        mDefaultCellParams = defaultCellParams;
        return this;
    }

    public float getPercentPadding() {
        return mPercentPadding;
    }

    public void setPercentPadding(float percentPadding) {
        mPercentPadding = (percentPadding < 0) ? 0 :
                ((percentPadding > 1) ? 0 : percentPadding);
    }

    public View findViewAt(int row, int column) {
        final int childCount = getChildCount();
        for(int off = 0; off < childCount; off++) {
            final View view = getChildAt(off);
            if(view == null) {
                Log.d("Game","null child at off: " + off);
                continue;
            }

            final LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if(layoutParams.mRow == row && layoutParams.mCol == column)
                return view;
        }

        Log.d("Game", "no view at: " + row+","+column);
        return null;
    }

    private void measureExact(int totalWidth, int totalHeight) {
        final int otw = totalWidth; final int oth = totalHeight;
        totalWidth *= (1 - mPercentPadding);
        totalHeight *= (1 - mPercentPadding);

        final int widthPerChild = totalWidth / mRows;
        final int heightPerChild = totalHeight / mCols;

        final int childWidthMeasureSpec =
                MeasureSpec.makeMeasureSpec(widthPerChild, MeasureSpec.EXACTLY);
        final int childHeightMeasureSpec =
                MeasureSpec.makeMeasureSpec(heightPerChild, MeasureSpec.EXACTLY);

        final int childCount = getChildCount();

        for(int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }

        setMeasuredDimension(otw, oth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int count = getChildCount();

        if(widthMode == MeasureSpec.EXACTLY &&
                heightMode == MeasureSpec.EXACTLY) {
            Log.d("Permainan", "EXECT");
            measureExact(MeasureSpec.getSize(widthMeasureSpec),
                    MeasureSpec.getSize(heightMeasureSpec));
        } else if(widthMode == MeasureSpec.EXACTLY) {
            final int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
            int totalHeight, requestedHeight = totalWidth * mCols / mRows;

            if(heightMode == MeasureSpec.AT_MOST) {
                final int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
                totalHeight = (requestedHeight < maxHeight) ? requestedHeight : maxHeight;
            } else {
                totalHeight = requestedHeight;
            }

            measureExact(totalWidth, totalHeight);
        } else if(heightMode == MeasureSpec.EXACTLY) {
            final int totalHeight = MeasureSpec.getSize(heightMeasureSpec);
            int totalWidth, requestedWidth = totalHeight * mRows / mCols;

            if(widthMode == MeasureSpec.AT_MOST) {
                final int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
                totalWidth = (requestedWidth < maxWidth) ? requestedWidth : maxWidth;
            } else {
                totalWidth = requestedWidth;
            }

            measureExact(totalWidth, totalHeight);
        } else {
            throw new RuntimeException("Nothing given exact, not supported");
         //   setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
           //         MeasureSpec.getSize(heightMeasureSpec));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        final int ph = (int) (mPercentPadding * (float)(r - l));
        final int pv = (int) (mPercentPadding * (float)(b - t));

        l += ph; r -= ph;
        t += pv; b -= pv;
        // l, r, t, b updated for padding

        final int cellWidth = (r - l) / mCols;
        final int cellHeight = (b - t) / mRows;

        int realCellLeft, realCellTop;
        int realCellWidth, realCellHeight;
        int viewLeft, viewTop, viewRight, viewBottom;

        for(int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();

            int row = (lp != null) ? lp.getRow() : 0;
            int column = (lp != null) ? lp.getCol() : 0;

            if (mBoardTransformer != null) {
                final int or = row; final int oc = column;
                row = mBoardTransformer.transformRow(or, oc);
                column = mBoardTransformer.transformCol(or, oc);
            }

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            CellParams cellParams = mCellParams[row][column];
            if (cellParams == null)
                cellParams = getDefaultCellParams();

            if (cellParams != null) {
                final int absPaddingLeft = (int) cellParams.absolutePaddingLeft(cellWidth);
                final int absPaddingTop = (int) cellParams.absolutePaddingTop(cellHeight);
                final int absPaddingRight = (int) cellParams.absolutePaddingRight(cellWidth);
                final int absPaddingBottom = (int) cellParams.absolutePaddingBottom(cellHeight);

                realCellLeft = l + column * cellWidth + absPaddingLeft;
                realCellTop = t + row * cellHeight + absPaddingTop;
                realCellWidth = cellWidth - absPaddingLeft - absPaddingRight;
                realCellHeight = cellHeight - absPaddingTop - absPaddingBottom;
            } else {
                realCellLeft = l + column * cellWidth;
                realCellTop = t + row * cellHeight;
                realCellWidth = cellWidth;
                realCellHeight = cellHeight;
            }

            if (realCellWidth < childWidth)
                childWidth = realCellWidth;
            if (realCellHeight < childHeight)
                childHeight = realCellHeight;

            if (lp != null) {
                float positionFrac = lp.getPositionFrac();
                if (mBoardTransformer != null)
                    positionFrac *= -1;
                final int horizontalCorrection = (int) (positionFrac * realCellWidth);
                final int verticalCorrection = (int) (positionFrac * realCellHeight);

                viewLeft = realCellLeft + horizontalCorrection;
                viewTop = realCellTop + verticalCorrection;
                viewRight = viewLeft + childWidth;
                viewBottom = viewTop + childHeight;
            } else {
                viewLeft = realCellLeft;
                viewTop = realCellTop;
                viewRight = realCellLeft + childWidth;
                viewBottom = realCellTop + childHeight;
            }

            child.layout(viewLeft, viewTop, viewRight, viewBottom);
        }
    }

    /**
     * {@code BoardLayout.LayoutParams} is used to position child views within
     * a {@link BoardLayout}.
     */
    public static class LayoutParams extends MarginLayoutParams {

        private int mRow;
        private int mCol;
        private float mPositionFrac;

        public LayoutParams(int row, int column) {
            super(0, 0);
            this.mRow = row;
            this.mCol = column;
        }

        /**
         * Returns the cell row for the view.
         *
         * @return cell row
         */
        public int getRow() {
            return mRow;
        }

        public void setRow(int row) {
            this.mRow = row;
        }

        /**
         * Returns the cell column for the view.
         *
         * @return cell column
         */
        public int getCol() {
            return mCol;
        }

        public void setCol(int col) {
            this.mCol = col;
        }

        /**
         * Returns the position-fraction for this view. The position fraction
         * is used to calculate the top-left corner's position with in the
         * board layout by these formulae:
         * <tt>left(view) = left(cell) + positionFrac * cellWidth</tt>
         * <tt>top(view) = top(cell) + positionFrac * cellHeight</tt>
         *
         * @return
         */
        public float getPositionFrac() {
            return mPositionFrac;
        }

        public LayoutParams setPositionFrac(float positionFrac) {
            mPositionFrac = positionFrac;
            return this;
        }
    }

    /**
     * {@code CellParams} should be used to set layout parameters for specific
     * cells. {@link BoardLayout} always lays fixed-size cells which are always
     * adjacent (no gaps), which can be overcome by position views outside one
     * cells (for example, on the border of two cells).
     *
     * It exposes padding properties (paddingLeft, paddingTop, paddingRight,
     * and paddingBottom which are absolute, and relativePadding which is
     * a percent of cell space to use for padding).
     */
    public static class CellParams {

        private int mPaddingLeft;
        private int mPaddingTop;
        private int mPaddingRight;
        private int mPaddingBottom;
        private float mRelativePadding;

        /**
         * Constructs this object with the given absolute paddings, but with no
         * relative padding property.
         *
         * @param paddingLeft padding on left border
         * @param paddingTop padding on top border
         * @param paddingRight padding on right border
         * @param paddingBottom padding on bottom border
         */
        public CellParams(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
            mPaddingLeft = paddingLeft;
            mPaddingTop = paddingTop;
            mPaddingRight = paddingRight;
            mPaddingBottom = paddingBottom;
            mRelativePadding = 0;
        }

        /**
         * Constructs this object with the given {@code relativePadding} value, but
         * with no absolute padding values.
         *
         * @param relativePadding see {@code getRelativePadding} for info
         */
        public CellParams(float relativePadding) {
            mPaddingLeft = 0;
            mPaddingTop = 0;
            mPaddingRight = 0;
            mPaddingBottom = 0;
            mRelativePadding = relativePadding;
        }

        /**
         * Returns the padding set on the left-border of the cell.
         *
         * @return paddingLeft property
         */
        public int getPaddingLeft() {
            return mPaddingLeft;
        }

        public CellParams setPaddingLeft(int paddingLeft) {
            mPaddingLeft = paddingLeft;
            return this;
        }

        /**
         * Returns the padding set on the top-border of the cell.
         *
         * @return paddingTop property
         */
        public int getPaddingTop() {
            return mPaddingTop;
        }

        public CellParams setPaddingTop(int paddingTop) {
            mPaddingTop = paddingTop;
            return this;
        }

        /**
         * Returns the padding set on the right-border of the cell.
         *
         * @return paddingRight property
         */
        public int getPaddingRight() {
            return mPaddingRight;
        }

        public CellParams setPaddingRight(int paddingRight) {
            mPaddingRight = paddingRight;
            return this;
        }

        /**
         * Returns the padding set on the bottom-border of the cell.
         *
         * @return paddingBottom property
         */
        public int getPaddingBottom() {
            return mPaddingBottom;
        }

        public CellParams setPaddingBottom(int paddingBottom) {
            mPaddingBottom = paddingBottom;
            return this;
        }

        /**
         * Returns the relative-padding set for this cell. The property
         * {@code relativeProperty} is the percent of the cell-dimensions
         * that are used for padding.
         *
         * @return relativePadding property
         */
        public float getRelativePadding() {
            return mRelativePadding;
        }

        public CellParams setRelativePadding(float relativePadding) {
            mRelativePadding = relativePadding;
            return this;
        }

        /**
         * Calculates the absolute value of the left-padding for this cell
         * which includes the relative-padding.
         *
         * @param cellWidth width measured for this cell
         * @return absolute left-padding
         */
        public float absolutePaddingLeft(float cellWidth) {
            return (float) getPaddingLeft() + cellWidth * getRelativePadding();
        }

        /**
         * Calculates the absolute value of the top-padding for this cell
         * which includes the relative-padding.
         *
         * @param cellHeight height meansured for this cell
         * @return absolute top-padding
         */
        public float absolutePaddingTop(float cellHeight) {
            return (float) getPaddingTop() + cellHeight * getRelativePadding();
        }

        /**
         * Calculates the absolute value of the right-padding for this cell
         * which includes the relative-padding.
         *
         * @param cellWidth width measured for this cell
         * @return absolute right-padding
         */
        public float absolutePaddingRight(float cellWidth) {
            return (float) getPaddingRight() + cellWidth * getRelativePadding();
        }

        /**
         * Calculates the absolute value of the bottom-padding for this cell
         * which includes the relative-padding.
         *
         * @param cellHeight height meansured for this cell
         * @return absolute bottom-padding
         */
        public float absolutePaddingBottom(float cellHeight) {
            return (float) getPaddingBottom() + cellHeight * getRelativePadding();
        }

    }
}
