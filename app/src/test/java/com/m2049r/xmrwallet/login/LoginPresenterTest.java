package com.m2049r.xmrwallet.login;


import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LoginPresenterTest {
    @Mock
    private LoginContract.View mockView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    public void tearDown() throws Exception {
    }

    @Test
    public void onSelectWallet_doesNotMatchNet_shouldShowError() {
        LoginPresenter loginPresenter = new LoginPresenter(mockView);
        loginPresenter.onWalletSelected("", "0");
        verify(mockView).showErrorWalletNetMismatch();
    }

}