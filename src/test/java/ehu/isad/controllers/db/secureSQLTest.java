package ehu.isad.controllers.db;

import ehu.isad.utils.Bilaketa;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class secureSQLTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void eskaeraBabestua() throws SQLException, IOException {
        Boolean emaitza;
        Bilaketa b = new Bilaketa();

        //Datu Basea hutsik dago
        //SQLi lehenengo parametroan
        String query = "SELECT * FROM scans S, targets T WHERE T.target_id=S.target_id AND T.status=200 AND string= ? AND target= ? AND module=?";
        List<String> parametroak = new ArrayList<String>();
        parametroak.add("UNITED STATES ' --");
        parametroak.add("https://www.apple.com/es/");
        parametroak.add("US");
        List<String> motak = new ArrayList<String>();
        motak.add("String");
        motak.add("String");
        motak.add("String");
        List<Integer> likePos = new ArrayList<>();
        ResultSet rs = SecureSQL.getInstantzia().eskaeraBabestua(query,parametroak,motak,likePos);
        emaitza = rs.next();
        Assertions.assertFalse(emaitza);

        //SQLi bigarren parametroan
        query = "SELECT * FROM scans S, targets T WHERE T.target_id=S.target_id AND T.status=200 AND string= ? AND target= ? AND module=?";
        parametroak = new ArrayList<String>();
        parametroak.add("UNITED STATES");
        parametroak.add("https://www.apple.com/es/ ' --");
        parametroak.add("US");
        motak = new ArrayList<String>();
        motak.add("String");
        motak.add("String");
        motak.add("String");
        likePos = new ArrayList<>();
        rs = SecureSQL.getInstantzia().eskaeraBabestua(query,parametroak,motak,likePos);
        emaitza = rs.next();
        Assertions.assertFalse(emaitza);

        //SQLi hirugarren parametroan
        query = "SELECT * FROM scans S, targets T WHERE T.target_id=S.target_id AND T.status=200 AND string= ? AND target= ? AND module=?";
        parametroak = new ArrayList<String>();
        parametroak.add("UNITED STATES");
        parametroak.add("https://www.apple.com/es/");
        parametroak.add("US ' --");
        motak = new ArrayList<String>();
        motak.add("String");
        motak.add("String");
        motak.add("String");
        likePos = new ArrayList<>();
        rs = SecureSQL.getInstantzia().eskaeraBabestua(query,parametroak,motak,likePos);
        emaitza = rs.next();
        Assertions.assertFalse(emaitza);


        //Datu Basean elementuak daude, parametroak ondo daude
        b.urlIrakurri("https://www.apple.com/es/");
        WhatWebSQLKud.getInstantzia().insertIrakurri();

        //SQLi lehenengo parametroan
        query = "SELECT * FROM scans S, targets T WHERE T.target_id=S.target_id AND T.status=200 AND string= ? AND target= ? AND module=?";
        parametroak = new ArrayList<String>();
        parametroak.add("UNITED STATES ' --");
        parametroak.add("https://www.apple.com/es/");
        parametroak.add("US");
        motak = new ArrayList<String>();
        motak.add("String");
        motak.add("String");
        motak.add("String");
        likePos = new ArrayList<>();
        rs = SecureSQL.getInstantzia().eskaeraBabestua(query,parametroak,motak,likePos);
        emaitza = rs.next();
        Assertions.assertFalse(emaitza);

        //SQLi bigarren parametroan
        query = "SELECT * FROM scans S, targets T WHERE T.target_id=S.target_id AND T.status=200 AND string= ? AND target= ? AND module=?";
        parametroak = new ArrayList<String>();
        parametroak.add("UNITED STATES");
        parametroak.add("https://www.apple.com/es/ ' --");
        parametroak.add("US");
        motak = new ArrayList<String>();
        motak.add("String");
        motak.add("String");
        motak.add("String");
        likePos = new ArrayList<>();
        rs = SecureSQL.getInstantzia().eskaeraBabestua(query,parametroak,motak,likePos);
        emaitza = rs.next();
        Assertions.assertFalse(emaitza);

        //SQLi hirugarren parametroan
        query = "SELECT * FROM scans S, targets T WHERE T.target_id=S.target_id AND T.status=200 AND string= ? AND target= ? AND module=?";
        parametroak = new ArrayList<String>();
        parametroak.add("UNITED STATES");
        parametroak.add("https://www.apple.com/es/");
        parametroak.add("US ' --");
        motak = new ArrayList<String>();
        motak.add("String");
        motak.add("String");
        motak.add("String");
        likePos = new ArrayList<>();
        rs = SecureSQL.getInstantzia().eskaeraBabestua(query,parametroak,motak,likePos);
        emaitza = rs.next();
        Assertions.assertFalse(emaitza);
    }
}