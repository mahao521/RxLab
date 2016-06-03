package com.example.ysq.rxlab.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.activity.IScrew;

/**
 * 作者：ysq
 * 时间：2016/5/27
 */

public class YSFragment extends Fragment {


    IScrew screw;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        screw = (IScrew) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        screw.invalidateActionbar(this);
    }


    public int invaliMenu() {
        return R.menu.black;
    }

    public void invaliOptionSelected(MenuItem item) {
    }

    public String invaliTitle() {
        return getClass().getSimpleName();
    }
}
