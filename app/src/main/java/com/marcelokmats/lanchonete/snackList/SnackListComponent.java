package com.marcelokmats.lanchonete.snackList;

import dagger.Component;

@Component(modules = {SnackListModule.class})
public interface SnackListComponent {
    void injectSnackList(SnackListFragment snackListFragment);

}
