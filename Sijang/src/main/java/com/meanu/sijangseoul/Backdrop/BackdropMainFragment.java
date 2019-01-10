package com.meanu.sijangseoul.Backdrop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meanu.sijangseoul.R;
import com.meanu.sijangseoul.model.RetroPrice;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BackdropMainFragment extends android.support.v4.app.Fragment {
    private static String ARG_PARAM1 = "stelli";
    @BindView(R.id.backdrop_A)
    MaterialButton mMaterialButton1;
    private RetroPrice.Mgismulgainfo.row dataList;


    public static BackdropMainFragment newInstance(RetroPrice.Mgismulgainfo.row param1) {
        BackdropMainFragment fragment = new BackdropMainFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.backdrop, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            dataList = (RetroPrice.Mgismulgainfo.row) getArguments().getSerializable(ARG_PARAM1);
        }
        return view;
    }

    @OnClick({R.id.backdrop_A, R.id.backdrop_B, R.id.backdrop_C})
    void setBackdropClick(View view) {
        switch (view.getId()) {
            case R.id.backdrop_A:
                android.support.v4.app.FragmentTransaction ftsA = getActivity().getSupportFragmentManager().beginTransaction();
                ftsA.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                ftsA.replace(R.id.backdrop, new BackdropListFragment(), "BackdropAFragment");
                ftsA.addToBackStack("BackdropAFragment");
                ftsA.commit();
                break;
//            case R.id.backdrop_B:
//                android.support.v4.app.FragmentTransaction ftsB = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
//                ftsB.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                ftsB.replace(R.id.backdrop, new BackdropMeFragment(), "BackdropMeFragment");
//                ftsB.addToBackStack("BackdropMeFragment");
//                ftsB.commit();
//                break;
            case R.id.backdrop_C:
                android.support.v4.app.FragmentTransaction ftsC = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                ftsC.setCustomAnimations(R.anim.slideup, android.R.anim.fade_out);
                ftsC.replace(R.id.product_grid, new BackdropFravorFragment(), "BackdropFravorFragment");
                ftsC.addToBackStack("BackdropFravorFragment");
                ftsC.commit();
                break;
        }
    }
}
