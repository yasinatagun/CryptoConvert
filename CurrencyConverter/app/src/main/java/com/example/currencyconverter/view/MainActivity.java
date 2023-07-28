package com.example.currencyconverter.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.currencyconverter.databinding.ActivityMainBinding;
import com.example.currencyconverter.model.CryptoModel;
import com.example.currencyconverter.R;
import com.example.currencyconverter.model.ResponseModel;
import com.example.currencyconverter.service.CryptaAPI;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ResponseModel responseModels;
    private Spinner spinner;
    private Spinner spinner2;
    private ArrayAdapter adapter;
    private ArrayAdapter adapter2;
    private String BASE_URL = "https://rest.coinapi.io/v1/";
    private Retrofit retrofit;
    private String currencyValue;
    private List<CryptoModel> cryptoModels;
    private List<CryptoModel> lastCryptoModels = new ArrayList<>();
    private List<String> dropdownSpinner = new ArrayList<>();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //https://rest.coinapi.io/v1/exchangerate/BTC?apikey=BC90657E-FE12-4D18-906E-CF4C8F5EE33E

        //RETROFIT AND JSON
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        getDataFromAPI();
        currencyValue = "";
    }

    private void getDataFromAPI() {
        List<String> abc = Arrays.asList("s", "asd", "asd", "sfa");
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);

        adapter2 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_selected_item, dropdownSpinner);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);

        adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_selected_item, dropdownSpinner);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        CryptaAPI cryptaAPI = retrofit.create(CryptaAPI.class);

        Call<ResponseModel> call = cryptaAPI.getData();

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    cryptoModels = response.body().cryptoModelList;
                    ArrayList name = new ArrayList(Arrays.asList("DEV", "OLE", "PAR", "REI", "RPL", "SMG", "SNC"));

                    for (CryptoModel cryptoModel : cryptoModels) {
                        if (name.contains(cryptoModel.name)) {
                            lastCryptoModels.add(cryptoModel);
                        }
                    }
                    for (CryptoModel cryptoModel : lastCryptoModels) {
                        dropdownSpinner.add(cryptoModel.name);
                        adapter.notifyDataSetChanged();
                        adapter2.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void oncNumber(View view) {
        MaterialButton materialButton = (MaterialButton) view;
        if (binding.txtCurrencyOne.getText().toString().length() < 8) {
            if (binding.txtCurrencyOne.getText().toString().equals("0")) {
                binding.txtCurrencyOne.setText(materialButton.getText().toString());
                currencyValue = materialButton.getText().toString();
            } else {
                currencyValue += materialButton.getText().toString();
                binding.txtCurrencyOne.setText(currencyValue);
            }
        }
    }
    public void oncClear(View view) {
        currencyValue = "0";
        binding.txtCurrencyOne.setText(currencyValue);
        binding.txtCurrencyTwo.setText(currencyValue);
    }
    public void oncCalculate(View view) {
        int index1 = spinner.getSelectedItemPosition();
        int index2 = spinner2.getSelectedItemPosition();
        double rate1 = Double.parseDouble(lastCryptoModels.get(index1).rate);
        double rate2 = Double.parseDouble(lastCryptoModels.get(index2).rate);
        double value1 = Double.parseDouble(binding.txtCurrencyOne.getText().toString());
        double value2 = (rate1 * Double.parseDouble(binding.txtCurrencyOne.getText().toString())) / rate2;
        String lastValue = String.format("%.2f", value2);
        if (value2 == Math.round(value2)){
            binding.txtCurrencyTwo.setText(String.valueOf(Math.round(value2)));
        }else{
            binding.txtCurrencyTwo.setText(lastValue);
        }
    }
}