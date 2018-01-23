package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;

public class App2 {

    public static void main(String[] args){


        MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

        MongoDatabase database = client.getDatabase("students");
        MongoCollection<Document> grades = database.getCollection("grades");

        ArrayList<Document> studentsGrades = new ArrayList<>();

        grades
                .find(new Document("type", "homework"))
                .sort(Sorts.orderBy(Sorts.ascending("student_id"), Sorts.ascending("score")))
                .into(studentsGrades);


        Document before = null;
        for (Document student : studentsGrades) {

            if (before == null || !before.getInteger("student_id").equals(student.getInteger("student_id"))) {
                grades.deleteOne(new Document("_id", student.get("_id")));
            }

            before = student;
        }
    }
}
