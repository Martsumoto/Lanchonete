package com.marcelokmats.lanchonete.util;

import io.reactivex.disposables.Disposable;

public class RxUtils {

    public static void unsubscribe(Disposable subscription) {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }


    public static void unsubscribe(Disposable... subscriptions){
        for (Disposable subscription : subscriptions) {
            unsubscribe(subscription);
        }
    }
}
