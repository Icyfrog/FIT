# 使用JMessage实现好友聊天功能

## [Android IM SDK 集成指南](https://docs.jiguang.cn/jmessage/client/jmessage_android_guide/)

[注册信息](https://www.jiguang.cn/dev/#/app/ff4a6be471a248b2325698d3/info)

appkey: ff4a6be471a248b2325698d3

## 使用的接口

### SDK初始化

```java
JMessageClient.init(Context context);
```

在Application类中调用

### 注册（可以放在后端进行）

```java
JMessageClient.register(String userName, String password, RegisterOptionalUserInfo optionalUserInfo, BasicCallback callback);
```

参数说明

+ ```String username``` 用户名
+ ```String password``` 用户密码
+ ```RegisterOptionalUserInfo optionalUserInfo``` 注册时的用户其他信息
+ ```BasicCallback callback``` 结果回调

### 登录

```java
JMessageClient.login(String username, String password, BasicCallback callback);
```

### 登出

```java
JMessageClient.logout();
```

### 修改密码

应该需要在后端进行

```java
JMessageClient.updateUserPassword(String oldPassword, String newPassword, BasicCallback callback);
```

### 管理好友

+ 搜索-获取用户信息 在```FriendSearchPresenter```中
+ 发送好友请求 在```FriendAddPresenter```中

```java
  public static void sendInvitationRequest(final String targetUsername,String appKey, final String reason, final BasicCallback callback)
```

+ 监听其他用户发来的加好友请求
  
  *问题：只有用户在登录状态时才可以接收到事件，离线时发送的事件在登录后会收不到*
  
```java
public void onEvent(ContactNotifyEvent event){
       // ......
    }
```

+ 同意/拒绝好友请求 在```FriendDetailPresenter```中
+ 删除好友 ***待完成***

### 发消息

目前只支持单聊，只能发送文字和emoji，相关代码在./chat/中

消息发送界面复用了JChat Android中的代码  [github 地址](https://github.com/jpush/jchat-android.git)

接收在线/离线消息，更新消息列表 写在```FragmentFriend```中
