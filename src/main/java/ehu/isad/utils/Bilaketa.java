package ehu.isad.utils;

import ehu.isad.controllers.db.WhatWebKud;
import ehu.isad.controllers.ui.WhatWebKudeatzaile;
import ehu.isad.utils.Utils;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Bilaketa {


    public List<String> urlIrakurri(String url) {
        List<String> processes = new LinkedList<String>();
        try {
            String line;
            Process p=null;

            String komandoa = "whatweb --log-sql="+ Utils.lortuEzarpenak().getProperty("pathToInserts")+"insertak.sql " + url+" --color=never";

            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                komandoa = "wsl " + komandoa;
            }

            p = Runtime.getRuntime().exec(komandoa);
            p.waitFor();

            System.out.println(komandoa);

            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                processes.add(line);
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        return processes;
    }

}
