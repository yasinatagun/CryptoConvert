package com.example.currencyconverter.service;

import com.example.currencyconverter.model.CryptoModel;
import com.example.currencyconverter.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptaAPI {
    // get post update delete
    //https://rest.coinapi.io/v1/exchangerate/BTC?apikey=BC90657E-FE12-4D18-906E-CF4C8F5EE33E

    @GET("exchangerate/BTC?apikey=BC90657E-FE12-4D18-906E-CF4C8F5EE33E")
    Call<ResponseModel> getData();


}
