package com.raykaco.andriod;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.raykaco.andriod.database.DataAccess;
import com.raykaco.andriod.internet.LruBitmapCache;


public class G extends Application {

    public static Context        context;
    public static Typeface       font1;
    public static Typeface       font2;
    public static LayoutInflater inflater;
    public static Activity       currentActivity;

    public static final String   DIR_SDCARD   = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String         DB_PATH      = DIR_SDCARD + "/rayka_co/";
    public static String         DB_NAME_Ass  = "db/rayka2.db";
    public static String         DB_NAME      = "rayka2.db";

    public static String         FONT1_NAME   = "font/IRAN-Sans-Light.otf";
    public static String         FONT2_NAME   = "font/IRAN-Sans-Light.otf";

    private static G             mInstance;
    private ImageLoader          mImageLoader;
    public static RequestQueue   mRequestQueue;
    public static final String   TAG          = G.class.getSimpleName();

    public static String         countyRecive = "12";

    public static String[]       DATALOGIN    = new String[6];
    public static String         LoginOk      = "0";

    public static String         GetDeviceID;
    public static String         verApp       = "1.1";


    @Override
    public void onCreate() {

        super.onCreate();

        mInstance = this;

        context = getApplicationContext();

        DataAccess dataAccess = new DataAccess();
        dataAccess.copyDatabase();

        font1 = Typeface.createFromAsset(context.getAssets(), FONT1_NAME);
        font2 = Typeface.createFromAsset(context.getAssets(), FONT2_NAME);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //GetDeviceID = Secure.getString(G.context.getContentResolver(), Secure.ANDROID_ID);
        BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        GetDeviceID = m_BluetoothAdapter.getAddress();

    }


    public static synchronized G getInstance() {
        return mInstance;
    }


    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static String[] cat             = { "مدرس", "محصولات Cisco ", "محصولات Microsoft", "محصولات Linux",
                                           "محصولات Mikrotic", "محصولات Comptia", "محصولات Management", "محصولات VMware", "محصولات برنامه نویسی",
                                           "کتاب", "گواهینامه ها", "تیشرت های رایکا", "فروش ویژه" };

    public static String[] subCatTeacher   = { "مهندس مجید اسدپور", "مهندس علی شریعتی", "مهندس بهروز صالح پور", "مهندس یاسین علیزاده",
                                           "مهندس نیما حسینی", "مهندس سامان کلیایی", "مهندس بهاره فاطمه جهرمی", "مهندس پژمان محمد علی نژاد", "مهندس پدارم شاه صفی",
                                           "مهندس پریسا گرگین نیا", "مهندس مهدی گرگین نیا", "مهندس سید حسین رجاء" };

    public static String[] subCatCisco     = { "Routing & Switching", "Security", "Design", "Service Provider" };
    public static String[] subCatMicrosoft = { "Microsoft2008", "Microsoft2012", "SharePoint" };

    public static String[] subCatBooks     = { "کتاب های سیسکو ", "کتاب های لینوکس", "کتاب های مایکروسافت" };
}