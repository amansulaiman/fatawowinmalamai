package com.aman.fatawowin.malamai.Adapters;

import android.view.View;


/**
 * Created by Abdulrahman Sulaiman on 2/23/17.
 */

public abstract class CustomOnItemClickListerner extends CustomSimpleClickListner {
    @Override
    public void onItemClick(CustomBaseQuickAdapter adapter, View view, int position) {
        onSimpleItemClick(adapter,view,position);
    }

    @Override
    public void onItemLongClick(CustomBaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(CustomBaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(CustomBaseQuickAdapter adapter, View view, int position) {

    }
    public abstract void onSimpleItemClick(CustomBaseQuickAdapter adapter, View view, int position);
}
