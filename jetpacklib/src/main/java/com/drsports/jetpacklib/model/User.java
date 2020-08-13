package com.drsports.jetpacklib.model;

import android.database.Observable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.drsports.jetpacklib.BR;

/**
 * @author vson
 * @date 2020/7/6
 * Company:上海动博士企业发展有限公司
 * E-mail :vson1718@163.com
 * 项目描述:
 */
public class User extends BaseObservable {


    private String name;

    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
