package com.bwrsks.uasbeta;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    public static final String EXTRA_URL = "image";
    public static final String EXTRA_NAME = "name";

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private ArrayList<List> mList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON(){
        String url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("events");

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject event = jsonArray.getJSONObject(i);

                            String name = event.getString("strEvent");
                            String image = event.getString("strThumb");

                            mList.add(new List(image,name));
                        }

                        mAdapter = new Adapter(MainActivity.this, mList);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.setOnClickListener(MainActivity.this);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        List clickedItem = mList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImage());
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getName());

        startActivity(detailIntent);
    }
}