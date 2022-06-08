package com.example.team_joinus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoryActivity extends AppCompatActivity {

    private RequestQueue mQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        mQueue = Volley.newRequestQueue(this);

        LinearLayout InfoLayout = (LinearLayout) findViewById(R.id.infoLayout);
        ImageButton room_exit = (ImageButton) findViewById(R.id.btn_roomexit);
        ImageButton board_game = (ImageButton) findViewById(R.id.btn_cafe);
        ImageButton bowling = (ImageButton) findViewById(R.id.btn_bowling);
        ImageButton room_cafe = (ImageButton) findViewById(R.id.btn_partyroom);
        ImageButton billiard = (ImageButton) findViewById(R.id.btn_billiard);
        ImageButton vr_game = (ImageButton) findViewById(R.id.btn_vr);

        Button logout_bt = findViewById(R.id.logout_bt);

        logout_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_intent;

                Toast.makeText(getApplicationContext(), "로그인 아웃!", Toast.LENGTH_SHORT).show();
                new_intent = new Intent(CategoryActivity.this, LoginActivity.class);
                startActivity(new_intent);
            }
        });

        room_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getCategory(room_exit); }
        });

        board_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategory(board_game);
            }
        });

        billiard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategory(billiard);
            }
        });

        vr_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategory(vr_game);
            }
        });

        room_cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategory(room_cafe);
            }
        });

        bowling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategory(bowling);
            }
        });
    }

    private void getCategory(ImageButton pointerButton)
    {
        String url = "http://3.34.53.201/facility";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                // 성공시
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            LinearLayout InfoLayout = (LinearLayout) findViewById(R.id.infoLayout);
                            ImageButton room_exit = (ImageButton) findViewById(R.id.btn_roomexit);
                            ImageButton board_game = (ImageButton) findViewById(R.id.btn_cafe);
                            ImageButton bowling = (ImageButton) findViewById(R.id.btn_bowling);
                            ImageButton room_cafe = (ImageButton) findViewById(R.id.btn_partyroom);
                            ImageButton billiard = (ImageButton) findViewById(R.id.btn_billiard);
                            ImageButton vr_game = (ImageButton) findViewById(R.id.btn_vr);

                            // 토큰(쿠키) 가져오기
                            SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
                            String user_value= sf.getString("mem_id", "");
                            // String user_p = sf.getString("mem_p", "");
                            JSONArray jsonArray = response.getJSONArray("facility");
                            int fac_code = 0;

                            if (pointerButton == room_exit)
                                fac_code = 1;

                            else if (pointerButton == board_game)
                                fac_code = 2;

                            else if (pointerButton == bowling)
                                fac_code = 3;

                            else if (pointerButton == room_cafe)
                                fac_code = 4;

                            else if (pointerButton == billiard)
                                fac_code = 5;

                            else if (pointerButton == vr_game)
                                fac_code = 6;

                            InfoLayout.removeAllViews();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject facility = jsonArray.getJSONObject(i);
                                int fac_type = Integer.parseInt(facility.getString("fac_p"));

                                if ((fac_type % 1000) / 100 + 1 == fac_code)
                                {
                                    Button new_btn;

                                    new_btn = new Button(getApplicationContext());
                                    new_btn.setText("시설주 : " + facility.getString("fac_ceo")
                                            + "    시설 이름 : " + facility.getString("fac_title"));
                                    new_btn.setTextSize(15);
                                    String facility_name = facility.getString("fac_title");

                                    new_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent res_intent;

                                            res_intent = new Intent(CategoryActivity.this, ResActivity.class);
                                            // getExtra로 값 꺼내올 수 있음
                                            res_intent.putExtra("fac_title", facility_name);
                                            startActivity(res_intent);
                                        }
                                    });

                                    InfoLayout.addView(new_btn);
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
    
}
