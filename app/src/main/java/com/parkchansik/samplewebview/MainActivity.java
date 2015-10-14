package com.parkchansik.samplewebview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    EditText urlView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlView=(EditText)findViewById(R.id.edit_url);
        webView=(WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);//자바스크립트 사용할수 있게 해줄게

        webView.setWebViewClient(new WebViewClient() {// 이거 해줘야 내 앱에서 인터넷이 가능함


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {// 클릭이나 찾을떄 url이 http이렇게 세팅 안되어있으면 에러처리 할수 있도록 해주는거
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {

                    }
                    return false;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i("MainActivity", "progress : " + newProgress);
            }
        });

        webView.loadUrl("http://www.google.com");//초기화면

        Button btn=(Button)findViewById(R.id.btn_go);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlView.getText().toString();

                if (!TextUtils.isEmpty(url)) {
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "http://" + url;
                    }
                    webView.loadUrl(url);

                }
            }

        });
        btn = (Button)findViewById(R.id.btn_back);//뒤로 가기 버튼
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            }
        });

        btn = (Button)findViewById(R.id.btn_forward);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoForward()) {
                    webView.goForward();
                }
            }
        });
}

    @Override
    protected void onPause() {//이거는 이해가 잘 안되는데 그래도 그냐 넣어라
        super.onPause();
        webView.pauseTimers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.resumeTimers();
    }
}

