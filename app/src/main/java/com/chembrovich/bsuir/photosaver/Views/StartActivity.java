package com.chembrovich.bsuir.photosaver.Views;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chembrovich.bsuir.photosaver.R;
import com.chembrovich.bsuir.photosaver.Views.LoginFragment;
import com.chembrovich.bsuir.photosaver.Views.ViewPagerAdapter;

public class StartActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new LoginFragment(),"Log In");
        viewPagerAdapter.addFragment(new RegistrationFragment(),"Register");
        viewPager.setAdapter(viewPagerAdapter);
    }
}
