package ehu.isad.controllers.db;

import ehu.isad.utils.Bilaketa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;


class ServerKudTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        String query1 = "DELETE FROM scans";
        String query2 = "DELETE FROM request_configs";
        String query3 = "DELETE FROM targets";

        DBKudeatzaile.getInstantzia().execSQL(query1);
        DBKudeatzaile.getInstantzia().execSQL(query2);
        DBKudeatzaile.getInstantzia().execSQL(query3);
    }


    @Test
    void lortuTargets() throws IOException {
        List<String> emaitza;
        Bilaketa b = new Bilaketa();

        //Datu basea hutsik dagoenean
        emaitza = ServerKud.getInstantzia().lortuTargets();
        Assertions.assertTrue(emaitza.isEmpty());

        //Elementu bakarra
        b.urlIrakurri("https://www.apple.com/es/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = ServerKud.getInstantzia().lortuTargets();
        Assertions.assertTrue(emaitza.size() == 1);
        Assertions.assertEquals("https://www.apple.com/es/",emaitza.get(0));

        //Zenbait elementu
        b.urlIrakurri("https://ikasten.io/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = ServerKud.getInstantzia().lortuTargets();
        Assertions.assertTrue(emaitza.size() == 2);
        Assertions.assertEquals("https://www.apple.com/es/", emaitza.get(0));
        Assertions.assertEquals("https://ikasten.io/", emaitza.get(1));
    }


    @Test
    void lortuZerbitzaria() throws IOException {
        List<String> emaitza;
        Bilaketa b = new Bilaketa();

        //Datu basea hutsik dagoenean
        emaitza = ServerKud.getInstantzia().lortuZerbitzaria();
        Assertions.assertTrue(emaitza.size()==1);
        Assertions.assertEquals("Iragazki Gabe",emaitza.get(0));

        //Elementu bakarra, Apache erabiltzen du
        b.urlIrakurri("https://www.apple.com/es/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = ServerKud.getInstantzia().lortuZerbitzaria();
        Assertions.assertTrue(emaitza.size() == 2);
        Assertions.assertEquals("Apache",emaitza.get(0));
        Assertions.assertEquals("Iragazki Gabe",emaitza.get(1));

        //Zenbait elementu, Apache eta bestea nginx erabiltzen du
        b.urlIrakurri("https://www.dropbox.com/es_ES");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = ServerKud.getInstantzia().lortuZerbitzaria();
        Assertions.assertTrue(emaitza.size() == 3);
        Assertions.assertEquals("Apache",emaitza.get(0));
        Assertions.assertEquals("nginx",emaitza.get(1));
        Assertions.assertEquals("IRAGAZKI GABE",emaitza.get(2));
    }


    @Test
    void zerbitzariaDa() throws IOException {
        Boolean emaitza;
        Bilaketa b = new Bilaketa();

        //Datu basea hutsik dagoenean, eta ez da elementurik pasatzen
        emaitza = ServerKud.getInstantzia().zerbitzariaDa("","");
        Assertions.assertFalse(emaitza);

        //Datu basea hutsik dagoenean, elementua pasatzen da
        emaitza = ServerKud.getInstantzia().zerbitzariaDa("Apache","https://www.apple.com/es/");
        Assertions.assertFalse(emaitza);

        //Datu basean elementu bakarra, eta ez da elementurik pasatzen
        b.urlIrakurri("https://www.apple.com/es/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = ServerKud.getInstantzia().zerbitzariaDa("","");
        Assertions.assertFalse(emaitza);

        //Datu basean elementu bakarra, zerbitzaria berdina
        emaitza = ServerKud.getInstantzia().zerbitzariaDa("Apache","https://www.apple.com/es/");
        Assertions.assertTrue(emaitza);

        //Datu basean elementu bakarra, zerbitzari ezberdina
        emaitza = ServerKud.getInstantzia().zerbitzariaDa("nginx","https://www.apple.com/es/");
        Assertions.assertFalse(emaitza);

        //Datu basean zenbait elementu, eta ez da elementurik pasatzen
        b.urlIrakurri("https://ikasten.io/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = ServerKud.getInstantzia().zerbitzariaDa("","");
        Assertions.assertFalse(emaitza);

        //Datu basean zenbait elementu, zerbitzari berdina
        emaitza = ServerKud.getInstantzia().zerbitzariaDa("Apache","https://www.apple.com/es/");
        Assertions.assertTrue(emaitza);

        //Datu basean zenbait elementu, zerbitzari ezberdina
        emaitza = ServerKud.getInstantzia().zerbitzariaDa("nginx","https://www.apple.com/es/");
        Assertions.assertFalse(emaitza);

        //SQLi bat egiten saiatu lehenengo elementuan
        emaitza = ServerKud.getInstantzia().zerbitzariaDa("Apache ' --","https://www.apple.com/es/");
        Assertions.assertFalse(emaitza);

        //SQLi bat egiten saiatu bigarren elementuan
        emaitza = ServerKud.getInstantzia().zerbitzariaDa("Apache","https://www.apple.com/es/ ' --");
        Assertions.assertFalse(emaitza);
    }
}