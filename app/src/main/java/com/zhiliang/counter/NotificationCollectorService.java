package com.zhiliang.counter;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationCollectorService extends NotificationListenerService {
  @Override
  public void onNotificationPosted(StatusBarNotification sbn) {
    Log.i("xiaolong", "open" + "-----" + sbn.getPackageName());
    Log.i("xiaolong", "open" + "------" + sbn.getNotification().tickerText);
    Log.i("xiaolong", "open" + "-----" + sbn.getNotification().extras.get("android.title"));
    Log.i("xiaolong", "open" + "-----" + sbn.getNotification().extras.get("android.text"));
  }

  @Override
  public void onNotificationRemoved(StatusBarNotification sbn) {
    Log.i("xiaolong", "remove" + "-----" + sbn.getPackageName());

  }
}