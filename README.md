## Overview
### Purpose
介绍Java SE 8 中lambda表达式。
### Introduction
Lambda表达式是Java SE 8 中提出的新特性，它为含有一个方法的接口提供了一种简介的表示。Lambda对Collection库的遍历、过滤和提取等方面提供了增强。另外，在多核环境下，为新的并发特性提高了性能。

以下例子展示了如何使用Lambda表达式提高代码质量，另外展现了一些通用功能接口，如java.util.function提供的Predicate和Function。

### Hardware and Software Requirements
JDK8

## Background
### 匿名内部类
匿名类提供了一种实现接口的方式，该方式实现的接口可能在应用中只出现一次。比如：
```java
JButton testButton = new JButton("Test Button");
 //Old style
testButton.addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent ae) {
         System.out.println("Click Detected by Anon Class");
     }
 });
```
这种方式的代码很不优雅，因为仅仅为了实现一个方法，需要太多额外的代码。

### 功能接口（Functional Interfaces）
我们可以看看要实现的接口ActionLinstener:
```java
package java.awt.event;

import java.util.EventListener;

public interface ActionListener extends EventListener {

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e);

}
```
ActionListener是只有一个方法，Java8中称这种接口为“功能接口”。
> Note:
功能接口只有一个方法，先前也称为Single Abstract Method type（SAM）。 

功能接口很是常见，除了上述的EventListener，还如Runnable、Comparator等。这些都可以使用Lambda表达式哦。

### Lambda表达式语法

 Body 可以是一条表达式，也可以是一个代码块。
`break`和`continue`关键词在顶级（top  level）中是非法的，但是可以出现在循环（loops）中。如果Body产生一个结果，每一个控制分支必须都要返回或者抛出异常。
如：
```java
(int x, int y) -> x + y //return result
() -> 42    //return 42
(String s) -> { System.out.println(s);    //return nothing }
```

## Lambda Examples
### 1. Runnable Lambda
如下展示了如何使用Lambda表达式写一个实现Runnable接口。
```java
Runnable r2 = () -> System.out.println("Hello world two!");
```
就这样。对比使用匿名内部类的方式：
```java
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello world one!");
    }
};
```

### 2. Comparator Lambda
实现Comparator接口，也是一行代码。如根据SurName对一个Person列表进行排序，Person属性如下所示：
```java
public class Person {
    private String givenName;
    private String surName;
    private int age;
    private Gender gender;
    private String eMail;
    private String phone;
    private String address;
```
Lambda表达式实现的Comparator接口如下：
```java
List<Person> personList = Person.createShortList();//这里产生了一个Person列表
Collections.sort(personList, (Person p1, Person p2) -> p1.getSurName().compareTo(p2.getSurName()));
```
与使用匿名内部类的方式比较：
```java
Collections.sort(personList, new Comparator<Person>(){
    @Override
    public int compare(Person p1, Person p2){
        return p1.getSurName().compareTo(p2.getSurName());
    }
});
```
另外，Lambda表达式中的参数也是可选的，因为编译器可以推断出Collection其中的类型，如下代码p1/p2的参数类型也没有指明。注意这里排序使用逆序：
```java
Collections.sort(personList, (p1,  p2) -> p2.getSurName().compareTo(p1.getSurName()));
```

### 3. Listener Lambda
回到到头的例子，对于ActionListener接口，使用匿名内部类的方式如下：
```java
JButton testButton = new JButton("Test Button");
 //Old style
testButton.addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent ae) {
         System.out.println("Click Detected by Anon Class");
     }
 });
```
若使用Lambda表达式，很简洁：
```java
//Lambda style
 testButton.addActionListener(e -> System.out.println("Click Detected by Lambda Listner"));
```

## Improving Code with Lambda Expressions
使用Lambda表达式可以使得代码更加简洁和易读，更好地支持“Don't Repeat Yourself（DRY）”。
### 1. 一个查询用例
Drivers: age>16
Draftees(应征入伍): 25>=age>=18 && Male
Pilots:  65>=age>=23

### 2. Person Class
Person属性如下所示：
```java
public class Person {
    private String givenName;
    private String surName;
    private int age;
    private Gender gender;
    private String eMail;
    private String phone;
    private String address;
```
createShortList方法产生一个Person列表。

### 3. Lambda
给对满足Drivers的人打电话，执行roboCall方法；
给对满足Draftees的人发邮件，执行roboEmail方法；
给对满足Pilots的人写信，执行roboMail方法；

- 使用Predicate的test进行判断
```java
Predicate<Person> allDrivers = p -> p.getAge() >= 16;
Predicate<Person> allDraftees = p -> p.getAge() >= 18 && p.getAge() <= 25 && p.getGender() == Gender.MALE;
Predicate<Person> allPilots = p -> p.getAge() >= 23 && p.getAge() <= 65;
```

使用Predicate的test方法可断言是否满足条件，示例如下：
```java
public void phoneContacts(List<Person> pl, Predicate<Person> pred) {
    for (Person p : pl) {
        if (pred.test(p)) {
            roboCall(p);
        }
    }
}
```
>Note:
参考资料Reference中有各种实现方式的比较。

## The java.util.funciton Package
除了Predicate，Java SE 8还提供了一系列标准接口：
Predicate: A property of the object passed as argument
Consumer: An action to be performed with the object passed as argument
Function: Transform a T to a U
Supplier: Provide an instance of a T (such as a factory)
UnaryOperator: A unary operator from T -> T
BinaryOperator: A binary operator from (T, T) -> T

### Function接口
当打印人名的时候，打算安装东方和西方的习惯分别打印，比如东方姓+名，西方名+姓的区别。
Function接口可以满足这种情况，该接口如下：
```java
import java.util.Objects;
@FunctionalInterface
public interface Function<T, R> {
   R apply(T t);
}
```
Person类中定义一下方法，将Function作为参数传入，并在apply方法中回调当前对象：
```java
public String printCustom(Function<Person, String> f){
    return f.apply(this);
}
```
在客户端调用时，实现Function方法：
```java
Function<Person, String> westernStyle = p -> {
    return "\nName: " + p.getGivenName() + " " + p.getSurName() + "\n" +
            "Age: " + p.getAge() + "  " + "Gender: " + p.getGender() + "\n" +
            "EMail: " + p.getEmail() + "\n" +
            "Phone: " + p.getPhone() + "\n" +
            "Address: " + p.getAddress();
};

Function<Person, String> easternStyle =  p -> "\nName: " + p.getSurName() + " "
        + p.getGivenName() + "\n" + "Age: " + p.getAge() + "  " +
        "Gender: " + p.getGender() + "\n" +
        "EMail: " + p.getEmail() + "\n" +
        "Phone: " + p.getPhone() + "\n" +
        "Address: " + p.getAddress();
```
> Note:
westernStyle的Lambda表达式Body中含有return语句，而easternStyle没有，这似乎并没有太大影响。

客户端调用实现的Function方法进行个性化打印：
```java
// Print Western List
System.out.println("\n===Western List===");
for (Person person:list1){
    System.out.println(
            person.printCustom(westernStyle)
    );
}

// Print Eastern List
System.out.println("\n===Eastern List===");
for (Person person:list1){
    System.out.println(
            person.printCustom(easternStyle)
    );
}
```

## Lambda Expressions and Collections
这一章节展示如何使用Lambda表示式改进Collection类。
### 1. Looping
提供了forEach表达式：
```java
List<Person> pl = Person.createShortList();

System.out.println("\n=== Western Phone List ===");
pl.forEach( p -> p.printWesternName() );

System.out.println("\n=== Eastern Phone List ===");
pl.forEach(Person::printEasternName);

System.out.println("\n=== Custom Phone List ===");
pl.forEach(p -> { System.out.println(p.printCustom(r -> "Name: " + r.getGivenName() + " EMail: " + r.getEmail())); });
```
上述第一个forEach展现了标准的Lambda表达式，每个Person对象都会调用打印方法； 第二个forEach展示了方法 参考（method reference）； 第三个forEach显示了printCustom方法在其中的一个实现，注意实现printCustom方法中实现的Function中变量Person采用不同的符号（r）表示。

### 2. Chaining and Filters
过滤之前，先提供一个工具类：
```java
public class SearchCriteria {

    private final Map<String, Predicate<Person>> searchMap = new HashMap<>();

    private SearchCriteria() {
        super();
        initSearchMap();
    }

    private void initSearchMap() {
        Predicate<Person> allDrivers = p -> p.getAge() >= 16;
        Predicate<Person> allDraftees = p -> p.getAge() >= 18 && p.getAge() <= 25 && p.getGender() == Gender.MALE;
        Predicate<Person> allPilots = p -> p.getAge() >= 23 && p.getAge() <= 65;

        searchMap.put("allDrivers", allDrivers);
        searchMap.put("allDraftees", allDraftees);
        searchMap.put("allPilots", allPilots);
    }

    public Predicate<Person> getCriteria(String PredicateName) {
        Predicate<Person> target;

        target = searchMap.get(PredicateName);

        if (target == null) {
            System.out.println("Search Criteria not found... ");
            System.exit(1);
        }

        return target;
    }

    public static SearchCriteria getInstance() {
        return new SearchCriteria();
    }
}
```
使用Predicate接口可进行Collection的过滤操作。如：
```java
SearchCriteria search = SearchCriteria.getInstance();

System.out.println("\n=== Western Pilot Phone List ===");

pl.stream().filter(search.getCriteria("allPilots"))
        .forEach(Person::printWesternName);
```
这里只打印符合appPilots的Person信息。

- Getting Lazy
These features are useful, but why add them to the collections classes when there is already a perfectly good for loop? By moving iteration features into a library, it allows the developers of Java to do more code optimizations. To explain further, a couple of terms need definitions.
Laziness: In programming, laziness refers to processing only the objects that you want to process when you need to process them. In the previous example, the last loop is "lazy" because it loops only through the two Person objects left after the List is filtered. The code should be more efficient because the final processing step occurs only on the selected objects.
Eagerness: Code that performs operations on every object in a list is considered "eager". For example, an enhanced for loop that iterates through an entire list to process two objects, is considered a more "eager" approach.
By making looping part of the collections library, code can be better optimized for "lazy" operations when the opportunity arises. When a more eager approach makes sense (for example, computing a sum or an average), eager operations are still applied. This approach is a much more efficient and flexible than always using eager operations.

- The stream Method
In the previous code example, notice that the stream method is called before filtering and looping begin. This method takes a Collection as input and returns a java.util.stream.Stream interface as the output. A Stream represents a sequence of elements on which various methods can be chained. By default, once elements are consumed they are no longer available from the stream. Therefore, a chain of operations can occur only once on a particular Stream. In addition, a Stream can be serial(default) or parallel depending on the method called. An example of a parallel stream is included at the end of this section.

### 3. Mutation and Results
将集合的处理结果保存为新的集合类：
As previously mentioned, a Stream is disposed of after its use. Therefore, the elements in a collection cannot be changed or mutated with a Stream. However, what if you want to keep elements returned from your chained operations? You can save them to a new collection. The following code shows how to do just that.
```java
SearchCriteria search = SearchCriteria.getInstance();

// Make a new list after filtering.
List<Person> pilotList = pl
        .stream()
        .filter(search.getCriteria("allPilots"))
        .collect(Collectors.toList());
```

### 4. Calculating with Map
The map method is commonly used with filter. 
```java
List<Person> pl = Person.createShortList();

SearchCriteria search = SearchCriteria.getInstance();
// Get sum of ages
System.out.println("\n== Calc New Style ==");
long totalAge = pl
        .stream()
        .filter(search.getCriteria("allPilots"))
        .mapToInt(p -> p.getAge())
        .sum();

// Get average of ages
OptionalDouble averageAge = pl
        .parallelStream()
        .filter(search.getCriteria("allPilots"))
        .mapToDouble(p -> p.getAge())
        .average();
```
The last loop computes the average age from the stream. Notice that the parallelStream method is used to get a parallel stream so that the values can be computed concurrently. The return type is a bit different here as well.

## Summary
In this tutorial, you learned how to use:
Anonymous inner classes in Java.
Lambda expressions to replace anonymous inner classes in Java SE 8.
The correct syntax for lambda expressions.
A Predicate interface to perform searches on a list.
A Function interface to process an object and produce a different type of object.
New features added to Collections in Java SE 8 that support lambda expressions.

## Reference
[JAVA SE 8: Lambda Quick Start](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html#)




