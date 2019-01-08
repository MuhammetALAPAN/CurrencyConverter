package com.example.muhem.currencyconverter;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    float TRY;
    float USD;
    float EUR;
    float GBP;
    float given_count;
    String selection;

    private RadioGroup radioGroup;
    private Button button;
    private EditText editText;
    private TextView tv1, tv2, tv3, tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Background().execute("https://api.exchangeratesapi.io/latest?base=TRY");
        selection = "TRY";

        button = findViewById(R.id.b1);
        radioGroup = findViewById(R.id.rg);
        editText = findViewById(R.id.et1);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        switch (selection) {
                            case "TRY":
                                selectionTRY();
                                break;
                            case "USD":
                                selectionUSD();
                                break;
                            case "EUR":
                                selectionEUR();
                                break;
                            case "GBP":
                                selectionGBP();
                                break;
                            default:
                        }
                    }
                }, 300);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("Mesaj: ", "onCheckedChanged");
                if (checkedId == R.id.rb1) {
                    selection = "TRY";
                } else if (checkedId == R.id.rb2) {
                    selection = "USD";
                } else if (checkedId == R.id.rb3) {
                    selection = "EUR";
                } else if (checkedId == R.id.rb4) {
                    selection = "GBP";
                }
            }
        });

    }

    class Background extends AsyncTask<String, String, String> {

        protected String doInBackground(String ...params){
            params[0] = "https://api.exchangeratesapi.io/latest?base=TRY";
            HttpURLConnection connection;
            BufferedReader br;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                String satir;
                String dosya = "";

                while ((satir = br.readLine()) != null) {
                    dosya += satir;
                }

                br.close();
                return dosya;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "hata";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jo = jsonObject.getJSONObject("rates");
                TRY = 1;
                USD = Float.parseFloat(jo.getString("USD"));
                EUR = Float.parseFloat(jo.getString("EUR"));
                GBP = Float.parseFloat(jo.getString("GBP"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void selectionTRY() {
        given_count = Float.parseFloat(editText.getText().toString());
        float try_try = given_count;
        float try_usd = (USD / TRY) * given_count;
        float try_eur = (EUR / TRY) * given_count;
        float try_gbp = (GBP / TRY) * given_count;

        tv1.setText("TRY = " + Float.toString(try_try));
        tv2.setText("USD = " + Float.toString(try_usd));
        tv3.setText("EUR = " + Float.toString(try_eur));
        tv4.setText("GBP = " + Float.toString(try_gbp));
    }

    protected void selectionUSD() {
        given_count = Float.parseFloat(editText.getText().toString());
        float usd_try = (TRY / USD) * given_count;
        float usd_usd = given_count;
        float usd_eur = (EUR / USD) * given_count;
        float usd_gbp = (GBP / USD) * given_count;

        tv1.setText("TRY = " + Float.toString(usd_try));
        tv2.setText("USD = " + Float.toString(usd_usd));
        tv3.setText("EUR = " + Float.toString(usd_eur));
        tv4.setText("GBP = " + Float.toString(usd_gbp));
    }

    protected void selectionEUR() {
        given_count = Float.parseFloat(editText.getText().toString());
        float eur_try = (TRY / EUR) * given_count;
        float eur_usd = (USD / EUR) * given_count;
        float eur_eur = given_count;
        float eur_gbp = (GBP / EUR) * given_count;

        tv1.setText("TRY = " + Float.toString(eur_try));
        tv2.setText("USD = " + Float.toString(eur_usd));
        tv3.setText("EUR = " + Float.toString(eur_eur));
        tv4.setText("GBP = " + Float.toString(eur_gbp));
    }

    protected void selectionGBP() {
        given_count = Float.parseFloat(editText.getText().toString());
        float try_try = (TRY / GBP) * given_count;
        float try_usd = (USD / GBP) * given_count;
        float try_eur = (EUR / GBP) * given_count;
        float try_gbp = given_count;

        tv1.setText("TRY = " + Float.toString(try_try));
        tv2.setText("USD = " + Float.toString(try_usd));
        tv3.setText("EUR = " + Float.toString(try_eur));
        tv4.setText("GBP = " + Float.toString(try_gbp));
    }
}
