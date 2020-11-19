package com.raykaco.andriod;

import java.util.ArrayList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.raykaco.andriod.database.BasketProductDB;
import com.raykaco.android.customcontrol.AdapterBasket;
import com.raykaco.android.customcontrol.StrucTopic;


public class BasketProduct extends Enhance {

    ListView                            lstProduct;
    public static ArrayList<StrucTopic> DataPouplar = new ArrayList<StrucTopic>();
    public static ArrayAdapter          adapterListProduct;
    ImageView                           im;
    RelativeLayout                      layBuy;
    public static TextView              txtTotalyPrice;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DataPouplar.clear();
        adapterListProduct.clear();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_root);
        menue();

        lstProduct = (ListView) findViewById(R.id.lst_product);
        txtTotalyPrice = (TextView) findViewById(R.id.txt_totaly_price);
        layBuy = (RelativeLayout) findViewById(R.id.lay_complete_buy);

        BasketProductDB basketProductDB = new BasketProductDB();
        long toalPrice = basketProductDB.selectAllPrice();
        txtTotalyPrice.setText("" + toalPrice);

        DataPouplar = basketProductDB.selectAllItem();
        Log.i("SSS", " " + DataPouplar.size());
        lstProduct.setVisibility(View.VISIBLE);

        layBuy.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startLogin();
            }
        });

        if (DataPouplar.size() == 0) {
            lstProduct.setVisibility(View.GONE);
            Log.i("SSS", " " + " DataPouplar.size()");

        } else {

            adapterListProduct = new AdapterBasket(DataPouplar);

            lstProduct.setAdapter(adapterListProduct);
        }
    }
}
