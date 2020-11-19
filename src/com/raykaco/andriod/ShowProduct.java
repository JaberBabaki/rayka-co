package com.raykaco.andriod;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.CenterLayout;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.raykaco.andriod.database.BasketProductDB;
import com.raykaco.andriod.internet.Connectivity;
import com.raykaco.andriod.internet.WebServiceUrl;
import com.raykaco.android.customcontrol.AdapterDetilsTopic;
import com.raykaco.android.customcontrol.CustomLoading;
import com.raykaco.android.customcontrol.NestedListView;
import com.raykaco.android.customcontrol.StrucTopic;


public class ShowProduct extends Enhance {

    TextView                     txtLoading;

    int                          id;

    ImageLoader                  imageLoader        = G.getInstance().getImageLoader();
    NetworkImageView             thumbNail;
    ImageView                    imageSliderDefault;
    ImageView                    imgPlay;
    VideoView                    mVideoView;
    CenterLayout                 layVideo;

    TextView                     txtView;
    TextView                     txrName;
    TextView                     txtPrice;
    TextView                     txtTeacher;
    TextView                     txtDescrip;
    Button                       btnRezomeh;
    Button                       btnMoreDescrip;

    public ArrayList<StrucItem>  DataProduct        = new ArrayList<StrucItem>();

    public ArrayList<StrucTopic> DataAnotherProduct = new ArrayList<StrucTopic>();

    ArrayAdapter                 adapterListDiscount;

    ArrayAdapter                 adapterListTopic;
    ListView                     lstTopic;

    ArrayAdapter                 adapterListAnotherProduct;
    NestedListView               griAnotherProduct;

    TextView                     nameTeacherRezumeh;
    TextView                     nameHeaderTeacherRezumeh;
    TextView                     nameRezumeh1;
    TextView                     nameRezumeh2;
    TextView                     nameRezumeh3;

    String                       UrlFilm;
    String                       URLPoster;

    CustomLoading                loading;

    LinearLayout                 layLoading;

    LinearLayout                 LayAnother;
    LinearLayout                 layLoadingAnotherProduct;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_product_root);

        menue();

        thumbNail = (NetworkImageView) findViewById(R.id.img_product_show);
        imageSliderDefault = (ImageView) findViewById(R.id.img_default_show);
        imgPlay = (ImageView) findViewById(R.id.img_play);
        mVideoView = (VideoView) findViewById(R.id.buffer);
        layVideo = (CenterLayout) findViewById(R.id.lay_video);

        txtView = (TextView) findViewById(R.id.txt_view_details);
        txrName = (TextView) findViewById(R.id.txt_name_details);
        txtPrice = (TextView) findViewById(R.id.txt_price_details);
        txtTeacher = (TextView) findViewById(R.id.txt_teacher_detils);
        txtDescrip = (TextView) findViewById(R.id.txt_description_detils);

        btnRezomeh = (Button) findViewById(R.id.btn_rezomeh);
        btnMoreDescrip = (Button) findViewById(R.id.btn_more_descrip_detils);

        btnRezomeh.setTypeface(G.font1);
        btnMoreDescrip.setTypeface(G.font1);
        // adapterListDiscount = new AdapterCommodityTiles(G.DataKalaDiscount);

        lstTopic = (ListView) findViewById(R.id.lst_topic);

        griAnotherProduct = (NestedListView) findViewById(R.id.gri_another_product);
        LayAnother = (LinearLayout) findViewById(R.id.lay_another_product);
        layLoadingAnotherProduct = (LinearLayout) findViewById(R.id.lay_loading_abother_product);

        Bundle extras = getIntent().getExtras();
        id = 0;
        if (extras != null) {
            id = extras.getInt("id");
        }

        final LinearLayout LayDescripH = (LinearLayout) findViewById(R.id.lay_description_h);
        final LinearLayout LayTopicH = (LinearLayout) findViewById(R.id.lay_topic_h);
        final LinearLayout LayAnotherH = (LinearLayout) findViewById(R.id.lay_another_product_h);
        final LinearLayout LayDescrip = (LinearLayout) findViewById(R.id.lay_description);
        final LinearLayout LayTopic = (LinearLayout) findViewById(R.id.lay_topic);

        final RelativeLayout layAddProduct = (RelativeLayout) findViewById(R.id.lay_add_product);

        final ImageView ImgDescrip = (ImageView) findViewById(R.id.img_descip);
        final ImageView ImgTopic = (ImageView) findViewById(R.id.img_topic);
        final ImageView ImgAnother = (ImageView) findViewById(R.id.img_another_product);

        final ImageView imgShaering = (ImageView) findViewById(R.id.img_shaering);

        LinearLayout LayCheck = (LinearLayout) findViewById(R.id.lay_check_internet);
        LinearLayout LayMain = (LinearLayout) findViewById(R.id.lay_main_show_product);

        LinearLayout LayRefresh = (LinearLayout) findViewById(R.id.lay_check_refresh);
        LinearLayout LayMobile = (LinearLayout) findViewById(R.id.lay_check_mobile);
        LinearLayout LayWifi = (LinearLayout) findViewById(R.id.lay_check_wifi);

        // loading = (TextView) findViewById(R.id.lo);
        layLoading = (LinearLayout) findViewById(R.id.lay_loading);

        final Connectivity Check = new Connectivity();
        if (Check.isConnected(G.context)) {

            conected();
            layLoading.setVisibility(View.VISIBLE);
            layMain.setVisibility(View.GONE);

            reciveNewProduct(id);

            LayDescripH.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (LayDescrip.getVisibility() == View.GONE) {
                        ImgDescrip.setImageResource(R.drawable.up_arrow);
                        LayDescrip.setVisibility(View.VISIBLE);
                    } else {
                        ImgDescrip.setImageResource(R.drawable.down_arrow);
                        LayDescrip.setVisibility(View.GONE);
                    }

                }
            });

            LayTopicH.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (LayTopic.getVisibility() == View.GONE) {
                        ImgTopic.setImageResource(R.drawable.up_arrow);
                        LayTopic.setVisibility(View.VISIBLE);
                    } else {
                        ImgTopic.setImageResource(R.drawable.down_arrow);
                        LayTopic.setVisibility(View.GONE);
                    }

                }
            });

            LayAnotherH.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Log.i("DATA", "" + DataAnotherProduct.size());
                    //LayAnother.setVisibility(View.VISIBLE);
                    layLoadingAnotherProduct.setVisibility(View.VISIBLE);
                    if (DataAnotherProduct.size() == 0) {

                        Log.i("DATA", "" + DataProduct.get(0).nameTeacher);
                        //DataAnotherProduct.clear();
                        reciveAnotherProduct("byTeacher", DataProduct.get(0).nameTeacher);
                    } else {
                        showAnotherProduct();
                    }

                    if (LayAnother.getVisibility() == View.GONE) {

                        ImgAnother.setImageResource(R.drawable.up_arrow);
                        LayAnother.setVisibility(View.VISIBLE);
                    } else {

                        ImgAnother.setImageResource(R.drawable.down_arrow);
                        LayAnother.setVisibility(View.GONE);
                    }

                }
            });

            btnRezomeh.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    final Dialog dialog2 = new Dialog(G.currentActivity);
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog2.setContentView(R.layout.dialog_short_rezomeh);
                    ImageLoader imageLoader2 = G.getInstance().getImageLoader();
                    NetworkImageView thumbPicPro = (NetworkImageView) dialog2.findViewById(R.id.img_short_rezumeh);
                    imageLoader2.get(WebServiceUrl.getPictureTeacher + DataProduct.get(0).picTeache, ImageLoader.getImageListener(thumbPicPro, R.drawable.loading, R.drawable.no_teacher_pic));
                    thumbPicPro.setImageUrl(WebServiceUrl.getProductPicture + DataProduct.get(0).picTeache, imageLoader2);

                    nameTeacherRezumeh = (TextView) dialog2.findViewById(R.id.txt_teacher_name_rezumeh);
                    nameHeaderTeacherRezumeh = (TextView) dialog2.findViewById(R.id.txt_header_teacher_name);
                    nameRezumeh1 = (TextView) dialog2.findViewById(R.id.txt_rezuneh1);
                    nameRezumeh2 = (TextView) dialog2.findViewById(R.id.txt_rezuneh2);
                    nameRezumeh3 = (TextView) dialog2.findViewById(R.id.txt_rezuneh3);

                    reciveShortRezumeh(DataProduct.get(0).nameTeacher);

                    dialog2.setCancelable(true);
                    dialog2.show();

                }
            });

            btnMoreDescrip.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    final Dialog dialog2 = new Dialog(G.currentActivity);
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog2.setContentView(R.layout.dialog_more_topic);

                    TextView txtHeader = (TextView) dialog2.findViewById(R.id.txt_dialog_header);
                    TextView txtText = (TextView) dialog2.findViewById(R.id.txt_text_dialog);
                    TextView txtOk = (TextView) dialog2.findViewById(R.id.txt_ok_dialog);

                    txtHeader.setText(" سرفصل ");
                    txtText.setText(DataProduct.get(0).descrip);

                    txtOk.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            dialog2.hide();
                        }
                    });

                    dialog2.setCancelable(true);
                    dialog2.show();

                }
            });

            imgShaering.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "نام محصول : " + DataProduct.get(0).name + "\n" + " مدرس" + DataProduct.get(0).nameTeacher + " \n" + "قیمت : " + DataProduct.get(0).price + "\n آدرس فروشگاه : \n رایکا" + "\n" + "http://www.http://rayka-co.ir";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    G.currentActivity.startActivity(Intent.createChooser(sharingIntent, "ارسال برای دیگران"));

                }
            });

            layAddProduct.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    BasketProductDB bp = new BasketProductDB();
                    bp.setId(DataProduct.get(0).id);
                    int count = bp.selectId();
                    if (bp.selectId() == 0) {

                        Toast.makeText(G.context, "محصول به سبد اضافه شد", Toast.LENGTH_LONG).show();
                        bp.setText(DataProduct.get(0).name);
                        bp.setPrice(DataProduct.get(0).price);
                        bp.setTeacherName(DataProduct.get(0).nameTeacher);
                        bp.setCount((bp.selectId() + 1));
                        bp.insertProduct();

                        addProduct();

                    } else {

                        Toast.makeText(G.context, "این محصول قبلا در سبد شما قرار دارد ", Toast.LENGTH_LONG).show();
                        // bp.setCount((bp.selectId() + 1));
                        // bp.updateProduct();

                        // addProduct();
                    }

                }
            });

            imgPlay.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent intentm = new Intent(G.currentActivity, ShowVideo.class);
                    // intentm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    intentm.putExtra("URL", UrlFilm);
                    G.currentActivity.startActivity(intentm);
                    // setVideo();

                }
            });

        } else {
            disconected();

        }
        LayWifi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Toast.makeText(G.context, "text", Toast.LENGTH_SHORT).show();
                Check.goToSettingWiFiNet();
            }
        });
        LayMobile.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Check.goToSettingMobileNet();
            }
        });

    }


    private void showDetailsProduct() {

        Log.i("SHO", " k " + "object");

        txtView.setText("" + DataProduct.get(0).view);
        txrName.setText("" + DataProduct.get(0).name);
        txtPrice.setText("" + DataProduct.get(0).price);
        txtTeacher.setText("" + DataProduct.get(0).nameTeacher);
        txtDescrip.setText("" + DataProduct.get(0).descrip);

        if (DataProduct.get(0).topic.size() == 0) {
            lstTopic.setVisibility(View.GONE);
        } else {
            lstTopic.setVisibility(View.VISIBLE);
            adapterListTopic = new AdapterDetilsTopic(DataProduct.get(0).topic);
            lstTopic.setAdapter(adapterListTopic);
            adapterListTopic.notifyDataSetChanged();
        }

    }


    private void showAnotherProduct() {
        Log.i("ANO", " " + " jaber");

        if (DataAnotherProduct.size() == 0) {
            griAnotherProduct.setVisibility(View.GONE);
            Log.i("ANO", " " + " jaber");

        } else {

            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);
            adapterListAnotherProduct = new AdapterDetilsTopic(DataAnotherProduct);

            griAnotherProduct.setAdapter(adapterListAnotherProduct);
            griAnotherProduct.setVisibility(View.VISIBLE);
            layLoadingAnotherProduct.setVisibility(View.GONE);
            // lstAll.setSelection(l);
        }

    }


    public void reciveNewProduct(final int id) {
        Log.i("PRO", " " + "response" + id);
        try {
            String url = WebServiceUrl.getProductById;
            //txtLoading.setVisibility(View.VISIBLE);
            //  String url = WebServiceUrl.getDataDiscount;// WebServiceUrl.Person;
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            if (response != null && response != "") {
                                Log.i("PRO", " " + response);

                                try {
                                    JSONArray array = new JSONArray(response);

                                    StrucItem kala = new StrucItem();
                                    JSONObject object = array.getJSONObject(0);
                                    kala.id = object.getInt("Product_id");
                                    kala.name = object.getString("NameProduct");
                                    kala.nameTeacher = object.getString("TeacherProduct");
                                    Log.i("DATA", kala.nameTeacher);
                                    kala.price = object.getString("PriceProduct");
                                    kala.discountPrice = object.getInt("DiscountedPriceProduct");
                                    kala.picTeache = object.getString("ImgTeacherProduct");
                                    if ( !object.getString("DescriptionProduct").equals("--")) {
                                        kala.descrip = object.getString("DescriptionProduct");
                                    } else {
                                        kala.descrip = "توضیحی برای این محصول ثبت نشده";
                                    }
                                    kala.picFilm = object.getString("ImgProduct");
                                    kala.view = object.getInt("ViewProduct");
                                    kala.spe = object.getInt("special");
                                    kala.aparat = object.getString("idAparat");
                                    for (int n = 1; n <= 5; n++) {
                                        StrucTopic topic = new StrucTopic();
                                        if ( !object.getString("Topic" + n).equals("--")) {
                                            topic.name = object.getString("Topic" + n);
                                            topic.id = -1;

                                            kala.topic.add(topic);
                                        }
                                    }
                                    kala.var = 2;
                                    DataProduct.add(kala);

                                    showDetailsProduct();
                                    //  reciveHtml(kala.aparat);
                                    recivePoster(kala.aparat);
                                    reciveData(kala.aparat);
                                    // txtLoading.setVisibility(View.GONE);
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
                            //txtWaite.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("action", "byId");
                    params.put("id", "" + id);
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


    public void reciveShortRezumeh(final String name) {
        try {
            String url = WebServiceUrl.getDataAnotherProduct;
            Log.i("ANO", " kjk" + "        " + name);
            // String url = WebServiceUrl.getDataPouplar + "&count=" + count + "&page=" + page;// WebServiceUrl.Person;
            Log.i("ANO", url);
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.i("ANO", " " + response);
                            if (response != null && response != "") {
                                try {
                                    JSONArray array = new JSONArray(response);

                                    for (int i = 0; i < array.length(); i++) {

                                        JSONObject object = array.getJSONObject(i);
                                        nameTeacherRezumeh.setText(object.getString("name"));

                                        if (object.getString("header").length() >= 35) {
                                            nameHeaderTeacherRezumeh.setText(object.getString("header").subSequence(0, 34));
                                        } else {
                                            nameHeaderTeacherRezumeh.setText(object.getString("header"));
                                        }

                                        if (object.getString("topic1").length() >= 35) {
                                            nameRezumeh1.setText(object.getString("topic1").subSequence(0, 34));
                                        } else {
                                            nameRezumeh1.setText(object.getString("topic1"));
                                        }

                                        if (object.getString("topic2").length() >= 35) {
                                            nameRezumeh2.setText(object.getString("topic2").subSequence(0, 34));
                                        } else {
                                            nameRezumeh2.setText(object.getString("topic2"));
                                        }

                                        if (object.getString("topic3").length() >= 35) {
                                            nameRezumeh3.setText(object.getString("topic3").subSequence(0, 34));
                                        } else {
                                            nameRezumeh3.setText(object.getString("topic3"));
                                        }

                                    }

                                    // showListDiscountProduct();
                                    // txtLoading.setVisibility(View.GONE);
                                }
                                catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    Log.i("ANO", "" + e.toString());
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ANO", error.toString());
                            //txtWaite.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("action", "teacherName");
                    params.put("name", name);
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


    public void reciveAnotherProduct(final String n, final String count) {
        try {
            String url = WebServiceUrl.getDataAnotherProduct;
            Log.i("ANO", " kjk" + n + "        " + count);
            // String url = WebServiceUrl.getDataPouplar + "&count=" + count + "&page=" + page;// WebServiceUrl.Person;
            Log.i("ANO", url);
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.i("ANO", " " + response);
                            if (response != null && response != "") {
                                try {
                                    JSONArray array = new JSONArray(response);

                                    for (int i = 0; i < array.length(); i++) {

                                        StrucTopic kala = new StrucTopic();
                                        JSONObject object = array.getJSONObject(i);
                                        kala.id = object.getInt("Product_id");
                                        kala.name = object.getString("NameProduct");
                                        /*kala.nameTeacher = object.getString("TeacherProduct");
                                        kala.price = object.getString("PriceProduct");
                                        kala.discountPrice = object.getInt("DiscountedPriceProduct");
                                        kala.picTeache = object.getString("ImgTeacherProduct");
                                        kala.picFilm = object.getString("ImgProduct");
                                        kala.view = object.getInt("ViewProduct");
                                        kala.spe = object.getInt("special");
                                        kala.var = 3;*/
                                        DataAnotherProduct.add(kala);
                                    }

                                    showAnotherProduct();
                                    // showListDiscountProduct();
                                    // txtLoading.setVisibility(View.GONE);
                                }
                                catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    Log.i("ANO", "" + e.toString());
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ANO", error.toString());
                            //txtWaite.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("action", n);
                    params.put("name", count);
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


    public void reciveHtml(String id) {
        try {

            String url = "http://www.aparat.com/v/" + id;
            // String url = WebServiceUrl.getDataPouplar + "&count=" + count + "&page=" + page;// WebServiceUrl.Person;
            Log.i("SSS", url);
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            // String str = Html.fromHtml(response).toString();
                            response = response.substring(response.indexOf("og:video:url\" content=\"") + 23);
                            response = response.substring(0, response.indexOf(".mp4") + 4);
                            UrlFilm = response;
                            Log.i("HTML", " " + response);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            //txtWaite.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();

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


    public void sePoster() {

        layMain.setVisibility(View.VISIBLE);
        layLoading.setVisibility(View.GONE);

        imageLoader.get(URLPoster, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.no_teacher_pic));
        thumbNail.setImageUrl(URLPoster, imageLoader);
        imageSliderDefault.setVisibility(View.GONE);
        thumbNail.setVisibility(View.VISIBLE);
        imgPlay.setVisibility(View.VISIBLE);

    }


    public void recivePoster(String id) {
        try {

            String url = "http://www.aparat.com/etc/api/video/videohash/" + id;
            // String url = WebServiceUrl.getDataPouplar + "&count=" + count + "&page=" + page;// WebServiceUrl.Person;
            Log.i("SSS", url);
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String objectr = object.getJSONObject("video").getString("big_poster");
                                URLPoster = objectr;
                                sePoster();
                                //JSONArray array = new JSONArray(response);
                                //JSONArray jsonArray = object.getJSONArray("video");
                                Log.i("HTML", " fs " + objectr);
                            }
                            catch (JSONException e) {
                                // TODO Auto-generated catch block
                                Log.i("ERR", " fs " + e.toString());
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            //txtWaite.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
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


    public void reciveData(String id) {
        try {

            String url = "http://www.aparat.com/v/" + id;
            // String url = WebServiceUrl.getDataPouplar + "&count=" + count + "&page=" + page;// WebServiceUrl.Person;
            Log.i("SSS", url);
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            // String str = Html.fromHtml(response).toString();
                            response = response.substring(response.indexOf("og:video:url\" content=\"") + 23);
                            response = response.substring(0, response.indexOf(".mp4") + 4);
                            UrlFilm = response;
                            Log.i("HTML", " " + response);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            //txtWaite.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
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


    public void setVideo() {

        if ( !LibsChecker.checkVitamioLibs(this))
            return;

        if (UrlFilm != "") {

            layVideo.setVisibility(View.VISIBLE);
            thumbNail.setVisibility(View.GONE);
            imageSliderDefault.setVisibility(View.GONE);

            mVideoView = (VideoView) findViewById(R.id.buffer);
            mVideoView.setVideoPath(UrlFilm);
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.requestFocus();
            mVideoView.setBufferSize(300);
            mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            mVideoView.getHolder().setFormat(PixelFormat.RGBX_8888);
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });

        }

    }
}