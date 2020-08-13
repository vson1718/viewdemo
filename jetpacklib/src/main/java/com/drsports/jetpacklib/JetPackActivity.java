package com.drsports.jetpacklib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.drsports.jetpacklib.databinding.ActivityJetpack0Binding;
import com.drsports.jetpacklib.model.Person;
import com.drsports.jetpacklib.model.User;


/**
 * @author vson
 */
public class JetPackActivity extends AppCompatActivity {

    private ActivityJetpack0Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_jetpack0);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jetpack0);
        User user = new User("vson", "123456");
        binding.setUser(user);
        Person person = new Person("jeet", "23");
        binding.setPerson(person);
        person.setName("haha");

        user.setName("thanks");
        binding.setVariable(BR.user,user);
//        new ViewPager().setOffscreenPageLimit();
    }
}
