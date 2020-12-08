package ehu.isad.controllers.db;

import ehu.isad.utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WhatWebSQLKud {

    // singleton patroia
    private static WhatWebSQLKud instantzia = new WhatWebSQLKud();

    private WhatWebSQLKud(){ }

    public static WhatWebSQLKud getInstantzia() {
        return instantzia;
    }


    public void insertIrakurri() throws IOException {
        //WhatWeb-etik lortutako INSERT-en fitxategiko lerroak SQLiterako itzultzen dira
        // eta exekutatzen dira, datuak datu basean gordetzeko. Azkenik fitxategia ezabatzen da
        FileReader fileR = new FileReader(Utils.lortuEzarpenak().getProperty("pathToInserts")+"insertak.sql");
        DBKudeatzaile dbKud = DBKudeatzaile.getInstantzia();
        try(BufferedReader bufferedReader = new BufferedReader(fileR)){
            String lerroa;
            while((lerroa=bufferedReader.readLine())!=null){
                lerroa = this.lerroaItzuli(lerroa);
                dbKud.execSQL(lerroa);
            }
        }
        Utils.ezabatu();
    }


    private String lerroaItzuli(String lerroa){
        //Lerroan beharrezko aldaketak gauzatzen ditu SQLite-n datuak txertatu ahal izateko
        lerroa = lerroa.replace("IGNORE","OR IGNORE");
        if(lerroa.contains("(status,target)")){
            lerroa = lerroa.replace("(status,target)","(status,target,lastUpdated)");
            lerroa = lerroa.substring(0,lerroa.length()-2);
            Calendar c = Calendar.getInstance();
            String eguna = Integer.toString(c.get(Calendar.DATE));
            String hila = Integer.toString(c.get(Calendar.MONTH));
            String urtea = Integer.toString(c.get(Calendar.YEAR));
            String data = urtea+"/"+hila+"/"+eguna;
            lerroa = lerroa+",'"+data+"');";
        }
        return lerroa;
    }


    public Boolean jadaBilatuta(String url){
        //Zehaztutako URL-a jada bilatu den konprobatzen du
        String query = "SELECT target FROM targets WHERE target= ? ";
        List<String> parametroak = new ArrayList<String>();
        parametroak.add(url);
        List<String> motak = new ArrayList<String>();
        motak.add("String");
        List<Integer> likePos = new ArrayList<>();
        ResultSet rs = SecureSQL.getInstantzia().eskaeraBabestua(query,parametroak,motak,likePos);

        try {
           if (rs.next()) {
               return true;
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return false;
    }

}