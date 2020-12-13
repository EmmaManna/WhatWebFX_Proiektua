package ehu.isad.controllers.db;

import com.google.gson.Gson;
import ehu.isad.model.CmsMongo;
import ehu.isad.model.MongoErabiltzailea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class CmsMongoKudTest {

    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown(){
    }


    @Test
    void lortuCmsMongo(){
        //suposatuz datu basearen bilduma ona whatweb deitzen dela
        MongoErabiltzailea.getInstance().setCollection("whatweb");
        List<CmsMongo> lista=CmsMongoKud.getInstance().lortuCmsMongo();
        lista.forEach((p)->{
            Assertions.assertNotNull(p);
        });

        MongoErabiltzailea.getInstance().setCollection("EzDaBilduma");
        lista=CmsMongoKud.getInstance().lortuCmsMongo();
        lista.forEach((p)->{
            Assertions.assertNull(p);
        });
    }


    @Test
    void atributuGehiago(){
        //metodo honek era normal batean baino atributu gehiago badaude begiratuko du
        //eta programak zer egingo lukeen egoera honen aurrean aurreikusi nahi da
        String ondo="{\"target\":\"https://es.wikipedia.org/wiki/Wikipedia:Portada\",\"plugins\":{\"MetaGenerator\":{\"string\":[\"MediaWiki 1.36.0-wmf.18\"]},\"Country\":{\"string\":[\"NETHERLANDS\"],\"module\":[\"NL\"]}}}";
        String txarto="{\"target\":\"https://es.wikipedia.org/wiki/Wikipedia:Portada\",\"plugins\":{\"MetaGenerator\":{\"string\":[\"MediaWiki 1.36.0-wmf.18\"]},\"Country\":{\"string\":[\"NETHERLANDS\"],\"module\":[\"NL\"]},\"EzNintzatekeHemenEgonBehar\":\"NullPointerIncoming\"}}";
        CmsMongo cmsOndo=new Gson().fromJson(ondo, CmsMongo.class);
        CmsMongo cmsTxarto=new Gson().fromJson(txarto, CmsMongo.class);
        Assertions.assertEquals(cmsTxarto.toString(),cmsOndo.toString());
    }


    @Test
    void atributuGutxiago(){
        //metodo honek era normal batean baino atributu gutxiago badaude begiratuko du
        //et programak zer egingo lukeen egoera honen aurrean aurrekikusi nahi da
        String osoTxarto="{}";
        CmsMongo cmsOsoTxarto=new Gson().fromJson(osoTxarto, CmsMongo.class);
        Assertions.assertEquals(cmsOsoTxarto.toString(),"{}");
    }
}
