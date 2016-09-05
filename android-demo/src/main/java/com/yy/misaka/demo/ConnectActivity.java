package com.yy.misaka.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yy.httpproxy.Config;
import com.yy.httpproxy.ProxyClient;
import com.yy.httpproxy.service.DefaultDnsHandler;
import com.yy.httpproxy.service.DefaultNotificationHandler;
import com.yy.httpproxy.subscribe.ConnectCallback;
import com.yy.httpproxy.subscribe.PushCallback;
import com.yy.httpproxy.util.Logger;
import com.yy.misaka.demo.appmodel.DemoApp;

import java.util.Set;

/**
 * Created by huangzhilong on 2016/8/31.
 */
public class ConnectActivity extends Activity implements ConnectCallback, PushCallback{
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        editText = (EditText) findViewById(R.id.et_host);

        findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = String.valueOf(editText.getText()).trim();
                DemoApp.APP_CONTEXT.proxyClient = new ProxyClient(new Config(DemoApp.APP_CONTEXT).setHost(host).setConnectCallback(ConnectActivity.this)
                        .setNotificationHandler(DefaultNotificationHandler.class)
                        .setDnsHandler(DefaultDnsHandler.class)
                        .setPushCallback(ConnectActivity.this)
                        .setLogger(DemoLogger.class));
            }
        });
    }

    @Override
    public void onConnect(String uid, Set<String> topics) {
        Intent intent = new Intent();
        intent.setClass(ConnectActivity.this, NickNameActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDisconnect() {
        Toast.makeText(this, "Connect Socket failed", Toast.LENGTH_SHORT);
    }

    @Override
    public void onPush(String data) {

    }

    public static class DemoLogger implements Logger {
        @Override
        public void log(int level, String message, Throwable e) {
            Log.d("DemoLogger", "demo " + message, e);
        }
    }
}