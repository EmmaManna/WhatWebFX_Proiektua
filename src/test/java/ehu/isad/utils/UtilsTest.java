package ehu.isad.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class UtilsTest {

    private Utils u = new Utils();


    @Test
    void ezabatuTest() {
        //insertaksql fitxategia sisteman dago
        Bilaketa b = new Bilaketa();
        b.urlIrakurri("https://egela.ehu.eus/");
        try {
            File path = new File(new File("./").getCanonicalPath());
            File f = new File(path.toString()+"/insertak.sql");
            Assertions.assertTrue(f.exists());
            Utils.ezabatu();
            Assertions.assertTrue(!f.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }


        //insertaksql fitxategia ez dago sisteman
        try {
            File path = new File(new File("./").getCanonicalPath());
            File f = new File(path.toString()+"/insertak.sql");
            Assertions.assertTrue(!f.exists());
            Utils.ezabatu();
            Assertions.assertTrue(!f.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
