package com.example.fitmvp.contract;

import java.util.List;

import cn.jpush.im.android.api.model.UserInfo;

public interface FriendSearchContract {
    interface Model {
    }

    interface View {
        void setSearchList(List<UserInfo> list);

    }

    interface Presenter {
        void search(String phone);
        Boolean isFriend(UserInfo friend);
    }
}
