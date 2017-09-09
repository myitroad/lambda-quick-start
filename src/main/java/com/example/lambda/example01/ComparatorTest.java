package com.example.lambda.example01;

import com.example.lambda.bean.Person;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by liutingna on 2017/9/9.
 */
public class ComparatorTest {

    public static void main(String[] args) {

        List<Person> personList = Person.createShortList();
        System.out.println("===Before sorted===");
        for(Person p:personList){
            p.printName();
        }

        // Sort with Inner Class
        Collections.sort(personList, new Comparator<Person>(){
            @Override
            public int compare(Person p1, Person p2){
                return p1.getSurName().compareTo(p2.getSurName());
            }
        });

        System.out.println("=== Sorted Asc SurName ===");
        for(Person p:personList){
            p.printName();
        }

        // Use Lambda instead

        // Print Asc
        System.out.println("=== Sorted Asc SurName ===");
        Collections.sort(personList, (Person p1, Person p2) -> p1.getSurName().compareTo(p2.getSurName()));

        for(Person p:personList){
            p.printName();
        }

        // Print Desc
        System.out.println("=== Sorted Desc SurName ===");
        Collections.sort(personList, (p1,  p2) -> p2.getSurName().compareTo(p1.getSurName()));

        for(Person p:personList){
            p.printName();
        }

    }
}