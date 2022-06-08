package com.example.team_joinus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FacilityActivity extends AppCompatActivity {
    private RequestQueue mQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);

        TextView AllFacility = (TextView) findViewById(R.id.allFacility);
        Button GetFacility = (Button) findViewById(R.id.getFacility);
        Button PostFacility = (Button) findViewById(R.id.postFacility);
        Button DeleteFacility = (Button) findViewById(R.id.deleteFacility);
        Button SeeUsers = (Button) findViewById(R.id.seeUsers);

        mQueue = Volley.newRequestQueue(this);

        GetFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFacility();
            }
        });

        PostFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postFacility();
            }
        });

        DeleteFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 토큰(쿠키) 가져오기
                SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
                String user_value= sf.getString("mem_id", "");

                EditText fac_TITLE = findViewById(R.id.fac_title);
                EditText fac_CEO = findViewById(R.id.fac_ceo);

                deleteFacility(fac_TITLE.getText().toString(), fac_CEO.getText().toString());
            }
        });

        SeeUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_intent;

                new_intent = new Intent(FacilityActivity.this, MainActivity.class);
                startActivity(new_intent);
                finish();
            }
        });
    }

    private void getFacility()
    {
        String url = "http://3.34.53.201/facility";
        TextView AllFacility = (TextView) findViewById(R.id.allFacility);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                // 성공시
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // 토큰(쿠키) 가져오기
                            SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
                            String user_value= sf.getString("mem_id", "");
                            // String user_p = sf.getString("mem_p", "");
                            JSONArray jsonArray = response.getJSONArray("facility");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject facility = jsonArray.getJSONObject(i);

                                String fac_P = facility.getString("fac_p");
                                String fac_CEO = facility.getString("fac_ceo");
                                String fac_TITLE = facility.getString("fac_title");
                                String fac_INFO = facility.getString("fac_info");
                                String fac_MAX = facility.getString("fac_max");

                                if (user_value.equals(fac_CEO))
                                {
                                    AllFacility.append("시설 이름 : " + fac_TITLE + "\n" +
                                            "시설 정보 : " + fac_INFO + "\n");
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

    private void postFacility()
    {
        TextView AllFacility = (TextView) findViewById(R.id.allFacility);

        String fac_url;
        String response = "error!";

        EditText fac_P = findViewById(R.id.fac_p);
        // EditText fac_CEO = findViewById(R.id.fac_ceo);
        EditText fac_TITLE = findViewById(R.id.fac_title);
        EditText fac_INFO = findViewById(R.id.fac_info);
        EditText fac_MAX = findViewById(R.id.fac_max);

        // 토큰(쿠키) 가져오기
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        String user_value= sf.getString("mem_id", "");
        String mem_user_p = sf.getString("mem_p", "");

        fac_url = "http://3.34.53.201/facility";
        Map<String, String> params = new HashMap<String, String>();
        params.put("fac_p", fac_P.getText().toString());
        params.put("fac_ceo", user_value);
        params.put("fac_title", fac_TITLE.getText().toString());
        params.put("fac_info", fac_INFO.getText().toString());
        params.put("fac_max", fac_MAX.getText().toString());
        params.put("fac_clicked", "0");

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, fac_url, jsonObj,
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
                        Toast.makeText(getApplicationContext(), user_value, Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {

                Gson gson = new Gson();
                FacilityInfo facilityInfo = new FacilityInfo(fac_P.getText().toString(),
                        user_value, fac_TITLE.getText().toString(),
                        fac_INFO.getText().toString(), fac_MAX.getText().toString(), "0");

                String fac_json = gson.toJson(facilityInfo);

                AllFacility.append("\n" + fac_json.toString() + "\n");

                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("fac_p", facilityInfo.getFac_p());
                params.put("fac_ceo", facilityInfo.getFac_ceo());
                params.put("fac_title", facilityInfo.getFac_title());
                params.put("fac_info", facilityInfo.getFac_info());
                params.put("fac_max", facilityInfo.getFac_max());
                params.put("fac_clicked", "0");

                return params;
            }
        };

        mQueue.add(postRequest);
    }

    private void deleteFacility(String facilityTitle, String facilityCeo) {

        String url = "http://3.34.53.201/facility/deleteFac";
        String response = "error!";

        EditText fac_P = findViewById(R.id.fac_p);
//        EditText fac_CEO = findViewById(R.id.fac_ceo);
        EditText fac_TITLE = findViewById(R.id.fac_title);
        EditText fac_INFO = findViewById(R.id.fac_info);
        EditText fac_MAX = findViewById(R.id.fac_max);

        // 토큰(쿠키) 가져오기
        SharedPreferences sf = getSharedPreferences("sFile", MODE_PRIVATE);
        String user_value = sf.getString("mem_id", "");
        String mem_user_p = sf.getString("mem_p", "");

        url = "http://3.34.53.201/facility/deleteFac";
        Map<String, String> params = new HashMap<String, String>();
        params.put("ceoName", user_value.toString());
        params.put("facilityName", fac_TITLE.getText().toString());

        JSONObject jsonObj = new JSONObject(params);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                TextView AllFacility = (TextView) findViewById(R.id.allFacility);
                Gson gson = new Gson();
                FacilityInfo facilityInfo = new FacilityInfo(fac_P.getText().toString(),
                        user_value, fac_TITLE.getText().toString(),
                        fac_INFO.getText().toString(), fac_MAX.getText().toString(), "0");

                String fac_json = gson.toJson(facilityInfo);

                AllFacility.append("\n" + fac_json.toString() + "\n");

                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("ceoName", facilityInfo.getFac_ceo());
                params.put("facilityName", facilityInfo.getFac_title());

                return params;
            }
        };

        mQueue.add(postRequest);
    }
}
