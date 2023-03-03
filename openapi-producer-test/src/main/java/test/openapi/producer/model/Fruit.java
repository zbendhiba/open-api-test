package test.openapi.producer.model;

// inspired from https://github.com/quarkusio/quarkus-quickstarts/blob/main/openapi-swaggerui-quickstart/src/main/java/org/acme/openapi/swaggerui/Fruit.java
public class Fruit {

    public String name;
    public String description;

    public Fruit() {
    }

    public Fruit(String name, String description) {
        this.name = name;
        this.description = description;
    }
}