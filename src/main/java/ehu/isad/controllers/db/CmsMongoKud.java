package ehu.isad.controllers.db;

public class CmsMongoKud {
    //singleton
    private static CmsMongoKud mongoKud=new CmsMongoKud();

    public CmsMongoKud(){

    }

    public static CmsMongoKud getInstance(){
        return mongoKud;
    }


}
