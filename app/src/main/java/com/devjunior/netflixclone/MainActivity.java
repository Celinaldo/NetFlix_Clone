package com.devjunior.netflixclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devjunior.netflixclone.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_main);
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i <30; i++) {
            Movie movie = new Movie();
            movie.setCoverUrl(R.drawable.movie);
            movies.add(movie);
        }
        mainAdapter = new MainAdapter(movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(mainAdapter);
    }

    private static class MovirHolder extends RecyclerView.ViewHolder {
        final ImageView imageViewCover;

        public MovirHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCover = itemView.findViewById(R.id.image_view_cover);
        }
    }

    private class MainAdapter extends RecyclerView.Adapter<MovirHolder> {

        private final List<Movie> movies;

        private MainAdapter(List<Movie> movies) {
            this.movies = movies;
        }

        @NonNull
        @Override
        public MovirHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MovirHolder(getLayoutInflater().inflate(R.layout.movie_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MovirHolder holder, int position) {
            Movie movie = movies.get(position);
            holder.imageViewCover.setImageResource(movie.getCoverUrl());

        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }
}