package com.listing.movie.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.listing.movie.R
import com.listing.movie.databinding.FragmentRegisterBinding
import com.listing.movie.model.User
import com.listing.movie.utils.DatastoreUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegisterBinding.inflate(inflater,container,false)
        with(binding) {
            btnRegister.setOnClickListener {
                register()
            }
        }
        return binding.root
    }

    private fun register() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                if(isValid()) {
                    with(binding) {
                        btnRegister.isVisible = false
                        pbLoading.isVisible = true
                        val username = etUsername.text.toString()
                        val email = etEmail.text.toString()
                        val password = etPassword.text.toString()
                        val user = User(email,username,password,null,null,null)
                        DatastoreUtils.saveUser(user,requireContext())
                        reset()
                        Toast.makeText(requireContext(),"Registrasi berhasil",Toast.LENGTH_LONG).show()
                        btnRegister.isVisible = true
                        pbLoading.isVisible = false
                    }
                }
            }
        }
    }

    private fun reset() {
        with(binding){
            etUsername.setText(null)
            etEmail.setText(null)
            etPassword.setText(null)
            etPasswordConfirmation.setText(null)
        }
    }

    private suspend fun isValid(): Boolean {
        var valid = true
        with(binding){
            if (etUsername.text.isEmpty()) {
                tilUsername.error = "Username tidak boleh kosong"
                valid = false
            } else {
                tilUsername.isErrorEnabled = false
            }

            if (etEmail.text.isEmpty()) {
                tilEmail.error = "Email tidak boleh kosong"
                valid = false
            }else if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.text).matches()){
                tilEmail.error = "Email tidak valid"
                valid = false
            }else{
                val user = DatastoreUtils.getUser(requireContext(),etEmail.text.toString())
                if(user!=null){
                    tilEmail.error = "Email telah terpakai"
                    valid = false
                }else{
                    tilEmail.isErrorEnabled = false
                }
            }

            if(etPassword.text.isEmpty()){
                tilPassword.error = "Password tidak boleh kosong"
                valid = false
            }else{
               tilPassword.isErrorEnabled = false
            }

            if(etPasswordConfirmation.text.toString() != etPassword.text.toString()){
                tilPasswordConfirmation.error = "Konfirmasi password tidak sama"
                valid = false
            }else{
                tilPasswordConfirmation.isErrorEnabled = false
            }
        }

        return valid
    }
}