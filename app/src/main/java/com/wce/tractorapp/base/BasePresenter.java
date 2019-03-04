package com.wce.tractorapp.base;

public interface BasePresenter<T extends BaseView> {

    void attach(T View);
    void detach();
}
