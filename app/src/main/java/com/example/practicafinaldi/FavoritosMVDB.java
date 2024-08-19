package com.example.practicafinaldi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import java.util.List;

public class FavoritosMVDB extends AppCompatActivity {
    ListView lista;

    ArrayList<String> id= new ArrayList<String>();
    ArrayList<String> imagen= new ArrayList<String>();
    ArrayList<String> nombre= new ArrayList<String>();
    ArrayList<String> trama= new ArrayList<String>();
    ArrayList<String> estreno= new ArrayList<String>();
    ArrayList<String> temporadas= new ArrayList<String>();

    AdaptadorParaCoches adaptadorParaCoches;
    int cont=0;
    static final String rutaimagen= "https://image.tmdb.org/t/p/original";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos_mvdb);
        lista= findViewById(R.id.lista);

        DescargarJSON descargarJSON= new DescargarJSON();
        descargarJSON.execute("https://api.themoviedb.org/3/tv/90669-1899?api_key=4ff18dcb18a536b9c289bfa319fc1ce3&language=es");
        DescargarJSON descargarJSON2= new DescargarJSON();
        descargarJSON2.execute("https://api.themoviedb.org/3/tv/60574-peaky-blinders?api_key=4ff18dcb18a536b9c289bfa319fc1ce3&language=es");
        DescargarJSON descargarJSON3= new DescargarJSON();
        descargarJSON3.execute("https://api.themoviedb.org/3/tv/1399-game-of-thrones?api_key=4ff18dcb18a536b9c289bfa319fc1ce3&language=es");
        DescargarJSON descargarJSON4= new DescargarJSON();
        descargarJSON4.execute("https://api.themoviedb.org/3/tv/1402-the-walking-dead?api_key=4ff18dcb18a536b9c289bfa319fc1ce3&language=es");
        DescargarJSON descargarJSON5= new DescargarJSON();
        descargarJSON5.execute("https://api.themoviedb.org/3/tv/119051-wednesday?api_key=4ff18dcb18a536b9c289bfa319fc1ce3&language=es");
        DescargarJSON descargarJSON6= new DescargarJSON();
        descargarJSON6.execute("https://api.themoviedb.org/3/tv/456-the-simpsons?api_key=4ff18dcb18a536b9c289bfa319fc1ce3&language=es");
        DescargarJSON descargarJSON7= new DescargarJSON();
        descargarJSON7.execute("https://api.themoviedb.org/3/tv/60625-rick-and-morty?api_key=4ff18dcb18a536b9c289bfa319fc1ce3&language=es");
        DescargarJSON descargarJSON8= new DescargarJSON();
        descargarJSON8.execute("https://api.themoviedb.org/3/tv/63174-lucifer?api_key=4ff18dcb18a536b9c289bfa319fc1ce3&language=es");
        DescargarJSON descargarJSON9= new DescargarJSON();
        descargarJSON9.execute("https://api.themoviedb.org/3/tv/1396-breaking-bad?api_key=4ff18dcb18a536b9c289bfa319fc1ce3&language=es");
        DescargarJSON descargarJSON10= new DescargarJSON();
        descargarJSON10.execute("https://api.themoviedb.org/3/tv/94997-house-of-the-dragon?api_key=4ff18dcb18a536b9c289bfa319fc1ce3&language=es");
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

            adaptadorParaCoches = new AdaptadorParaCoches(FavoritosMVDB.this,R.layout.serie, id);
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
                    Toast.makeText(FavoritosMVDB.this, "No se pudo conectar a la nube", Toast.LENGTH_SHORT).show();
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

            ImageView Imagen = mifila.findViewById(R.id.Imagen);
            //Uri uri= Uri.parse(rutaimagen+imagen.get(posicion));
            //Imagen.setImageURI(uri);

            new DownloadImageTask(Imagen).execute(rutaimagen+imagen.get(posicion));

            /*
            ImageView Imagen = mifila.findViewById(R.id.Imagen);
            Bitmap foto = null;
            try {
                foto= BitmapFactory.decodeStream((InputStream) new URL(rutaimagen+imagen.get(posicion)).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Imagen.setImageBitmap(foto);
            */

            TextView Nombre = mifila.findViewById(R.id.Nombre);
            Nombre.setText(nombre.get(posicion));

            TextView Trama = mifila.findViewById(R.id.Trama);
            Trama.setText(trama.get(posicion));

            TextView Estreno = mifila.findViewById(R.id.Estreno);
            Estreno.setText("Estreno: "+estreno.get(posicion));

            TextView Temporadas = mifila.findViewById(R.id.Temporadas);
            Temporadas.setText("Nº Temporadas: "+temporadas.get(posicion));

            return mifila;
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}