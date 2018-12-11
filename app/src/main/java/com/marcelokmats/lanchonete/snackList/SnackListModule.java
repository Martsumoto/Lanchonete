package com.marcelokmats.lanchonete.snackList;

import dagger.Module;
import dagger.Provides;

@Module
public class SnackListModule {

    SnackListView mSnackListFragment;

    public SnackListModule(SnackListFragment snackListFragment) {
        this.mSnackListFragment = snackListFragment;
    }

    @Provides
    public SnackListPresenter snackListPresenter() {
        return new SnackListPresenterImpl(this.mSnackListFragment);
    }
}
