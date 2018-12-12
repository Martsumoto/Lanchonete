package com.marcelokmats.lanchonete.sandwichDetail;

import dagger.Module;
import dagger.Provides;

@Module
public class SandwichDetailsModule {
    SandwichDetailsActivity mSandwichDetailsActivity;

    public SandwichDetailsModule(SandwichDetailsActivity activity) {
        this.mSandwichDetailsActivity = activity;
    }

    @Provides
    public SandwichDetailsPresenter movieDetailsPresenter() {
        return new SandwichDetailsPresenterImpl(mSandwichDetailsActivity);
    }
}
