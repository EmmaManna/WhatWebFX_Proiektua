package ehu.isad.controllers.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ServerKud {

    // singleton patroia
    private static ServerKud instantzia = new ServerKud();

    private ServerKud(){
    }

    public static ServerKud getInstantzia() {
        return instantzia;
    }


    public ResultSet lortuTargets(){
        String query="SELECT target FROM targets";

        DBKudeatzaile dbKudeatzaile=DBKudeatzaile.getInstantzia();
        return dbKudeatzaile.execSQL(query);
    }
}
