package ehu.isad.controllers.db;

import java.sql.ResultSet;

public class ServerKud {

    // singleton patroia
    private static ServerKud instantzia = new ServerKud();

    private ServerKud(){
    }

    public static ServerKud getInstantzia() {
        return instantzia;
    }


    public ResultSet lortuTargets(){
        String query="SELECT * FROM targets";

        DBKudeatzaile dbKudeatzaile=DBKudeatzaile.getInstantzia();
        return dbKudeatzaile.execSQL(query);
    }

}
