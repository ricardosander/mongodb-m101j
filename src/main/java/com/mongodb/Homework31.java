package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Homework31 {

    public static void main(String[] args) {

        MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

        MongoDatabase database = client.getDatabase("school");
        MongoCollection<Document> collection = database.getCollection("students");

        ArrayList<Document> students = new ArrayList<>();

        collection
                .find(new Document("scores.type", "homework"))
                .into(students);


        for (Document student : students) {

            List<Document> scores = (List<Document>) student.get("scores");

            Optional<Document> first = scores
                    .stream()
                    .filter(d -> d.getString("type").equals("homework"))
                    .sorted((d1, d2) -> {
                        return (int) (d1.getDouble("score") - d2.getDouble("score"));
                    })
                    .findFirst();

            if (first.isPresent()) {
                scores.remove(first.get());
            }

            collection.updateOne(
                    new Document("_id", student.get("_id")),
                    new Document("$set", new Document("scores", scores))
            );

        }
    }

}
