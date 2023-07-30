package com.kemalgeylani.kotlinretrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemalgeylani.kotlinretrofit.R
import com.kemalgeylani.kotlinretrofit.adapter.CryptoAdapter
import com.kemalgeylani.kotlinretrofit.databinding.ActivityMainBinding
import com.kemalgeylani.kotlinretrofit.model.CryptoModel
import com.kemalgeylani.kotlinretrofit.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels : ArrayList<CryptoModel>? = null
    private var cryptoAdapter: CryptoAdapter? = null

    // Disposable -> Activity, yaşam döngüsünde, destroy edildiğinde, call'ları hafızada yer tutmaması için
    // temizleyen kullan-at torbadır.
    private var compositeDisposable : CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        compositeDisposable = CompositeDisposable()

        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        loadData()

    }

    fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava ile kullanlır.
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handlerResponse)
        )

        /*
         val call = retrofit.create(CryptoAPI::class.java).getData()

        call.enqueue(object: Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)

                        cryptoModels?.let {
                            cryptoAdapter = CryptoAdapter(it)
                            binding.recyclerView.adapter = cryptoAdapter
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
        */

    }

    private fun handlerResponse(cryptoList: List<CryptoModel>) {

        cryptoModels = ArrayList(cryptoList)

        cryptoModels?.let {
            cryptoAdapter = CryptoAdapter(it)
            binding.recyclerView.adapter = cryptoAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }

}