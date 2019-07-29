package com.example.fitmvp.view.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.base.BaseFragment;
import com.example.fitmvp.bean.RecordBean;
import com.example.fitmvp.contract.MainPageContract;
import com.example.fitmvp.presenter.MainPagePresenter;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.activity.PhotoType;
import com.example.fitmvp.view.activity.RecordDetailActivity;
import com.example.fitmvp.view.draw.CalorieCircle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FragmentMainpage extends BaseFragment<MainPagePresenter> implements MainPageContract.View {
    private LinearLayout progress;
    private LinearLayout progress_circle;
    private LinearLayout empty_record;
    private RecyclerView recyclerView;
    private CalorieCircle calorieCircle;
    private FloatingActionButton takePhoto;
    private BaseAdapter<RecordBean> adapter;
    // 样例数据，后续需要从数据库中获得
    private List<RecordBean> records = new ArrayList<>();

    @Override
    protected void initListener() {
        takePhoto.setOnClickListener(this);
    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.mainpage;
    }

    @Override
    protected MainPagePresenter loadPresenter() {
        return new MainPagePresenter();
    }

    @Override
    protected void initView() {
        progress = view.findViewById(R.id.progress);
        progress_circle = view.findViewById(R.id.progress_circle);
        empty_record = view.findViewById(R.id.empty_record);

        recyclerView = view.findViewById(R.id.rcyc_record);
        calorieCircle = view.findViewById(R.id.calCircle);

        takePhoto = view.findViewById(R.id.takePhoto);

        // 初始时显示正在加载数据，环形统计图和数据列表不可见
        hideCircle();
        hideList();

        // 设置layout方式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        // 创建adapter实例
        adapter = new BaseAdapter<RecordBean>(records) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.record_item;
            }

            @Override
            public void convert(BaseAdapter.MyHolder holder, RecordBean data, int position) {
                holder.setText(R.id.record_title, data.getFood());
                holder.setText(R.id.record_weight, String.format(Locale.getDefault(), "%.2f", data.getWeight()));
                holder.setText(R.id.record_timestamp, data.getTimeStamp());
                holder.setText(R.id.record_cal, String.format(Locale.getDefault(), "%.2f", data.getCal()));
            }
        };

        // 设置Adapter的事件监听
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), RecordDetailActivity.class);
                // 传参
                RecordBean item = records.get(position);
                intent.putExtra("food",item.getFood());
                intent.putExtra("timeStamp", item.getTimeStamp());
                intent.putExtra("weight", String.format(Locale.getDefault(), "%.2f", item.getWeight()));
                intent.putExtra("cal", String.format(Locale.getDefault(), "%.2f", item.getCal()));
                intent.putExtra("pro", String.format(Locale.getDefault(), "%.2f", item.getProtein()));
                intent.putExtra("fat", String.format(Locale.getDefault(), "%.2f", item.getFat()));
                intent.putExtra("carbohydrate", String.format(Locale.getDefault(), "%.2f", item.getCarbohydrate()));
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View view, int position){}
        });
        // 设置adapter
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        // 获取热量记录
        mPresenter.getCalValue();
        // 获取最新五条识别记录
        mPresenter.getList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.takePhoto:
                Intent intent = new Intent(getActivity(), PhotoType.class);
                startActivity(intent);
                break;
        }
    }

    public void setCircleValue(final double target, final double current){
        new Handler().post(new Runnable() {
            public void run() {
                calorieCircle.setCurProgress(current);
                calorieCircle.setTargetProgress(target);
                showCircle();
            }
        });
    }

    public void setList(List<RecordBean> list){
        //records = list;
        records.clear();
        records.addAll(list);
    }

    public void addList(List<RecordBean> list){
        records.addAll(list);
    }

    public void updateList(){
        new Handler().post(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
                showList();
            }
        });
    }

    private void showCircle(){
        calorieCircle.setVisibility(View.VISIBLE);
        progress_circle.setVisibility(View.GONE);
    }

    private void hideCircle(){
        calorieCircle.setVisibility(View.GONE);
        progress_circle.setVisibility(View.VISIBLE);
    }

    private void showList(){
        recyclerView.setVisibility(View.VISIBLE);
        empty_record.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
    }

    private void hideList(){
        recyclerView.setVisibility(View.GONE);
        empty_record.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    public void showEmptyList(){
        records.clear();
        recyclerView.setVisibility(View.GONE);
        empty_record.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }
}
