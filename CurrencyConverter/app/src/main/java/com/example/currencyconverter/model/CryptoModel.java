package com.example.currencyconverter.model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    @SerializedName("rate")
    public String rate;

    @SerializedName("asset_id_quote")
    public String name;
}
