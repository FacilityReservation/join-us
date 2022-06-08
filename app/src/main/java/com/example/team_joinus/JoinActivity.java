package com.example.team_joinus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {
    private TextView textViewResult;
    private RequestQueue mQueue;
    String content;

    //디비 서버 주소
    String url = "http://3.34.53.201/users";
    String response = "error!";
    String id_count="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        Button joinIn = findViewById(R.id.PostButton);
        Button id_ck = findViewById(R.id.id_ck);

        mQueue = Volley.newRequestQueue(this);
        //회원가입 버튼 클릭 트리거
        joinIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPOST_1();
            }
        });
        id_ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                json_id_ck();
            }
        });
    }

    private void json_id_ck()
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                // 성공시
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            EditText id = findViewById(R.id.m_id);

                            JSONArray jsonArray = response.getJSONArray("users");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);

                                String ID = user.getString("mem_id");
                                //Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                                if(id.getText().toString().equals(ID)){
                                    id_count = "1";
                                }
                            }
                            //Toast.makeText(getApplicationContext(), id.getText(), Toast.LENGTH_SHORT).show();
                            if(id_count.equals("1")){
                                Toast.makeText(getApplicationContext(), "중복되는 아이디가 있습니다", Toast.LENGTH_SHORT).show();
                                id_count = "0";
                            }else{
                                Toast.makeText(getApplicationContext(), "사용 가능한 아이디 입니다", Toast.LENGTH_SHORT).show();
                            }

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

    private void jsonPOST_1()
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                // 성공시
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            EditText id = findViewById(R.id.m_id);
                            EditText pw1 = findViewById(R.id.m_pw);
                            EditText pw2 = findViewById(R.id.m_pw2);

                            JSONArray jsonArray = response.getJSONArray("users");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);

                                String ID = user.getString("mem_id");
                                //Toast.makeText(getApplicationContext(), ID, Toast.LENGTH_SHORT).show();
                                if(id.getText().toString().equals(ID)){
                                    id_count = "1";
                                }
                            }
                            //Toast.makeText(getApplicationContext(), id.getText(), Toast.LENGTH_SHORT).show();
                            if(id_count.equals("1")){
                                Toast.makeText(getApplicationContext(), "중복되는 아이디가 있습니다", Toast.LENGTH_SHORT).show();
                                id_count = "0";
                            }else{
                                if(pw1.getText().toString().equals(pw2.getText().toString())){
                                    jsonPOST_2();
                                }else{
                                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                                }
                            }

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


    private void jsonPOST_2()
    {
        //id 받아오는 부분
        EditText p = findViewById(R.id.m_p);
        EditText id = findViewById(R.id.m_id);
        EditText pw = findViewById(R.id.m_pw);
        EditText name = findViewById(R.id.m_name);
        EditText company = findViewById(R.id.m_company);
        TextView text = findViewById(R.id.getText);

        //각 id로 받은값을 디비로 쏘는부분
        Map<String, String> params = new HashMap<String, String>();
        params.put("mem_p", p.getText().toString());
        params.put("mem_id", id.getText().toString());
        params.put("mem_pw", pw.getText().toString());
        params.put("mem_name", name.getText().toString());
        params.put("mem_company", "3");
        params.put("mem_type", "3");


        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObj,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        finish();
        mQueue.add(postRequest);
    }
}
