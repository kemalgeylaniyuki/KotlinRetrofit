package com.kemalgeylani.kotlinretrofit.service

import com.kemalgeylani.kotlinretrofit.model.CryptoModel
import io.reactivex.Observable
import io.reactivex.Observer
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {

    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    // https://raw.githubusercontent.com/ -> Base URL
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")

    suspend fun getData(): Response<List<CryptoModel>>

    //fun getData(): Observable<List<CryptoModel>> RxJava

    //fun getData(): Call<List<CryptoModel>> Normal

}