package com.listing.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.listing.movie.R
import com.listing.movie.adapter.MovieAdapter
import com.listing.movie.databinding.FragmentHomeBinding
import com.listing.movie.model.Movie
import com.listing.movie.model.MovieResponse
import com.listing.movie.model.User
import com.listing.movie.network.ApiClient
import com.listing.movie.utils.DatastoreUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        with(binding){
            rvMovie.layoutManager = LinearLayoutManager(requireContext())
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    val user = DatastoreUtils.getLoggedUser(requireContext())
                    tvGreeting.text = "Welcome, ${user.username}"
                }
            }
            btnProfile.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
            }
            ApiClient.apiServiceInstance
                .getNowPlaying(ApiClient.API_KEY)
                .enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                    ) {
                        if (response.isSuccessful) {
                            rvMovie.adapter = MovieAdapter(response.body()?.results?:listOf(), object : MovieAdapter.MovieAdapterListener{
                                override fun onClick(movie: Movie) {
                                    val b = Bundle()
                                    b.putSerializable("movie",movie)
                                    findNavController().navigate(R.id.action_homeFragment_to_detailFragment,b)
                                }
                            })
                        }
                    }
                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
                    }
                })
        }
        return binding.root
    }
}