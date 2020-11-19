package com.raykaco.android.customcontrol;

import java.util.ArrayList;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.raykaco.andriod.G;
import com.raykaco.andriod.R;
import com.raykaco.andriod.internet.StructSlider;


public class ImagePagerAdapter extends PagerAdapter {

    public ArrayList<StructSlider> imageIds;
    public ArrayList<StructSlider> imageTitles;
    ImageLoader                    imageLoader = G.getInstance().getImageLoader();


    public ImagePagerAdapter(ArrayList<StructSlider> imageIds) {
        this.imageIds = imageIds;
    }


    @Override
    public int getCount() {
        return imageIds.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = G.inflater.inflate(R.layout.sample, null);

        NetworkImageView imageSlider = (NetworkImageView) view.findViewById(R.id.img_slider);
        ImageView imageSliderDefault = (ImageView) view.findViewById(R.id.img_default);

        if ( !imageIds.get(position).imgAddress.equals("no")) {

            imageSlider.setVisibility(View.VISIBLE);
            imageSliderDefault.setVisibility(View.GONE);

            String link = imageIds.get(position).imgAddress.replaceAll("\\\\", "/");

            imageLoader.get(imageIds.get(position).imgAddress, ImageLoader.getImageListener(imageSlider, R.drawable.loading, R.drawable.ad_big_1));

            imageSlider.setImageUrl(imageIds.get(position).imgAddress, imageLoader);

        } else {

            imageSliderDefault.setVisibility(View.VISIBLE);
            imageSlider.setVisibility(View.GONE);

            Log.i("PPP", "http:// no");
            imageSliderDefault.setImageResource(R.drawable.ad_big_1);

        }
        container.addView(view);
        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
