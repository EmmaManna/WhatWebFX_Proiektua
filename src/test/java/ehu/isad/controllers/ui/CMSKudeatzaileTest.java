package ehu.isad.controllers.ui;

import ehu.isad.WhatWebFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotException;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;


import java.util.concurrent.TimeoutException;


class CMSKudeatzaileTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(WhatWebFX.class.getResource("/FXML/Main.fxml"));
        Parent mainUI = (Parent) loader.load();
        stage.setScene(new Scene(mainUI));
        stage.show();
        stage.toFront();

    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    void onClickAddURL() throws FxRobotException{
        clickOn("#txt_bilatu");
        write("https://medium.com/");
        clickOn("#btn_addUrl");
    }

    @Test
    void onKlikEgin() {
    }

    @Test
    void onTestuaAldatuDa() {
    }
}