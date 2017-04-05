package com.aman.fatawowin.malamai;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aman.fatawowin.malamai.Entities.Fatawa;
import com.aman.fatawowin.malamai.R;
import com.cooltechworks.views.WhatsAppEditText;
import com.cooltechworks.views.WhatsappViewCompat;
import com.emmasuzuki.easyform.EasyTextInputLayout;
import com.firebase.ui.auth.ui.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class SendFatawaActivityFragment extends Fragment {
    private Button mButton;
    private EasyTextInputLayout tambaya;
    private EasyTextInputLayout bayani;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    public SendFatawaActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_send_fatawa, container, false);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("fatawowi");
        tambaya = (EasyTextInputLayout) mView.findViewById(R.id.gundari);
        bayani = (EasyTextInputLayout) mView.findViewById(R.id.bayani);
        //WhatsappViewCompat.applyFormatting(tambaya.getEditText());
        //WhatsappViewCompat.applyFormatting(bayani.getEditText());
        mButton = (Button) mView.findViewById(R.id.submit_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fatawa fatawa = new Fatawa();
                fatawa.setAnYijawabi(false);
                fatawa.setJawabinTambaya(bayani.getEditText().getText().toString());
                fatawa.setLokacinTambaya(new Date());
                fatawa.setMaiTambaya(user.getUid());
                fatawa.setSunanTambaya(tambaya.getEditText().getText().toString());
                fatawa.setJawabinMalami("Ina jiran wani Malami ya amsa");
                fatawa.setMalamiID("ba komai");
                fatawa.setSunanMalami("Ba Malamain da ya amsa");
                mMessageDatabaseReference.push().setValue(fatawa);
                getActivity().finish();
            }
        });
        return mView;
    }
}
