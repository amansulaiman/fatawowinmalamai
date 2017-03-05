package com.aman.fatawowin.malamai;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aman.fatawowin.malamai.Adapters.CustomBaseQuickAdapter;
import com.aman.fatawowin.malamai.Adapters.CustomOnItemClickListerner;
import com.aman.fatawowin.malamai.Adapters.FatawowiAdapter;
import com.aman.fatawowin.malamai.Entities.Fatawa;
import com.aman.fatawowin.malamai.Entities.FatawaData;
import com.aman.fatawowin.malamai.R;
import com.aman.fatawowin.malamai.loadmore.CustomLoadMore;
import com.aman.fatawowin.malamai.util.Utils;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.LinkedList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FatawowiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FatawowiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FatawowiFragment extends Fragment implements CustomBaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private FatawowiAdapter mFatawowiAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventLister;
    static boolean calledAlready = false;
    //private FirebaseListAdapter firebaseListAdapter;
    private int delayMillis = 1000;
    private static final int RC_SIGN_IN_TAMBAYA = 8;
    private boolean isErr;
    private boolean mLoadMoreEndGone = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FatawowiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FatawowiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FatawowiFragment newInstance(String param1, String param2) {
        FatawowiFragment fragment = new FatawowiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    private void addHeadView() {
        View headView = getActivity().getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
        headView.findViewById(R.id.iv).setVisibility(View.GONE);
        ((TextView) headView.findViewById(R.id.tv)).setText("Fatawowin Malamai");
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadMoreEndGone = true;
                mFatawowiAdapter.setLoadMoreView(new CustomLoadMore());
                mRecyclerView.setAdapter(mFatawowiAdapter);
                Toast.makeText(getActivity(), "Duba Fatawoin Malamai", Toast.LENGTH_LONG).show();
            }
        });
        mFatawowiAdapter.addHeaderView(headView);
    }

    private void initAdapter() {
        final LinkedList<FatawaData> fatawowi = new LinkedList<>();
        mFatawowiAdapter = new FatawowiAdapter( R.layout.layout_animation, fatawowi);
        mFatawowiAdapter.setLoadMoreView(new CustomLoadMore());
        mFatawowiAdapter.setOnLoadMoreListener(this);
        mFatawowiAdapter.openLoadAnimation(CustomBaseQuickAdapter.SLIDEIN_LEFT);
        //pullToRefreshAdapter.setAutoLoadMoreSize(3);
        mRecyclerView.setAdapter(mFatawowiAdapter);

        mRecyclerView.addOnItemTouchListener(new CustomOnItemClickListerner() {
            @Override
            public void onSimpleItemClick(CustomBaseQuickAdapter adapter, View view, int position) {
                Intent detailFatwa = new Intent(getActivity().getApplicationContext(), FatawaDetailActivity.class);
                FatawaData selected = mFatawowiAdapter.getItem(position);
                detailFatwa.putExtra("FatawaData", selected);
                startActivity(detailFatwa);
            }
        });

//        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener( ) {
//            @Override
//            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(getActivity(), "Child Item" + Integer.toString(position), Toast.LENGTH_SHORT).show();
//            }
//        });
//        if (mMessageDatabaseReference.getRef() == null)
//        {
            //TODO: Initialize Data
//        for (int i = 0; i < 10; i++) {
//            Fatawa fatawa = new Fatawa();
//            fatawa.setSunanMalami("Mal. Ibrahim Khalil");
//            fatawa.setAnYijawabi(true);
//            fatawa.setJawabinMalami("Ya halatta yin amfani da yanar gizo gizo don samun bayanai na addini da na rayuwa musamman idan an yi kyaykyar amfani da ita wannan kafar");
//            fatawa.setJawabinTambaya("Malam me ne hukuncin yin amfani da internet don karartun addini");
//            fatawa.setLokacinTambaya(new Date());
//            fatawa.setMaiTambaya("Abdulrahman Sulaiman");
//            fatawa.setSunanTambaya("Hukuncin yin amfani da Internet");
//            mMessageDatabaseReference.push().setValue(fatawa);

//
//        }

        //}
        //attach data
        attacheDatabaseListner();
    }

    private void attacheDatabaseListner() {
        if (mChildEventLister == null) {
            mChildEventLister = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Fatawa fatawa = dataSnapshot.getValue(Fatawa.class);
                    String key = dataSnapshot.getKey();
                    Log.w(s, key);
                    FatawaData fatawaData = new FatawaData(fatawa.isAnYijawabi(),fatawa.getJawabinMalami(), fatawa.getSunanTambaya(), fatawa.getJawabinTambaya(), fatawa.getMaiTambaya(), fatawa.getLokacinTambaya(), fatawa.getSunanMalami(), fatawa.getMalamiID(), key);
                    mFatawowiAdapter.addData(fatawaData);
                    mFatawowiAdapter.notifyDataSetChanged();
                    onLoadMoreRequested();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    //onRefresh();
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    //onRefresh();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    //onRefresh();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

                @Override
                protected void finalize() throws Throwable {
                    super.finalize();
                }
            };
            mMessageDatabaseReference.orderByValue().addChildEventListener(mChildEventLister);
        }
    }

    private  void detachedDatabaseListener(){
        if (mChildEventLister != null) {
            mMessageDatabaseReference.removeEventListener(mChildEventLister);
            mChildEventLister = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (!calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("fatawowi");
        mMessageDatabaseReference.keepSynced(true);

        View mView = inflater.inflate(R.layout.fragment_fatawowi, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(FatawowiFragment.this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //getActivity().setTitle("Pull TO Refresh Use");
        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.user == null)
                {
                    Toast.makeText(getActivity(), "Kayi haquri ina bukatar ka shigar da bayanaka", Toast.LENGTH_LONG).show();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                                    .setTheme(R.style.GreenTheme)
                                    .setLogo(R.mipmap.ic_launcher)

                                    .build(),
                            RC_SIGN_IN_TAMBAYA);
                }else
                {
                    sendTambaya();
                }

            }
        });


        Utils.init(getActivity());
        initAdapter();
        addHeadView();
        return mView;
    }

    private void sendTambaya() {
        Intent sendTambaya = new Intent(getActivity().getApplicationContext(), SendFatawaActivity.class);
        startActivity(sendTambaya);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        //attacheDatabaseListner();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
        //detachedDatabaseListener();
        //mFatawowiAdapter = null;
    }


    @Override
    public void onPause() {
        super.onPause();
        //detachedDatabaseListener();
        //mFatawowiAdapter = null;

    }
//
    @Override
    public void onResume() {
        super.onResume();
        //attacheDatabaseListner();
    }


    @Override
    public void onRefresh() {
        mFatawowiAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                detachedDatabaseListener();
                LinkedList<FatawaData> fatawaList = new LinkedList<FatawaData>();
                mFatawowiAdapter.setNewData(fatawaList);
                attacheDatabaseListner();
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                mFatawowiAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }



    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mFatawowiAdapter.getData().size() == mRecyclerView.getChildCount()) {
                    mFatawowiAdapter.loadMoreEnd(true);
                } else {
                    mFatawowiAdapter.loadMoreEnd();//default visible
                    mFatawowiAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
                    //mFatawowiAdapter.loadMoreComplete();
                    mSwipeRefreshLayout.setEnabled(true);

                }
            }

        }, delayMillis);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN_TAMBAYA)
        {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(getActivity(), "Na kammala shigar da baynanka", Toast.LENGTH_SHORT).show();
                sendTambaya();
            }else  if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(getActivity(), "Ka dakatar da shigarwa", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
