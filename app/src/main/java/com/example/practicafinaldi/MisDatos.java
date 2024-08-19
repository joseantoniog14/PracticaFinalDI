package com.example.practicafinaldi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MisDatos extends AppCompatActivity {
    Button buttonCSV,buttonJSON,buttonXML,buttonGET,buttonPOST,buttonBORRAR,buttonMODIFICAR,buttonRecomendacion;
    EditText editTextID,editTextNombre,editTextEstreno,editTextCadena,editTextTemporadas,editTextRecomendacion;
    ListView lista;
    static final String SERVIDOR= "http://192.168.3.203/nube/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);
        buttonCSV= findViewById(R.id.buttonCSV);
        buttonJSON= findViewById(R.id.buttonJSON);
        buttonXML= findViewById(R.id.buttonXML);
        buttonGET= findViewById(R.id.buttonGET);
        buttonPOST= findViewById(R.id.buttonPOST);
        buttonBORRAR= findViewById(R.id.buttonBORRAR);
        buttonMODIFICAR= findViewById(R.id.buttonMODIFICAR);
        buttonRecomendacion= findViewById(R.id.buttonRecomendacion);

        editTextID=findViewById(R.id.editTextID);
        editTextNombre=findViewById(R.id.editTextNombre);
        editTextEstreno=findViewById(R.id.editTextEstreno);
        editTextCadena=findViewById(R.id.editTextCadena);
        editTextTemporadas=findViewById(R.id.editTextTemporadas);
        editTextRecomendacion=findViewById(R.id.editTextRecomendacion);
        lista= findViewById(R.id.listview);

        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String reg= (String) lista.getItemAtPosition(i);
                editTextID.setText(reg.split(",")[0].split(": ")[1]);
                editTextNombre.setText(reg.split(",")[1].split(": ")[1]);
                editTextEstreno.setText(reg.split(",")[2].split(": ")[1]);
                editTextCadena.setText(reg.split(",")[3].split(": ")[1]);
                editTextTemporadas.setText(reg.split(",")[4].split(": ")[1]);
                editTextRecomendacion.setText(reg.split(",")[5].split(": ")[1]);
            }
        });
        buttonCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DescargarCSV descargarCSV= new DescargarCSV();
                descargarCSV.execute("listadoCSV.php");
            }
        });
        buttonJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DescargarJSON descargarJSON= new DescargarJSON();
                descargarJSON.execute("listadoJSON.php");

            }
        });
        buttonXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DescargarXML descargarXML= new DescargarXML();
                descargarXML.execute("listadoXML.php");
            }
        });
        buttonGET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNombre.getText().toString().compareTo("")==0 || editTextEstreno.getText().toString().compareTo("")==0 || editTextCadena.getText().toString().compareTo("")==0 || editTextTemporadas.getText().toString().compareTo("")==0 || editTextRecomendacion.getText().toString().compareTo("")==0){
                    Toast.makeText(MisDatos.this, "Error,Introduce todos los datos", Toast.LENGTH_SHORT).show();
                }else{
                    if (Integer.parseInt(editTextRecomendacion.getText().toString())>0 && Integer.parseInt(editTextRecomendacion.getText().toString())<6){
                        if (Integer.parseInt(editTextTemporadas.getText().toString())>0){
                            InsertarGET insertarGET= new InsertarGET();
                            insertarGET.execute("insertar.php?Nombre="+editTextNombre.getText()+"&AnodeEstreno="+editTextEstreno.getText()+"&Cadena="+editTextCadena.getText()+"&NumTemporadas="+editTextTemporadas.getText()+"&Recomendacion="+editTextRecomendacion.getText());
                            Toast.makeText(MisDatos.this, "Datos insertados", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MisDatos.this, "Las temporadas deben ser mayoresm a 0", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MisDatos.this, "En recomendación debes meter un número entre el 0 y 5", Toast.LENGTH_SHORT).show();
                    }
                }
                buttonJSON.callOnClick();
            }
        });
        buttonPOST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNombre.getText().toString().compareTo("")==0 || editTextEstreno.getText().toString().compareTo("")==0 || editTextCadena.getText().toString().compareTo("")==0 || editTextTemporadas.getText().toString().compareTo("")==0 || editTextRecomendacion.getText().toString().compareTo("")==0){
                    Toast.makeText(MisDatos.this, "Error,introduzca todos los datos", Toast.LENGTH_SHORT).show();
                }else{
                    if (Integer.parseInt(editTextRecomendacion.getText().toString())>0 && Integer.parseInt(editTextRecomendacion.getText().toString())<6){
                        if (Integer.parseInt(editTextTemporadas.getText().toString())>0){
                            InsertarPOST insertarPOST= new InsertarPOST();
                            insertarPOST.execute("insertarPOST.php?");
                            Toast.makeText(MisDatos.this, "Datos insertados", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MisDatos.this, "Las temporadas deben ser mayoresm a 0", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MisDatos.this, "En recomendación debes meter un número entre el 0 y 5", Toast.LENGTH_SHORT).show();
                    }
                }
                buttonJSON.callOnClick();
            }
        });
        buttonBORRAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextID.getText().toString().compareTo("")==0){
                    Toast.makeText(MisDatos.this, "Error,mete un id", Toast.LENGTH_SHORT).show();
                }else{
                    BORRAR borrar= new BORRAR();
                    borrar.execute("borrar.php?id="+editTextID.getText());
                    Toast.makeText(MisDatos.this, "Datos borrados", Toast.LENGTH_SHORT).show();
                }
                buttonJSON.callOnClick();
                editTextID.setText("");
                editTextNombre.setText("");
                editTextEstreno.setText("");
                editTextCadena.setText("");
                editTextTemporadas.setText("");
                editTextRecomendacion.setText("");
            }
        });
        buttonMODIFICAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextID.getText().toString().compareTo("")==0){
                    Toast.makeText(MisDatos.this, "Error,mete un id", Toast.LENGTH_SHORT).show();
                }else{
                    if (editTextNombre.getText().toString().compareTo("")==0 || editTextEstreno.getText().toString().compareTo("")==0 || editTextCadena.getText().toString().compareTo("")==0 || editTextTemporadas.getText().toString().compareTo("")==0 || editTextRecomendacion.getText().toString().compareTo("")==0){
                        Toast.makeText(MisDatos.this, "Error,introduzca todos los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        if (Integer.parseInt(editTextRecomendacion.getText().toString())>0 && Integer.parseInt(editTextRecomendacion.getText().toString())<6){
                            if (Integer.parseInt(editTextTemporadas.getText().toString())>0){
                                MODIFICAR mod= new MODIFICAR();
                                mod.execute("actualizar.php?id="+editTextID.getText()+"&Nombre="+editTextNombre.getText().toString()+"&AnodeEstreno="+ editTextEstreno.getText().toString() +"&Cadena="+editTextCadena.getText().toString()+"&NumTemporadas="+Integer.parseInt(editTextTemporadas.getText().toString())+"&Recomendacion="+Integer.parseInt(editTextRecomendacion.getText().toString()));
                                Toast.makeText(MisDatos.this, "Datos modificados", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MisDatos.this, "Las temporadas deben ser mayoresm a 0", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MisDatos.this, "En recomendación debes meter un número entre el 0 y 5", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                buttonJSON.callOnClick();
            }
        });
        buttonRecomendacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatosPedidos datos= new DatosPedidos();
                datos.execute("sugerencia.php");

            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("¿Qué quiere hacer?");
        menu.add(0,v.getId(),0,"Modificar");
        menu.add(0,v.getId(),0,"Borrar");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int posicionLista=info.position;
        if(item.getTitle()=="Modificar"){
            if (editTextID.getText().toString().compareTo("")==0){
                Toast.makeText(MisDatos.this, "Error,mete un id", Toast.LENGTH_SHORT).show();
            }else{
                if (editTextNombre.getText().toString().compareTo("")==0 || editTextEstreno.getText().toString().compareTo("")==0 || editTextCadena.getText().toString().compareTo("")==0 || editTextTemporadas.getText().toString().compareTo("")==0 || editTextRecomendacion.getText().toString().compareTo("")==0){
                    Toast.makeText(MisDatos.this, "Error,introduzca todos los datos", Toast.LENGTH_SHORT).show();
                }else{
                    if (Integer.parseInt(editTextRecomendacion.getText().toString())>0 && Integer.parseInt(editTextRecomendacion.getText().toString())<6){
                        if (Integer.parseInt(editTextTemporadas.getText().toString())>0){
                            MODIFICAR mod= new MODIFICAR();
                            mod.execute("actualizar.php?id="+editTextID.getText()+"&Nombre="+editTextNombre.getText().toString()+"&AnodeEstreno="+ editTextEstreno.getText().toString() +"&Cadena="+editTextCadena.getText().toString()+"&NumTemporadas="+Integer.parseInt(editTextTemporadas.getText().toString())+"&Recomendacion="+Integer.parseInt(editTextRecomendacion.getText().toString()));
                            Toast.makeText(MisDatos.this, "Datos modificados", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MisDatos.this, "Las temporadas deben ser mayoresm a 0", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MisDatos.this, "En recomendación debes meter un número entre el 0 y 5", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            buttonJSON.callOnClick();

        }
        if(item.getTitle()=="Borrar"){
            if (editTextID.getText().toString().compareTo("")==0){
                Toast.makeText(MisDatos.this, "Error,Introduce un id", Toast.LENGTH_SHORT).show();
            }else{
                BORRAR borrar= new BORRAR();
                borrar.execute("borrar.php?id="+editTextID.getText());
                Toast.makeText(MisDatos.this, "Datos borrados", Toast.LENGTH_SHORT).show();
            }
            buttonJSON.callOnClick();
            editTextID.setText("");
            editTextNombre.setText("");
            editTextEstreno.setText("");
            editTextCadena.setText("");
            editTextTemporadas.setText("");
            editTextRecomendacion.setText("");
        }
        return  true;
    }
    private  class DescargarCSV extends AsyncTask<String,Void,Void> {
        String todo;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            ArrayAdapter<String> adapter;
            List<String> list= new ArrayList<String>();
            String [] lineas = todo.split("\n");
            for (String linea: lineas){
                String[] campos= linea.split(";");
                String dato= "ID: "+campos[0];
                dato+= ", Nombre: "+campos[1];
                dato+= ", Estreno: "+campos[2];
                dato+= ", Cadena: "+campos[3];
                dato+= ", Temporadas: "+campos[4];
                dato+= ", Recomendacion: "+campos[5];
                list.add(dato);
            }
            adapter= new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,list);
            lista.setAdapter(adapter);
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
                url=new URL(SERVIDOR+script);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode()==httpURLConnection.HTTP_OK){
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    String linea="";
                    while ((linea=br.readLine())!=null){
                        todo+=linea+"\n";
                        Thread.sleep(100);
                        publishProgress();
                    }
                    br.close();
                    inputStream.close();
                }else{
                    Toast.makeText(MisDatos.this, "No se pudo conectar a la nube", Toast.LENGTH_SHORT).show();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  null;
        }
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
            ArrayAdapter<String> adapter;
            List<String> list = new ArrayList<String>();
            String[] lineas = todo.split("\n");
            JsonParser parser= new JsonParser();
            JsonArray array= parser.parse(todo).getAsJsonArray();
            for (JsonElement linea : array) {
                JsonObject object= linea.getAsJsonObject();
                String dato = "ID: " + object.get("ID").getAsString();
                dato += ", Nombre: " + object.get("Nombre").getAsString();
                dato += ", Estreno: " + object.get("AnodeEstreno").getAsString();
                dato += ", Cadena: " + object.get("Cadena").getAsString();
                dato += ", Temporadas: " + object.get("NumTemporadas").getAsString();
                dato += ", Recomendacion: " + object.get("Recomendacion").getAsString();
                list.add(dato);
            }
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
            lista.setAdapter(adapter);
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
                url = new URL(SERVIDOR + script);
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
                    Toast.makeText(MisDatos.this, "No se pudo conectar a la nube", Toast.LENGTH_SHORT).show();
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
    private  class DescargarXML extends AsyncTask<String,Void,Void>{
        String todo;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            ArrayAdapter<String> adapter;
            List<String> list= new ArrayList<String>();
            String [] lineas = todo.split("\n");
            for (String linea: lineas){
                list.add(linea);
            }
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
            lista.setAdapter(adapter);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(String... strings) {
            String script = SERVIDOR+ strings[0];
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db= null;
            try {
                db = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = db.parse(new URL(script).openStream());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            Element raiz = doc.getDocumentElement();
            NodeList hijos= raiz.getChildNodes();
            for (int i = 0; i < hijos.getLength(); i++) {
                Node nodo = hijos.item(i);
                if (nodo instanceof Element) {
                    NodeList nietos=nodo.getChildNodes();
                    String[] fila = new String[nietos.getLength()];
                    for (int j = 0; j < nietos.getLength(); j++) {
                        fila[j]= nietos.item(j).getNodeName() +": "+nietos.item(j).getTextContent()+", ";
                        todo+=fila[j];
                    }
                    todo+="\n";
                }
            }
            return  null;
        }
    }
    private  class InsertarGET extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String script = strings[0];

            URL url = null;
            try {
                url = new URL(SERVIDOR + script);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpURLConnection;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private  class InsertarPOST extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String script = strings[0];

            URL url = null;
            try {
                url = new URL(SERVIDOR + script);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpURLConnection;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);

                PrintStream ps = new PrintStream(httpURLConnection.getOutputStream());

                ps.print("Nombre="+editTextNombre.getText().toString());
                ps.print("&AnodeEstreno="+editTextEstreno.getText().toString());
                ps.print("&Cadena="+editTextCadena.getText().toString());
                ps.print("&NumTemporadas="+editTextTemporadas.getText().toString());
                ps.print("&Recomendacion="+editTextRecomendacion.getText().toString());
                InputStream inputStream = httpURLConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private  class BORRAR extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String script = strings[0];

            URL url = null;
            try {
                url = new URL(SERVIDOR + script);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpURLConnection;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private  class MODIFICAR extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String script = strings[0];
            URL url = null;
            try {
                url = new URL(SERVIDOR + script);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpURLConnection;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private  class DatosPedidos extends AsyncTask<String,Void,Void> {
        String todo="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            ArrayAdapter<String> adapter;
            List<String> list = new ArrayList<String>();
            String[] lineas = todo.split("\n");
            JsonParser parser= new JsonParser();
            JsonArray array= parser.parse(todo).getAsJsonArray();
            for (JsonElement linea : array) {
                JsonObject object= linea.getAsJsonObject();
                String dato = "ID: " + object.get("ID").getAsString();
                dato += ", Nombre: " + object.get("Nombre").getAsString();
                dato += ", Estreno: " + object.get("AnodeEstreno").getAsString();
                dato += ", Cadena: " + object.get("Cadena").getAsString();
                dato += ", Temporadas: " + object.get("NumTemporadas").getAsString();
                dato += ", Recomendacion: " + object.get("Recomendacion").getAsString();
                list.add(dato);
            }
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
            lista.setAdapter(adapter);
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
                url = new URL(SERVIDOR + script);
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
                    Toast.makeText(MisDatos.this, "No se pudo conectar a la nube", Toast.LENGTH_SHORT).show();
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
}