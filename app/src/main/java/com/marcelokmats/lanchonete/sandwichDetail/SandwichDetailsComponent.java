package com.marcelokmats.lanchonete.sandwichDetail;

import dagger.Component;

@Component(modules = {SandwichDetailsModule.class})
public interface SandwichDetailsComponent {
    void injectSandwichDetailsPresenter(SandwichDetailsActivity activity);
}
