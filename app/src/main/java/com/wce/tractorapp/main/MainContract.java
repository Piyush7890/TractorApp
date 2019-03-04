package com.wce.tractorapp.main;

import com.wce.tractorapp.base.BasePresenter;
import com.wce.tractorapp.base.BaseView;

public interface MainContract {
    interface MainPresenter extends BasePresenter<MainView>
    {
        void login();
    }

    interface MainView extends BaseView<MainPresenter>
    {
        void showLoading();
        void hideLoading();
        void showError();
    }
}
