package com.savills.praiapark.net;


import android.graphics.Color;

import com.blankj.utilcode.util.ToastUtils;
import com.savills.praiapark.R;
import com.savills.praiapark.bean.BaseEntity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {

    public BaseObserver() {

    }

    @Override
    public void onSubscribe(Disposable disposable) {
        RxNetManager.getInstance().addDisposable(disposable);
    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
        if (tBaseEntity.isSuccess()) {
            onSuccees(tBaseEntity);
        } else {
            onCodeError(tBaseEntity);
        }
        onComplete();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        toast(R.string.ConnectException);
        onComplete();
        onFailure(e);
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    /**
     * 返回成功
     *
     * @param t
     */
    protected abstract void onSuccees(BaseEntity<T> t);

    /**
     * 返回成功,但是code错误
     *
     * @param t
     */
    protected void onCodeError(final BaseEntity<T> t) {
        ToastUtils.setBgColor(Color.RED);
        ToastUtils.setMsgColor(Color.WHITE);
        ToastUtils.showShort(t.getErrorMsg());
        onFailure(null);
    }

    /**
     * 返回失败
     *
     * @param e
     */
    protected abstract void onFailure(Throwable e);

    protected abstract void onFinish();

    private void toast(int msg) {
        ToastUtils.setBgColor(Color.RED);
        ToastUtils.setMsgColor(Color.WHITE);
        ToastUtils.showShort(msg);
    }

}
