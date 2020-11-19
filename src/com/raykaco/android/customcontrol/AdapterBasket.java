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
import com.raykaco.andriod.BasketProduct;
import com.raykaco.andriod.Enhance;
import com.raykaco.andriod.G;
import com.raykaco.andriod.R;
import com.raykaco.andriod.ShowProduct;
import com.raykaco.andriod.database.BasketProductDB;


public class AdapterBasket extends ArrayAdapter<StrucTopic> {

    public AdapterBasket(ArrayList<StrucTopic> array) {
        super(G.context, R.layout.item_basket, array);

    }


    private static class ViewHolder {

        LinearLayout       imgDelete;
        NewControlTextView txtTitle;
        NewControlTextView txtPrice;
        NewControlTextView txtTeacherName;
        LinearLayout       layItem;


        public ViewHolder(View view) {

            txtTitle = (NewControlTextView) view.findViewById(R.id.txt_title_product);
            txtPrice = (NewControlTextView) view.findViewById(R.id.txt_price_product);
            txtTeacherName = (NewControlTextView) view.findViewById(R.id.txt_teacher_name);
            imgDelete = (LinearLayout) view.findViewById(R.id.lay_img_delete);
            layItem = (LinearLayout) view.findViewById(R.id.lay_item);

        }


        public void fill(ArrayAdapter<StrucTopic> adapter, final StrucTopic item, final int position) {
            txtTitle.setText(" " + item.name);
            txtPrice.setText(" " + item.price);
            txtTeacherName.setText(" " + item.nameTeacher);

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

            imgDelete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    BasketProductDB BasketProductDB = new BasketProductDB();
                    BasketProductDB.setId(item.id);
                    BasketProductDB.deleteProduct();

                    Enhance.addProduct();

                    BasketProduct.DataPouplar.remove(position);
                    BasketProduct.adapterListProduct.notifyDataSetChanged();

                    String pri = item.price;
                    String pri2 = pri.replaceAll("﷼", "");
                    pri2 = pri2.replace(",", "").trim();
                    long str = Long.parseLong(BasketProduct.txtTotalyPrice.getText().toString()) - Long.parseLong(pri2);

                    BasketProduct.txtTotalyPrice.setText("" + str);

                }
            });

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        StrucTopic item = getItem(position);
        if (convertView == null) {
            convertView = G.inflater.inflate(R.layout.item_basket, parent, false);
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