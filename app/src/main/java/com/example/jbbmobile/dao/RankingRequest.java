package com.example.jbbmobile.dao;

import android.content.Context;
import android.telecom.Call;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jbbmobile.model.Explorer;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RankingRequest {
    private static final String RANKING_REQUEST_URL = "http://rogerlenke.site88.net/Ranking.php";
    private Map<String,String> params;

    public void request(final Context context, final Callback callback){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Explorer> explorers = new ArrayList<>();
                    for (int i = 0; i <= jsonArray.length() - 2; i+=2){
                        String nickname = jsonArray.getString(i);
                        int score = jsonArray.getInt(i+1);
                        explorers.add(new Explorer(score, nickname));
                    }
                    callback.callbackResponse(explorers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RANKING_REQUEST_URL, responseListener, null);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public interface Callback{
        void callbackResponse(List<Explorer> explorers);
    }
}
