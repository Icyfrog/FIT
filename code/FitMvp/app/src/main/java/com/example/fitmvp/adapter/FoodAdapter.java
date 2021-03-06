package com.example.fitmvp.adapter;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSeekBar;

import com.example.fitmvp.R;
import com.example.fitmvp.bean.FoodItem;

import java.util.LinkedList;

public class FoodAdapter extends BaseAdapter{
    private LinkedList<FoodItem> fdata;
    private Context fContext;
    private boolean isClick = false;
    public FoodAdapter(LinkedList<FoodItem> fdata,Context fContext){
        this.fdata=fdata;
        this.fContext=fContext;
    }
    @Override
    public int getCount(){
        return fdata.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(fContext).inflate(R.layout.food_item, parent, false);
        ImageView foodpic = (ImageView)convertView.findViewById(R.id.foodipic);
        TextView foodname = (TextView) convertView.findViewById(R.id.foodiname);
        TextView foodmuch = (TextView) convertView.findViewById(R.id.foodimuch);
        TextView fooden= (TextView) convertView.findViewById(R.id.foodienergy);
        //AppCompatSeekBar show2_pressure = (AppCompatSeekBar) convertView.findViewById(R.id.show2_pressure);
        foodpic.setImageBitmap(fdata.get(position).getBitmap());
        foodname.setText(fdata.get(position).getFoodname());
        String s1=fdata.get(position).getWeight().toString();
        foodmuch.setText(s1);
        String s2=fdata.get(position).getEnergy().toString();
        fooden.setText(s2);
//       final Integer finalen1=fdata.get(0).getEnergy();
//       final Integer finalen2=fdata.get(1).getEnergy();
//        final Integer finalen3=fdata.get(2).getEnergy();
//        final Integer finalen4=fdata.get(3).getEnergy();
//        show2_pressure.setProgress(0);
//        String haha1="50";
//        foodmuch.setText(haha1);
//        show2_pressure.setMax(1450);
//        String haha2="100";
//        foodmuch.setText(haha2);
//        Integer kj2=(Integer.valueOf(haha2))*finalen1/100;String kjs2=kj2.toString();
//        fooden.setText(kjs2);
//        show2_pressure.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                int p=50+progress;
//                int q=p*finalen1/100;
//                String miao="" + String.valueOf(p);
//                String ju=""+String.valueOf(q);
//                foodmuch.setText(miao);// 50为进度条滑到最小值时代表的数值
//                fooden.setText(ju);
//            }
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {}
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {}
//        });
        return convertView;
    }
    public void setClick(boolean click) {
        this.isClick = click;
    }
}
