package ehu.isad.controllers.db;

import ehu.isad.model.Cms;
import ehu.isad.model.Herrialdea;
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
        //Eskaneatu den orrialde bakoitzeko URL-a, erabiltzen duen CMS-a eta
        // bertsioa (ezagunak badira), eta kargatu ziren data lortu
        String query = "SELECT target, lastUpdated FROM targets WHERE status=200 ORDER BY lastUpdated DESC";
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
        //Parametro bezala emandako listako URL bakoitzeko, erabiltzen duen CMS-a eta bertsioak lortu, ezagunak badira
        for(int i=0; i<cmsak.size(); i++){
            String query = "SELECT name, version FROM plugins P, scans S, targets T WHERE P.plugin_id=S.plugin_id AND "+ this.cmsMotakJarri()+" AND T.target_id=S.target_id AND T.target LIKE ?";
            List<String> parametroak = new ArrayList<String>();
            parametroak.add(cmsak.get(i).getUrl().getText());
            List<String> motak = new ArrayList<String>();
            motak.add("String");
            List<Integer> likePos = new ArrayList<>();
            likePos.add(1);
            ResultSet rs = SecureSQL.getInstantzia().eskaeraBabestua(query,parametroak,motak,likePos);

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
                    query = query+"P.name='"+cmsIzenak.get(i)+"')";
                }
                else
                    query = query+"P.name='"+cmsIzenak.get(i)+"' OR ";
            }
        }
        return query;
    }


    public List<Herrialdea> lortuHerrialdeak(){
        //Eskaneatutako herrialde guztien zerbitzariaren kokapenak lortu, ezagunak badira
        String query = "SELECT DISTINCT string, module FROM scans S, targets T WHERE NOT module='' AND NOT string='' AND T.target_id=S.target_id AND status=200";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        List<Herrialdea> emaitza = new ArrayList<>();
        try {
            while (rs.next()) {
                String string = rs.getString("string");
                String module = rs.getString("module");
                if(module.length()==2){
                    emaitza.add(new Herrialdea(module,string));
                }

            }
            emaitza.add(new Herrialdea("","IRAGAZKI GABE"));
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return emaitza;
    }


    public Boolean herrialdekoaDa(String string, String url, String module){
        //Emandako URL-a zehaztutako herrialdeakoa den konprobatzen da
        String query = "SELECT * FROM scans S, targets T WHERE T.target_id=S.target_id AND T.status=200 AND string= ? AND target= ? AND module=?";
        List<String> parametroak = new ArrayList<String>();
        parametroak.add(string);
        parametroak.add(url);
        parametroak.add(module);
        List<String> motak = new ArrayList<String>();
        motak.add("String");
        motak.add("String");
        motak.add("String");
        List<Integer> likePos = new ArrayList<>();
        ResultSet rs = SecureSQL.getInstantzia().eskaeraBabestua(query,parametroak,motak,likePos);

        try {
          return rs.next();
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return false;
    }

}
