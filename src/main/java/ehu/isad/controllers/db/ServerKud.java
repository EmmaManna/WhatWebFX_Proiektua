package ehu.isad.controllers.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerKud {

    // singleton patroia
    private static ServerKud instantzia = new ServerKud();
    private List<String> serverIzenak;

    private ServerKud(){
        serverIzenakKargatu();
    }

    public static ServerKud getInstantzia() {
        return instantzia;
    }

    private void serverIzenakKargatu(){
        //S.plugin_id IN (90,981,803,868,971,260)
        serverIzenak = new ArrayList<>();
        serverIzenak.add("Apache");
        serverIzenak.add("nginx");
        serverIzenak.add("LiteSpeed");
        serverIzenak.add("Microsoft-ISS");
        serverIzenak.add("lighttpd");
        serverIzenak.add("Cherokee");
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

    public List<String> lortuZerbitzaria(){
        String query = "SELECT DISTINCT name FROM scans S, plugins P WHERE P.plugin_id=S.plugin_id AND "+serverMotakJarri();
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        List<String> emaitza = new ArrayList<>();
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                emaitza.add(name);
            }
            emaitza.add("Iragazki Gabe");
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return emaitza;
    }

    private String serverMotakJarri(){
        String query = "";
        if(serverIzenak.size()>0){
            query = "(";
            for(int i=0; i<serverIzenak.size(); i++){
                if(i+1==serverIzenak.size()){
                    query = query+"P.name='"+serverIzenak.get(i)+"')";
                }
                else
                    query = query+"P.name='"+serverIzenak.get(i)+"' OR ";
            }
        }
        return query;
    }

    public Boolean zerbitzariaDa(String izena, String url){
        String query = "SELECT * FROM scans S, plugins P, targets T WHERE P.plugin_id=S.plugin_id AND T.target_id=S.target_id AND name= ? AND target= ?";
        List<String> parametroak = new ArrayList<String>();
        parametroak.add(izena);
        parametroak.add(url);
        List<String> motak = new ArrayList<String>();
        motak.add("String");
        motak.add("String");
        List<Integer> likePos = new ArrayList<>();
        ResultSet rs = secureSQL.getInstantzia().eskaeraBabestua(query,parametroak,motak,likePos);

        try {
            return rs.next();
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return false;
    }


}
