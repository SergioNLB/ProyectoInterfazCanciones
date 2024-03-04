package com.example.proyectointerfazcanciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActividadImagenes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_imagenes);

        leerXML();
    }

    private void leerXML() {
        XmlPullParser parser = getResources().getXml(R.xml.canciones);
        List<Album> albumList = new ArrayList<>();

        try {
            String titulo = "";
            String imagen = "";

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    if (tagName.equals("album")) {
                        titulo = "";
                        imagen = "";
                    } else if (tagName.equals("titulo")) {
                        titulo = parser.nextText();
                    } else if (tagName.equals("imagen")) {
                        imagen = parser.nextText();
                        albumList.add(new Album(titulo, imagen));
                    }
                }
            }

            updateViews(albumList);

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void updateViews(List<Album> albumList) {
        LinearLayout container = findViewById(R.id.container);

        for (final Album album : albumList) {
            View albumView = getLayoutInflater().inflate(R.layout.activity_actividad_imagenes, null);

            TextView titleTextView = albumView.findViewById(R.id.titleTextView);
            ImageView albumImageView = albumView.findViewById(R.id.albumImageView);

            titleTextView.setText(album.getTitulo());
            int resourceId = getResources().getIdentifier(album.getImagen(), "drawable", getPackageName());
            if (resourceId != 0) {
                albumImageView.setImageResource(resourceId);
            } else {
                // Manejar el caso en que no se encuentra la imagen
            }

            // Agregar un onClickListener al ImageView
            albumImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Abrir la nueva actividad pasando el título del álbum
                    Intent intent = new Intent(ActividadImagenes.this, ActivityCanciones.class);
                    intent.putExtra("album_title", album.getTitulo());
                    startActivity(intent);
                }
            });

            container.addView(albumView);
        }
    }
}