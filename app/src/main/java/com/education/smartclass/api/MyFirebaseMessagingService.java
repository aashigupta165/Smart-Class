package com.education.smartclass.api;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.education.smartclass.R;
import com.education.smartclass.roles.common.activities.SplashScreenActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService  extends FirebaseMessagingService {

        private static final String ADMIN_CHANNEL_ID = "abc";
        private static final String CHANNEL_ID = "SmartApp" + "";

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

            Log.e("Notification Message", "onMessageReceived: " + remoteMessage.getData().toString() );

            try {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData());
                Log.e("MY JSON", "onMessageReceived: " + jsonObject.toString());
//                String refreshData = jsonObject.getString("refreshData");

                if (jsonObject.getString("success").equals(1)){
                    String header = jsonObject.getString("header");
                    String text = jsonObject.getString("text");
//                    sendNotification("hey", "hello");
                }
//                if (refreshData.equalsIgnoreCase("true")) {

//                    if (!jsonObject.has("pickup")) {
//                        final MediaPlayer mp = MediaPlayer.create(this, R.raw.error);
//                        mp.start();
//                        Vibrator aa = (Vibrator) this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
//                        aa.vibrate(1000);



//                        NotificationManager mNotificationManager =
//                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

//                            Log.e("onMessageReceived", "onMessageReceived: " + "1");
//                            NotificationChannel channel = new NotificationChannel("500",
//                                    "PUSH",
//                                    NotificationManager.IMPORTANCE_DEFAULT);
//                            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
//                            mNotificationManager.createNotificationChannel(channel);
//                        }
//                        Log.e("onMessageReceived", "onMessageReceived: " + "2");
                    /*NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "500")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                    R.drawable.notification_logo)) // notification icon
                            .setContentTitle(header) // title for notification
                            .setContentText(text)
                            .setAutoCancel(true); // clear notification after click*/

//                        Log.e("onMessageReceived", "onMessageReceived: ---------"  +StaticFields.ACTIVE );
//
//                        if (StaticFields.ACTIVE == 1) {
//                            Log.e("onMessageReceived", "onMessageReceived: " + "3");
//                            Intent a = new Intent(getApplicationContext(), SplashActivity.class);
//                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                            startActivity(a);
//                        } else {
//                            if (StaticFields.ACTIVE == 2) {
//                                Log.e("onMessageReceived", "onMessageReceived: " + "4");
//                                Intent a = new Intent(getApplicationContext(), SplashActivity.class);
//                                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                                startActivity(a);

//                            Log.e("onMessageReceived", "onMessageReceived: " + "5");
//                            PackageManager manager = getPackageManager();
//                            Intent i = manager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
//                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            i.addCategory(Intent.CATEGORY_LAUNCHER);
//                            startActivity(i);
//                            }else{
//                                Log.e("onMessageReceived", "onMessageReceived: " + "5");
//                                PackageManager manager = getPackageManager();
//                                Intent i = manager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                i.addCategory(Intent.CATEGORY_LAUNCHER);
//                                startActivity(i);
//                            }
//                        }
//                        Log.e("onMessageReceived", "onMessageReceived: " + "6");

//                        sendNotification(header,text);
                 /*   Intent notificationIntent = new Intent(getApplicationContext(), SplashActivity.class);


//                Intent openSplash = new Intent(getApplicationContext(), SplashActivity.class);
//                startActivity(notificationIntent);
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP

                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                            notificationIntent, 0);

                    mBuilder.setContentIntent(intent);
                    mNotificationManager.notify(0, mBuilder.build());*/

//                    }else{
//                        if (StaticFields.ACTIVE == 1) {
//                            Log.e("onMessageReceived", "onMessageReceived: " + "3");
//                            Intent a = new Intent(getApplicationContext(), SplashActivity.class);
//                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                            startActivity(a);
//                        } else {
//                            if (StaticFields.ACTIVE == 2) {
//                                Log.e("onMessageReceived", "onMessageReceived: " + "4");
//                                Intent a = new Intent(getApplicationContext(), SplashActivity.class);
//                                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                                startActivity(a);
//                            }else{
//                                Log.e("onMessageReceived", "onMessageReceived: " + "5");
//                                PackageManager manager = getPackageManager();
//                                Intent i = manager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                i.addCategory(Intent.CATEGORY_LAUNCHER);
//                                startActivity(i);
//                            }
//                        }
//                    }

//                }else{
//
//                    //Location Not Received
//
//                    String text = "";
//
//                    text = jsonObject.getString("text");
//
//                    if (text.equalsIgnoreCase("Location Not Received")){
//
//
//                        if (StaticFields.ACTIVE == 1) {
                        /*Intent a = new Intent(getApplicationContext(), SplashActivity.class);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(a);*/

                            //Check for service


//                        } else {
//                            if (StaticFields.ACTIVE == 2) {
//                                Log.e("onMessageReceived", "onMessageReceived: " + "4");
                            /*Intent a = new Intent(getApplicationContext(), SplashActivity.class);
                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(a);*/


//                            Log.e("onMessageReceived", "onMessageReceived: " + "5");
//                            PackageManager manager = getPackageManager();
//                            Intent i = manager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
//                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            i.addCategory(Intent.CATEGORY_LAUNCHER);
//                            startActivity(i);
//                            }else{
//                                Log.e("onMessageReceived", "onMessageReceived: " + "5");
//                                PackageManager manager = getPackageManager();
//                                Intent i = manager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                i.addCategory(Intent.CATEGORY_LAUNCHER);
//                                startActivity(i);
//                            }
//                        }
//
//
//                        return;
//                    }
//
//                    if (StaticFields.ACTIVE == 1){
//                        Intent a = new Intent(getApplicationContext(),SplashActivity.class);
//                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                        startActivity(a);
//                    }else{
//                        if (StaticFields.ACTIVE == 2){
//                            PackageManager manager = getPackageManager();
//                            Intent i = manager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
//                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            i.addCategory(Intent.CATEGORY_LAUNCHER);
//                            startActivity(i);
//                        }else{
//                            PackageManager manager = getPackageManager();
//                            Intent i = manager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
//                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            i.addCategory(Intent.CATEGORY_LAUNCHER);
//                            startActivity(i);
//                        }
//                    }
//                }
            }catch(JSONException e){
                Log.e("NOTIFICATION Exception", "onMessageReceived: " + e.getMessage());
            }
        }




        private void sendNotification(String header, String messageBody) {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            String channelId = "500";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.edulogo)
                            .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                    R.drawable.edulogo)) // notification icon
                            .setContentTitle(header)
                            .setContentText(messageBody)
                            .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
}
