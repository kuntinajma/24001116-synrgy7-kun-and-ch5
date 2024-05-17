package com.listing.movie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.listing.movie.R
import com.listing.movie.databinding.FragmentDetailBinding
import com.listing.movie.model.Movie
import com.listing.movie.network.ApiClient

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var movie: Movie
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetailBinding.inflate(inflater,container,false)
        movie = arguments?.getSerializable("movie") as Movie
        with(binding){
            tvTitle.text=movie.title
            tvVote.text=movie.stringVote
            Glide.with(requireContext()).load(ApiClient.BASE_ORIGINAL_IMAGE_URL + movie.posterPath).into(binding.ivMovie)
            tvOverview.text=movie.overview
        }
        return binding.root
    }
}