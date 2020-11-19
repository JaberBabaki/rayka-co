package com.raykaco.andriod;

import io.vov.vitamio.utils.Log;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.raykaco.andriod.internet.Connectivity;
import com.raykaco.android.customcontrol.AdapterTeacherList;
import com.raykaco.android.customcontrol.StrucRezumeh;


public class TeacherRezumeh extends Enhance {

    ListView                              lstProduct;
    public static ArrayList<StrucRezumeh> DataRezumeh = new ArrayList<StrucRezumeh>();
    public static ArrayAdapter            adapterListRezumeh;

    LinearLayout                          layLoading;


    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rezumeh_root);
        menue();

        lstProduct = (ListView) findViewById(R.id.lst_product);
        layLoading = (LinearLayout) findViewById(R.id.lay_loading);

        final Connectivity Check = new Connectivity();
        if (Check.isConnected(G.context)) {

            conected();
            layLoading.setVisibility(View.VISIBLE);
            layMain.setVisibility(View.GONE);

            reciveNewProduct("1", "1", "1");

        } else {
            disconected();

        }
        Log.i("SSS", " " + DataRezumeh.size());
        lstProduct.setVisibility(View.VISIBLE);

    }


    private void showListNewProduct() {
        Log.i("ALL", " " + " jaber");
        lstProduct.setVisibility(View.VISIBLE);
        if (DataRezumeh.size() == 0) {
            lstProduct.setVisibility(View.GONE);

        } else {
            //  Data.set(1, id) int id = getResources().getIdentifier("yourpackagename:drawable/" + Data.get(1).bread, null, null);
            adapterListRezumeh = new AdapterTeacherList(DataRezumeh);
            lstProduct.setAdapter(adapterListRezumeh);

            layMain.setVisibility(View.VISIBLE);
            layLoading.setVisibility(View.GONE);

            // layLoadingSection.setVisibility(View.GONE);
            // layList.setVisibility(View.VISIBLE);
        }

    }


    public void reciveNewProduct(final String n, final String page, final String count) {

        StrucRezumeh st2 = new StrucRezumeh();
        st2.header = "فوق لیسانس هوش مصنوعی از دانشگاه صنعتی امیرکبیر";
        st2.name = "مهندس مجید اسدپور";
        st2.rezumeh1 = "عضو هیات علمی دانشگاه مازندران";
        st2.rezumeh2 = " 14 سال سابقه در زمینه شبکه و امنیت";
        st2.rezumeh3 = " بیش از 8 سال سابقه تدریس";
        st2.picTeacher = "http://rayka-co.ir/images/rayka-teacher/MajidAsadpoor.jpg";
        DataRezumeh.add(st2);

        StrucRezumeh st = new StrucRezumeh();
        st.header = "مولف , مدرس و طراح ارشد شبکه و امنیت";
        st.name = "مهندس روح اله آب نیکی";
        st.rezumeh1 = " مدرس برتر شرکت مخابرات استان تهران و البرز";
        st.rezumeh2 = " مشاورشبکه و امنیت سازمانهای دولتی و نظامی";
        st.rezumeh3 = " مولف کتب تخصصی شبکه و امنیت";
        st.picTeacher = "http://rayka-co.ir/images/rayka-teacher/Abniki.jpg";
        DataRezumeh.add(st);

        StrucRezumeh st3 = new StrucRezumeh();
        st3.header = "برنامه نویس و مدرس دوره های برنامه نویسی";
        st3.name = "مهندس پدرام شاه صفی";
        st3.rezumeh1 = "رتبه دو رقمی کنکور ارشد ";
        st3.rezumeh2 = " کارشناسی ارشد خواجه نصیر ";
        st3.rezumeh3 = " C, C++, C#, Java ";
        st3.picTeacher = "http://rayka-co.ir/images/rayka-teacher/ShahSafi.jpg";
        DataRezumeh.add(st3);

        showListNewProduct();
        /* try {
             String url = WebServiceUrl.getData;
             StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                     new Response.Listener<String>() {

                         @Override
                         public void onResponse(String response) {
                             Log.i("ALL", " " + response);
                             if (response != null && response != "") {
                                 try {
                                     JSONArray array = new JSONArray(response);

                                     for (int i = 0; i < array.length(); i++) {

                                         StrucRezumeh rezumeh = new StrucRezumeh();
                                         JSONObject object = array.getJSONObject(i);
                                         rezumeh.id = object.getInt("teacherId");
                                         rezumeh.name = object.getString("tezcherName");
                                         rezumeh.header = object.getString("headerTopic");
                                         rezumeh.picTeacher = object.getString("teacherPhoto");
                                         rezumeh.rezumeh1 = object.getString("topic1");
                                         rezumeh.rezumeh2 = object.getString("topic2");
                                         rezumeh.rezumeh3 = object.getString("topic3");
                                         DataRezumeh.add(rezumeh);
                                     }
                                     showListNewProduct();
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
         }*/

    }

}
