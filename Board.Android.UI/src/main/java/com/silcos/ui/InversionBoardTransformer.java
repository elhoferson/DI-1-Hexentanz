package com.silcos.ui;

/**
 * Inverts the rows & cols of a view's position both horizontally and
 * vertically. This transformer can be used to show the board from the
 * opponent's perspective.
 *
 * The transformers properties invRows & invCols should reflect the
 * rows & cols of the BoardLayout. The default values of these properties
 * is 8 (and that can be changed).
 */
public class InversionBoardTransformer implements BoardTransformer {

    private static int mDefaultInvRows = 8;
    private static int mDefaultInvCols = 8;

    private final int mInvRows;
    private final int mInvCols;

    public InversionBoardTransformer() {
        mInvRows = mDefaultInvRows;
        mInvCols = mDefaultInvCols;
    }

    public InversionBoardTransformer(final int invRows, final int invCols) {
        mInvRows = invRows;
        mInvCols = invCols;
    }

    public int getInvRows() {
        return mInvRows;
    }

    public int getInvCols() {
        return mInvCols;
    }

    @Override
    public int transformRow(int r, int c) {
        return mInvRows - r;
    }

    @Override
    public int transformCol(int r, int c) {
        return mInvCols - c;
    }

    public static int getDefaultInvRows() {
        return mDefaultInvRows;
    }

    public static void setDefaultInvRows(int defaultInvRows) {
        mDefaultInvRows = defaultInvRows;
    }

    public static int getDefaultInvCols() {
        return mDefaultInvCols;
    }

    public static void setDefaultInvCols(int defaultInvCols) {
        mDefaultInvCols = defaultInvCols;
    }

}
