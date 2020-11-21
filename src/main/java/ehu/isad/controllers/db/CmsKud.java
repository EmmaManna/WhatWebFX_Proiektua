package ehu.isad.controllers.db;

import ehu.isad.model.Cms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CmsKud {
    // singleton patroia
    private static CmsKud instantzia = new CmsKud();
    private List<String> cmsIzenak;

    private CmsKud(){
        cmsIzenakKargatu();
    }

    public static CmsKud getInstantzia() {
        return instantzia;
    }


    private void cmsIzenakKargatu(){
        //S.plugin_id IN (1716,732,414,827,185,1342,1433,1706,1215,1568)
        cmsIzenak = new ArrayList<>();
        cmsIzenak.add("WordPress");
        cmsIzenak.add("Joomla");
        cmsIzenak.add("Drupal");
        cmsIzenak.add("Magento");
        cmsIzenak.add("Blogger");
        cmsIzenak.add("Shopify");
        cmsIzenak.add("SquareSpace");
        cmsIzenak.add("Wix");
        cmsIzenak.add("PrestaShop");
        cmsIzenak.add("TYPO3");
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

            String query = "SELECT name, version FROM plugins P, scans S, targets T WHERE P.plugin_id=S.plugin_id AND "+ this.cmsMotakJarri()+" AND T.target_id=S.target_id AND T.target LIKE ?";
            List<String> parametroak = new ArrayList<String>();
            parametroak.add(cmsak.get(i).getUrl().getText());
            List<String> motak = new ArrayList<String>();
            motak.add("String");
            List<Integer> likePos = new ArrayList<>();
            likePos.add(1);
            ResultSet rs = secureSQL.getInstantzia().eskaeraBabestua(query,parametroak,motak,likePos);

            /*
            String query = "SELECT name, version FROM plugins P, scans S, targets T WHERE P.plugin_id=S.plugin_id AND "+ this.cmsMotakJarri()+" AND T.target_id=S.target_id AND T.target LIKE '" + cmsak.get(i).getUrl()+"'" ;
            DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
            ResultSet rs = dbKudeatzaile.execSQL(query);
             */

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

    private String cmsMotakJarri(){
        String query = "";
        if(cmsIzenak.size()>0){
            query = "(";
            for(int i=0; i<cmsIzenak.size(); i++){
                if(i+1==cmsIzenak.size()){
                    query = query+"P.name='"+cmsIzenak.get(0)+"')";
                }
                else
                    query = query+"P.name='"+cmsIzenak.get(0)+"' OR ";
            }
        }
        return query;
    }

    public void lortuHerrialdeak(){

    }


}
