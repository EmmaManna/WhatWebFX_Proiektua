package ehu.isad.controllers.db;

import ehu.isad.model.Cms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerKud {

    // singleton patroia
    private static ServerKud instantzia = new ServerKud();

    private ServerKud(){
    }

    public static ServerKud getInstantzia() {
        return instantzia;
    }


    public List<String> lortuTargets(){
        String query="SELECT * FROM targets";
        DBKudeatzaile dbKudeatzaile=DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        List<String> lista = new ArrayList<>();

        try{
            while (rs.next()){
                lista.add(rs.getString("target"));
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return lista;
    }

}
