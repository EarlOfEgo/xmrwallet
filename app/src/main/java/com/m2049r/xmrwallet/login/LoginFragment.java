package com.m2049r.xmrwallet.login;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.m2049r.xmrwallet.R;
import com.m2049r.xmrwallet.layout.DropDownEditText;
import com.m2049r.xmrwallet.layout.WalletInfoAdapter;
import com.m2049r.xmrwallet.model.WalletManager;
import com.m2049r.xmrwallet.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener {

    private static final String TAG = LoginFragment.class.getName();

    private LoginContract.Presenter presenter;
    private WalletInfoAdapter adapter;

    List<WalletManager.WalletInfo> walletList = new ArrayList<>();
    List<WalletManager.WalletInfo> displayedList = new ArrayList<>();

    EditText etDummy;
    ImageView ivGunther;
    DropDownEditText etDaemonAddress;
    ArrayAdapter<String> nodeAdapter;

    private boolean isFabOpen = false;
    private FloatingActionButton fab, fabNew, fabView, fabKey, fabSeed;
    private FrameLayout fabScreen;
    private RelativeLayout fabNewL, fabViewL, fabKeyL, fabSeedL;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward, fab_open_screen, fab_close_screen;
    private Animation fab_pulse;

    @Override
    public void setPresenter(final LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ivGunther = (ImageView) view.findViewById(R.id.ivGunther);
        fabScreen = (FrameLayout) view.findViewById(R.id.fabScreen);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fabNew = (FloatingActionButton) view.findViewById(R.id.fabNew);
        fabView = (FloatingActionButton) view.findViewById(R.id.fabView);
        fabKey = (FloatingActionButton) view.findViewById(R.id.fabKey);
        fabSeed = (FloatingActionButton) view.findViewById(R.id.fabSeed);

        fabNewL = (RelativeLayout) view.findViewById(R.id.fabNewL);
        fabViewL = (RelativeLayout) view.findViewById(R.id.fabViewL);
        fabKeyL = (RelativeLayout) view.findViewById(R.id.fabKeyL);
        fabSeedL = (RelativeLayout) view.findViewById(R.id.fabSeedL);

        fab_pulse = AnimationUtils.loadAnimation(getContext(), R.anim.fab_pulse);
        fab_open_screen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open_screen);
        fab_close_screen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close_screen);
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fabNew.setOnClickListener(this);
        fabView.setOnClickListener(this);
        fabKey.setOnClickListener(this);
        fabSeed.setOnClickListener(this);
        fabScreen.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        registerForContextMenu(recyclerView);
        //TODO
        final WalletInfoAdapter.OnInteractionListener adapterListener = getAdapterListener();
        this.adapter = new WalletInfoAdapter(getContext(), adapterListener);
        recyclerView.setAdapter(adapter);

        etDummy = (EditText) view.findViewById(R.id.etDummy);
        etDaemonAddress = (DropDownEditText) view.findViewById(R.id.etDaemonAddress);
        nodeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line);
        etDaemonAddress.setAdapter(nodeAdapter);

        Helper.hideKeyboard(getActivity());

        etDaemonAddress.setThreshold(0);
        etDaemonAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDaemonAddress.showDropDown();
                Helper.showKeyboard(getActivity());
            }
        });

        etDaemonAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && !getActivity().isFinishing() && etDaemonAddress.isLaidOut()) {
                    etDaemonAddress.showDropDown();
                    Helper.showKeyboard(getActivity());
                }
            }
        });

        etDaemonAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Helper.hideKeyboard(getActivity());
                    etDummy.requestFocus();
                    return true;
                }
                return false;
            }
        });

        etDaemonAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                Helper.hideKeyboard(getActivity());
                etDummy.requestFocus();

            }
        });

        //TODO -> presenter
//        loadPrefs();

        return view;
    }

    @Override
    public void showErrorWalletNetMismatch() {
        Toast.makeText(getActivity(), getString(R.string.prompt_wrong_net), Toast.LENGTH_LONG).show();
    }

    @NonNull
    private WalletInfoAdapter.OnInteractionListener getAdapterListener() {
        return new WalletInfoAdapter.OnInteractionListener() {

            @Override
            public void onInteraction(final View view, final WalletManager.WalletInfo item) {
                presenter.onWalletSelected(item.name, item.address);
            }

            @Override
            public boolean onContextInteraction(final MenuItem item,
                    final WalletManager.WalletInfo infoItem) {
                return false;
            }
        };
    }

    public boolean isFabOpen() {
        return isFabOpen;
    }

    public void animateFAB() {
        if (isFabOpen) {
            fabScreen.setVisibility(View.INVISIBLE);
            fabScreen.setClickable(false);
            fabScreen.startAnimation(fab_close_screen);
            fab.startAnimation(rotate_backward);
            fabNewL.startAnimation(fab_close);
            fabNew.setClickable(false);
            fabViewL.startAnimation(fab_close);
            fabView.setClickable(false);
            fabKeyL.startAnimation(fab_close);
            fabKey.setClickable(false);
            fabSeedL.startAnimation(fab_close);
            fabSeed.setClickable(false);
            isFabOpen = false;
        } else {
            fabScreen.setClickable(true);
            fabScreen.startAnimation(fab_open_screen);
            fab.startAnimation(rotate_forward);
            fabNewL.startAnimation(fab_open);
            fabNew.setClickable(true);
            fabViewL.startAnimation(fab_open);
            fabView.setClickable(true);
            fabKeyL.startAnimation(fab_open);
            fabKey.setClickable(true);
            fabSeedL.startAnimation(fab_open);
            fabSeed.setClickable(true);
            isFabOpen = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fabNew:
                fabScreen.setVisibility(View.INVISIBLE);
                isFabOpen = false;
                //TODO
//                activityCallback.onAddWallet(isTestnet(), GenerateFragment.TYPE_NEW);
                break;
            case R.id.fabView:
                animateFAB();
                //TODO
//                activityCallback.onAddWallet(isTestnet(), GenerateFragment.TYPE_VIEWONLY);
                break;
            case R.id.fabKey:
                animateFAB();
                //TODO
//                activityCallback.onAddWallet(isTestnet(), GenerateFragment.TYPE_KEY);
                break;
            case R.id.fabSeed:
                animateFAB();
                //TODO
//                activityCallback.onAddWallet(isTestnet(), GenerateFragment.TYPE_SEED);
                break;
            case R.id.fabScreen:
                animateFAB();
                break;
        }
    }
}
