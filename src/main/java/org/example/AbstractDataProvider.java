package org.example;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dataProviders.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.List;

public abstract class AbstractDataProvider {
    private static final Logger logger = LogManager.getLogger(AbstractDataProvider.class);
    public abstract Car getById(long id);
    public abstract List<Car> selectCars();
    public abstract void insert(Car car);
    public abstract void delete(long id);
    public abstract void update(Car car);

    public static boolean saveHistoryContent(String className, Status status, Object json){
        boolean isSaved;
        try {
            MongoClient mongoClient = MongoClients.create(ConstantsProperties.MONGODB_HOST);
            MongoDatabase database = mongoClient.getDatabase(ConstantsProperties.MONGODB_DATABASE_NAME);
            try {
                database.createCollection(ConstantsProperties.MONGODB_COLLECTION_NAME);
            } catch (Exception e) {
                logger.info(ConstantsProperties.MONGODB_COLLECTION_ALREADY_EXIST);
            }
            HistoryContent historyContent = new HistoryContent(className, status, new Gson().toJson(json));
            MongoCollection<Document> collection = database.getCollection(ConstantsProperties.MONGODB_COLLECTION_NAME);
            collection.insertOne(Document.parse(new Gson().toJson(historyContent)));
            isSaved = true;
        } catch (Exception e) {
            logger.error(e);
            isSaved = false;
        }
        return isSaved;
    }
}
