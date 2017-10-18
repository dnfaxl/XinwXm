package com.edu.nbl.xinwxm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.edu.nbl.xinwxm.R;
import com.edu.nbl.xinwxm.activity.LoginActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class Mine extends Fragment {
    @BindView(R.id.civ)
    CircleImageView mCiv;
    @BindView(R.id.tvlogin)
    TextView mTvlogin;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View mView = inflater.inflate(R.layout.activity_mine, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.civ, R.id.tvlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ:
            case R.id.tvlogin:
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}

