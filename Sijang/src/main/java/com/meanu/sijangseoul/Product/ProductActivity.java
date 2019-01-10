package com.meanu.sijangseoul.Product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.meanu.sijangseoul.Detail.DetailActivity;
import com.meanu.sijangseoul.R;
import com.meanu.sijangseoul.Search.SearchFragment;
import com.meanu.sijangseoul.Util.NavigationIconClickListener;
import com.meanu.sijangseoul.Util.PriceGenerator;
import com.meanu.sijangseoul.Util.RecyclerItemClickListener;
import com.meanu.sijangseoul.model.RetroPrice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity implements SearchFragment.OnPickerSetListener {
    private static final String TAG = "stelli";
    private RecyclerView mRecyclerView;
    private String sijangName;
    private RetroPrice retroPrice;
    private String guName;
    private double locationX;
    private double locationY;
    private List<RetroPrice.Mgismulgainfo.row> dataList;
    private List<RetroPrice.Mgismulgainfo.row> dataList2;
    private ProductAdapter adapter;
    @BindView(R.id.myCurruntLocation)
    TextView myCurruntLocation;
    NavigationIconClickListener navigationIconClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_grid_view);
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#e8d5c3"));
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findViewById(R.id.NestedScrollView).setBackground(Objects.requireNonNull(this).getDrawable(R.drawable.backdrop_shape));
        }

        ButterKnife.bind(this);

        initLayout();

        retroToRecycleView();
        setUpToolbar(dataList);
        int transition = getIntent().getIntExtra("transition", 0);
        transitionToSearch(transition);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        transition(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }


    public void transition(int position) {
        Intent it = new Intent(ProductActivity.this, DetailActivity.class);
        it.putExtra("key", dataList.get(position));
        startActivity(it);
    }

    private void transitionToSearch(int transition) {
        switch (transition) {
            case 0:
                FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
                fts.setCustomAnimations(R.anim.popshow, R.anim.slide_out_right, R.anim.popshow, R.anim.slide_out_right);
                fts.add(R.id.product_grid, new SearchFragment());
                fts.addToBackStack("frist");
                fts.commit();
                break;
            case 1:

                break;
        }
    }

    void retroToRecycleView() {

        SharedPreferences prefs = getSharedPreferences("text", MODE_PRIVATE);
        String json = prefs.getString("jsonMeanu", null);//If there is no YOURKEY found null will be the default value.
        final RetroPrice Price = new Gson().fromJson(json, RetroPrice.class);

        retroPrice = Price;
        dataList = retroPrice.getMgismulgainfo().getRow();
        dataList2 = retroPrice.getMgismulgainfo().getRow();
        generateDataList();

//        RetroPrice.Service service = PriceGenerator.createService(RetroPrice.Service.class);
//        Call<RetroPrice> call = service.getService(sijangName);
//        call.enqueue(new Callback<RetroPrice>() {
//            @Override
//            public void onResponse(Call<RetroPrice> call, Response<RetroPrice> response) {
//                // onresponse takes long
//                //check update
//
//                if (!Price.equals(response.body())) {
//                    String Pri = new Gson().toJson(response.body());
//                    create(getApplicationContext(), "pricesample.json", response.body().toString());
//                    Toast.makeText(getApplicationContext(), "update!", Toast.LENGTH_SHORT).show();
//                }
////
//
////                Log.d(TAG, "response.raw :" + response.raw());
////                if (response.body() != null) {
////                    retroPrice = response.body();
////                    dathaList = retroPrice.getMgismulgainfo().getRow();
////                    dataList2 = retroPrice.getMgismulgainfo().getRow();
////                    generateDataList();
////                } else {
////                    Log.d(TAG, "onResponse: NULL");
////                }
//            }
//
//            @Override
//            public void onFailure(Call<RetroPrice> call, Throwable t) {
//                myCurruntLocation.setText("네트워크를 연결하세요");
//
////                String json = test();
////                RetroPrice retroPrice = new Gson().fromJson(json, RetroPrice.class);
////                dataList = retroPrice.getMgismulgainfo().getRow();
////                dataList2 = retroPrice.getMgismulgainfo().getRow();
////                generateDataList();
//            }
//        });
    }

    String test() {
        String json = null;
        AssetManager assetManager = getApplicationContext().getAssets();
        InputStream is = null;
        try {
            is = assetManager.open("pricesample.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    private boolean create(Context context, String fileName, String jsonString) {
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }
    }

    private void generateDataList() {
        locationX = getIntent().getDoubleExtra("latitude", 126.977094419);//위도 = X
        locationY = getIntent().getDoubleExtra("longitude", 37.560236678);//경도 = Y , default 남대문시장
        adapter = new ProductAdapter(dataList, R.layout.product_card, locationX, locationY, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        getLocation();
    }

    private void initLayout() {
        mRecyclerView = findViewById(R.id.rvContact);
    }

    private void setUpToolbar(List<RetroPrice.Mgismulgainfo.row> dataList) {
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        navigationIconClickListener = new NavigationIconClickListener( this,
                getSupportFragmentManager(),
                findViewById(R.id.NestedScrollView),
                new AccelerateDecelerateInterpolator(),
                getDrawable(R.drawable.chevron_down), // Menu open icon
                getDrawable(R.drawable.chevron_up));
        toolbar.setNavigationOnClickListener(navigationIconClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public void onBackPressed() {
//        NavigationIconClickListener navigationIconClickListener = new NavigationIconClickListener(this,
//                getSupportFragmentManager(),
//                findViewById(R.id.NestedScrollView),
//                new AccelerateDecelerateInterpolator(),
//                getDrawable(R.drawable.chevron_down), // Menu open icon
//                getDrawable(R.drawable.chevron_up)));
//        navigationIconClickListener.



        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            if(navigationIconClickListener.animated()){
                navigationIconClickListener.updateFragment(null);
            } else{
                new AlertDialog.Builder(this)
                        .setTitle("종료")
                        .setMessage("종료하시겠습니까?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                ProductActivity.super.onBackPressed();
                            }
                        }).create().show();

            }
           }


    }

    @OnClick(R.id.NestedScrollView)
    void setNestedScrollView(){
        if(navigationIconClickListener.animated()){
            navigationIconClickListener.updateFragment(null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
                fts.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right);
                fts.add(R.id.product_grid, new SearchFragment());
                fts.addToBackStack("first");
                fts.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPickerSet(String sijangName, String guName, String dongName, double locationX, double locationY, int postion) {
        this.sijangName = sijangName;
        this.locationX = locationX;
        this.locationY = locationY;
        this.guName = guName;
        getLocation();
        myCurruntLocation.setText(guName + " " + dongName);
        mRecyclerView.smoothScrollToPosition(0);
    }

    public void getLocation() {
        Location locationA = new Location("point A");
        locationA.setLongitude(locationX);//x
        locationA.setLatitude(locationY);//y
        HashMap<Double, RetroPrice.Mgismulgainfo.row> d = new HashMap();
        Location locationB = new Location("B");
        for (int i = 0; i < dataList.size(); i++) {
            locationB.setLongitude(dataList.get(i).getcOT_COORD_X());
            locationB.setLatitude(dataList.get(i).getcOT_COORD_Y());
            double distance = locationA.distanceTo(locationB);
            d.put(distance, dataList.get(i));
        }
        TreeMap<Double, RetroPrice.Mgismulgainfo.row> tm = new TreeMap<>(d);
        Iterator<Double> iteratorKey = tm.keySet().iterator();
        dataList.clear();
        while (iteratorKey.hasNext()) {
            Double key = iteratorKey.next();
            RetroPrice.Mgismulgainfo.row row = tm.get(key);
            dataList.add(row);
//            Log.d(TAG, "거리 : " + key);
        }
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_falldown);
        mRecyclerView.setLayoutAnimation(animation);
        adapter.notifyDataSetChanged();
        dataList.addAll(dataList2);
    }
}