package ehu.isad.controllers.db;

import ehu.isad.utils.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WhatWebKud {

    private WhatWebKud(){
    }

    public void insertIrakur(String artx) throws IOException {
        FileReader fileR = new FileReader(artx);
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
