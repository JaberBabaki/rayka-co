package com.raykaco.android.customcontrol;

import java.util.ArrayList;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.raykaco.andriod.G;
import com.raykaco.andriod.R;
import com.raykaco.andriod.ShowProduct;
import com.raykaco.andriod.StrucItem;
import com.raykaco.andriod.internet.WebServiceUrl;


public class AdapterCommodityList extends ArrayAdapter<StrucItem> {

    public AdapterCommodityList(ArrayList<StrucItem> array) {
        super(G.context, R.layout.item, array);

    }


    private static class ViewHolder {

        NewControlTextView       txtName;
        NewControlTextView       txtsalary;
        NewControlTextView       txtDiscountPrice;
        NewControlTextView       txtTeacher;
        NewControlTextView       txtNamePicture;
        NewControlTextView       txtViewProduct;

        ImageView                imgTeacher;
        ImageView                imgFilm;
        ImageView                imgDiscount;

        ImageLoader              imageLoader  = G.getInstance().getImageLoader();
        ImageLoader              imageLoader2 = G.getInstance().getImageLoader();
        CircularNetworkImageView thumbNail;
        NetworkImageView         thumbPicPro;

        LinearLayout             layDiscount;
        LinearLayout             layItem;


        //  int[]              img         = new int[]{ R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.image5, R.drawable.image6 };

        public ViewHolder(View view) {

            txtName = (NewControlTextView) view.findViewById(R.id.txt_name);
            txtsalary = (NewControlTextView) view.findViewById(R.id.txt_salary);
            txtDiscountPrice = (NewControlTextView) view.findViewById(R.id.txt_discount);
            txtTeacher = (NewControlTextView) view.findViewById(R.id.txt_teacher);
            txtNamePicture = (NewControlTextView) view.findViewById(R.id.txt_name_in_picture);
            txtViewProduct = (NewControlTextView) view.findViewById(R.id.txt_view_product);
            // imgTeacher = (ImageView) view.findViewById(R.id.roundedCornerImageView1);
            // imgFilm = (ImageView) view.findViewById(R.id.img_film);

            thumbNail = (CircularNetworkImageView) view.findViewById(R.id.img_teacher);
            thumbPicPro = (NetworkImageView) view.findViewById(R.id.img_pro);

            imgDiscount = (ImageView) view.findViewById(R.id.img_discount);

            layDiscount = (LinearLayout) view.findViewById(R.id.lay_discount);
            layItem = (LinearLayout) view.findViewById(R.id.lay_itemm);

        }


        public void fill(ArrayAdapter<StrucItem> adapter, final StrucItem item, int position) {
            //   try {
            if (item.name.replaceAll("آموزش تصویری", " ").length() >= 30) {
                txtName.setText(" " + item.name.replaceAll("آموزش تصویری", " ").substring(0, 27));
                txtNamePicture.setText(" " + item.name.replaceAll("آموزش تصویری", " ").substring(0, 27));
            } else {
                txtName.setText(" " + item.name.replaceAll("آموزش تصویری", " "));
                txtNamePicture.setText(" " + item.name.replaceAll("آموزش تصویری", " "));
            }

            txtsalary.setText(" " + item.price);

            txtTeacher.setText(" " + item.nameTeacher);

            // if (item.var == 2) {
            //String dtr =""+item.discountPrice;
            String dtr = item.price.substring(0, (item.price.length() - 1)).replace(",", "").trim();
            Log.i("ERR", "ddd : " + dtr + "  " + item.discountPrice);
            int dis = Integer.parseInt(dtr);
            layDiscount.setVisibility(View.VISIBLE);
            imgDiscount.setVisibility(View.VISIBLE);
            int num = dis * (100 - item.discountPrice) / 100;
            txtDiscountPrice.setText(num + " ریال");
            txtViewProduct.setText("" + item.view);
            //}
            if (item.var == 1) {
                //layDiscount.setVisibility(View.GONE);
                //imgDiscount.setVisibility(View.GONE);
            }

            Log.i("PIC", "Percent: " + item.picTeache);
            Log.i("PIC", "Percent: " + item.picFilm);
            //  G.mRequestQueue.getCache().clear();
            //Log.i("PIC", "Percent: " + thumbNail);
            // G.mRequestQueue.getCache().remove(WebServiceUrl.getPictureTeacher + item.picTeache);
            imageLoader.get(WebServiceUrl.getPictureTeacher + item.picTeache, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.no_teacher_pic));
            thumbNail.setImageUrl(WebServiceUrl.getPictureTeacher + item.picTeache, imageLoader);

            imageLoader2.get(WebServiceUrl.getProductPicture + item.picFilm, ImageLoader.getImageListener(thumbPicPro, R.drawable.loading, R.drawable.image));
            thumbPicPro.setImageUrl(WebServiceUrl.getProductPicture + item.picFilm, imageLoader2);
            //  imgFilm.setImageResource(item.film);
            //imgTeacher.setImageResource(item.pic);
            layItem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intentm = new Intent(G.currentActivity, ShowProduct.class);
                    // intentm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    intentm.putExtra("id", item.id);
                    G.currentActivity.startActivity(intentm);
                }
            });

            //}
            /* catch (Exception f) {
                 Log.i("ERR", "Percent: " + f.toString());
             }*/

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucItem item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        Animation animation = AnimationUtils.loadAnimation(G.context,
                android.R.anim.slide_in_left);
        convertView.startAnimation(animation);
        return convertView;
    }
}