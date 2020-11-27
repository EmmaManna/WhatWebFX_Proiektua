package ehu.isad.controllers.db;

import com.mongodb.internal.connection.tlschannel.util.Util;
import ehu.isad.model.MongoErabiltzailea;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import ehu.isad.utils.Utils;
import org.bson.Document;


public class WhatWebMongoKud {

    private static WhatWebMongoKud instantzia=new WhatWebMongoKud();

    private WhatWebMongoKud(){
    }

    public static WhatWebMongoKud getInstance(){
        return instantzia;
    }


    public Boolean jadaBilatutaMongo(String url){
        List<String> lista=new ArrayList<>();
        //String query="db."+ MongoErabiltzailea.getInstance().getCollection()+".find({target:"+url+"})";

        try (MongoClient client = new MongoClient("localhost", 27017)) {
            MongoDatabase database=client.getDatabase(Utils.lortuEzarpenak().getProperty("dbMongo"));
            MongoCollection<Document> collection=database.getCollection(MongoErabiltzailea.getInstance().getCollection());

            Document query = new Document();
            query.append("target",url);

            Document projection=new Document();
            projection.append("target",1.0);
            projection.append("_id",0.0);

            Consumer<Document> konprobatu = new Consumer<Document>() {

                @Override
                public void accept(Document document) {
                    lista.add(document.toJson());
                }
            };

            collection.find(query).projection(projection).forEach(konprobatu);

        }
        catch (MongoException e){
            e.printStackTrace();
        }

        return !lista.isEmpty();
    }
}
