package ehu.isad.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Sarea {

    public Sarea(){}


    public void irudiaLortu(String url){
        //Zehaztutako URL-ko screenshota lortzen da: 1.zerbitzariari eskaera egiten zaio eta
        //2. irudia deskargatzen da eta bordetzen da
        //(deskargatzeko izena soilik beharrezkoa da, HTTP/S buruko gabe)
        this.zerbitzariariDeitu(url);
        url= url.replace("https://","");
        url=url.replace("http://","");
        this.irudiaGorde(url);
    }


    private void irudiaGorde(String izena){
        //Emandako izena duen irudia zerbitzarira eskatzen da, eta sisteman gordetzen da
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
        //Emandako URL-an lehenengo / -tik aurrera dagoen guztia kendu
        while(izena.contains("/")){
            izena=izena.substring(0,izena.length()-1);
        }
        return izena;
    }


    private void zerbitzariariDeitu(String orrialdea){
        //Zerbitzariari eskatu zehaztutako orrialdeko screenshot-a egitea
        String url = "http://jongondra.xyz:3000/?page="+orrialdea;
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()

        try {
            //GET eskaera
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            connection.connect();

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}