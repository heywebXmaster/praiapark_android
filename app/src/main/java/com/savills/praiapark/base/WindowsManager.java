package com.savills.praiapark.base;

/**
 * 窗口跳转管理类
 */
public class WindowsManager {

    private static WindowsManager instance;


    public synchronized static WindowsManager getInstance() {
        if (null == instance) {
            synchronized (WindowsManager.class) {
                if (null == instance) {
                    instance = new WindowsManager();
                }
            }
        }
        return instance;
    }



}
