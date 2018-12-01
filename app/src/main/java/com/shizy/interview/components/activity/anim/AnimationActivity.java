package com.shizy.interview.components.activity.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.shizy.interview.R;
import com.shizy.interview.components.activity.BaseActivity;

public class AnimationActivity extends BaseActivity {

    private FragmentManager mFragmentManager;
    private Fragment mViewFragment;
    private Fragment mPropertyFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.animation_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.view:
                if (mViewFragment == null) {
                    mViewFragment = new ViewAnimationFragment();
                }
                fragment = mViewFragment;
                break;
            case R.id.property:
                if (mPropertyFragment == null) {
                    mPropertyFragment = new PropertyAnimationFragment();
                }
                fragment = mPropertyFragment;
                break;
        }
        if (fragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commitAllowingStateLoss();
        }
        return true;
    }
}
