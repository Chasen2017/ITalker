package com.chasen.italker.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

/**
 * 解决对Fragment的调度与重用问题
 * 达到最优的Fragment切换
 *
 * @author:Chasen
 * @data: on 18-8-19
 */

public class NavHelper<T> {

    private final SparseArray<Tab<T>> tabs = new SparseArray<>();
    // 用于初始化的一些参数
    private final Context context;
    private final int containerId;
    private final FragmentManager fragmentManager;
    private final OnTabChangedListener<T> listener;

    private Tab<T> currentTab;

    /**
     * 构造方法
     * @param context   Context
     * @param containerId 容器的id
     * @param fragmentManager FragmentManager
     * @param listener OnTabChangedListener
     */
    public NavHelper(Context context, int containerId, FragmentManager fragmentManager, OnTabChangedListener<T> listener) {
        this.context = context;
        this.containerId = containerId;
        this.fragmentManager = fragmentManager;
        this.listener = listener;
    }

    /**
     * 添加Tab
     *
     * @param menuId Tab对应的菜单Id
     * @param tab Tab
     * @return NavHelper
     */
    public NavHelper<T> add(int menuId, Tab<T> tab) {
        tabs.put(menuId, tab);
        return this;
    }

    public Tab<T> getCurrentTab() {
        return currentTab;
    }


    /**
     * 执行菜单点击的操作
     * @param menuId 菜单id
     * @return true表示消费掉
     */
    public boolean performClickMenu(int menuId) {
        // tab 不为空，执行点击后的操作
        Tab<T> tab = tabs.get(menuId);
        if (tab != null) {
            doSelect(tab);
            return true;
        }


        return false;
    }

    /**
     * 执行真实的tab选择操作
     *
     * @param tab  点击的Tab
     */
    private void doSelect(Tab<T> tab) {
        Tab<T> oldTab = null;
        if (currentTab != null) {
            oldTab = currentTab;
            // 重复点击了同一个tab
            if (oldTab == tab) {
                notifyTabReselect(tab);
                return;
            }
        }
        // 赋值并调用切换的方法
        currentTab = tab;
        doTabChanged(currentTab, oldTab);
    }

    private void notifyTabReselect(Tab<T> tab) {
        // TODO 二次点击tab之后的操作
    }

    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // 从界面移除，但是仍然存在缓存中
        if (oldTab != null) {
            if (oldTab.fragment != null) {
                ft.detach(oldTab.fragment);
            }
        }

        if (newTab != null) {
            if (newTab.fragment == null) {
                Fragment fragment = Fragment.instantiate(context, newTab.clx.getName(), null);
                newTab.fragment = fragment;
                ft.add(containerId, fragment, newTab.clx.getName());
            } else {
                ft.attach(newTab.fragment);
            }
        }
        ft.commit();
        notifyTabSelect(newTab, oldTab);
    }

    /**
     * 回调监听器
     * @param newTab 新的tab
     * @param oldTab 旧的tab
     */
    private void notifyTabSelect(Tab<T> newTab, Tab<T> oldTab) {
        if (listener != null) {
            listener.onTabChanged(newTab, oldTab);
        }
    }

    public static class Tab<T> {
        /**
         * Fragment对应的Class信息
         */
        public Class<?> clx;
        public T extra;
        /**
         * 缓存
         */
        Fragment fragment;

        public Tab(Class<?> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }
    }

    /**
     * tab切换的回调接口
     */
    public interface OnTabChangedListener<T> {
        /**
         * Tab切换完成后的回调
         * @param newTab 新的tab
         * @param oldTab 旧的tab
         */
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }



}
