package com.drsports.jetpacklib.model;

import androidx.databinding.BaseObservable;

/**
 * @author vson
 * @date 2020/7/6
 * Company:上海动博士企业发展有限公司
 * E-mail :vson1718@163.com
 * 项目描述:
 */
public class Person  extends BaseObservable {

    private String name;

    private String age;

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
