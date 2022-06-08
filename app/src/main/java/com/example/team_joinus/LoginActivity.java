package com.example.team_joinus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.net.CookieManager;
import java.net.URL;

import javax.crypto.SecretKey;
import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;

public class LoginActivity extends AppCompatActivity {
    private RequestQueue mQueue;

    private static SharedPreferences.Editor editor;
    private static SharedPreferences sharedPreferences;
    private static Context mContext;

    private TextView SecretKey;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mQueue = Volley.newRequestQueue(this);
        Button confirm_btn = findViewById(R.id.confirm);
        Button join_btn = findViewById(R.id.join);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo();
            }
        });

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join();
            }
        });
    }

    private void getUserInfo()
    {
        String url1 = "http://3.34.53.201/users";
        EditText new_id = findViewById(R.id.new_id);
        EditText new_pw = findViewById(R.id.new_pw);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url1, null,
                // 성공시
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("users");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);

                                String tmp_p = user.getString("mem_p");
                                String tmp_id = user.getString("mem_id");
                                String tmp_pw = user.getString("mem_pw");

                                if (tmp_id.equals(new_id.getText().toString()) &&
                                tmp_pw.equals(new_pw.getText().toString())) {
                                    String tmp_type = user.getString("mem_type");
                                    Intent fac_intent;

                                    if (tmp_type.equals("1"))
                                    {
                                        fac_intent = new Intent(LoginActivity.this, FacilityActivity.class);
                                        Toast.makeText(getApplicationContext(), "슈퍼 계정", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        fac_intent = new Intent(LoginActivity.this, CategoryActivity.class);
                                        Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                                    }

                                    // 0525 추가 ///////////////////////////////////////////////////////////
                                    SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sf.edit();
                                    //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
                                    editor.putString("mem_p", tmp_p);
                                    editor.putString("mem_id", tmp_id);
                                    editor.commit();


                                    /*
                                    String asdwqfdasd= sf.getString("mem_id","sss");
                                    Toast.makeText(getApplicationContext(), asdwqfdasd, Toast.LENGTH_SHORT).show();

                                    int session_id = sf.getInt("tmp_id", 0);
                                    Toast.makeText(getApplicationContext(), session_id, Toast.LENGTH_SHORT).show();
                                    */
                                    // SecretKey.setText(tmp_id);
                                    ////////////////////////////////////////////////////////////////////////

                                    startActivity(fac_intent);
                                    finish();
                                    return;
                                }
                            }
                            Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            // 실패시
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    private void join()
    {
        Intent new_intent;

        new_intent = new Intent(LoginActivity.this, JoinActivity.class);
        startActivity(new_intent);
        //finish();
    }
}
