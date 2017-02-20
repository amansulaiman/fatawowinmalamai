package com.bigscreen.fatawowinmalamai.loadmore;

import com.bigscreen.fatawowinmalamai.R;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * Created by Abdulrahman Sulaiman on 2/16/17.
 */

public class CustomLoadMore extends LoadMoreView {
    @Override public int getLayoutId() {
        return R.layout.view_loadmore;
    }

    @Override protected int getLoadingViewId() {
        return R.id.load_more_loading_view_;
    }

    @Override protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view_;
    }

    @Override protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view_;
    }
}
