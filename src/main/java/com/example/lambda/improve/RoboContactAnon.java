package com.example.lambda.improve;

import com.example.lambda.bean.Gender;
import com.example.lambda.bean.Person;

import java.util.List;

/**
 * Created by liutingna on 2017/9/9.
 * 通过抽象接口的一种实现方式
 */

/**
 * 抽象接口
 *
 * @param <T>
 */
interface MyTest<T> {
    boolean test(T t);
}

public class RoboContactAnon {

    public void phoneContacts(List<Person> pl, MyTest<Person> aTest) {
        for (Person p : pl) {
            if (aTest.test(p)) {
                roboCall(p);
            }
        }
    }

    public void emailContacts(List<Person> pl, MyTest<Person> aTest) {
        for (Person p : pl) {
            if (aTest.test(p)) {
                roboEmail(p);
            }
        }
    }

    public void mailContacts(List<Person> pl, MyTest<Person> aTest) {
        for (Person p : pl) {
            if (aTest.test(p)) {
                roboMail(p);
            }
        }
    }

    public void roboCall(Person p) {
        System.out.println("Calling " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getPhone());
    }

    public void roboEmail(Person p) {
        System.out.println("EMailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getEmail());
    }

    public void roboMail(Person p) {
        System.out.println("Mailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getAddress());
    }

    /**
     * 测试
     */
    public static void main(String[] args) {

        List<Person> pl = Person.createShortList();
        RoboContactAnon robo = new RoboContactAnon();

        System.out.println("\n==== Test 03 ====");
        System.out.println("\n=== Calling all Drivers ===");
        robo.phoneContacts(pl,
                new MyTest<Person>() {
                    @Override
                    public boolean test(Person p) {
                        return p.getAge() >= 16;
                    }
                }
        );

        System.out.println("\n=== Emailing all Draftees ===");
        robo.emailContacts(pl,
                new MyTest<Person>() {
                    @Override
                    public boolean test(Person p) {
                        return p.getAge() >= 18 && p.getAge() <= 25 && p.getGender() == Gender.MALE;
                    }
                }
        );

        System.out.println("\n=== Mail all Pilots ===");
        robo.mailContacts(pl,
                new MyTest<Person>() {
                    @Override
                    public boolean test(Person p) {
                        return p.getAge() >= 23 && p.getAge() <= 65;
                    }
                }
        );
    }

}
