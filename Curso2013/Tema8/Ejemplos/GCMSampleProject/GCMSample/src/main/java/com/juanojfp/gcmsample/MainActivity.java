package com.juanojfp.gcmsample;

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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main UI for the demo app.
 */
public class MainActivity extends Activity
{

    //Datos para el servidor
    private final String PROJECT_ID = "912305911334";
    private final String apiKey = "AIzaSyC0KejqE2t9yN3d9ko2FY1er1AKeG89Mpo";

    private static final String URL_TO_SERVER = "http://api.juanjofp.com/c2dm/";

    private static final String MODULE_DEVICE = "device/";

    private static final String COMMAND_REGISTER = "register/";

    private static final String PARAM_C2DM_DEVICE_TOKEN = "token";
    private static final String PARAM_C2DM_DEVICE_IMEI = "imei";
    private static final String PARAM_C2DM_DEVICE_MODEL = "model";
    private static final String PARAM_C2DM_DEVICE_SDK = "sdk";

    private static final String PARAM_C2DM_DEVICE_MESSAGE = "message";
    private static final String PARAM_C2DM_DEVICE_MESSAGE_ID = "id";
    private static final String PARAM_C2DM_DEVICE_MESSAGE_CODE = "code";

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_ON_SERVER_EXPIRATION_TIME = "onServerExpirationTimeMs";
    /**
     * Default lifespan (7 days) of a reservation until it is considered expired.
     */
    public static final long REGISTRATION_EXPIRY_TIME_MS = 1000 * 3600 * 24 * 7;
    /**
     * You must use your own project ID instead.
     */
    private final String SENDER_ID = PROJECT_ID;

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCMDemo";

    TextView mDisplay;
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    Context context;

    String regid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mDisplay = (TextView) findViewById(R.id.display);

        context = getApplicationContext();
        regid = getRegistrationId(context);

        if (regid.length() == 0)
        {
            registerBackground();
        }
        else
        {
            mDisplay.setText(regid);
        }
        gcm = GoogleCloudMessaging.getInstance(this);

        Log.e("OnCreate", "------------");
        getExtrasFromIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        Log.e("NewIntent", "------------");
        getExtrasFromIntent(intent);
    }

    private void getExtrasFromIntent(Intent it)
    {
        if(it != null && it.getExtras() != null)
        {
            Log.e("Intent GCM ", it.getExtras().toString());
            Bundle bundle = it.getExtras();
            StringBuilder sb = new StringBuilder();
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                String msg = String.format("%s %s (%s)", key, value.toString(), value.getClass().getName());
                sb.append(msg).append("\n");
                Log.d(TAG, msg);
            }

            if(mDisplay != null)
                mDisplay.setText(sb.toString());

        }
        else
            Log.e("Intent GCM ", "NO DATA IN INTENT");
    }

    /**
     * Stores the registration id, app versionCode, and expiration time in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration id
     */
    private void setRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.v(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        long expirationTime = System.currentTimeMillis() + REGISTRATION_EXPIRY_TIME_MS;

        Log.v(TAG, "Setting registration expiry time to " +
                new Timestamp(expirationTime));
        editor.putLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, expirationTime);
        editor.commit();
    }

    /**
     * Gets the current registration id for application on GCM service.
     * <p>
     * If result is empty, the registration has failed.
     *
     * @return registration id, or empty string if the registration is not
     *         complete.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.length() == 0) {
            Log.v(TAG, "Registration not found.");
            return "";
        }
        // check if app was updated; if so, it must clear registration id to
        // avoid a race condition if GCM sends a message
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion || isRegistrationExpired()) {
            Log.v(TAG, "App version changed or registration expired.");
            return "";
        }
        return registrationId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration id, app versionCode, and expiration time in the application's
     * shared preferences.
     */
    private void registerBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    msg = registerInServer(regid);


                    // Save the regid - no need to register again.
                    setRegistrationId(context, regid);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                mDisplay.append(msg + "\n");
            }
        }.execute(null, null, null);
    }

    /*
    public void onClick(final View view) {
        if (view == findViewById(R.id.send)) {
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    String msg = "";
                    try {
                        Bundle data = new Bundle();
                        data.putString("hello", "World");
                        String id = Integer.toString(msgId.incrementAndGet());
                        gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
                        msg = "Sent message";
                    } catch (IOException ex) {
                        msg = "Error :" + ex.getMessage();
                    }
                    return msg;
                }

                @Override
                protected void onPostExecute(String msg) {
                    mDisplay.append(msg + "\n");
                }
            }.execute(null, null, null);
        } else if (view == findViewById(R.id.clear)) {
            mDisplay.setText("");
        }
    }
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
    }

    /**
     * Checks if the registration has expired.
     *
     * <p>To avoid the scenario where the device sends the registration to the
     * server but the server loses it, the app developer may choose to re-register
     * after REGISTRATION_EXPIRY_TIME_MS.
     *
     * @return true if the registration has expired.
     */
    private boolean isRegistrationExpired() {
        final SharedPreferences prefs = getGCMPreferences(context);
        // checks if the information is not stale
        long expirationTime =
                prefs.getLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, -1);
        return System.currentTimeMillis() > expirationTime;
    }

    public String registerInServer(String token)
    {

        String imei = Build.SERIAL;
        String model = Build.MANUFACTURER+":"+Build.MODEL;
        String sdk = Build.VERSION.SDK+":"+Build.VERSION.RELEASE;

        Log.e("RegisterInServer", "Imei: "+imei);

        HttpClient client = new DefaultHttpClient();

        StringBuilder peticion = new StringBuilder();
        peticion.append(URL_TO_SERVER).append(MODULE_DEVICE).append(COMMAND_REGISTER);

        Log.e("Peticion:", peticion.toString());

        HttpPost method=new HttpPost(peticion.toString());
        String msgServer;

        try {
            List<NameValuePair> nvp = new ArrayList<NameValuePair>();
            nvp.add(new BasicNameValuePair(PARAM_C2DM_DEVICE_TOKEN, token));
            nvp.add(new BasicNameValuePair(PARAM_C2DM_DEVICE_IMEI, imei));
            nvp.add(new BasicNameValuePair(PARAM_C2DM_DEVICE_MODEL, model));
            nvp.add(new BasicNameValuePair(PARAM_C2DM_DEVICE_SDK, sdk));

            for(NameValuePair v: nvp)
                Log.e("Parametro", v.getName()+":"+v.getValue());

            method.setEntity(new UrlEncodedFormEntity(nvp, HTTP.UTF_8));

            HttpResponse response = client.execute(method);
            //Respuestas
            //{'message':'username', 'id':idUser, 'code':0}
            //{'message':'Token perdido', 'code':1}
            JSONObject json = new JSONObject(input2String(response.getEntity().getContent()));
            String msg = json.getString(PARAM_C2DM_DEVICE_MESSAGE);
            int id = json.getInt(PARAM_C2DM_DEVICE_MESSAGE_ID);
            int code = json.getInt(PARAM_C2DM_DEVICE_MESSAGE_CODE);

            msgServer = id+":"+msg+":"+code;

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            msgServer = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            msgServer = e.getMessage();
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            msgServer = e.getMessage();
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            msgServer = e.getMessage();
            e.printStackTrace();
        }

        Log.e("Serever Response", msgServer);
        return msgServer;
    }

    public String input2String(InputStream input) throws IOException {
        int c = 0;
        StringBuilder sb = new StringBuilder();
        while ((c = input.read()) != -1)
            sb.append((char) c);
        input.close();
        return sb.toString();

    }
}