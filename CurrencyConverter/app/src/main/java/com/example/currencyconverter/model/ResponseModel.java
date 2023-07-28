package com.example.currencyconverter.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {

    @SerializedName("asset_id_base")
    public String assetIdBase;

    @SerializedName("rates")
    public List<CryptoModel> cryptoModelList;
}
