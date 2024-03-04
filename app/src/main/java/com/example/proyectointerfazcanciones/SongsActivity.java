package com.example.proyectointerfazcanciones;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class SongsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        TextView textView = findViewById(R.id.textView);

        StringBuilder stringBuilder = new StringBuilder();


        try {
            XmlResourceParser parser = getResources().getXml(R.xml.canciones);
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    if ("album".equals(tagName)) {
                        stringBuilder.append("Álbum:\n");
                    } else if ("titulo".equals(tagName)) {
                        stringBuilder.append("Título: ").append(parser.nextText()).append("\n");
                    } else if ("artista".equals(tagName)) {
                        stringBuilder.append("Artista: ").append(parser.nextText()).append("\n");
                    } else if ("genero".equals(tagName)) {
                        stringBuilder.append("Género: ").append(parser.nextText()).append("\n");
                    } else if ("fecha_publicacion".equals(tagName)) {
                        stringBuilder.append("Fecha de Publicación: ").append(parser.nextText()).append("\n");
                    } else if ("duracion".equals(tagName)) {
                        stringBuilder.append("Duración: ").append(parser.nextText()).append("\n");
                    } else if ("discografia".equals(tagName)) {
                        stringBuilder.append("Discografía: ").append(parser.nextText()).append("\n");
                    } else if ("imagen".equals(tagName)) {
                        stringBuilder.append("Imagen: ").append(parser.nextText()).append("\n");
                    } else if ("canciones".equals(tagName)) {
                        stringBuilder.append("\nCanciones:\n");
                    } else if ("cancion".equals(tagName)) {
                        stringBuilder.append("  - Canción:\n");
                    } else if ("titulo".equals(tagName)) {
                        stringBuilder.append("    Título: ").append(parser.nextText()).append("\n");
                    } else if ("escritores".equals(tagName)) {
                        stringBuilder.append("    Escritores: ").append(parser.nextText()).append("\n");
                    } else if ("productores".equals(tagName)) {
                        stringBuilder.append("    Productores: ").append(parser.nextText()).append("\n");
                    } else if ("duracion".equals(tagName)) {
                        stringBuilder.append("    Duración: ").append(parser.nextText()).append("\n");
                    }
                }
                eventType = parser.next();
            }

            parser.close();
        } catch (XmlPullParserException | IOException e) {
            Log.e("XML Parsing", "Error parsing XML", e);
        }

        textView.setText(stringBuilder.toString());
    }
}
