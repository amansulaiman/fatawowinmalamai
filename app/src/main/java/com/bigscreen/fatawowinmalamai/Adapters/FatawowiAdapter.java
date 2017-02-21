package com.bigscreen.fatawowinmalamai.Adapters;

import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.bigscreen.fatawowinmalamai.DummyData.DataServer;
import com.bigscreen.fatawowinmalamai.Entities.Fatawa;
import com.bigscreen.fatawowinmalamai.R;
import com.bigscreen.fatawowinmalamai.util.SpannableStringUtils;
import com.bigscreen.fatawowinmalamai.util.ToastUtils;
import com.bigscreen.fatawowinmalamai.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by aman on 2/16/17.
 */

public class FatawowiAdapter extends BaseQuickAdapter<Fatawa, BaseViewHolder> {


    public FatawowiAdapter(int layoutResId, List<Fatawa> data) {
        super(layoutResId, data);
    }

//    public FatawowiAdapter() {
//        super( R.layout.layout_animation, DataServer.getAllFatwa());
//    }

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            //ToastUtils.showShortToast("Malam Aminu Daurawa");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            //Utils.init(this);
            ds.setColor(Utils.getContext().getResources().getColor(R.color.clickspan_color));
            ds.setUnderlineText(false);
        }
    };


    @Override
    protected void convert(BaseViewHolder helper, final Fatawa item) {
        //helper.addOnClickListener(R.id.tweetName);
        //helper.addOnClickListener(R.id.tweetText);
        String intro = item.getJawabinTambaya().substring(0, Math.min(item.getJawabinTambaya().length(), 60));
        helper.setText(R.id.tweetName , item.getSunanTambaya());
        String msg="\""+intro+"\"... ";
        ( (TextView)helper.getView(R.id.tweetText)).setText(SpannableStringUtils.getBuilder(msg).append(item.getSunanMalami()).setClickSpan(clickableSpan).create());
        ( (TextView)helper.getView(R.id.tweetText)).setMovementMethod(LinkMovementMethod.getInstance());
        helper.setText(R.id.tweetDate, item.getLokacinTambaya().toString());
        //helper.linkify(R.id.tweetName);
    }


}
