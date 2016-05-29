package com.example.ysq.rxlab.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.fragment.MainFragment;
import com.example.ysq.rxlab.fragment.YSFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IScrew {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    YSFragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new MainFragment()).commit();

    }

    public void startFragment(YSFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in_pop, R.anim.fragment_fade_out_pop)
                .replace(R.id.container, fragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mFragment != null) {
            getMenuInflater().inflate(mFragment.invaliMenu(), menu);
        } else {
            getMenuInflater().inflate(R.menu.black, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mFragment != null) {
            mFragment.invaliOptionSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void invalidateActionbar(YSFragment fragment) {
        mToolbar.setTitle(fragment.invaliTitle());
        mFragment = fragment;
        invalidateOptionsMenu();
    }
}
