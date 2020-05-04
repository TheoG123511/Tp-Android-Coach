package com.example.coach.modele;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProfilTest {

    private Profil profil = new Profil(67, 165, 35, 0);
    private float img = (float)32.2;
    private String message = "trop élevé";
    @Test
    public void getImg() {
        assertEquals(img, profil.getImg(), (float)0.1);
    }

    @Test
    public void getMessage() {
        assertEquals(message, profil.getMessage());
    }
}