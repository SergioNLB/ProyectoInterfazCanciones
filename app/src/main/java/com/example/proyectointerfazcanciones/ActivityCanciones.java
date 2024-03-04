package com.example.proyectointerfazcanciones;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivityCanciones extends AppCompatActivity {

    private TextView tituloCancionTextView, escritorTextView, productorTextView, duracionCancionTextView;
    private List<Cancion> cancionList;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_canciones);

        mediaPlayer = new MediaPlayer();
        leerXML();
    }

    private void leerXML() {
        XmlPullParser parser = getResources().getXml(R.xml.canciones);
        List<Cancion> cancionList = new ArrayList<>();

        try {
            String titulo = "";
            String escritor = "";
            String productor = "";
            String duracion = "";
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    if (tagName.equals("canciones")) {
                        titulo = "";
                        escritor = "";
                        productor = "";
                        duracion = "";
                    } else if (tagName.equals("titulo")) {
                        titulo = parser.nextText();
                    } else if (tagName.equals("escritores")) {
                        escritor = parser.nextText();
                    } else if (tagName.equals("productores")) {
                        productor = parser.nextText();
                    } else if (tagName.equals("duracion")) {
                        duracion = parser.nextText();
                        cancionList.add(new Cancion(titulo, escritor, productor, duracion));
                    }
                }
            }
            this.cancionList = cancionList;
            updateViews();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void updateViews() {
        if (cancionList.isEmpty()) {
            // Considera agregar un mensaje de "lista vacía" o alguna acción similar aquí.
            Log.d("updateViews", "La lista de canciones está vacía.");
        }

        LinearLayout container = findViewById(R.id.container);

        for (final Cancion cancion : cancionList) {
            View cancionView = getLayoutInflater().inflate(R.layout.activity_actividad_canciones, null);


            TextView tituloTextView = cancionView.findViewById(R.id.tituloCancionTextView);
            TextView escritorTextView = cancionView.findViewById(R.id.escritorTextView);
            TextView productorTextView = cancionView.findViewById(R.id.productorTextView);
            TextView duracionTextView = cancionView.findViewById(R.id.duracionCancionTextView);

            tituloTextView.setText("Título: " + cancion.getTitulo());
            escritorTextView.setText("Escritor: " + cancion.getEscritor());
            productorTextView.setText("Productor: " + cancion.getProductor());
            duracionTextView.setText("Duración: " + cancion.getDuracion());
            // Agregar listener de clics al título de la canción para reproducir
            tituloTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playSong(cancion.getTitulo());
                    Toast.makeText(getApplicationContext(),"Reproduciendo",Toast.LENGTH_SHORT).show();
                }
            });
            getSongResourceId(String.valueOf(cancionView));
            container.addView(cancionView);
        }
    }
    private int getSongResourceId(String title) {
        // Convertir el título a minúsculas y eliminar espacios
        String resourceName = title.toLowerCase().replace(" ", "_");

        // Obtener el ID del recurso en la carpeta raw
        int resourceId = getResources().getIdentifier(resourceName, "raw", getPackageName());

        return resourceId;
    }
    private void playSong(String title) {
        int resourceId = 0;
        for (Cancion cancion : cancionList) {
            if (cancion.getTitulo().equals(title)) {
                resourceId = getResources().getIdentifier(cancion.getTitulo(), "raw", getPackageName());
                break;
            }
        }
        if (resourceId != 0) {
            try {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(this, resourceId);
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}