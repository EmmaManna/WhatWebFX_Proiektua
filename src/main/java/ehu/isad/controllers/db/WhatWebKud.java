package ehu.isad.controllers.db;

import ehu.isad.utils.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        FileReader fileR = new FileReader("insertak.sql");
        DBKudeatzaile dbKud = DBKudeatzaile.getInstantzia();
        try(BufferedReader bufferedReader = new BufferedReader(fileR)){
            String lerroa;
            while((lerroa=bufferedReader.readLine())!=null){
                dbKud.execSQL(lerroa.replace("IGNORE","OR IGNORE"));
            }
        }
    Utils.ezabatu();
    }

    public Boolean jadaBilatuta(String url){
        String query = "SELECT target,status FROM targets WHERE target='"+url+"'";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        try {
           if (rs.next()) {
                String status = rs.getString("status");
                if(status.equals("200")){ //Bilaketa egin da eta dena OK zegoen
                    return true;
                }
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return false;
    }
}
