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
import android.widget.LinearLayout;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.raykaco.andriod.G;
import com.raykaco.andriod.R;
import com.raykaco.andriod.ShowProduct;


public class AdapterTeacherList extends ArrayAdapter<StrucRezumeh> {

    public AdapterTeacherList(ArrayList<StrucRezumeh> array) {
        super(G.context, R.layout.item_teacher, array);

    }


    private static class ViewHolder {

        NewControlTextView       txtName;
        NewControlTextView       txtHeaderRezumeh;
        NewControlTextView       txtRezumeh1;
        NewControlTextView       txtRezumeh2;
        NewControlTextView       txtRezumeh3;

        ImageLoader              imageLoader = G.getInstance().getImageLoader();
        CircularNetworkImageView thumbNail;
        NetworkImageView         thumbPicPro;

        LinearLayout             layItem;


        public ViewHolder(View view) {

            txtName = (NewControlTextView) view.findViewById(R.id.txt_name_teacher);
            txtHeaderRezumeh = (NewControlTextView) view.findViewById(R.id.txt_header_rezumeh_teacher);
            txtRezumeh1 = (NewControlTextView) view.findViewById(R.id.txt_rezuneh1);
            txtRezumeh2 = (NewControlTextView) view.findViewById(R.id.txt_rezuneh2);
            txtRezumeh3 = (NewControlTextView) view.findViewById(R.id.txt_rezuneh3);

            thumbNail = (CircularNetworkImageView) view.findViewById(R.id.img_teacher);
            thumbPicPro = (NetworkImageView) view.findViewById(R.id.img_pro);

            layItem = (LinearLayout) view.findViewById(R.id.lay_itemm);

        }


        public void fill(ArrayAdapter<StrucRezumeh> adapter, final StrucRezumeh item, int position) {

            txtName.setText(" " + item.name);

            txtHeaderRezumeh.setText(" " + item.header);

            txtRezumeh1.setText(" " + item.rezumeh1);
            txtRezumeh2.setText(" " + item.rezumeh2);
            txtRezumeh3.setText(" " + item.rezumeh3);

            Log.i("PIC", "Percent: " + item.header);
            Log.i("PIC", "Percent: " + item.name);

            imageLoader.get(item.picTeacher, ImageLoader.getImageListener(thumbNail, R.drawable.loading, R.drawable.no_teacher_pic));
            thumbNail.setImageUrl(item.picTeacher, imageLoader);

            layItem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intentm = new Intent(G.currentActivity, ShowProduct.class);
                    // intentm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    intentm.putExtra("id", item.id);
                    G.currentActivity.startActivity(intentm);
                }
            });

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucRezumeh item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_teacher, parent, false);
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