package ehu.isad.controllers.db;

import ehu.isad.utils.Bilaketa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


class WhatWebSQLKudTest {

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
    void insertIrakurri() throws IOException {
        List<String> emaitza;
        Bilaketa b = new Bilaketa();

        //Bilaketa bat txarto egin datu basea hutsik dagoenean
        // eta zihurtatu elementua gordetzen ez dela
        b.urlIrakurri("sdjghskdjfgkjshg");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = ServerKud.getInstantzia().lortuTargets();
        Assertions.assertTrue(emaitza.isEmpty());

        //Bilaketa bat ondo egin datu basea hutsik dagoenean
        // eta zihurtatu elementua gordetzen dela
        b.urlIrakurri("https://www.apple.com/es/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = ServerKud.getInstantzia().lortuTargets();
        Assertions.assertTrue(emaitza.size() == 1);
        Assertions.assertEquals("https://www.apple.com/es/",emaitza.get(0));

        //Bilaketa bat txarto egin datu basea hutsik ez dagoenean
        // eta zihurtatu elementua gordetzen ez dela
        b.urlIrakurri("sdjghskdjfgkjshg");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = ServerKud.getInstantzia().lortuTargets();
        Assertions.assertTrue(emaitza.size() == 1);
        Assertions.assertEquals("https://www.apple.com/es/",emaitza.get(0));

        //Bilaketa bat ondo egin datu basea hutsik ez dagoenean
        // eta zihurtatu elementua gordetzen dela
        b.urlIrakurri("https://ikasten.io/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = ServerKud.getInstantzia().lortuTargets();
        Assertions.assertTrue(emaitza.size() == 2);
        Assertions.assertEquals("https://www.apple.com/es/",emaitza.get(0));
        Assertions.assertEquals("https://ikasten.io/",emaitza.get(1));
    }

    @Test
    void jadaBilatuta() throws IOException {
        Boolean emaitza;
        Bilaketa b = new Bilaketa();

        //Datu Basea hutsik dago, ez da elementurik sartzen
        emaitza = WhatWebSQLKud.getInstantzia().jadaBilatuta("");
        Assertions.assertFalse(emaitza);

        //Datu Basea hutsik dago, elementua sartzen da
        emaitza = WhatWebSQLKud.getInstantzia().jadaBilatuta("https://ikasten.io/");
        Assertions.assertFalse(emaitza);

        //Elementu bakarra, ez da berdina
        b.urlIrakurri("https://www.apple.com/es/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = WhatWebSQLKud.getInstantzia().jadaBilatuta("https://ikasten.io/");
        Assertions.assertFalse(emaitza);

        //Elementu bakarra, berdina da
        emaitza = WhatWebSQLKud.getInstantzia().jadaBilatuta("https://www.apple.com/es/");
        Assertions.assertTrue(emaitza);

        //Elementu bakarra, bilaketa hutsik
        emaitza = WhatWebSQLKud.getInstantzia().jadaBilatuta("");
        Assertions.assertFalse(emaitza);

        //Zenbait elementu, ez dago
        b.urlIrakurri("https://ikasten.io/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = WhatWebSQLKud.getInstantzia().jadaBilatuta("https://www.instagram.com/");
        Assertions.assertFalse(emaitza);


        //Zenbait elementu, badago
        b.urlIrakurri("https://www.instagram.com/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = WhatWebSQLKud.getInstantzia().jadaBilatuta("https://ikasten.io/");
        Assertions.assertTrue(emaitza);

        //Zenbait elementu, bilaketa hutsik
        emaitza = WhatWebSQLKud.getInstantzia().jadaBilatuta("");
        Assertions.assertFalse(emaitza);

        //SQLi saiatu
        emaitza = WhatWebSQLKud.getInstantzia().jadaBilatuta("https://ikasten.io/ ' --");
        Assertions.assertFalse(emaitza);
    }
}