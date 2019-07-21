package com.example.fitmvp.view.fragment.friends;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.base.BaseFragment;
import com.example.fitmvp.bean.ConversationEntity;
import com.example.fitmvp.chat.activity.ChatActivity;
import com.example.fitmvp.database.UserEntry;
import com.example.fitmvp.presenter.MessagePresenter;
import com.example.fitmvp.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;


public class FragmentMsg extends BaseFragment<MessagePresenter>
        implements View.OnClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

    private List<ConversationEntity> convList = new ArrayList<>();
    BaseAdapter<ConversationEntity> adapter;

    @Override
    protected Integer getLayoutId(){
        return R.layout.message;
    }

    @Override
    protected MessagePresenter loadPresenter() {
        return new MessagePresenter();
    }

    @Override
    protected void initView(){
        recyclerView = ButterKnife.findById(view,R.id.message_list);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initData(){
        convList = mPresenter.getConvList();
        TextView emptyList = ButterKnife.findById(view,R.id.empty_msg_list);
        if(convList.size()==0 || convList==null){
            emptyList.setVisibility(View.VISIBLE);
        }
        else {
            emptyList.setVisibility(View.GONE);
        }
        adapter = new BaseAdapter<ConversationEntity>(convList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.message_item;
            }

            @Override
            public void convert(final MyHolder holder, ConversationEntity data, int position) {
                holder.setText(R.id.message_name,data.getTitle());
                holder.setText(R.id.message,data.getMessage());
                holder.setText(R.id.message_time,data.getTime());
                // 设置头像
                if(data.getAvatar()!=null){
                    holder.setImage(R.id.message_photo, BitmapFactory.decodeFile(data.getAvatar()));
                }
                else{
                    JMessageClient.getUserInfo(data.getUsername(), new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int i, String s, UserInfo userInfo) {
                            if(i == 0){
                                userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                                    @Override
                                    public void gotResult(int i, String s, Bitmap bitmap) {
                                        if (i == 0) {
                                            holder.setImage(R.id.message_photo,bitmap);
                                        }else {
                                            // 设置为默认头像
                                            holder.setImage(R.id.message_photo,R.drawable.default_portrait80);
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
                // 设置未读信息
                if(data.getNewMsgNum()>0){
                    holder.setText(R.id.new_msg_number,String.format("%d",data.getNewMsgNum()));
                    holder.setVisible(R.id.new_msg_number,View.VISIBLE);
                }
                else{
                    holder.setVisible(R.id.new_msg_number,View.GONE);
                }
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ConversationEntity entity = convList.get(position);
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(BaseApplication.CONV_TITLE, entity.getTitle());
                intent.putExtra(BaseApplication.TARGET_ID, entity.getUsername());
                intent.putExtra(BaseApplication.TARGET_APP_KEY, BaseApplication.getAppKey());
                startActivity(intent);
                Conversation conversation = JMessageClient.getSingleConversation(entity.getUsername(), BaseApplication.getAppKey());
                conversation.setUnReadMessageCnt(0);
                updateData();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void updateData(){
        convList = mPresenter.getConvList();
        TextView emptyList = ButterKnife.findById(view,R.id.empty_msg_list);
        if(convList.size()==0 || convList==null){
            emptyList.setVisibility(View.VISIBLE);
        }
        else {
            emptyList.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void initListener(){}
    @Override
    public void onClick(View view){}

    public void onEvent(MessageEvent event) {
        updateData();
        LogUtils.e("接收在线消息","...");
    }

    /**
     * 接收离线消息
     *
     * @param event 离线消息事件
     */
    public void onEvent(OfflineMessageEvent event) {
        updateData();
        LogUtils.e("接收离线消息","...");
    }

}
