package com.example.building.net;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * rx请求异步管理类
 */
public class RxNetManager {
    private static RxNetManager instance;
    protected CompositeDisposable mDisposables;

    public synchronized static RxNetManager getInstance() {
        if (null == instance) {
            synchronized (RxNetManager.class) {
                if (null == instance) {
                    instance = new RxNetManager();
                }
            }
        }
        return instance;
    }

    public void addDisposable(Disposable disposable) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    public void cancelAllDisposable() {
        if (mDisposables != null && mDisposables.size() > 0) {
            mDisposables.clear();
        }
    }
}
