package app.cinematch;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import app.cinematch.entities.Movie;
import app.cinematch.entities.Theater;

public class RcvAdapter extends RecyclerView.Adapter<RcvAdapter.FrgViewHolder> implements View.OnClickListener{
    private Theater theater;
    private View.OnClickListener listener;

    public RcvAdapter(Theater theater) {
        this.theater = theater;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public RcvAdapter.FrgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_image, parent, false);
        itemView.setOnClickListener(this);
        return new FrgViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RcvAdapter.FrgViewHolder holder, int position) {
        Movie movie = theater.getMovies()[position];
        System.out.println(movie.getTitle());
        System.out.println(movie.getPosterPath());
        Uri url = Uri.parse("https://image.tmdb.org/t/p/w500" + movie.getPosterPath());
        Glide.with(holder.imgFrg.getContext()).load(url).into(holder.imgFrg);
    }

    @Override
    public int getItemCount() {
        if (theater != null && theater.getMovies() != null) {
            return theater.getMovies().length;
        } else {
            return 0;
        }
    }

    public class FrgViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgFrg;
        public FrgViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFrg = itemView.findViewById(R.id.imgFrg);
        }
    }
}
