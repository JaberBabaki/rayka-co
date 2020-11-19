package com.raykaco.andriod;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.raykaco.andriod.internet.Connectivity;
import com.raykaco.andriod.internet.WebServiceUrl;


public class Contact extends Enhance {

    Button   btnNew;
    EditText edtName;
    EditText edtMail;
    EditText edtSubject;
    EditText edtText;

    boolean  state        = true;

    int      ErrorName    = 0;
    int      ErrorSubject = 0;
    int      ErrorEmail   = 0;
    int      ErrorText    = 0;


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
        setContentView(R.layout.contact_root);
        menue();

        btnNew = (Button) findViewById(R.id.btn_send_message);
        edtName = (EditText) findViewById(R.id.edt_name_message);
        edtMail = (EditText) findViewById(R.id.edt_mail_message);
        edtSubject = (EditText) findViewById(R.id.edt_subject_message);
        edtText = (EditText) findViewById(R.id.edt_text_message);

        // final Button btnPopular = (Button) header.findViewById(R.id.btn_popular);
        // final Button btnDiscount = (Button) header.findViewById(R.id.btn_discount);

        edtName.setTypeface(G.font1);
        btnNew.setTypeface(G.font1);
        edtMail.setTypeface(G.font1);
        edtSubject.setTypeface(G.font1);
        edtText.setTypeface(G.font1);

        final Connectivity Check = new Connectivity();
        if (Check.isConnected(G.context)) {
            conected();

            btnNew.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    final String P1 = edtName.getText().toString().trim();
                    final String P2 = edtMail.getText().toString().trim();
                    final String P3 = edtSubject.getText().toString().trim();
                    final String P4 = edtText.getText().toString().trim();
                    //final String P3 = G.phoneNumber;
                    //Toast.makeText(G.context, "number :==" + P3, Toast.LENGTH_SHORT).show();

                    if (P1.length() < 3 || P1.length() >= 40) {
                        Log.i("SEND", "" + P1 + "   " + P2 + "   " + P3);
                        ErrorName = 1;
                    }

                    if ( !P2.contains("@") && !P2.contains(".")) {
                        ErrorEmail = 1;
                    }

                    if (P3.length() < 5 || P1.length() >= 40) {
                        Log.i("SEND", "" + P1 + "   " + P2 + "   " + P3);
                        ErrorSubject = 1;
                    }

                    if (P4.length() < 11) {
                        ErrorText = 1;
                    }

                    final Dialog dialog5 = new Dialog(G.currentActivity);
                    dialog5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog5.setContentView(R.layout.dialog_error);
                    final TextView txtNameError = (TextView) dialog5.findViewById(R.id.txt_name_error);
                    final TextView txtEmailError = (TextView) dialog5.findViewById(R.id.txt_email_eror);
                    final TextView txtSubjectError = (TextView) dialog5.findViewById(R.id.txt_subject_error);
                    final TextView txtTextError = (TextView) dialog5.findViewById(R.id.txt_text_error);
                    LinearLayout btnOk = (LinearLayout) dialog5.findViewById(R.id.lay_ok_error);

                    btnOk.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            /* txtNameError.setVisibility(View.GONE);
                             txtEmailError.setVisibility(View.GONE);
                             txtSubjectError.setVisibility(View.GONE);
                             txtTextError.setVisibility(View.GONE);*/
                            dialog5.dismiss();
                        }
                    });

                    dialog5.setCancelable(false);

                    if (ErrorName == 1) {
                        Log.i("SEND", "" + P1 + "   " + P2 + "   " + P3);
                        txtNameError.setVisibility(View.VISIBLE);
                        txtNameError.setText("نام باید بیش از دو حرف باشد");
                    }

                    if (ErrorEmail == 1) {
                        txtEmailError.setVisibility(View.VISIBLE);
                        txtEmailError.setText("لطفا ایمیلی صحیح وارد کنید");
                    }
                    if (ErrorText == 1) {
                        txtTextError.setVisibility(View.VISIBLE);
                        txtTextError.setText("متن پیام باید بیش از 10 حرف باشد");
                    }
                    if (ErrorSubject == 1) {
                        txtSubjectError.setVisibility(View.VISIBLE);
                        txtSubjectError.setText("موضوع پیام باید بیش از 4 حرف باشد");
                    }

                    if (ErrorName == 0 && ErrorEmail == 0 && ErrorSubject == 0 && ErrorText == 0) {

                        Log.i("SEND", "" + P1 + "   " + P2 + "   " + P3);

                        sendIdea(P1, P2, P3, P4);

                        // dialog2.dismiss();

                    } else {

                        ErrorName = 0;
                        ErrorEmail = 0;
                        ErrorSubject = 0;
                        ErrorText = 0;

                        dialog5.show();
                    }
                }
            });

        } else {
            disconected();
        }

    }


    public void sendIdea(final String name, final String email, final String subject, final String text) {
        try {
            String url = WebServiceUrl.SendIdea;
            Log.i("SSS", " kjk");
            // String url = WebServiceUrl.getDataPouplar + "&count=" + count + "&page=" + page;// WebServiceUrl.Person;
            Log.i("SSS", url);
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.i("ALL", " " + response);
                            if (response != null && response != "") {
                                try {
                                    if (response.equals("ok")) {

                                        Toast.makeText(G.context, "پیام شما با موفقیت ثبت شد، به زودی کارشناسان ما پاسخگو هستند ", Toast.LENGTH_LONG).show();
                                    } else {
                                        JSONArray array = new JSONArray(response);
                                        Toast.makeText(G.context, "مشکل در ارسال پیام لطفا ایمیل را صحیح وارد کنید ", Toast.LENGTH_LONG).show();
                                    }
                                    // showListPouplartProduct();
                                    // showListDiscountProduct();
                                    //  loading.setVisibility(View.GONE);
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
                            // loading.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("FullName", name);
                    params.put("Email", email);
                    params.put("Subject", subject);
                    params.put("Description", text);
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

}