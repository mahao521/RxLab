package com.example.ysq.rxlab.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.fragment.MainFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IScrew {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    int menuID = R.menu.black;

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
    public void invalidateActionBar(String title, int menuID) {
        mToolbar.setTitle(title);
        this.menuID = menuID;
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.black, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
