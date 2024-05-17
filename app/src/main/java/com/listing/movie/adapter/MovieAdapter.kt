package com.listing.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.listing.movie.databinding.ItemMovieBinding
import com.listing.movie.model.Movie
import com.listing.movie.network.ApiClient

class MovieAdapter(
    private var movies: List<Movie>, private var listener: MovieAdapterListener
) : RecyclerView.Adapter<MovieAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val bind: ItemMovieBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bindItem(movie: Movie) {
            with(bind) {
                tvTitle.text = movie.title
                tvOverview.text = movie.overview
            }
            Glide.with(itemView).load(ApiClient.BASE_ORIGINAL_IMAGE_URL + movie.posterPath).into(bind.ivMovie)
            itemView.setOnClickListener {
                listener.onClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapter =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(adapter)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    public interface MovieAdapterListener {
        fun onClick(movie: Movie)
    }
}