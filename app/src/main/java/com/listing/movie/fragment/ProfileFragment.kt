package com.listing.movie.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.listing.movie.R
import com.listing.movie.databinding.FragmentProfileBinding
import com.listing.movie.model.User
import com.listing.movie.utils.DatastoreUtils
import com.listing.movie.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.util.Calendar

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private lateinit var user:User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        with(binding){
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    user = DatastoreUtils.getLoggedUser(requireContext())
                    etUsername.setText(user.username)
                    etNama.setText(user.nama)
                    etAlamat.setText(user.alamat)
                    user.tanggalLahir?.let {
                        etTanggalLahir.setText(DateUtils.formatDate(it))
                    }
                }
            }
            btnLogout.setOnClickListener{
                GlobalScope.launch {
                    withContext(Dispatchers.Main) {
                        DatastoreUtils.logout(requireContext())
                        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                    }
                }
            }
            btnUpdate.setOnClickListener{
                if(isValid() && this@ProfileFragment::user.isInitialized) {
                    GlobalScope.launch {
                        withContext(Dispatchers.Main) {
                            btnUpdate.isVisible = false
                            pbLoading.isVisible = true

                            user.username = etUsername.text.toString()
                            user.nama = etNama.text.toString()
                            user.tanggalLahir = DateUtils.parseDate(etTanggalLahir.text.toString())
                            user.alamat = etAlamat.text.toString()

                            DatastoreUtils.saveUser(user,requireContext())
                            Toast.makeText(requireContext(),"Berhasil ubah data",Toast.LENGTH_LONG).show()

                            btnUpdate.isVisible = true
                            pbLoading.isVisible = false
                        }
                    }
                }
            }
            etTanggalLahir.setOnClickListener{
                showDatePickerDialog()
            }
        }
        return binding.root
    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                binding.etTanggalLahir.setText(DateUtils.formatDate(DateUtils.getDate(selectedYear,selectedMonth,selectedDay)))
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private fun isValid(): Boolean {
        var valid = true
        with(binding){
            if (etUsername.text.isEmpty()) {
                tilUsername.error = "Username tidak boleh kosong"
                valid = false
            } else {
                tilUsername.isErrorEnabled = false
            }

            if (etNama.text.isEmpty()) {
                tilNama.error = "Nama lengkap tidak boleh kosong"
                valid = false
            } else {
                tilNama.isErrorEnabled = false
            }

            if (etTanggalLahir.text.isEmpty()) {
                tilTanggalLahir.error = "Tanggal lahir tidak boleh kosong"
                valid = false
            } else {
                tilTanggalLahir.isErrorEnabled = false
            }

            if (etAlamat.text.isEmpty()) {
                tilAlamat.error = "Alamat tidak boleh kosong"
                valid = false
            } else {
                tilAlamat.isErrorEnabled = false
            }
        }

        return valid
    }
}