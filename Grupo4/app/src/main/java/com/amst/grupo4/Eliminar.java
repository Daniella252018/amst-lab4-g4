package com.amst.grupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Eliminar extends AppCompatActivity {

    private JsonArrayRequest mRequestQueue;
    private StringRequest mStringRequest;
    private static final String TAG = Eliminar.class.getName();
    public ArrayList<String> ides = new ArrayList<>();
    public ArrayList<String> temp = new ArrayList<>();
    private RequestQueue ListaRequest = null;
    public ListView listado1, listado2;
    public ArrayAdapter<String> adaptador1;
    public ArrayAdapter<String> adaptador2;
    public RequestQueue mQueue;
    private Eliminar contexto;
    TextView dato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);
        ListaRequest = Volley.newRequestQueue(Eliminar.this);
        listado1 = findViewById(R.id.lista1);
        listado2 = findViewById(R.id.lista2);
        dato = (TextView)findViewById(R.id.datoeliminado);
        mQueue = Volley.newRequestQueue(Eliminar.this);
        contexto = this;
        this.Temperaturas();
    }

    public void regresar(View view){
        Intent intent5 = new Intent(Eliminar.this, Registros.class);
        startActivity(intent5);
    }


    private void Temperaturas() {
        String url_registros = "https://amstdb-lab.herokuapp.com/db/logTres";
        JsonArrayRequest requestRegistros = new JsonArrayRequest(
                Request.Method.GET, url_registros, null,
                response -> {
                    llenarlistas(response);

                }, error -> System.out.println(error)
        );
        ListaRequest.add(requestRegistros);
    }



    private void llenarlistas(JSONArray temperaturas){

        try {
            for (int i = 0; i < temperaturas.length(); i++) {
                JSONObject json = temperaturas.getJSONObject(i);
                //Aquí se obtiene el dato y es guardado en una lista
                ides.add(json.getString("id"));
                temp.add(json.getString("value"));
                adaptador1 = new ArrayAdapter<>(Eliminar.this, android.R.layout.simple_list_item_1,ides);
                listado1.setAdapter(adaptador1);
                adaptador2 = new ArrayAdapter<>(Eliminar.this, android.R.layout.simple_list_item_1,temp);
                listado2.setAdapter(adaptador2);
                listado1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String posicion = ides.get(position);
                        dato.setText(posicion);
                        eliminarTemperatura(posicion);

                    }
                });


            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }



    private void eliminarTemperatura(String ide){
        String tmpid = "https://amstdb-lab.herokuapp.com/api/logTres/"+ide;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, tmpid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                //datos.setText(response.toString());
                Toast.makeText(getApplicationContext(),"Response : Temperatura eliminada con éxito", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void
            onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),"Response :" + error.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Response : Temperatura eliminada con éxito", Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }



    }

