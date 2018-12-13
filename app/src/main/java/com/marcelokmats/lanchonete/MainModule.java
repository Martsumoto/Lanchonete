package com.marcelokmats.lanchonete;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    MainActivity mMainActivity;

    public MainModule(MainActivity activity) {
        this.mMainActivity = activity;
    }

    @Provides
    public MainPresenter mainPresenter() {
        return new MainPresenterImpl(mMainActivity);
    }
}
