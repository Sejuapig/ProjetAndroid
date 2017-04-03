package com.example.e149769s.Miniprojet2017;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by E149769S on 03/04/17.
 */
public class ActivityAffichage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);

        final ImageView img = (ImageView) findViewById(R.id.imageView);
        final TextView title = (TextView) findViewById(R.id.title);
        final TextView originalTitle = (TextView) findViewById(R.id.originalTitle);
        final TextView popularity = (TextView) findViewById(R.id.popularity);
        final TextView id = (TextView) findViewById(R.id.id);
        final TextView releaseDate = (TextView) findViewById(R.id.releaseDate);
        final Button bRetour = (Button) findViewById(R.id.button);

        //Récupération du film cliqué
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            Intent retourMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(retourMain);
        }
        Movie movie = extras.getParcelable("item");

        //Remplissage des textView avec les données du récupérées du film
        if(movie != null) {
            String url = "https://image.tmdb.org/t/p/w640" + movie.getPosterPath();
            Picasso.with(getApplicationContext()).load(url).into(img);
            title.append(movie.getTitle());
            originalTitle.append(movie.getOriginalTitle());
            id.append(String.valueOf(movie.getId()));
            popularity.append(movie.getPopularity());
            releaseDate.append(String.valueOf(movie.getReleaseDate()));
        }

        //Bouton permettant un retour à la première activité
        bRetour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent retourMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(retourMain);
            }
        });
    }
}
