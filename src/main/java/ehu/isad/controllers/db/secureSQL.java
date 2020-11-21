package ehu.isad.controllers.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class secureSQL {
    // singleton patroia
    private static secureSQL instantzia = new secureSQL();

    private secureSQL(){
    }

    public static secureSQL getInstantzia() {
        return instantzia;
    }

    public ResultSet eskaeraBabestua(String query, List<String> parametroak, List<String> motak, List<Integer> likePos){
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        try {
            PreparedStatement ps = dbKudeatzaile.conn.prepareStatement(query);
            int kont = 0;
            int zenb = 1;
            while(kont < motak.size()){
                if(motak.get(kont).equals("int")){
                    ps.setInt(zenb,Integer.parseInt(parametroak.get(kont)));
                }
                else{
                    if(likePos.size()>0){
                        if(likePos.get(0) == zenb){
                            ps.setString(zenb, "%"+parametroak.get(kont)+"%");
                            likePos.remove(0);
                        }
                    }
                    else
                        ps.setString(zenb, parametroak.get(kont));
                }
                kont++;
                zenb++;
            }

            return ps.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}