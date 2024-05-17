package com.listing.movie.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.remove
import androidx.datastore.preferences.createDataStore
import com.listing.movie.model.User
import kotlinx.coroutines.flow.first
import java.util.Date

class DatastoreUtils {
    companion object{
        suspend fun saveUser(user:User, ctx:Context){
            val dataStore = ctx.createDataStore("users")
            dataStore.edit {settings->
                settings[preferencesKey<String>("email_${user.email}")] = user.email
                settings[preferencesKey<String>("username_${user.email}")] = user.username
                settings[preferencesKey<String>("password_${user.email}")] = user.password
                settings[preferencesKey<String>("nama_${user.email}")] = user.nama?:""
                settings[preferencesKey<Long>("tanggal_lahir_${user.email}")] = user.tanggalLahir?.time?:0
                settings[preferencesKey<String>("alamat_${user.email}")] = user.alamat?:""
            }
        }

        suspend fun getUser(ctx:Context,email:String):User?{
            val dataStore = ctx.createDataStore("users")
            val preferences = dataStore.data.first()
            val emailData:String = preferences[preferencesKey("email_$email")]?:""
            val username:String = preferences[preferencesKey("username_$email")]?:""
            val password:String = preferences[preferencesKey("password_$email")]?:""
            val nama:String = preferences[preferencesKey("nama_$email")]?:""
            val tanggalLahir:Long = preferences[preferencesKey("tanggal_lahir_$email")]?:0
            val alamat:String = preferences[preferencesKey("alamat_$email")]?:""
            if(emailData==""){
                return null
            }
            return User(emailData,username,password,nama,Date(tanggalLahir),alamat)
        }

        suspend fun getLoggedUser(ctx:Context):User{
            val dataStore = ctx.createDataStore("users")
            val preferences = dataStore.data.first()
            val email:String = preferences[preferencesKey("login")]?:""

            val emailData:String = preferences[preferencesKey("email_$email")]?:""
            val username:String = preferences[preferencesKey("username_$email")]?:""
            val password:String = preferences[preferencesKey("password_$email")]?:""
            val tanggalLahir:Long = preferences[preferencesKey("tanggal_lahir_$email")]?:0
            val alamat:String = preferences[preferencesKey("alamat_$email")]?:""
            val nama:String = preferences[preferencesKey("nama_$email")]?:""
            return User(emailData,username,password,nama,Date(tanggalLahir),alamat)
        }

        suspend fun login(ctx:Context,email:String, password:String): Boolean {
            val dataStore = ctx.createDataStore("users")
            val preferences = dataStore.data.first()
            val dataEmail:String = preferences[preferencesKey("email_$email")]?:""
            val dataPassword:String = preferences[preferencesKey("password_$email")]?:""
            if(dataEmail!=""&&password==dataPassword){
                dataStore.edit {settings->
                    settings[preferencesKey<String>("login")] = email
                }
                return true
            }
            return false
        }
        suspend fun logout(ctx:Context){
            val dataStore = ctx.createDataStore("users")
            dataStore.edit {
                it.remove(preferencesKey<String>("login"));
            }
        }
        suspend fun checkLogin(ctx: Context): String {
            val dataStore = ctx.createDataStore("users")
            val preferences = dataStore.data.first()
            return preferences[preferencesKey<String>("login")] ?: ""
        }
    }

}