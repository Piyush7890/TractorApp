package com.wce.tractorapp.base;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);
}
