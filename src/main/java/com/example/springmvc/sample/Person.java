package com.example.springmvc.sample;

/**
 * Spring Web Mvc
 * WebMvcConfigurer 1부 Formatter
 */
public class Person {

    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
