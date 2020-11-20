package ehu.isad.controllers.db;

import ehu.isad.model.Cms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CmsKud {
    // singleton patroia
    private static CmsKud instantzia = new CmsKud();

    private CmsKud(){
    }

    public static CmsKud getInstantzia() {
        return instantzia;
    }

    public List<Cms> lortuCmsak(){

        String query = "SELECT target, lastUpdated FROM targets WHERE status=200";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        List<Cms> emaitza = new ArrayList<>();
        try {
            while (rs.next()) {
                String url = rs.getString("target");
                String cms = "Ezezaguna";
                String version =  "";
                String lastUpdate =  rs.getString("lastUpdated");
                emaitza.add(new Cms(url,cms,version,lastUpdate));
            }
            this.cmsKargatu(emaitza);

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return emaitza;
    }

    private void cmsKargatu(List<Cms> cmsak){
        for(int i=0; i<cmsak.size(); i++){
            String url = cmsak.get(i).getUrl().getText();
            String query = "SELECT name, version FROM plugins P, scans S, targets T WHERE P.plugin_id=S.plugin_id AND S.plugin_id IN (1716,732,414,827,185,1342,1433,1706,1215,1568) AND T.target_id=S.target_id AND T.target LIKE '" + url+"'" ;
            DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
            ResultSet rs = dbKudeatzaile.execSQL(query);
            try {
                if(rs.next()){
                    String cms = rs.getString("name");
                    String version =  rs.getString("version");
                    cmsak.get(i).setCms(cms);
                    cmsak.get(i).setVersion(version);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public String lortuZerbitzaria(String server){
        String query="SELECT target FROM targets WHERE target LIKE '%"+server+"%'";
        DBKudeatzaile dbKudeatzaile=DBKudeatzaile.getInstantzia();
        ResultSet resultSet=dbKudeatzaile.execSQL(query);

        try{
            return resultSet.getString("target");
        } catch (SQLException throwables) {
            return "";
        }

    }


}
