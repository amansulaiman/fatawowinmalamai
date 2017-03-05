package com.aman.fatawowin.malamai;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aman.fatawowin.malamai.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SendFatawaActivityFragment extends Fragment {

    public SendFatawaActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send_fatawa, container, false);
    }
}
