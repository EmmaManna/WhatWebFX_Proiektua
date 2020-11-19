package ehu.isad.controllers.db;

import ehu.isad.utils.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WhatWebKud {

    // singleton patroia
    private static WhatWebKud instantzia = new WhatWebKud();

    private WhatWebKud(){
    }

    public static WhatWebKud getInstantzia() {
        return instantzia;
    }

    public void insertIrakurri() throws IOException {
        FileReader fileR = new FileReader(Utils.lortuEzarpenak().getProperty("pathToInserts")+"insertak.sql");
        DBKudeatzaile dbKud = DBKudeatzaile.getInstantzia();
        try(BufferedReader bufferedReader = new BufferedReader(fileR)){
            String lerroa;
            while((lerroa=bufferedReader.readLine())!=null){
                System.out.println(lerroa);
                dbKud.execSQL(lerroa.replace("IGNORE","OR IGNORE"));
            }
        }
    Utils.ezabatu();
    }

    public Boolean jadaBilatuta(String url){
        String query = "SELECT target FROM targets WHERE target= ? ";
        List<String> parametroak = new ArrayList<String>();
        parametroak.add(url);
        List<String> motak = new ArrayList<String>();
        motak.add("String");
        ResultSet rs = this.eskaeraBabestua(query,parametroak,motak);
        /*
        String query = "SELECT target FROM targets WHERE target='"+url+"'";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);
        */
        try {
           if (rs.next()) {
               return true;
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return false;


    }

    private ResultSet eskaeraBabestua(String query, List<String> parametroak, List<String> motak){
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        try {
            PreparedStatement ps = dbKudeatzaile.conn.prepareStatement(query);
            int kont = 0;
            int zenb = 1;
            while(kont < motak.size()){
                if(motak.get(kont).equals("int")){
                    ps.setInt(zenb,Integer.parseInt(parametroak.get(kont)));
                }
                else{
                    ps.setString(zenb, parametroak.get(kont));
                }
                kont++;
                zenb++;
            }
             return ps.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}