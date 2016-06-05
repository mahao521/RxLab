package com.example.ysq.rxlab.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.fragment.MainFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new MainFragment()).commit();
    }

    public void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in_pop, R.anim.fragment_fade_out_pop)
                .replace(R.id.container, fragment)
                .commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
