package com.piyush.currenttemp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    EditText etCity;
    Button btnFind;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etCity=(EditText)findViewById(R.id.etCity);
        btnFind=(Button)findViewById(R.id.btnFind);
        tvInfo=(TextView)findViewById(R.id.tvInfo);


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String c = etCity.getText().toString();
                String url = "http://api.openweathermap.org/";
                String sp = "data/2.5/weather?units=metric";
                String qu = "&q=" + c;
                String id = "e4c4aadb5cf75c2f2bc18a4e2ce8ce76";
                String m = url + sp + qu + "&appid=" + id;

                MyTask t = new MyTask();
                t.execute(m);
            }



        });
    }








    class MyTask extends AsyncTask<String,Void,Double>

    {

        @Override
        protected Double doInBackground(String... strings) {
            double temp=0.0;
            String line="";
            String json="";

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);


                while ((line = br.readLine()) != null) {
                    json = json + line + "\n";
                    }

                JSONObject o = new JSONObject(json);
                JSONObject p = o.getJSONObject("main");
                temp = p.getDouble("temp");





            } catch (Exception e) {
            }return temp;


        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);


            tvInfo.setText("Temp= "+aDouble);
        }
    }

}
