package com.example.e149769s.Miniprojet2017;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class MainActivity extends Activity {
    public ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner sp = (Spinner) findViewById(R.id.nbResult);
        final EditText recherche = (EditText) findViewById(R.id.recherche);
        Button bRecherche = (Button)findViewById(R.id.bRecherche);

        this.movies = new ArrayList<Movie>();

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);

        ArrayAdapter<Integer> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        sp.setAdapter(aa);

        bRecherche.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if((recherche.toString().length() < 3) || (recherche.toString() == "Recherchez ici")){
                    Toast.makeText(MainActivity.this, "Recherche invalide!", Toast.LENGTH_LONG).show();
                }else{
                    TMDBQueryManager tmnb = new TMDBQueryManager();
                    String query = recherche.getText().toString();
                    try {
                        tmnb.searchIMDB(query);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Updates the View with the results. This is called asynchronously
     * when the results are ready.
     * @param result The results to be presented to the user.
     */
    public void updateViewWithResults(ArrayList<Movie> result) {
        ListView listView = new ListView(this);
        Log.d("updateViewWithResults", result.toString());
        // Add results to listView.
        ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(this, android.R.layout.simple_list_item_1, result);
        listView.setAdapter(adapter);

        // Update Activity to show listView
        setContentView(listView);
    }


    private class TMDBQueryManager {
        final ListView lv = (ListView)findViewById(R.id.resultat);

        private final String TMDB_API_KEY = "c6fdc30d8458beffc46ce81a128180c6";
        private static final String DEBUG_TAG = "TMDBQueryManager";

        public void parseResult(String result) {
            String streamAsString = result;
            ArrayList<Movie> results = new ArrayList<Movie>();
            try {
                JSONObject jsonObject = new JSONObject(streamAsString);
                JSONArray array = (JSONArray) jsonObject.get("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonMovieObject = array.getJSONObject(i);

                    Movie m = new Movie(jsonMovieObject.getString("backdrop_path"),
                            jsonMovieObject.getString("original_title"),
                            Integer.parseInt(jsonMovieObject.getString("id")),
                            jsonMovieObject.getString("popularity"),
                            jsonMovieObject.getString("poster_path"),
                            jsonMovieObject.getString("release_date"),
                            jsonMovieObject.getString("title"));
                    results.add(m);
                    ArrayAdapter<Movie> movieArrayAdapter = new ArrayAdapter<Movie>(MainActivity.this,android.R.layout.simple_list_item_1, results);
                    lv.setAdapter(movieArrayAdapter);
                    //Toast.makeText(MainActivity.this, "Aucun résultat !", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                System.err.println(e);
            }
        }

        /**
         * Searches IMDBs API for the given query
         * @param query The query to search.
         * @return A list of all hits.
         */
        public void searchIMDB(String query) throws IOException {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url ="http://api.themoviedb.org/3/search/movie?api_key=" + TMDB_API_KEY +"&query=" + query;


            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            parseResult(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("error");
                }
            });
            queue.add(stringRequest);
        }

    }
}
