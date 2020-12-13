package ehu.isad.utils;

import ehu.isad.model.MongoErabiltzailea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;

public class BilaketaTest{

    private Bilaketa b = new Bilaketa();

    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void urlIrakurriTest() {
        List<String> processes = new LinkedList<String>();
        MongoErabiltzailea me = MongoErabiltzailea.getInstance();

        //SQL erabiliz: Existitzen den URL bat emanda datuak itzultzea
        processes = b.urlIrakurri("https://egela.ehu.eus/");
        Assertions.assertEquals("[https://egela.ehu.eus/ [303 See Other] Charset[UTF-8], Content-Language[eu], Cookies[MoodleSessionegela], Country[SPAIN][ES], HTML5, HTTPServer[nginx], IP[158.227.0.93], PHP[7.1.30], RedirectLocation[https://egela.ehu.eus/login/index.php], Title[Berbideratu], X-Powered-By[PHP/7.1.30], nginx, https://egela.ehu.eus/login/index.php [200 OK] Charset[UTF-8], Content-Language[eu], Cookies[MoodleSessionegela], Country[SPAIN][ES], HTML5, HTTPServer[nginx], IP[158.227.0.93], Open-Graph-Protocol[website], PHP[7.1.30], PasswordField[password], Script[text/css,text/javascript], Title[eGela UPV/EHU: Sartu gunean], UncommonHeaders[content-script-type,content-style-type], X-Frame-Options[sameorigin], X-Powered-By[PHP/7.1.30], X-UA-Compatible[IE=edge], nginx]",processes.toString());
        Utils.ezabatu();

        //SQL erabiliz: Existitzen ez den URL bat emanda daturik ez itzultzea
        processes = b.urlIrakurri("blablablajUntikProbakEgiten");
        Assertions.assertEquals("[]", processes.toString());
        Utils.ezabatu();

        //SQL erabiliz: String huts bat emanda daturik ez itzultzea
        processes = b.urlIrakurri("");
        Assertions.assertEquals("[]", processes.toString());
        Utils.ezabatu();

        //Mongo erabiliz: Existitzen den URL bat emanda datuak itzultzea
        me.setCollection("whatweb");
        processes = b.urlIrakurri("https://egela.ehu.eus/");
        Assertions.assertEquals("[https://egela.ehu.eus/ [303 See Other] Charset[UTF-8], Content-Language[eu], Cookies[MoodleSessionegela], Country[SPAIN][ES], HTML5, HTTPServer[nginx], IP[158.227.0.93], PHP[7.1.30], RedirectLocation[https://egela.ehu.eus/login/index.php], Title[Berbideratu], X-Powered-By[PHP/7.1.30], nginx, https://egela.ehu.eus/login/index.php [200 OK] Charset[UTF-8], Content-Language[eu], Cookies[MoodleSessionegela], Country[SPAIN][ES], HTML5, HTTPServer[nginx], IP[158.227.0.93], Open-Graph-Protocol[website], PHP[7.1.30], PasswordField[password], Script[text/css,text/javascript], Title[eGela UPV/EHU: Sartu gunean], UncommonHeaders[content-script-type,content-style-type], X-Frame-Options[sameorigin], X-Powered-By[PHP/7.1.30], X-UA-Compatible[IE=edge], nginx]",processes.toString());
        Utils.ezabatu();

        //Mongo erabiliz: Existitzen ez den URL bat emanda daturik ez itzultzea
        processes = b.urlIrakurri("blablablajUntikProbakEgiten");
        Assertions.assertEquals("[]", processes.toString());
        Utils.ezabatu();

        //Mongo erabiliz: String huts bat emanda daturik ez itzultzea
        processes = b.urlIrakurri("");
        Assertions.assertEquals("[]", processes.toString());
        Utils.ezabatu();
    }
}
