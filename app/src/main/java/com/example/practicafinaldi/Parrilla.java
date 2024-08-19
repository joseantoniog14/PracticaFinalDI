package com.example.practicafinaldi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Parrilla extends AppCompatActivity {
    ProgressDialog progressDialog;
    ListView lista;
    ArrayList<String> Canales=new ArrayList<>();
    ArrayList<String> nombre=new ArrayList<>();
    ArrayList<String> icono=new ArrayList<>();
    ArrayList<String> id=new ArrayList<>();

    AdaptadorParaCoches adaptadorParaCoches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parrilla);
        lista=findViewById(R.id.lista2);
        DescargarXML descargarXML = new DescargarXML();
        descargarXML.execute("https://raw.githubusercontent.com/dracohe/CARLOS/master/guide_IPTV.xml");
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DescargarXML2 descargarXML2 = new DescargarXML2();
                descargarXML2.execute("https://raw.githubusercontent.com/dracohe/CARLOS/master/guide_IPTV.xml",Integer.toString(position));
            }
        });
    }
    private class DescargarXML extends AsyncTask<String, Void, Void> {
        String todo="" ;
        ArrayAdapter<String> adapter;
        List<String> list;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Parrilla.this);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            /*
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
            lista.setAdapter(adapter);
             */

            adaptadorParaCoches = new AdaptadorParaCoches(Parrilla.this,R.layout.pa, id);
            lista.setAdapter(adaptadorParaCoches);

            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(progressDialog.getVolumeControlStream() + 10);

        }

        @Override
        protected Void doInBackground(String... strings) {
            String script = strings[0];
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(script);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                list= new ArrayList<String>();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(new URL(url.toString()).openStream());
                    Element raiz = doc.getDocumentElement();
                    NodeList hijos = raiz.getChildNodes();

                    for (int i = 0; i < hijos.getLength(); i++) {
                        Node nodo = hijos.item(i);


                        if (nodo instanceof Element ) {
                            if(nodo.getNodeName().compareTo("channel")==0) {
                                NodeList nietos = nodo.getChildNodes();
                                for (int j = 0; j < nietos.getLength(); j++) {
                                    todo += nietos.item(j).getTextContent();
                                    if(nietos.item(j).getNodeName().compareTo("display-name")==0){
                                        nombre.add(nietos.item(j).getTextContent());
                                        id.add(""+j);
                                    }
                                }
                                list.add(todo);
                                todo = "";
                            }
                        }

                    }
                } else {
                    Toast.makeText(Parrilla.this, "No se puede conectar a la nube", Toast.LENGTH_SHORT).show();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private class DescargarXML2 extends AsyncTask<String, Void, Void> {
        String todo="" ;
        List<String> list;
        int posicion;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Parrilla.this);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressDialog.dismiss();
            AlertDialog.Builder builder=new AlertDialog.Builder(Parrilla.this);
            builder.setTitle("***PROGRAMACION***");
            for(int i =0;i<Canales.size();i++){
                builder.setMessage("\n"+Canales.get(i));
            }
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Canales.removeAll(Canales);
                }
            });
            builder.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected Void doInBackground(String... strings) {
            String script = strings[0];
            posicion=Integer.parseInt(strings[1]);
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(script);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                list= new ArrayList<String>();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(new URL(url.toString()).openStream());
                    Element raiz = doc.getDocumentElement();
                    NodeList hijos = raiz.getChildNodes();

                    boolean aux=false;

                    for (int i = 0; i < hijos.getLength(); i++) {
                        Node nodo = hijos.item(i);

                        if (nodo instanceof Element ) {
                            if(nodo.getNodeName().compareTo("programme")==0) {
                                if(((Element) nodo).getAttribute("channel").compareTo(nombre.get(posicion))==0) {
                                    NodeList nietos = nodo.getChildNodes();
                                    for (int j = 0; j < nietos.getLength(); j++) {
                                        String nietos2 = nietos.item(j).getTextContent();
                                        aux=true;
                                        if(nietos.item(j).getNodeName().compareTo("title")==0){
                                            todo+="~~~";
                                            todo += nietos.item(j).getTextContent();
                                            todo+="~~~\n";
                                        }
                                        if(nietos.item(j).getNodeName().compareTo("desc")==0){
                                            todo += nietos.item(j).getTextContent();
                                        }
                                        if(nietos.item(j).getNodeName().compareTo("icon")==0){
                                            icono.add(nietos.item(j).getTextContent());
                                        }
                                    }
                                    todo+="\n";
                                    todo+="\n";
                                }
                            }
                        }
                    }
                    if(aux==false){
                        todo+="*************\n";
                        todo+= "No existe información\n";
                        todo+="*************\n";
                    }
                    Canales.add(todo);
                } else {
                    Toast.makeText(Parrilla.this, "No se puede conectar a la nube", Toast.LENGTH_SHORT).show();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
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
            View mifila = inflater.inflate(R.layout.pa, padre, false);

            TextView Nombre = mifila.findViewById(R.id.Nombre);
            Nombre.setText(nombre.get(posicion));

            return mifila;
        }
    }
}