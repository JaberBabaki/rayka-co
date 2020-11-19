package com.raykaco.andriod;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.raykaco.andriod.internet.Connectivity;
import com.raykaco.andriod.internet.StructSlider;
import com.raykaco.andriod.internet.WebServiceUrl;
import com.raykaco.android.customcontrol.AESCrypt;
import com.raykaco.android.customcontrol.AdapterCommodityList;
import com.raykaco.android.customcontrol.CustomLoading;
import com.raykaco.android.customcontrol.CustomLoadingIndicator;
import com.raykaco.android.customcontrol.ImagePagerAdapter;


public class ActivityRaykaCo extends Enhance {

    CustomLoadingIndicator             indicator;
    ViewPager                          pager;
    ArrayList<StructSlider>            imageIds           = new ArrayList<StructSlider>();

    ArrayList<StrucItem>               Data               = new ArrayList<StrucItem>();

    Button                             btnGoHelp;

    ListView                           lstProduct;

    ViewGroup                          header;

    int                                ProductNew         = 1;
    int                                ProductDiscount    = 0;
    int                                StoreNew           = 0;

    ArrayAdapter                       adapterListNew;
    ArrayAdapter                       adapterListDiscount;
    ArrayAdapter                       adapterListPouplar;

    public static ArrayList<StrucItem> DataNew            = new ArrayList<StrucItem>();
    public static int                  getCounterNew      = 0;

    public static ArrayList<StrucItem> DataDiscount       = new ArrayList<StrucItem>();
    public static int                  getCounterDiscount = 0;

    public static ArrayList<StrucItem> DataPouplar        = new ArrayList<StrucItem>();
    public static int                  getCounterPouplar  = 0;

    int                                lastItemPosition   = 0;
    int                                l                  = 0;

    LinearLayout                       layLoading;
    // LinearLayout                       layLoadingSection;
    // RelativeLayout                     layList;

    CustomLoading                      loading;


    public void testEncryptDecrypt() {

        String password = "password";
        String message = "hello world";

        if (BuildConfig.DEBUG) {
            AESCrypt.DEBUG_LOG_ENABLED = true;
        }

        String encryptedMsg = null;
        try {
            encryptedMsg = AESCrypt.encrypt(password, message);
        }
        catch (GeneralSecurityException e) {
            // fail("error occurred during encrypt");
            e.printStackTrace();
        }

        String messageAfterDecrypt = null;
        try {
            messageAfterDecrypt = AESCrypt.decrypt(password, encryptedMsg);

        }
        catch (GeneralSecurityException e) {
            // fail("error occurred during Decrypt");
            e.printStackTrace();
        }

        if ( !messageAfterDecrypt.equals(message)) {
            //fail("messages don't match after encrypt and decrypt");
        }
    }


    public void testEncryt() {

        String password = "password";
        String message = "hello world";

        try {
            String encryptedMsg = AESCrypt.encrypt(password, message);
            Log.i("ENC", encryptedMsg);

        }
        catch (GeneralSecurityException e) {
            //handle error

            // fail("error occurred during encrypt");
            e.printStackTrace();
        }
    }


    public void testDecrpyt() {

        String password = "passwrd";
        String encryptedMsg = "2B22cS3UC5s35WBihLBo8w==";

        try {

            String messageAfterDecrypt = AESCrypt.decrypt(password, encryptedMsg);
            Log.i("ENC", messageAfterDecrypt);
        }
        catch (GeneralSecurityException e) {
            // fail("error occurred during Decrypt");
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        menue();
        G.currentActivity = this;
        //  Data.clear();
        //imageIds.clear();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_root);
        menue();

        // testEncryt();
        // testDecrpyt();
        // a.a = "";
        lstProduct = (ListView) findViewById(R.id.lst_product);
        header = (ViewGroup) G.inflater.inflate(R.layout.head, lstProduct, false);

        final Button btnNew = (Button) header.findViewById(R.id.btn_new);
        final Button btnPopular = (Button) header.findViewById(R.id.btn_popular);
        final Button btnDiscount = (Button) header.findViewById(R.id.btn_discount);

        layLoading = (LinearLayout) findViewById(R.id.lay_loading);
        //layList = (RelativeLayout) findViewById(R.id.lay_list);
        loading = (CustomLoading) findViewById(R.id.txt_waite_main);

        btnNew.setTypeface(G.font1);
        btnPopular.setTypeface(G.font1);
        btnDiscount.setTypeface(G.font1);

        btnNew.setBackgroundResource(R.drawable.button_send_corner_select);
        btnNew.setTextColor(Color.parseColor("#1A75BC"));

        final Connectivity Check = new Connectivity();
        if (Check.isConnected(G.context)) {
            conected();
            layLoading.setVisibility(View.VISIBLE);
            layMain.setVisibility(View.GONE);

            imageIds = new ArrayList<StructSlider>();
            reciveSlider();

            /*  int imageId = R.drawable.sli1;
              int imageId2 = R.drawable.sli2;
              imageIds.add(imageId);
              imageIds.add(imageId2);

              ImagePagerAdapter adapter = new ImagePagerAdapter(imageIds);
              pager.setAdapter(adapter);
              indicator.setIndicatorsCount(imageIds.size());

              pager.setOnPageChangeListener(new OnPageChangeListener() {

                  @Override
                  public void onPageSelected(int index) {}


                  @Override
                  public void onPageScrolled(int startIndex, float percent, int pixel) {
                      indicator.setPercent(percent);
                      Log.i("LOG", "Percent: " + startIndex + ", " + percent);
                      indicator.setCurrentPage(startIndex);
                  }


                  @Override
                  public void onPageScrollStateChanged(int arg0) {

                  }
              });*/

            reciveNewProduct("read", Integer.toString(getCounterNew), G.countyRecive);
            getCounterNew++;
            ProductNew = 1;
            adapterListNew = new AdapterCommodityList(DataNew);
            lstProduct.addHeaderView(header, null, false);
            lstProduct.setAdapter(adapterListNew);
            adapterListNew.notifyDataSetChanged();
            lstProduct.setTextFilterEnabled(true);

            btnNew.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (ProductNew == 0) {

                        ProductNew = 1;
                        //layLoadingSection.setVisibility(View.VISIBLE);
                        //  layList.setVisibility(View.GONE);

                        getCounterNew = 0;
                        DataNew.clear();
                        reciveNewProduct("read", Integer.toString(getCounterNew), G.countyRecive);

                        btnNew.setBackgroundResource(R.drawable.button_send_corner_select);
                        btnNew.setTextColor(Color.parseColor("#1A75BC"));

                        DataDiscount.clear();
                        btnDiscount.setBackgroundResource(R.drawable.button_send_corner);
                        btnDiscount.setTextColor(Color.parseColor("#ffffff"));
                        StoreNew = 0;

                        DataPouplar.clear();
                        btnPopular.setBackgroundResource(R.drawable.button_send_corner);
                        btnPopular.setTextColor(Color.parseColor("#ffffff"));
                        ProductDiscount = 0;

                        //  lastItemPosition = 0;
                        //l = 0;
                    }

                }
            });

            btnDiscount.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (ProductDiscount == 0) {
                        ProductDiscount = 1;

                        //layLoadingSection.setVisibility(View.VISIBLE);
                        //layList.setVisibility(View.GONE);

                        getCounterDiscount = 0;
                        DataDiscount.clear();
                        adapterListDiscount = new AdapterCommodityList(DataDiscount);
                        reciveDiscountProduct("readDiscount", Integer.toString(getCounterDiscount), G.countyRecive);

                        btnDiscount.setBackgroundResource(R.drawable.button_send_corner_select);
                        btnDiscount.setTextColor(Color.parseColor("#1A75BC"));

                        DataNew.clear();
                        btnNew.setBackgroundResource(R.drawable.button_send_corner);
                        btnNew.setTextColor(Color.parseColor("#ffffff"));
                        ProductNew = 0;

                        DataPouplar.clear();
                        btnPopular.setBackgroundResource(R.drawable.button_send_corner);
                        btnPopular.setTextColor(Color.parseColor("#ffffff"));
                        StoreNew = 0;

                    }

                }
            });

            btnPopular.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (StoreNew == 0) {
                        StoreNew = 1;

                        //layLoadingSection.setVisibility(View.VISIBLE);
                        //layList.setVisibility(View.GONE);

                        getCounterPouplar = 0;
                        DataPouplar.clear();
                        adapterListPouplar = new AdapterCommodityList(DataPouplar);
                        recivePouplarProduct("readPouplar", Integer.toString(getCounterPouplar), G.countyRecive);

                        btnPopular.setBackgroundResource(R.drawable.button_send_corner_select);
                        btnPopular.setTextColor(Color.parseColor("#1A75BC"));

                        DataNew.clear();
                        btnNew.setBackgroundResource(R.drawable.button_send_corner);
                        btnNew.setTextColor(Color.parseColor("#ffffff"));
                        ProductNew = 0;

                        DataDiscount.clear();
                        btnDiscount.setBackgroundResource(R.drawable.button_send_corner);
                        btnDiscount.setTextColor(Color.parseColor("#ffffff"));
                        ProductDiscount = 0;

                    }

                }
            });

            lstProduct.setOnScrollListener(new OnScrollListener() {

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    // if (isTouch == 1) {
                    //isTouch = 0;
                    Log.i("FFF", "jaber ");
                    if (ProductNew == 1) {
                        int lastItem = firstVisibleItem + visibleItemCount;
                        l = lastItem;

                        Log.i("FFF", "lastK==  " + adapterListNew.getCount());
                        if (adapterListNew.getCount() >= 10 && lastItem > adapterListNew.getCount() - 3) {
                            boolean isLoading = false;
                            if ( !isLoading) {
                                if (lastItem > lastItemPosition) {
                                    getCounterNew++;
                                    lastItemPosition = adapterListNew.getCount() + 3;
                                    reciveNewProduct("read", Integer.toString(getCounterNew), G.countyRecive);
                                }
                                isLoading = true;
                            }
                        }
                    } else if (StoreNew == 1) {

                        int lastItem = firstVisibleItem + visibleItemCount;
                        l = lastItem;

                        Log.i("FFF", "adapterListStore.getCount()= " + adapterListPouplar.getCount());
                        Log.i("FFF", "lastItem= " + lastItem);
                        Log.i("FFF", "lastItemPosition= " + lastItemPosition);
                        if (adapterListPouplar.getCount() >= 10 && lastItem > adapterListPouplar.getCount() - 3) {
                            boolean isLoading = false;
                            if ( !isLoading) {
                                if (lastItem > lastItemPosition) {
                                    Log.i("FFF", " in ");
                                    getCounterPouplar++;
                                    lastItemPosition = adapterListPouplar.getCount() + 3;
                                    //lastItemPosition = lastItemPosition - lstAll.getHeaderViewsCount();
                                    recivePouplarProduct("readPouplar", Integer.toString(getCounterPouplar), G.countyRecive);
                                }
                                isLoading = true;
                            }

                        }
                    } else if (ProductDiscount == 1) {

                        int lastItem = firstVisibleItem + visibleItemCount;
                        l = lastItem;

                        Log.i("FFF", "adapterListStore.getCount()= " + adapterListDiscount.getCount());
                        Log.i("FFF", "lastItem= " + lastItem);
                        Log.i("FFF", "lastItemPosition= " + lastItemPosition);
                        if (adapterListDiscount.getCount() >= 10 && lastItem > adapterListDiscount.getCount() - 3) {
                            boolean isLoading = false;
                            if ( !isLoading) {
                                if (lastItem > lastItemPosition) {
                                    Log.i("FFF", " in ");
                                    getCounterDiscount++;
                                    lastItemPosition = adapterListDiscount.getCount() + 3;
                                    //lastItemPosition = lastItemPosition - lstAll.getHeaderViewsCount();
                                    reciveDiscountProduct("readDiscount", Integer.toString(getCounterDiscount), G.countyRecive);
                                }
                                isLoading = true;
                            }

                        }
                    }
                    // }
                }


                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

            });

        } else {
            disconected();
        }

    }


    private void showListNewProduct() {
        Log.i("ALL", " " + " jaber");
        lstProduct.setVisibility(View.VISIBLE);
        if (DataNew.size() == 0) {
            lstProduct.setVisibility(View.GONE);

        } else {
            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);
            adapterListNew = new AdapterCommodityList(DataNew);
            lstProduct.setAdapter(adapterListNew);
            lstProduct.setSelection(l);

            layMain.setVisibility(View.VISIBLE);
            layLoading.setVisibility(View.GONE);

            // layLoadingSection.setVisibility(View.GONE);
            // layList.setVisibility(View.VISIBLE);
        }

    }


    private void showListDiscountProduct() {
        Log.i("ALL", " " + " jaber");
        lstProduct.setVisibility(View.VISIBLE);
        if (DataDiscount.size() == 0) {
            lstProduct.setVisibility(View.GONE);

        } else {
            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);
            adapterListDiscount = new AdapterCommodityList(DataDiscount);
            lstProduct.setAdapter(adapterListDiscount);
            lstProduct.setSelection(l);

            layMain.setVisibility(View.VISIBLE);
            layLoading.setVisibility(View.GONE);

            // layLoadingSection.setVisibility(View.GONE);
            // layList.setVisibility(View.VISIBLE);
        }
    }


    private void showListPouplartProduct() {
        Log.i("SSS", " " + " jaber");
        lstProduct.setVisibility(View.VISIBLE);
        if (DataPouplar.size() == 0) {
            lstProduct.setVisibility(View.GONE);
            Log.i("SSS", " " + " jaber");

        } else {
            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);
            adapterListPouplar = new AdapterCommodityList(DataPouplar);

            lstProduct.setAdapter(adapterListPouplar);
            lstProduct.setSelection(l);
            layMain.setVisibility(View.VISIBLE);
            layLoading.setVisibility(View.GONE);

            // layLoadingSection.setVisibility(View.GONE);
            // layList.setVisibility(View.VISIBLE);
        }

    }


    public void showSlider() {

        indicator = (CustomLoadingIndicator) header.findViewById(R.id.Indicator);
        pager = (ViewPager) header.findViewById(R.id.view_pager_slider);

        ImagePagerAdapter adapter = new ImagePagerAdapter(imageIds);
        if (imageIds.size() == 0) {
            StructSlider slider = new StructSlider();
            slider.id = -1;
            slider.imgAddress = "no";
            slider.link = "no";
            imageIds.add(slider);
        }
        pager.setAdapter(adapter);
        indicator.setVisibility(View.VISIBLE);
        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int index) {}


            @Override
            public void onPageScrolled(int startIndex, float percent, int pixel) {
                Log.i("PPP", "  count " + startIndex);
                indicator.setCurrentPage(startIndex);
            }


            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }


    public void reciveNewProduct(final String n, final String page, final String count) {

        try {
            String url = WebServiceUrl.getData;
            loading.setVisibility(View.VISIBLE);
            //  String url = WebServiceUrl.getData;// WebServiceUrl.Person;
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.i("ALL", " " + response);
                            if (response != null && response != "") {
                                try {
                                    JSONArray array = new JSONArray(response);

                                    for (int i = 0; i < array.length(); i++) {

                                        StrucItem kala = new StrucItem();
                                        JSONObject object = array.getJSONObject(i);
                                        kala.id = object.getInt("Product_id");
                                        kala.name = object.getString("NameProduct");
                                        kala.nameTeacher = object.getString("TeacherProduct");
                                        kala.price = object.getString("PriceProduct");
                                        kala.picTeache = object.getString("ImgTeacherProduct");
                                        kala.picFilm = object.getString("ImgProduct");
                                        kala.view = object.getInt("ViewProduct");
                                        kala.discountPrice = object.getInt("DiscountedPriceProduct");
                                        kala.var = 1;
                                        DataNew.add(kala);
                                    }
                                    showListNewProduct();
                                    loading.setVisibility(View.GONE);
                                }
                                catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    Log.i("LOL", "" + e.toString());
                                    e.printStackTrace();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            loading.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("action", n);
                    params.put("count", count);
                    params.put("page", page);
                    params.put("androidId", G.GetDeviceID);

                    return params;
                }
            };

            postRequest.setRetryPolicy((RetryPolicy) new DefaultRetryPolicy(
                    20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //postRequest.setShouldCache(false);
            G.getInstance().addToRequestQueue(postRequest);
        }
        catch (Exception e) {
            Log.i("ALL", " kjk" + e.toString());
        }

    }


    public void reciveDiscountProduct(final String n, final String page, final String count) {
        try {
            String url = WebServiceUrl.getDataDiscount;
            loading.setVisibility(View.VISIBLE);
            //  String url = WebServiceUrl.getDataDiscount;// WebServiceUrl.Person;
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            if (response != null && response != "") {
                                Log.i("ALL", " " + response);

                                try {
                                    JSONArray array = new JSONArray(response);

                                    for (int i = 0; i < array.length(); i++) {

                                        StrucItem kala = new StrucItem();
                                        JSONObject object = array.getJSONObject(i);
                                        kala.id = object.getInt("Product_id");
                                        kala.name = object.getString("NameProduct");
                                        kala.nameTeacher = object.getString("TeacherProduct");
                                        kala.price = object.getString("PriceProduct");
                                        kala.discountPrice = object.getInt("DiscountedPriceProduct");
                                        kala.picTeache = object.getString("ImgTeacherProduct");
                                        kala.picFilm = object.getString("ImgProduct");
                                        kala.view = object.getInt("ViewProduct");
                                        kala.var = 2;
                                        DataDiscount.add(kala);
                                    }

                                    showListDiscountProduct();
                                    loading.setVisibility(View.GONE);
                                }
                                catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    Log.i("LOL", "" + e.toString());
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            loading.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("action", n);
                    params.put("count", count);
                    params.put("page", page);
                    params.put("androidId", G.GetDeviceID);

                    return params;
                }
            };
            postRequest.setRetryPolicy((RetryPolicy) new DefaultRetryPolicy(
                    4000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //postRequest.setShouldCache(false);
            G.getInstance().addToRequestQueue(postRequest);
        }
        catch (Exception e) {
            Log.i("ALL", " kjk" + e.toString());
        }

    }


    public void recivePouplarProduct(final String n, final String page, final String count) {
        try {
            loading.setVisibility(View.VISIBLE);
            String url = WebServiceUrl.getDataPouplar;
            Log.i("SSS", " kjk" + n + "    " + page + "        " + count);
            // String url = WebServiceUrl.getDataPouplar + "&count=" + count + "&page=" + page;// WebServiceUrl.Person;
            Log.i("SSS", url);
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.i("ALL", " " + response);
                            if (response != null && response != "") {
                                try {
                                    JSONArray array = new JSONArray(response);

                                    for (int i = 0; i < array.length(); i++) {

                                        StrucItem kala = new StrucItem();
                                        JSONObject object = array.getJSONObject(i);
                                        kala.id = object.getInt("Product_id");
                                        kala.name = object.getString("NameProduct");
                                        kala.nameTeacher = object.getString("TeacherProduct");
                                        kala.price = object.getString("PriceProduct");
                                        kala.discountPrice = object.getInt("DiscountedPriceProduct");
                                        kala.picTeache = object.getString("ImgTeacherProduct");
                                        kala.picFilm = object.getString("ImgProduct");
                                        kala.view = object.getInt("ViewProduct");
                                        kala.var = 3;
                                        DataPouplar.add(kala);
                                    }

                                    showListPouplartProduct();
                                    // showListDiscountProduct();
                                    loading.setVisibility(View.GONE);
                                }
                                catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    Log.i("LOL", "" + e.toString());
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            loading.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("action", n);
                    params.put("count", count);
                    params.put("page", page);
                    params.put("androidId", G.GetDeviceID);

                    return params;
                }
            };

            //postRequest.setShouldCache(false);
            G.getInstance().addToRequestQueue(postRequest);
        }
        catch (Exception e) {
            Log.i("ALL", " kjk" + e.toString());
        }

    }


    public void reciveSlider() {
        try {
            Log.i("PPP", " kjk" + " Nmm");
            imageIds.clear();
            String url = WebServiceUrl.GetSlider;
            Log.i("SLI", " kjk" + " Nmm");
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(String response) {
                            Log.i("SLI", "error   " + "e.toString()1");
                            try {
                                JSONArray array = new JSONArray(response);
                                Log.i("PPP", " kjk1" + array);
                                for (int i = 0; i <= 3; i++) {
                                    StructSlider slider = new StructSlider();
                                    JSONObject object = array.getJSONObject(i);
                                    Log.i("SLI", "error   " + "e.toString(2)");
                                    slider.id = object.getInt("Id");
                                    slider.imgAddress = object.getString("ImgAddress");
                                    Log.i("SLI", " kjk" + slider.imgAddress);
                                    slider.link = object.getString("Link");
                                    //Toast.makeText(G.context, object.getString("CityName"), Toast.LENGTH_LONG).show();
                                    imageIds.add(slider);

                                }

                                showSlider();
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                                Log.i("SLI", "error   " + e.toString());
                            }

                        }
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("SLI", "error2   " + error.toString());
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("androidId", G.GetDeviceID);

                    return params;
                }
            };
            postRequest.setRetryPolicy((RetryPolicy) new DefaultRetryPolicy(
                    4000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            G.getInstance().addToRequestQueue(postRequest);
        }
        catch (Exception e) {
            Log.i("SLI", "error 3 " + e.toString());
        }

    }
}