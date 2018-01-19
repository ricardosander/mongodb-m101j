package com.mongodb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {

        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(App.class, "/");

        Spark.get("/", (request, response) -> {

            StringWriter writer = new StringWriter();

            try {

                Template template = configuration.getTemplate("hello.ftl");

                HashMap<String, Object> helloMap = new HashMap<>();
                helloMap.put("name", "Frermarker");

                template.process(helloMap, writer);
            } catch (IOException | TemplateException e) {
                Spark.halt(500);
                e.printStackTrace();
            }

            return writer;
        });

    }

}