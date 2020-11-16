package ehu.isad.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
    public static Properties lortuEzarpenak()  {
        Properties properties = null;

        try (InputStream in = Utils.class.getResourceAsStream("/setup.properties")) {
            properties = new Properties();
            properties.load(in);

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    public static final boolean ezabatu() {
        if("insertak.sql"==null){
            return true;
        }
        File file = new File("insertak.sql");
        if(file.exists()){
            return file.delete();
        }
        return true;
    }
}

