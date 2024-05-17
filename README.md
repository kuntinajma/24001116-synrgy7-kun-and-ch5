# Listing Movie App

Listing Movie App adalah aplikasi Android yang memungkinkan pengguna untuk register, login, melihat daftar film, dan mengelola profil mereka. Aplikasi ini menggunakan API dari [The Movie Database (TMDb)](https://www.themoviedb.org/) untuk mengambil data film.

## Fitur
- **Login dan Register**: Pengguna dapat mendaftar dan masuk ke aplikasi.
- **Home**: Menampilkan daftar film.
- **Profil**: Menampilkan informasi profil pengguna.
- **Logout**: Pengguna dapat keluar dari aplikasi.
- **Sistem Penyimpanan Data Lokal**: Menggunakan DataStore untuk menyimpan data pengguna secara lokal.

## Permissions
Aplikasi ini membutuhkan izin berikut:
- **Internet**: Untuk mengakses API TMDb.
- **Kontak**: Untuk mengakses dan menyimpan informasi kontak.
- **Lokasi**: Untuk fitur berbasis lokasi.

## Teknologi yang Digunakan
- **Bahasa Pemrograman**: Kotlin
- **Networking**: Retrofit
- **Penyimpanan Lokal**: DataStore
- **UI**: XML layout
