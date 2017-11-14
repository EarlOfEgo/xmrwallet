package com.m2049r.xmrwallet.login;


import android.support.annotation.NonNull;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;

    public LoginPresenter(@NonNull final LoginContract.View loginView) {
        mView = loginView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onWalletSelected(final String name, final String address) {
        String x = isTestnet() ? "9A-" : "4-";
        if (address == null || address.length() <= 0 || x.indexOf(address.charAt(0)) < 0) {
            mView.showErrorWalletNetMismatch();
            //TODO
        } else {
//        if (activityCallback.onWalletSelected(infoItem.name, getDaemon(), isTestnet())) {
//            savePrefs();
//        }
        }
    }

    //TODO
    private boolean isTestnet() {
        return false;
    }
}
