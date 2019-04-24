package com.silcos.ui;

/**
 * A transformer produces new UI coordinates for a view at the
 * time of positioning for a BoardLayout. This can be used to
 * reflect changes in the user's perspective (like viewing from
 * opponent's side via the {@link InversionBoardTransformer}).
 */
public interface BoardTransformer {
    public int transformRow(int r, int c);
    public int transformCol(int r, int c);
}
