
package com.smac0.akqaconnectedhome.fragments.presenters;

import com.google.inject.Inject;
import com.smac0.akqaconnectedhome.R;
import com.smac0.akqaconnectedhome.fragments.views.BathView;
import com.smac0.akqaconnectedhome.model.BathWaterTemperature;
import com.smac0.akqaconnectedhome.restservices.BathService;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

import static rx.android.app.AppObservable.bindFragment;

/**
 * Presenter object responsible for interacting with the Bath server
 * to fetch the temperature values asynchronously and providing it to the
 * the BathWaterFragment for rendering its view.
 */
public class BathWaterFragmentPresenter implements Presenter,
        Observer<BathWaterTemperature> {

    @Inject
    BathService mBathService;

    private BathView mBathView;
    private Subscription mSubscription = Subscriptions.empty();
    private Observable<BathWaterTemperature> mRequestObservable;

    public void setView(BathView bathView) {
        mBathView = bathView;
    }

    public void getBathTemperature() {
        mBathView.showProgress();
        mRequestObservable = bindFragment(mBathView.getFragment(),
                mBathService.getBathTemperature())
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .cache();
        subscribe();
    }

    @Override
    public void onCreate() {
        subscribe();
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
        unsubscribe();
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable throwable) {
        mBathView.hideProgress();
        mBathView.showWaterTemperatureFetchError(mBathView.getContext().getString(
                R.string.error_bath_temperature_failure));
    }

    @Override
    public void onNext(BathWaterTemperature bathWaterTemperature) {
        mBathView.setWaterTemperature(bathWaterTemperature);
        mBathView.hideProgress();
    }

    private void subscribe() {
        if (mRequestObservable != null) {
            mSubscription = mRequestObservable.subscribe(this);
        }
    }

    private void unsubscribe() {
        if (mRequestObservable != null) {
            mSubscription.unsubscribe();
        }
    }
}
