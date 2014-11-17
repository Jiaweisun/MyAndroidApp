/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ie.wit.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;


/**
 * This {@code WakefulBroadcastReceiver} takes care of creating and managing a
 * partial wake lock for your app. It passes off the work of processing the GCM
 * message to an {@code IntentService}, while ensuring that the device does not
 * go back to sleep in the transition. The {@code IntentService} calls
 * {@code GcmBroadcastReceiver.completeWakefulIntent()} when it is ready to
 * release the wake lock.
 */

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
    
//    private void saveRegistrationId(Context context, String registrationId) {
//        SharedPreferences prefs = PreferenceManager
//            .getDefaultSharedPreferences(context);
//        Editor edit = prefs.edit();
//        edit.putString(C2DMClientActivity.AUTH, registrationId);
//        edit.commit();
//      }
//    
//    public void createNotification(Context context, String registrationId) {
//        NotificationManager notificationManager = (NotificationManager) context
//            .getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification = new Notification(R.drawable.icon,
//            "Registration successful", System.currentTimeMillis());
//        // hide the notification after its selected
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        Intent intent = new Intent(context, RegistrationResultActivity.class);
//        intent.putExtra("registration_id", registrationId);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//            intent, 0);
//        notification.setLatestEventInfo(context, "Registration",
//            "Successfully registered", pendingIntent);
//        notificationManager.notify(0, notification);
//      }
// // incorrect usage as the receiver may be canceled at any time
//    // do this in an service and in an own thread
    public void sendRegistrationIdToServer(String deviceId,
        String registrationId) {
      Log.d("C2DM", "Sending registration ID to my application server");
      HttpClient client = new DefaultHttpClient();
      HttpPost post = new HttpPost("http://loclhost:8080/myAndroidWeb_gcm/");
      try {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        // Get the deviceID
        nameValuePairs.add(new BasicNameValuePair("deviceid", deviceId));
        nameValuePairs.add(new BasicNameValuePair("registrationid",
            registrationId));

        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        while ((line = rd.readLine()) != null) {
          Log.e("HttpResponse", line);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

}
