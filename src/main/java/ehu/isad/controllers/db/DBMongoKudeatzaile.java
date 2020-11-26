package ehu.isad.controllers.db;

// Requires official Java MongoDB Driver 3.6+
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.function.Consumer;
import org.bson.Document;


public class DBMongoKudeatzaile {

    public void execMongo(){

        try (MongoClient client = new MongoClient("localhost", 27017)) {

            MongoDatabase database = client.getDatabase("whatweb");
            MongoCollection<Document> collection = database.getCollection("whatweb");

            // Created with Studio 3T, the IDE for MongoDB - https://studio3t.com/

            Document query = new Document();

            Consumer<Document> processBlock = new Consumer<Document>() {
                @Override
                public void accept(Document document) {
                    System.out.println(document);
                }
            };

            collection.find(query).forEach(processBlock);

        } catch (MongoException e) {
            // handle MongoDB exception
        }

    }

}
