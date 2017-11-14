package com.m2049r.xmrwallet.login;


import com.m2049r.xmrwallet.BasePresenter;
import com.m2049r.xmrwallet.BaseView;

public interface LoginContract {
    interface Presenter extends BasePresenter {
        void onWalletSelected(final String name, final String address);
    }

    interface View extends BaseView<Presenter> {
        void showErrorWalletNetMismatch();
    }
}
