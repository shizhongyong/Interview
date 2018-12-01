package com.shizy.interview.components.activity.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shizy.interview.R;

/**
 * 属性动画
 * ValueAnimator，需要使用AnimatorUpdateListener，刷新UI
 * ObjectAnimator, 自动更新属性
 * AnimatorSet
 */
public class PropertyAnimationFragment extends Fragment {

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_single:
                    singleObjectAnimator();
                    break;
                case R.id.btn_multi:
                    multiObjectAnimator();
                    break;
                case R.id.btn_view:
                    useViewPropertyAnimator();
                    break;
            }
        }
    };

    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_property_animation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButton = view.findViewById(R.id.button);
        view.findViewById(R.id.btn_single).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_multi).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_view).setOnClickListener(mOnClickListener);
    }

    private void multiObjectAnimator() {
        ObjectAnimator animX = ObjectAnimator.ofFloat(mButton, "x", 50f);
        ObjectAnimator animY = ObjectAnimator.ofFloat(mButton, "y", 100f);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.start();
    }

    private void singleObjectAnimator() {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", 50f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", 100f);
        ObjectAnimator.ofPropertyValuesHolder(mButton, pvhX, pvhY).start();
    }

    private void useViewPropertyAnimator() {
        mButton.animate().x(50f).y(100f);
    }

}
