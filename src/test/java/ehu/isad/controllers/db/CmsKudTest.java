package ehu.isad.controllers.db;

import ehu.isad.WhatWebFX;
import ehu.isad.model.Cms;
import ehu.isad.model.Herrialdea;
import ehu.isad.utils.Bilaketa;
import javafx.application.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;


class CmsKudTest {

    @BeforeEach
    void setUp(){
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
    void lortuCmsak() throws IOException, InterruptedException {
        System.out.printf("About to launch FX App\n");
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(WhatWebFX.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        System.out.printf("FX App thread started\n");
        Thread.sleep(500);

        List<Cms> emaitza;
        Bilaketa b = new Bilaketa();

        //Datu basea hutsik dagoenean
        emaitza = CmsKud.getInstantzia().lortuCmsak();
        Assertions.assertTrue(emaitza.isEmpty());

        //Elementu bakarra, CMS-a ez da ezaguna
        b.urlIrakurri("https://www.apple.com/es/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = CmsKud.getInstantzia().lortuCmsak();
        Assertions.assertTrue(emaitza.size() == 1);
        Assertions.assertEquals("Ezezaguna",emaitza.get(0).getCms());

        //Zenbait elementu, CMS bat ezaguna da
        b.urlIrakurri("https://ikasten.io/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = CmsKud.getInstantzia().lortuCmsak();
        Assertions.assertTrue(emaitza.size() == 2);
        Assertions.assertEquals("Ezezaguna", emaitza.get(0).getCms());
        Assertions.assertEquals("WordPress", emaitza.get(1).getCms());
    }


    @Test
    void lortuHerrialdeak() throws IOException {
        List<Herrialdea> emaitza;
        Bilaketa b = new Bilaketa();

        //Datu basea hutsik dagoenean
        emaitza = CmsKud.getInstantzia().lortuHerrialdeak();;
        Assertions.assertTrue(emaitza.size()==1);
        Assertions.assertEquals("IRAGAZKI GABE",emaitza.get(0).getString());

        //Elementu bakarra, Estatu batuetakoa
        b.urlIrakurri("https://www.apple.com/es/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = CmsKud.getInstantzia().lortuHerrialdeak();
        Assertions.assertTrue(emaitza.size() == 2);
        Assertions.assertEquals("UNITED STATES",emaitza.get(0).getString());
        Assertions.assertEquals("IRAGAZKI GABE",emaitza.get(1).getString());

        //Zenbait elementu, Estatu batuetakoa eta bestea Irlandakoa
        b.urlIrakurri("https://www.instagram.com/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = CmsKud.getInstantzia().lortuHerrialdeak();
        Assertions.assertTrue(emaitza.size() == 3);
        Assertions.assertEquals("UNITED STATES",emaitza.get(0).getString());
        Assertions.assertEquals("IRELAND",emaitza.get(1).getString());
        Assertions.assertEquals("IRAGAZKI GABE",emaitza.get(2).getString());
    }


    @Test
    void herrialdekoaDa() throws IOException {
        Boolean emaitza;
        Bilaketa b = new Bilaketa();

        //Datu basea hutsik dagoenean, eta ez da elementurik pasatzen
        emaitza = CmsKud.getInstantzia().herrialdekoaDa("","","");
        Assertions.assertFalse(emaitza);

        //Datu basea hutsik dagoenean, elementua pasatzen da
        emaitza = CmsKud.getInstantzia().herrialdekoaDa("UNITED STATES","https://www.apple.com/es/","US");
        Assertions.assertFalse(emaitza);

        //Datu basean elementu bakarra, eta ez da elementurik pasatzen
        b.urlIrakurri("https://www.apple.com/es/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = CmsKud.getInstantzia().herrialdekoaDa("","","");
        Assertions.assertFalse(emaitza);

        //Datu basean elementu bakarra, herrialdekoa da
        emaitza = CmsKud.getInstantzia().herrialdekoaDa("UNITED STATES","https://www.apple.com/es/","US");
        Assertions.assertTrue(emaitza);

        //Datu basean elementu bakarra, ez da herrialdekoa
        emaitza = CmsKud.getInstantzia().herrialdekoaDa("UKRAINE","https://www.apple.com/es/","UA");
        Assertions.assertFalse(emaitza);

        //Datu basean zenbait elementu, eta ez da elementurik pasatzen
        b.urlIrakurri("https://ikasten.io/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();
        emaitza = CmsKud.getInstantzia().herrialdekoaDa("","","");
        Assertions.assertFalse(emaitza);

        //Datu basean zenbait elementu, herrialdekoa da
        emaitza = CmsKud.getInstantzia().herrialdekoaDa("UNITED STATES","https://www.apple.com/es/","US");
        Assertions.assertTrue(emaitza);

        //Datu basean zenbait elementu, ez da herrialdekoa
        emaitza = CmsKud.getInstantzia().herrialdekoaDa("UKRAINE","https://www.apple.com/es/","UA");
        Assertions.assertFalse(emaitza);

        //SQLi bat egiten saiatu lehenengo elementuan
        emaitza = CmsKud.getInstantzia().herrialdekoaDa("UKRAINE ' --","https://www.apple.com/es/","UA");
        Assertions.assertFalse(emaitza);

        //SQLi bat egiten saiatu bigarren elementuan
        emaitza = CmsKud.getInstantzia().herrialdekoaDa("UKRAINE","https://www.apple.com/es/ ' --","UA");
        Assertions.assertFalse(emaitza);

        //SQLi bat egiten saiatu hirugarren elementuan
        emaitza = CmsKud.getInstantzia().herrialdekoaDa("UKRAINE","https://www.apple.com/es/","UA ' --");
        Assertions.assertFalse(emaitza);
    }
}