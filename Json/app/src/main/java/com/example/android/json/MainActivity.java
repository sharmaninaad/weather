package com.example.android.json;


        import android.app.Activity;
        import android.content.Context;
        import android.icu.text.RelativeDateTimeFormatter;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.constraint.ConstraintLayout;
        import android.text.Layout;
        import android.util.Log;
        //import android.view.Menu;
        //import android.view.MenuItem;
        import android.view.View;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.AbsoluteLayout;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        //import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.UnsupportedEncodingException;
        //import java.net.HttpURLConnection;
        import java.net.HttpURLConnection;
        //import java.net.MalformedURLException;
        import java.net.URL;
        import java.net.URLEncoder;
        import java.util.concurrent.ExecutionException;

        import static android.R.attr.description;
        import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
        import static android.support.v7.widget.AppCompatDrawableManager.get;

// import static android.R.attr.description;


public class MainActivity extends Activity {

    EditText cityName;
    TextView resultTextView;
   //layout;

    public void findWeather(View view) {
        cityName = (EditText) findViewById(R.id.cityName);

        //Log.i("cityName", cityName.getText().toString());

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        try {
            String encodedCityName = URLEncoder.encode(cityName.getText().toString(), "UTF-8");

            DownloadTask task = new DownloadTask();
            task.execute("http://api.apixu.com/v1/current.json?key=c457458589c14b33ae4120728171307&q=" + encodedCityName).get();


        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cityName = (EditText) findViewById(R.id.cityName);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

    }


    private class DownloadTask extends AsyncTask<String, Void, String> {
        LinearLayout layout= (LinearLayout) findViewById(R.id.lays);

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            //HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }

                return result;

            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                String message = "";

                JSONObject jsonObject = new JSONObject(result);

               JSONObject weatherInfo = jsonObject.getJSONObject("location");


                //Toast.makeText(MainActivity.this, weatherInfo, Toast.LENGTH_SHORT).show();
                //JSONArray arr = new JSONArray(weatherInfo);

                /*for (int i = 0; i < arr.length(); i++) {

                    JSONObject jsonPart = arr.getJSONObject(i);
                    String name;
                    String country;

                    name = jsonPart.getString("name");
                    country = jsonPart.getString("country");
                    if (!(name.equals("")) && !(country.equals(""))) {

                        message = message + "name :" + name + "\ncountry:" + country + "\n";

                    }*/
                String name=weatherInfo.getString("name");
                String region=weatherInfo.getString("region");
                message=message+"name :"+name+"\n region:"+region;
                    //Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                JSONObject current=jsonObject.getJSONObject("current");
                JSONObject condition=current.getJSONObject("condition");
                String text=condition.getString("text");
                String wind_mph=current.getString("wind_mph");
                String pressure_mb=current.getString("pressure_mb");
                String humidity=current.getString("humidity");
                String actualfeel=current.getString("feelslike_c");
                TextView textView=(TextView)findViewById(R.id.textView);
                textView.setText(text);
                message=message+"\nwind:"+wind_mph+" mph\n pressure:"+pressure_mb+"\n humidity:"+humidity+"\nactual feel:"+actualfeel;
               // String text=current.getString("text");

                    resultTextView = (TextView) findViewById(R.id.resultTextView);
                    if (!message.equals("")) {

                        resultTextView.setText(message);

                    } else {

                        Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();

                    }
                    // resultTextView.setText(message);


                } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        }
    }

                /*
                    String main;
                    String description;


                    main = jsonPart.getString("main");
                    description = jsonPart.getString("description");


                }




            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();

            }


        }
    }
}*/








