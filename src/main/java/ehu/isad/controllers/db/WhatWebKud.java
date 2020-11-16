package ehu.isad.controllers.db;

import ehu.isad.utils.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
                dbKud.execSQL(lerroa.replace("IGNORE","OR IGNORE"));
            }
        }
    Utils.ezabatu();
    }
}
