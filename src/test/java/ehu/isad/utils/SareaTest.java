package ehu.isad.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class SareaTest {

    private Sarea s = new Sarea();

    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void irudiaLortuTest() {
        //Existitzen den URL sinple bat eman eta irudia gorde dela egiaztatu
        File f = new File(Utils.lortuEzarpenak().getProperty("pathToImages")+"egela.ehu.eus.png");
        if(f.exists()){
            f.delete();
        }
        s.irudiaLortu("https://egela.ehu.eus/");
        Assertions.assertTrue(f.exists());
        f.delete();


        //Existitzen den URL konplexu bat eman eta irudia gorde dela egiaztatu
        f = new File(Utils.lortuEzarpenak().getProperty("pathToImages")+"aws.amazon.com.png");
        if(f.exists()){
            f.delete();
        }
        s.irudiaLortu("https://aws.amazon.com/education/awseducate/");
        Assertions.assertTrue(f.exists());
        f.delete();


        //URL-a beti CMS taulatik datorrenez, ez da beharrezkoa probatzea:
        //1. URL-a existitzen ez denean, taulan badago existitzen delako
        //2. String huts bat izatea, taulako datua hartzen delako
    }
}
