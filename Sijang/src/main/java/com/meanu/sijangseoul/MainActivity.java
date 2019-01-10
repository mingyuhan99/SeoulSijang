package com.meanu.sijangseoul;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.meanu.sijangseoul.Product.ProductActivity;
import com.meanu.sijangseoul.Util.PriceGenerator;
import com.meanu.sijangseoul.model.RetroPrice;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public final static int MY_PERMISSIONS_REQUEST_READ_LOCATION = 1;
    @BindView(R.id.floatingButton)
    FloatingActionButton floatingButton;
    private static final String TAG = "stelli";
    @BindView(R.id.buttonToResult)
    Button buttonToResult;
    LocationManager locationManager;
    private double latitude;
    private double longitude;

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
            findViewById(R.id.first_container).setBackground(Objects.requireNonNull(this).getDrawable(R.drawable.backdrop_shape));
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            GetLocationPermission();
            return;
        } else {
        }
        updateJson("");


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

    private boolean delete(Context context, String fileName, String jsonString) {
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            if (jsonString != null) {
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }


    void updateJson(String sijangName) {
        RetroPrice.Service service = PriceGenerator.createService(RetroPrice.Service.class);
        Call<RetroPrice> call = service.getService(sijangName);
        call.enqueue(new Callback<RetroPrice>() {
            @Override
            public void onResponse(Call<RetroPrice> call, Response<RetroPrice> response) {
                String result = new Gson().toJson(response.body());
                SharedPreferences.Editor editor = getSharedPreferences("text", MODE_PRIVATE).edit();
                editor.putString("jsonMeanu", result);
                editor.commit();


                SharedPreferences prefs = getSharedPreferences("text", MODE_PRIVATE);
                String name = prefs.getString("jsonMeanu", null);//If there is no YOURKEY found null will be the default value.
                // onresponse takes long
                //check update
//
//                RetroPrice retroPrice;
//                List<RetroPrice.Mgismulgainfo.row> dataList;
//                List<RetroPrice.Mgismulgainfo.row> dataList2;
//                retroPrice = response.body();
//                dataList = Price.getMgismulgainfo().getRow();
//                dataList2 = retroPrice.getMgismulgainfo().getRow();
//                String p = new Gson().toJson(retroPrice, RetroPrice.class);
////                String d = new Gson().toJson(response, RetroPrice.class);
//                String a = new Gson().toJson(response);
//                String o = new Gson().toJson(retroPrice);
//
//
//                if (!dataList.get(0).getcOT_UPDATE_DATE().equals(dataList2.get(0).getcOT_UPDATE_DATE())) {
//                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//                    editor.putString("YOURKEY",   );
//                    editor.commit();
//                    create(getApplicationContext(), "pricesample.json", o);
//
//                    Toast.makeText(getApplicationContext(), "update!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "already!", Toast.LENGTH_SHORT).show();
//
//                }
////

//                Log.d(TAG, "response.raw :" + response.raw());
//                if (response.body() != null) {
//                    retroPrice = response.body();
//                    dathaList = retroPrice.getMgismulgainfo().getRow();
//                    dataList2 = retroPrice.getMgismulgainfo().getRow();
//                    generateDataList();
//                } else {
//                    Log.d(TAG, "onResponse: NULL");
//                }
            }

            @Override
            public void onFailure(Call<RetroPrice> call, Throwable t) {
//                myCurruntLocation.setText("네트워크를 연결하세요");

//                String json = test();
//                RetroPrice retroPrice = new Gson().fromJson(json, RetroPrice.class);
//                dataList = retroPrice.getMgismulgainfo().getRow();
//                dataList2 = retroPrice.getMgismulgainfo().getRow();
//                generateDataList();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                0,
                                0,
                                this);
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                0,
                                0,
                                this);
                    }

                } else {
                    Log.d(TAG, "onRequestPermissionsResult: ");
                    Process.killProcess(android.os.Process.myPid());
                }
                return;
            }

        }
    }

    @OnClick(R.id.buttonToResult)
    void GoSearchFragment(View v) {
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        intent.putExtra("transition", 0);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
        this.finish();
    }

    @OnClick(R.id.floatingButton)
    void GoProductGirdFragment(View v) {
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        intent.putExtra("transition", 1);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
        this.finish();
    }

    public void GetLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_LOCATION);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
