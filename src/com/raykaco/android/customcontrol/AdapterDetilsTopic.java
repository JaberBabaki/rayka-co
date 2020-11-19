package com.raykaco.android.customcontrol;

import java.util.ArrayList;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import com.raykaco.andriod.G;
import com.raykaco.andriod.R;
import com.raykaco.andriod.ShowProduct;


public class AdapterDetilsTopic extends ArrayAdapter<StrucTopic> {

    public AdapterDetilsTopic(ArrayList<StrucTopic> array) {
        super(G.context, R.layout.item_faq, array);

    }


    private static class ViewHolder {

        NewControlTextView txtName;
        LinearLayout       layItem;


        public ViewHolder(View view) {

            txtName = (NewControlTextView) view.findViewById(R.id.txt_title);
            layItem = (LinearLayout) view.findViewById(R.id.lay_item);

        }


        public void fill(ArrayAdapter<StrucTopic> adapter, final StrucTopic item, int position) {
            txtName.setText(" " + item.name);

            layItem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (item.id != -1) {
                        Intent intentm = new Intent(G.currentActivity, ShowProduct.class);
                        // intentm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        intentm.putExtra("id", item.id);
                        G.currentActivity.startActivity(intentm);
                    }
                }
            });

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucTopic item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_faq, parent, false);
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