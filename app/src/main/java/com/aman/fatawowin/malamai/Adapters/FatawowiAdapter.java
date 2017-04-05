package com.aman.fatawowin.malamai.Adapters;

import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.aman.fatawowin.malamai.Entities.FatawaData;
import com.aman.fatawowin.malamai.R;
import com.aman.fatawowin.malamai.util.SpannableStringUtils;
import com.aman.fatawowin.malamai.util.Utils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cooltechworks.views.WhatsappViewCompat;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 * Created by Abdulrahman Sulaiman on 2/16/17.
 */

public class FatawowiAdapter extends CustomBaseQuickAdapter<FatawaData, BaseViewHolder> {



    public FatawowiAdapter(int layoutResId, LinkedList<FatawaData> data) {
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
    protected void convert(BaseViewHolder helper, final FatawaData item) {
        //helper.addOnClickListener(R.id.tweetName);
        //helper.addOnClickListener(R.id.tweetText);
        //TODO: Customization base if answered
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String date = format.format(item.getLokacinTambaya());
        String intro = item.getJawabinTambaya().substring(0, Math.min(item.getJawabinTambaya().length(), 60));
        helper.setText(R.id.tweetName , item.getSunanTambaya());
        String msg="\""+intro+"\"... ";
        ( (TextView)helper.getView(R.id.tweetText)).setText(SpannableStringUtils.getBuilder(msg).append(item.getSunanMalami()).setClickSpan(clickableSpan).create());
        ( (TextView)helper.getView(R.id.tweetText)).setMovementMethod(LinkMovementMethod.getInstance());
        helper.setText(R.id.tweetDate, date);
        //helper.linkify(R.id.tweetName);
        //WhatsappViewCompat.applyFormatting((TextView)helper.getView(R.id.tweetName));
        //WhatsappViewCompat.applyFormatting((TextView)helper.getView(R.id.tweetText));
    }

    @Override
    public void addData(FatawaData data) {
        super.addData(data);
    }
}
