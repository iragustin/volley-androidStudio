package lat.pam.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tvsembuh, tvkasus, tvmeninggal;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvsembuh = findViewById(R.id.tvsebuh);
        tvkasus = findViewById(R.id.tvkasus);
        tvmeninggal = findViewById(R.id.tvmeninggal);

        tampilData();
    }

    private void tampilData() {
        loading = ProgressDialog.show(MainActivity.this,  "Memuat Data", "Mohon Tunggu...");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://coronavirus-19-api.herokuapp.com/all";
        JSONObject jsonObject = new JSONObject();
        final String requestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>(){
                    @Override
            public void onResponse(String response){
                        try {
                            JSONObject jo = new JSONObject(response.toString());
                            String sembuh = jo.getString("recovered");
                            String kasus = jo.getString("cases");
                            String meninggal = jo.getString("deaths");

                            tvsembuh.setText(sembuh);
                            tvkasus.setText(kasus);
                            tvmeninggal.setText(meninggal);
                            loading.cancel();
                            Toast.makeText(MainActivity.this, "Berhasil Memuat", Toast.LENGTH_SHORT).show();

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
        }, new Response.ErrorListener(){
                    @Override
            public void onErrorResponse(VolleyError error){
                        loading.cancel();
                        Toast.makeText(MainActivity.this, "Gagal ambil Rest Api" +error, Toast.LENGTH_SHORT).show();
                    }
        }
        );
        queue.add(stringRequest);
    }

}