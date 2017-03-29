package com.example.e149769s.miniprojet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText lv = (EditText)findViewById(R.id.editText);
        final Spinner sp = (Spinner)findViewById(R.id.spinner);

        Integer tabInt[] = {5,10,15,20,25};

        ArrayAdapter<Integer> aa = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, tabInt);
        sp.setAdapter(aa);


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String text = sp.getSelectedItem().toString();
                final int nb = Integer.parseInt(text);
                getMovies(nb);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast toast = Toast.makeText(getApplicationContext(), "Go sélectionner", Toast.LENGTH_SHORT);
                toast.show();
            }

        });


        }

    public void getMovies(int a) {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=c6fdc30d8458beffc46ce81a128180c6&language=en-US&query=Batman&page=1&include_adult=false";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Requête crée", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        queue.add(request);
    }

    public String encode(String s){
        return Uri.encode(s);
    }


}