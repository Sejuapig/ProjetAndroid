package com.example.e149769s.Miniprojet2017;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
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
        final EditText editText = (EditText) findViewById(R.id.editText);
        final ListView lv = (ListView)findViewById(R.id.resultat);
        Button search = (Button)findViewById(R.id.search);

        this.movies = new ArrayList<>();

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

        //Permet de lancer une recherche pour le texte indiqué dans la barre de recherche
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if((editText.toString().length() < 3)){
                    Toast.makeText(MainActivity.this, "Recherche invalide!", Toast.LENGTH_LONG).show();
                }else{
                    TMDBQueryManager tmnb = new TMDBQueryManager();
                    String query = editText.getText().toString();
                    try {
                        tmnb.searchIMDB(query);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Permet, lorsque l'utilisateur clique sur un item de basculer vers l'activité ActivityAffichage et d'afficher les détails du film
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ActivityAffichage.class).putExtra("item", movies.get(position));
                startActivity(intent);
            }
        });

        //Permet de mettre le focus sur la barre de recherche, affiche un message si l'utilisateur change de focus
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) editText.setText("");
                else editText.setText("Veuillez saisir un film");
            }
        });
    }

    private class TMDBQueryManager {
        final Spinner sp = (Spinner) findViewById(R.id.nbResult);
        final ListView lv = (ListView)findViewById(R.id.resultat);

        private final String TMDB_API_KEY = "c6fdc30d8458beffc46ce81a128180c6";
        private static final String DEBUG_TAG = "TMDBQueryManager";

        //Parse les données et met à jour la vue
        public void parseResult(String result) {
            String streamAsString = result;
            ArrayList<Movie> results = new ArrayList<Movie>();
            try {
                JSONObject jsonObject = new JSONObject(streamAsString);
                JSONArray array = (JSONArray) jsonObject.get("results");

                for (int i = 0; i < Integer.parseInt(sp.getSelectedItem().toString()); i++) {
                    JSONObject jsonMovieObject = array.getJSONObject(i);

                    Movie m = new Movie(jsonMovieObject.getString("backdrop_path"),
                            jsonMovieObject.getString("original_title"),
                            Integer.parseInt(jsonMovieObject.getString("id")),
                            jsonMovieObject.getString("popularity"),
                            jsonMovieObject.getString("poster_path"),
                            jsonMovieObject.getString("release_date"),
                            jsonMovieObject.getString("title"));
                    results.add(m);
                    movies.addAll(results);
                    ArrayAdapter<Movie> movieArrayAdapter = new ArrayAdapter<Movie>(MainActivity.this,android.R.layout.simple_list_item_1, results);
                    lv.setAdapter(movieArrayAdapter);
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
            //La recherche de film "adultes" est désactivée par défaut.
            String url ="http://api.themoviedb.org/3/search/movie?api_key=" + TMDB_API_KEY +"&query=" + query + "&adult=false";

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
