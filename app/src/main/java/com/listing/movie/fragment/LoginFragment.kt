package com.listing.movie.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.listing.movie.R
import com.listing.movie.databinding.FragmentLoginBinding
import com.listing.movie.utils.DatastoreUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                val email = DatastoreUtils.checkLogin(requireContext())
                if(email!=""){
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
        with(binding){
            tvRegister.setOnClickListener{
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {
                login()
            }
        }
        return binding.root
    }

    private fun login() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                with(binding){
                    btnLogin.isVisible = false
                    pbLoading.isVisible = true

                    val email = etEmail.text.toString()
                    val password = etPassword.text.toString()

                    if(DatastoreUtils.login(requireContext(),email,password)){
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }else{
                        Toast.makeText(requireContext(),"Email atau Password salah",Toast.LENGTH_LONG).show()
                    }

                    btnLogin.isVisible = true
                    pbLoading.isVisible = false
                }
            }
        }
    }
}