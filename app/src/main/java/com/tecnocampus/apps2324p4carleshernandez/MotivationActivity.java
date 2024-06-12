package com.tecnocampus.apps2324p4carleshernandez;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tecnocampus.apps2324p4carleshernandez.adapters.QuoteAdapter;
import com.tecnocampus.apps2324p4carleshernandez.apiRest.RequestQueueSingleton;
import com.tecnocampus.apps2324p4carleshernandez.domain.Quote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MotivationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuoteAdapter adapter;
    private List<Quote> quoteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_motivation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_title));

        recyclerView = findViewById(R.id.rv_quotes);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        quoteList = new ArrayList<>();
        adapter = new QuoteAdapter(quoteList);
        recyclerView.setAdapter(adapter);

        fetchQuotes();

    }

    private void fetchQuotes() {
        String url = "https://type.fit/api/quotes";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject quoteObject = response.getJSONObject(i);
                                String text = quoteObject.getString("text");
                                String author = quoteObject.optString("author", "Unknown");

                                quoteList.add(new Quote(text, author));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MotivationActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}