package com.devjunior.netflixclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devjunior.netflixclone.model.Movie;
import com.devjunior.netflixclone.model.MovieDetail;
import com.devjunior.netflixclone.util.MovieDetailTask;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements MovieDetailTask.MovieDetailLoader {
    private TextView txtTitle, txtDesc, txtCast;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        txtTitle = findViewById(R.id.text_view_title);
        txtDesc = findViewById(R.id.text_view_desc);
        txtCast = findViewById(R.id.text_view_cast);
        recyclerView = findViewById(R.id.recycler_view_similar);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            getSupportActionBar().setTitle(null);
        }

        LayerDrawable drawable = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.shadows);

        if (drawable != null) {
            Drawable movieCover = ContextCompat.getDrawable(this, R.drawable.movie);
            drawable.setDrawableByLayerId(R.id.cover_drawable, movieCover);
            ((ImageView) findViewById(R.id.image_view_cover)).setImageDrawable(drawable);
        }

        txtTitle.setText("Batman Begins");
        txtDesc.setText("O jovem Bruce Wayne viaja para o Extremo Oriente, onde recebe treinamento em artes marciais do mestre Henri Ducard, um membro da misteriosa Liga das Sombras. Quando Ducard revela que a verdadeira proposta da Liga ?? a destrui????o completa da cidade de Gotham, Wayne retorna ?? sua cidade com o intuito de livr??-la de criminosos e assassinos. Com a ajuda do mordomo Alfred e do expert Lucius Fox, nasce Batman.");
        txtCast.setText(getString(R.string.cast, "junio" + ",yeda" + ",joao miguel"));

        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Movie movie = new Movie();
            movies.add(movie);
        }
        recyclerView.setAdapter(new MovieAdapter(movies));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id = extras.getInt("id");
            MovieDetailTask movieDetailTask = new MovieDetailTask(this);
            movieDetailTask.setMovieDetailLoader(this);
            movieDetailTask.execute("https://tiagoaguiar.co/api/netflix/1" + id + ".json");
        }
    }

    @Override
    public void onResult(MovieDetail movieDetail) {
        txtTitle.setText(movieDetail.getMovie().getTitle());
        txtCast.setText(movieDetail.getMovie().getCast());
        txtDesc.setText(movieDetail.getMovie().getDesc());

    }

    private static class MovieHolder extends RecyclerView.ViewHolder {

            final ImageView imageViewCover;

            public MovieHolder(@NonNull View itemView) {
                super(itemView);
                imageViewCover = itemView.findViewById(R.id.image_view_cover);
            }
        }


        private class MovieAdapter extends RecyclerView.Adapter<MainActivity.MovieHolder>{

            private List<Movie> movies;

            private MovieAdapter(List<Movie> movies) {
                this.movies = movies;
            }

            public void setMovies(List<Movie> movies) {
                this.movies.clear();
                this.movies.addAll(movies);
            }

            @NonNull
            @Override
            public MainActivity.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MainActivity.MovieHolder(getLayoutInflater().inflate(R.layout.movie_item_similar, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull MainActivity.MovieHolder holder, int position) {
                Movie movie = movies.get(position);
                // holder.imageViewCover.setImageResource(movie.getCoverUrl());
            }

            @Override
            public int getItemCount() {
                return movies.size();
            }

        }




    }

