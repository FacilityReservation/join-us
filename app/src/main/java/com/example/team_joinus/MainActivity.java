package com.example.team_joinus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private RequestQueue mQueue;
    String content="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonParse = findViewById(R.id.button);
        Button buttonPost = findViewById(R.id.PostButton);
        Button buttoDelete = findViewById(R.id.DeleteButton);
        Button buttonFacility = findViewById(R.id.FacilityButton);
        textViewResult = findViewById(R.id.getText);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonGet();
            }
        });

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPOST();
            }
        });

        buttoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText MEM_ID = findViewById(R.id.m_id);
                deleteUser(MEM_ID.getText().toString());
            }
        });

        buttonFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_intent;

                new_intent = new Intent(MainActivity.this, FacilityActivity.class);
                startActivity(new_intent);
                finish();
            }
        });
    }

    private void jsonGet()
    {
        String url = "http://3.34.53.201/users";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                // 성공시
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
                            String mem_value= sf.getString("mem_id", "");
                            JSONArray jsonArray = response.getJSONArray("users");
                            // 확인용
                            Toast.makeText(getApplicationContext(), mem_value, Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject user = jsonArray.getJSONObject(i);

                                String P = user.getString("mem_p");
                                String ID = user.getString("mem_id");
                                String PW = user.getString("mem_pw");
                                String NAME = user.getString("mem_name");
                                String COMPANY = user.getString("mem_company");
                                String TYPE = user.getString("mem_type");

                                textViewResult.append("P : " + P + "\n" +
                                        "ID : " + ID + "\n" + "PW : " + PW + "\n");
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

    private void jsonPOST()
    {
        String url = "http://3.34.53.201/users";
        String response = "error!";

        EditText p = findViewById(R.id.m_p);
        EditText id = findViewById(R.id.m_id);
        EditText pw = findViewById(R.id.m_pw);
        EditText name = findViewById(R.id.m_name);
        EditText company = findViewById(R.id.m_company);
        EditText type = findViewById(R.id.m_type);
        TextView text = findViewById(R.id.getText);

        url = "http://3.34.53.201/users";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mem_p", p.getText().toString());
        params.put("mem_id", id.getText().toString());
        params.put("mem_pw", pw.getText().toString());
        params.put("mem_name", name.getText().toString());
        params.put("mem_company", company.getText().toString());
        params.put("mem_type", type.getText().toString());

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
                )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Gson gson = new Gson();
                UserInfo userInfo = new UserInfo(p.getText().toString(), id.getText().toString(),
                        pw.getText().toString(), name.getText().toString(),
                        company.getText().toString(), type.getText().toString());

                String json = gson.toJson(userInfo);
                text.append("\n" + json.toString() + "\n");

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("mem_p", userInfo.getP());
                params.put("mem_id", userInfo.getID());
                params.put("mem_pw", userInfo.getPW());
                params.put("mem_name", userInfo.getNAME());
                params.put("mem_company", userInfo.getCOMPANY());
                params.put("mem_type", userInfo.getTYPE());

                return params;
            }
        };

        mQueue.add(postRequest);
    }

    private void deleteUser(String id)
    {
        String delurl = "http://3.34.53.201/users/" + id;
        StringRequest delRequest = new StringRequest(Request.Method.DELETE, delurl.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Delete Success!", Toast.LENGTH_SHORT).show();
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected HashMap<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("mem_id", id);
                return map;
            }
        };
        mQueue.add(delRequest);
    }
}