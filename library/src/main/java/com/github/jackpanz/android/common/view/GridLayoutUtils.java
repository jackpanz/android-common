package com.github.jackpanz.android.common.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;

public class GridLayoutUtils {

    public static void fullGridLayout(Context context,
                                      GridLayout gridLayout,
                                      int rid,
                                      int column,
                                      int length) {
        if (length > 0 && length < column) {
            for (int i = 0; i < column - length; i++) {
                View view = LayoutInflater.from(context).inflate(rid, gridLayout, false);
                view.setVisibility(View.INVISIBLE);
                gridLayout.addView(view);
            }
        }
    }

}
