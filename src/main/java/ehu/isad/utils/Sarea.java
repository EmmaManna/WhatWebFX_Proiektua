package ehu.isad.utils;

import ehu.isad.controllers.db.CmsKud;
import ehu.isad.controllers.db.WhatWebSQLKud;
import javafx.application.Platform;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Sarea {

    public Sarea(){}

    public void irudiaLortu(String url){
        this.zerbitzariariDeitu(url);
        url= url.replace("https://","");
        url=url.replace("http://","");
        this.irudiaGorde(url);
    }

    private void irudiaGorde(String izena){
        BufferedImage image;
        try{
            izena = this.ezabatuAtzekoa(izena);
            URL Url =new URL("http://jongondra.xyz:3000/"+izena+"/kaptura.png");
            image = ImageIO.read(Url);
            ImageIO.write(image, "png", new File(Utils.lortuEzarpenak().getProperty("pathToImages")+izena+".png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private String ezabatuAtzekoa(String izena){
        while(izena.contains("/")){
            izena=izena.substring(0,izena.length()-1);
        }
        return izena;
    }

    private void zerbitzariariDeitu(String orrialdea){
        //Prestatu
        String url = "http://jongondra.xyz:3000/?page="+orrialdea;
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()


        String query = null;
        try {
            //query = String.format("page=%,d",
            // URLEncoder.encode(orrialdea, charset));

            //GET eskaera
            URLConnection connection = new URL(url /*+ "?" + query*/).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            //System.out.println(response);
            connection.connect();

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}