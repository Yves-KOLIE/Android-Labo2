package com.kruserg.labo2;

import android.view.View;
import android.widget.ImageView;

public class AstreModel {
   private int id;
   private String nom;
   private int taille;
   private String couleur;
   private boolean status;
   private String image;
   private float x;
   private float y;
   View view;

    //four points of our rectangle to define the area of our planet
    float[] A = {x-100,y+100};
    float[] B = {x+100,y+100};
    float[] C = {x-100,y-100};
    float[] D = {x-100,y-100};


    public AstreModel(int id, String nom, int taille, String couleur, boolean status, String image) {
        this.id = id;
        this.nom = nom;
        this.taille = taille;
        this.couleur = couleur;
        this.status = status;
        this.image = image;
    }

    public float[] getA() {
        return A;
    }

    public void setA(float[] a) { A = a; }

    public float[] getB() {
        return B;
    }

    public void setB(float[] b) {
        B = b;
    }

    public float[] getC() {
        return C;
    }

    public void setC(float[] c) {
        C = c;
    }

    public float[] getD() {
        return D;
    }

    public void setD(float[] d) {
        D = d;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
