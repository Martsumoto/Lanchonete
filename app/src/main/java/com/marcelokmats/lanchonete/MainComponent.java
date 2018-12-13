package com.marcelokmats.lanchonete;

import dagger.Component;

@Component(modules = {MainModule.class})
public interface MainComponent {
    void injectMainPresenter(MainActivity activity);
}
