package com.meanu.sijangseoul.Backdrop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.meanu.sijangseoul.Detail.DetailActivity;
import com.meanu.sijangseoul.R;
import com.meanu.sijangseoul.model.RetroPrice;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class BackdropFravorFragment extends android.support.v4.app.Fragment {

    @BindView(R.id.lv_fvproduct)
    ListView lv_fvproduct;
    private List<RetroPrice.Mgismulgainfo.row> dataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.backdrop_fv_fragment, container, false);
        ButterKnife.bind(this, view);

        SharedPreferences prefs = getContext().getSharedPreferences("text", MODE_PRIVATE);
        String json = prefs.getString("jsonMeanu", null);//If there is no YOURKEY found null will be the default value.

        RetroPrice retroPrice = new Gson().fromJson(json, RetroPrice.class);
        dataList = retroPrice.getMgismulgainfo().getRow();
        final ArrayList<String> fv = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (readStaeBoolean(dataList.get(i).getcOT_CONTS_NAME())) {
                fv.add(readStaeString(dataList.get(i).getcOT_CONTS_NAME()));
            }
        }

        ArrayAdapter simpleAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, fv);
        lv_fvproduct.setAdapter(simpleAdapter);

        lv_fvproduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getContext(), DetailActivity.class);
                for (int j = 0; j < dataList.size(); j++) {
                    if (dataList.get(j).getcOT_CONTS_NAME().equals(fv.get(i))) {
                        it.putExtra("key", dataList.get(j));
                    }
                }
                startActivity(it);
                //(2018.11.5) 구독 시장 구현

            }
        });
        return view;
    }


    private String readStaeString(String string) {
        SharedPreferences aSharedPreferenes = getContext().getSharedPreferences(
                string, MODE_PRIVATE);
        return aSharedPreferenes.getString("Name", null);
    }

    private boolean readStaeBoolean(String string) {
        SharedPreferences aSharedPreferenes = getContext().getSharedPreferences(
                string, MODE_PRIVATE);
        return aSharedPreferenes.getBoolean("State", false);
    }


}
