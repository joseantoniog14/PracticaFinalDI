package com.example.practicafinaldi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ListView lista;
    ProgressDialog progressDialog;

    ArrayList<String> id= new ArrayList<String>();
    ArrayList<String> imagen= new ArrayList<String>();
    ArrayList<String> nombre= new ArrayList<String>();
    ArrayList<String> trama= new ArrayList<String>();
    ArrayList<String> estreno= new ArrayList<String>();
    ArrayList<String> temporadas= new ArrayList<String>();

    int cont=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setContentView(R.layout.activity_favoritos_mvdb);
        lista= findViewById(R.id.listview);

        DescargarJSON descargarJSON= new DescargarJSON();
        descargarJSON.execute("https://api.themoviedb.org/3/tv/90669-1899?api_key=4ff18dcb18a536b9c289bfa319fc1ce3&language=es");
    }
    private  class DescargarJSON extends AsyncTask<String,Void,Void> {
        String todo="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            JsonParser parser= new JsonParser();
            JsonObject array= parser.parse(todo).getAsJsonObject();
            JsonObject object= array.getAsJsonObject();

            imagen.add(object.get("backdrop_path").getAsString());
            nombre.add(object.get("original_name").getAsString());
            trama.add(object.get("overview").getAsString());
            estreno.add(object.get("first_air_date").getAsString());
            temporadas.add(object.get("number_of_seasons").getAsString());
            id.add(""+cont);
            cont++;

            /*ArrayAdapter<String> adapter;
            List<String> list= new ArrayList<String>();

            String dato= "nombre: "+object.get("original_name").getAsString();
            dato+= ", trama: "+object.get("overview").getAsString();
            dato+= ", estreno: "+object.get("first_air_date").getAsString();
            dato+= ", temporadas: "+object.get("number_of_seasons").getAsString();
            list.add(dato);
            adapter= new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,list);
            lista.setAdapter(adapter);*/

            AdaptadorParaCoches adaptadorParaCoches = new AdaptadorParaCoches(MainActivity2.this,R.layout.serie, id);
            lista.setAdapter(adaptadorParaCoches);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(String... strings) {
            String script = strings[0];
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(script);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    String linea = "";
                    while ((linea = br.readLine()) != null) {
                        todo += linea + "\n";
                        Thread.sleep(100);
                        publishProgress();
                    }
                    br.close();
                    inputStream.close();
                } else {
                    Toast.makeText(MainActivity2.this, "No se pudo conectar a la nube", Toast.LENGTH_SHORT).show();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public class AdaptadorParaCoches extends ArrayAdapter<String> {
        public AdaptadorParaCoches(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return rellenarFila(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return rellenarFila(position, convertView, parent);
        }

        public View rellenarFila(int posicion, View view, ViewGroup padre) {

            LayoutInflater inflater = getLayoutInflater();
            View mifila = inflater.inflate(R.layout.serie, padre, false);

            TextView textViewlema = mifila.findViewById(R.id.Nombre);
            textViewlema.setText(nombre.get(posicion));

            return mifila;
        }
    }
}