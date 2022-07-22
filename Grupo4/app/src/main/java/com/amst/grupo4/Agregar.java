package com.amst.grupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Agregar extends AppCompatActivity {
    TextView prueba, datos;
    Button btnRequest;
    public RequestQueue mQueue;
    public RequestQueue mRequestQueue;
    public StringRequest mStringRequest;
    private static final String TAG = Agregar.class.getName();
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        mQueue = Volley.newRequestQueue(Agregar.this);
        datos = (TextView) findViewById(R.id.datos);
        btnRequest = (Button) findViewById(R.id.button5);
        final EditText temp = (EditText) findViewById(R.id.temp);
        final EditText fecha = (EditText) findViewById(R.id.fecha);
        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){
                                              System.out.println("aqui");
                                              String str_temp = temp.getText().toString();
                                              String str_fecha = fecha.getText().toString();
                                              System.out.println(str_temp);
                                              SubirDatos(str_temp, str_fecha);
                                          }
                                      }
        );
    }

    public void mostrar(View view){
        Intent intent3 = new Intent(Agregar.this, Registros.class);
        startActivity(intent3);
    }



    public void SubirDatos(String temp, String fecha){
        Map<String, String> params = new HashMap();
        params.put("date_created", fecha);
        params.put("key", "temperatura");
        params.put("value",temp);
        JSONObject parametros = new JSONObject(params);
        String login_url = "https://amstdb-lab.herokuapp.com/db/logTres";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, login_url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                datos.setText(response.toString());
                Toast.makeText(getApplicationContext(),"Response : Datos ingresados con éxito", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void
            onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Response :" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "JWT " + token);
                System.out.println(token);
                return params;
            }
        };
        mQueue.add(request);
    }


}