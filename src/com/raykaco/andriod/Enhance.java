package com.raykaco.andriod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.raykaco.andriod.database.BasketProductDB;
import com.raykaco.andriod.database.Check;
import com.raykaco.andriod.database.DataLogin;
import com.raykaco.andriod.internet.CheckInternet;
import com.raykaco.android.customcontrol.MD5;


public class Enhance extends Activity {

    boolean                    state               = true;

    public LinearLayout        imgMenue;
    public LinearLayout        imgNotific;
    public LinearLayout        imgBack;
    public static LinearLayout imgBasket;
    public LinearLayout        imgSearch;
    public LinearLayout        layCheckWifi;
    public static LinearLayout layContactUs;
    public static TextView     txtCountProduct;

    RelativeLayout             layMain;
    LinearLayout               layCheck;
    Button                     BtnTryAgain;

    DrawerLayout               drawerLayout;
    public static TextView     txtLogin;
    public static ViewGroup    layPanel;
    public static TextView     txtUserName;

    private Dialog             dialog2;
    EditText                   edtUserName;
    EditText                   edtPas;
    EditText                   edtPasConfim;

    int                        ErrorUserName       = 0;
    int                        ErrorPassword       = 0;
    int                        ErrorConfimPassword = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // checkLoginOk();

    }


    public void disconected() {
        layMain = (RelativeLayout) findViewById(R.id.lay_lst_main);
        layCheck = (LinearLayout) findViewById(R.id.lay_check_internet);
        layCheckWifi = (LinearLayout) findViewById(R.id.lay_check_wifi);
        BtnTryAgain = (Button) findViewById(R.id.btn_no_connect);

        BtnTryAgain.setTypeface(G.font1);
        layMain.setVisibility(View.GONE);
        layCheck.setVisibility(View.VISIBLE);

        layCheckWifi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                startActivity(intent);

            }
        });

        BtnTryAgain.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final CheckInternet CheckInternet = new CheckInternet();
                if (CheckInternet.Access()) {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    // Intent intentm = new Intent(G.currentActivity, G.currentActivity.getClassLoader());
                    // intentm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    // G.currentActivity.startActivity(intentm);
                } else {
                    Toast.makeText(G.context, "ارتباط برقرار نیست", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public void conected() {
        layMain = (RelativeLayout) findViewById(R.id.lay_lst_main);
        layCheck = (LinearLayout) findViewById(R.id.lay_check_internet);

        layMain.setVisibility(View.VISIBLE);
        layCheck.setVisibility(View.GONE);

    }


    public void menue() {

        imgMenue = (LinearLayout) findViewById(R.id.lay_menue);
        imgNotific = (LinearLayout) findViewById(R.id.lay_notific);
        imgBack = (LinearLayout) findViewById(R.id.lay_back);
        imgBasket = (LinearLayout) findViewById(R.id.lay_basket);
        imgSearch = (LinearLayout) findViewById(R.id.lay_search);

        LinearLayout layLogin = (LinearLayout) findViewById(R.id.lay_login_sliding);
        LinearLayout layTeacher = (LinearLayout) findViewById(R.id.lay_teacher);

        txtCountProduct = (TextView) findViewById(R.id.txt_count_product);
        txtLogin = (TextView) findViewById(R.id.txt_login_register_sliding);
        layPanel = (ViewGroup) findViewById(R.id.lay_panel_mangment);
        txtUserName = (TextView) findViewById(R.id.txt_usename_sliding);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        layContactUs = (LinearLayout) findViewById(R.id.lay_contact_us);
        try {
            imgMenue.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        drawerLayout.closeDrawers();
                    } else {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                }
            });
        }
        catch (Exception e) {
            Log.i("MEN", e.toString());
        }

        layContactUs.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, Contact.class);
                G.currentActivity.startActivity(intent);
            }
        });

        imgBasket.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(G.currentActivity, BasketProduct.class);
                G.currentActivity.startActivity(intent);
            }
        });

        checkLoginOk();

        layLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                startLogin();
            }
        });

        layTeacher.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(G.currentActivity, TeacherRezumeh.class);
                G.currentActivity.startActivity(intent);
            }
        });
        addProduct();

    }


    public static void addProduct() {

        BasketProductDB bp = new BasketProductDB();
        int count = bp.selectAllCount();
        if (count != 0) {
            imgBasket.setVisibility(View.VISIBLE);
            txtCountProduct.setVisibility(View.VISIBLE);
            txtCountProduct.setText("" + count);

        } else {

            imgBasket.setVisibility(View.GONE);
            txtCountProduct.setVisibility(View.GONE);

        }

    }


    public static void checkLoginOk() {
        if (G.LoginOk.equals("0")) {

            //dialogLogin();
            txtLogin.setText("ورود و ثبت نام");
            layPanel.setVisibility(View.GONE);
            txtUserName.setText("هوجی بوجی");

            G.DATALOGIN[5] = "0";

        } else {

            //dialogExit();
            txtLogin.setText("خروج");
            layPanel.setVisibility(View.VISIBLE);
            txtUserName.setText(G.DATALOGIN[2]);

            G.DATALOGIN[5] = "1";
        }
    }


    public void startLogin() {
        drawerLayout.closeDrawers();

        if (G.LoginOk.equals("0")) {
            Log.i("GET", "dialogLogin");
            dialogLogin();

        } else {
            Log.i("GET", "dialogExit");
            dialogExit();

        }
    }


    public void dialogLogin() {

        state = true;

        dialog2 = new Dialog(G.currentActivity);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_login_register);

        edtUserName = (EditText) dialog2.findViewById(R.id.edt_username_dialog_login);
        edtPas = (EditText) dialog2.findViewById(R.id.edt_pas_dialog_login);
        edtPasConfim = (EditText) dialog2.findViewById(R.id.edt_confim_pas_dialog_login);
        LinearLayout layForgot = (LinearLayout) dialog2.findViewById(R.id.lay_forgot_dialog_login);
        LinearLayout layRegister = (LinearLayout) dialog2.findViewById(R.id.lay_register_dialog_login);
        final Button btnLogin = (Button) dialog2.findViewById(R.id.btn_login_dialog_login);
        final Button btnBack = (Button) dialog2.findViewById(R.id.btn_login_and_register_back);
        final TextView txtRegister = (TextView) dialog2.findViewById(R.id.txt_register_dialog_login);
        final LinearLayout layForget = (LinearLayout) dialog2.findViewById(R.id.lay_forgot_dialog_login);
        final LinearLayout layConfim = (LinearLayout) dialog2.findViewById(R.id.lay_confim_pas_dialog_login);

        btnLogin.setTypeface(G.font1);
        btnBack.setTypeface(G.font1);
        edtUserName.setTypeface(G.font1);
        edtPas.setTypeface(G.font1);
        edtPasConfim.setTypeface(G.font1);
        edtPas.clearFocus();

        layRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (state == true) {

                    txtRegister.setText("* ورود به رایکا");
                    layForget.setVisibility(View.GONE);
                    btnLogin.setText("عضویت");
                    layConfim.setVisibility(View.VISIBLE);
                    state = false;

                } else {

                    txtRegister.setText("* همین الان عضورایکا میشم");
                    layForget.setVisibility(View.VISIBLE);
                    btnLogin.setText("ورود");
                    layConfim.setVisibility(View.GONE);
                    state = true;

                }

            }
        });

        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.i("GET", "loginAndRegister");
                loginAndRegister();

            }
        });

        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Log.i("GET", "loginAndRegister");
                dialog2.dismiss();
            }
        });

        dialog2.setCancelable(true);
        dialog2.show();

    }


    private void loginAndRegister() {

        edtUserName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorUserName = 0;
            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorUserName = 0;
            }


            @Override
            public void afterTextChanged(Editable arg0) {
                ErrorUserName = 0;
            }
        });

        edtPas.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorPassword = 0;
            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorPassword = 0;
            }


            @Override
            public void afterTextChanged(Editable arg0) {
                ErrorPassword = 0;
            }
        });
        edtPasConfim.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorConfimPassword = 0;
            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                ErrorConfimPassword = 0;
            }


            @Override
            public void afterTextChanged(Editable arg0) {
                ErrorConfimPassword = 0;
            }
        });

        CheckInternet checkInternet = new CheckInternet();
        if (checkInternet.Access()) {

            final String grant_type = "password";
            final String userName = edtUserName.getText().toString().trim();
            final String password = edtPas.getText().toString().trim();

            if (userName.length() < 5 && userName.length() >= 0) {
                ErrorUserName = 1;
            }

            if (password.length() < 5 && password.length() >= 0) {
                ErrorPassword = 1;
            }

            final Dialog dialog5 = new Dialog(G.currentActivity);
            dialog5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog5.setContentView(R.layout.dialog_error);
            TextView txtHeaderError = (TextView) dialog5.findViewById(R.id.txt_dialog_header);
            final TextView txtUserNameError = (TextView) dialog5.findViewById(R.id.txt_name_error);
            final TextView txtPassword = (TextView) dialog5.findViewById(R.id.txt_name_error);
            final TextView txtConfimPas = (TextView) dialog5.findViewById(R.id.txt_email_eror);
            final TextView txtChangeError = (TextView) dialog5.findViewById(R.id.txt_subject_error);
            LinearLayout btnOk = (LinearLayout) dialog5.findViewById(R.id.lay_ok_error);

            btnOk.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    txtUserNameError.setVisibility(View.GONE);
                    txtPassword.setVisibility(View.GONE);
                    txtConfimPas.setVisibility(View.GONE);
                    txtChangeError.setVisibility(View.GONE);
                    dialog5.dismiss();
                }
            });
            dialog5.setCancelable(false);

            if (ErrorUserName == 1) {
                txtUserNameError.setVisibility(View.VISIBLE);
                txtUserNameError.setText("نام کاربری حداقل باید بیش از 4 حرف باشد");
            }

            if (ErrorPassword == 1) {
                txtPassword.setVisibility(View.VISIBLE);
                txtPassword.setText("رمز عبور حداقل بیش از 4 حرف باید باشد");
            }

            if (state == false) {
                final String confimPas = edtPasConfim.getText().toString().trim();

                if ( !confimPas.equals(password)) {
                    ErrorConfimPassword = 1;
                }

                if (ErrorConfimPassword == 1) {
                    txtConfimPas.setVisibility(View.VISIBLE);
                    txtConfimPas.setText("رمز عبور وارده مطابقت ندارد");
                }

                if (ErrorUserName == 0 && ErrorPassword == 0 && ErrorConfimPassword == 0) {

                    sendRegister(userName, password, confimPas);

                    Log.i("GET", "" + userName + "   " + MD5.crypt(password) + "   " + confimPas);

                    // dialog2.dismiss();

                } else {
                    dialog5.show();
                }

            } else {

                if (ErrorUserName == 0 && ErrorPassword == 0) {

                    Log.i("GET", "ErrorUserName");

                    reciveLogin(userName, password);

                    // dialog2.dismiss();
                    Log.i("GET", " dialog2.dismiss()");

                } else {
                    dialog5.show();
                }

            }
        } else {
            Toast.makeText(G.context, "لطفا به اینترنت متصل شوید", Toast.LENGTH_SHORT).show();
        }
    }


    public void sendRegister(final String userName, final String password, final String confimPas) {
        try {

            // txtLoading.setVisibility(View.VISIBLE);
            String url = "";// WebServiceUrl.registerCustomer;

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {
                                //            JSONArray array = new JSONArray(respone);
                                JSONObject object = new JSONObject(response);
                                Log.i("LOG", " " + "respone2");
                                String accessToken = object.getString("message");

                                Toast.makeText(G.context, accessToken, Toast.LENGTH_SHORT).show();
                                dialog2.dismiss();
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(G.context, "خطادر عملیات ثبت ! لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            Toast.makeText(G.context, "خطادر عملیات ثبت ! لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                            //txtWaite.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("UserName", userName);
                    params.put("Password", MD5.crypt(password));
                    params.put("ConfirmPassword", MD5.crypt(confimPas));//aleki
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
            Log.i("ALL", " kjk" + e.toString());
            Toast.makeText(G.context, "خطادر عملیات ثبت ! لطفا دوباره تلاش کنید", Toast.LENGTH_LONG).show();
        }

    }


    public void reciveLogin(final String userName, final String password) {
        try {

            // txtLoading.setVisibility(View.VISIBLE);
            String url = "";// WebServiceUrl.LoginAddress;

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {

                                Log.i("LOG", " " + "reader response");
                                //            JSONArray array = new JSONArray(respone);
                                JSONObject object = new JSONObject(response);
                                String accessToken = object.getString("access_token");
                                String tokenType = object.getString("token_type");
                                String expiresIn = object.getString("expires_in");
                                String userName = object.getString("userName");
                                String issUed = object.getString(".issued");
                                String expires = object.getString(".expires");

                                G.DATALOGIN[0] = accessToken;
                                G.DATALOGIN[1] = tokenType;
                                G.DATALOGIN[2] = userName;
                                G.DATALOGIN[3] = issUed;
                                G.DATALOGIN[4] = expires;
                                G.DATALOGIN[5] = "1";
                                G.LoginOk = "1";

                                DataLogin DataLogin = new DataLogin();

                                DataLogin.setAccessToken(accessToken);
                                DataLogin.setTokenType(tokenType);
                                DataLogin.setExpiresIn(expiresIn);
                                DataLogin.setUserName(userName);
                                DataLogin.setIssUed(issUed);
                                DataLogin.setExpires(expires);

                                Log.i("LOG", " " + G.DATALOGIN[0] + "***" + G.DATALOGIN[1] + "****" + G.DATALOGIN[2] + "****" + G.DATALOGIN[3] + "****" + G.DATALOGIN[4]);
                                DataLogin.updateDateLogin();

                                Enhance.checkLoginOk();

                                Toast.makeText(G.context, "شما با موفقیت وارد شدید", Toast.LENGTH_SHORT).show();
                                dialog2.dismiss();
                            }

                            catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(G.context, "رمز عبور یا نام کاربری اشتباه است", Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("ALL", error.toString());
                            Toast.makeText(G.context, "رمز عبور یا نام کاربری اشتباه است", Toast.LENGTH_LONG).show();
                            //txtWaite.setVisibility(View.GONE);
                            //Toast.makeText(G.context, "لطفا دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("grant_type", "password");
                    params.put("username", userName);
                    params.put("password", MD5.crypt(password));
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
            Log.i("ALL", " kjk" + e.toString());
            Toast.makeText(G.context, "رمز عبور یا نام کاربری اشتباه است", Toast.LENGTH_LONG).show();
        }

    }


    public void dialogExit() {

        final Dialog dialog5 = new Dialog(G.currentActivity);
        dialog5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog5.setContentView(R.layout.dialog_exit_panel);

        TextView txtHeaderError = (TextView) dialog5.findViewById(R.id.txt_header_error);

        Button btnOk = (Button) dialog5.findViewById(R.id.btn_back);
        Button btnNo = (Button) dialog5.findViewById(R.id.btn_setting);

        btnOk.setTypeface(G.font1);
        txtHeaderError.setTypeface(G.font1);
        btnNo.setTypeface(G.font1);

        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                txtLogin.setText("ورود و ثبت نام");
                layPanel.setVisibility(View.GONE);
                txtUserName.setText("هوجی بوجی");

                G.DATALOGIN[5] = "0";
                G.LoginOk = "0";

                DataLogin DataLogin = new DataLogin();
                DataLogin.updateExitLogin();

                checkLoginOk();

                Toast.makeText(G.context, "شما با موفقیت خارج شدید", Toast.LENGTH_SHORT).show();
                dialog5.dismiss();
            }
        });

        btnNo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(false);
        dialog5.show();

    }


    public static void sendJustOneAndroidId() {
        final Check ostan = new Check();
        int y = ostan.getLog();
        if (y == 0) {
            try {
                String url = "";//WebServiceUrl.getDataPouplar;
                Log.i("SSS", url);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                Log.i("ALL", " " + response);
                                if (response == "ok") {
                                    ostan.updateLog();
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
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String strDate = sdf.format(c.getTime());

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("Id", G.GetDeviceID);
                        params.put("version", G.verApp);
                        params.put("Date", strDate);

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


    public static void getNewev() {
        try {
            String url = ""; //WebServiceUrl.BaseApiAddress//G.url + "updateapp.aspx?" + "p1=" + URLEncoder.encode(G.verApp, "UTF-8");

            RequestQueue queue = Volley.newRequestQueue(G.context);
            StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(final String response) {

                            if ( !response.equals("")) {
                                //Toast.makeText(G.context, response, Toast.LENGTH_SHORT).show();

                                final Dialog dialog2 = new Dialog(G.currentActivity);
                                dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                dialog2.setContentView(R.layout.dialog_error);
                                Button btn_back = (Button) dialog2.findViewById(R.id.btn_back);
                                btn_back.setTypeface(G.font1);
                                btn_back.setOnClickListener(new OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {
                                        String url = response;
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        G.currentActivity.startActivity(i);
                                        dialog2.dismiss();
                                    }
                                });
                                dialog2.setCancelable(false);
                                dialog2.show();

                            } else {}

                        }
                    },
                    new Response.ErrorListener()
                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {}
                    });
            queue.add(postRequest);
        }
        catch (Exception e) {
            Log.i("LOG", " kjk" + e.toString());
        }

    }

}
