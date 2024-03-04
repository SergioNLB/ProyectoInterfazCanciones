package com.example.proyectointerfazcanciones;
public class Cancion {
    private String titulo;
    private String escritores;
    private String productores;
    private String duracion;

    public Cancion(String titulo, String escritores, String productores, String duracion) {
        this.titulo = titulo;
        this.escritores = escritores;
        this.productores = productores;
        this.duracion = duracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getEscritor() {
        return escritores;
    }

    public void setEscritores(String escritores) {
        this.escritores = escritores;
    }
    public String getProductor() {
        return productores;
    }
    public void setProductor(String productores) {
        this.productores = productores;
    }
}
