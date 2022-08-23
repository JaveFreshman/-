package com.example.test1.stream;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class test {

    public static List<User> users(){
        List<User> list = Arrays.asList(
                new User("李星云", 18, 0, "渝州",new BigDecimal(1000)),
                new User("陆林轩", 16, 1, "渝州",new BigDecimal(500)),
                new User("姬如雪", 17, 1, "幻音坊",new BigDecimal(800)),
                new User("袁天罡", 99, 0, "藏兵谷",new BigDecimal(100000)),
                new User("张子凡", 19, 0, "天师府",new BigDecimal(900)),
                new User("陆佑劫", 45, 0, "不良人",new BigDecimal(600)),
                new User("张天师", 48, 0, "天师府",new BigDecimal(1100)),
                new User("蚩梦", 18, 1, "万毒窟",new BigDecimal(800)),
                new User("李星云", 18, 0, "渝州",new BigDecimal(1000))
        );
        return list;
    }

    /*filter过滤(T-> boolean)*/
    public static void filter() {
        List<User> list = users();
        List<User> newlist = list.stream().filter(user -> user.getAge() > 20).collect(Collectors.toList());
        for (User user : newlist) {
            System.out.println(user.getName() + " --> " + user.getAge());
        }
    }

    /*distinct 去重*/
//    数据源中复制new User("李星云", 18, 0, "渝州",new BigDecimal(1000)) 并粘贴两个
    public static void distinct(){
        List<User> list = users();
        List<User> newlist = list.stream().distinct().collect(Collectors.toList());
        for (User user : newlist) {
            System.out.println(user.getName()+" --> "+ user.getAge());
        }
    }

    /*sorted排序*/
    public static void sorted(){
        List<User> list = users();
        List<User> newlist = list.stream()
                .sorted(Comparator.comparingInt(User::getAge))
                .collect(Collectors.toList());
        for (User user : newlist) {
            System.out.println(user.getName()+" --> "+ user.getAge());
        }
    }

    /*limit返回前n个元素*/
    public static void limit(){
        List<User> list = users();
        List<User> newlist = list.stream()
                .sorted(Comparator.comparingInt(User::getAge))
                .limit(2)
                .collect(Collectors.toList());
        for (User user : newlist) {
            System.out.println(user.getName()+" --> "+ user.getAge());
        }
    }

    /*skip去除前n个元素*/
    public static void skip(){
        List<User> list = users();
        List<User> newlist = list.stream()
                .sorted(Comparator.comparingInt(User::getAge))
                .skip(2)
                .collect(Collectors.toList());
        for (User user : newlist) {
            System.out.println(user.getName()+" --> "+ user.getAge());
        }
    }

    /*map(T->R)*/
    public static void map(){
        List<User> list = users();
        List<String> newlist = list.stream()
                .map(User::getName).distinct().collect(Collectors.toList());
        for (String add : newlist) {
            System.out.println(add);
        }
    }

    /*flatMap(T -> Stream<R>)*/
    public static void flatmap(){
        List<String> flatmap = new ArrayList<>();
        flatmap.add("常宣灵,常昊灵");
        flatmap.add("孟婆,判官红,判官蓝");
        /*
            这里原集合中的数据由逗号分割，使用split进行拆分后，得到的是Stream<String[]>，
            字符串数组组成的流，要使用flatMap的Arrays::stream
            将Stream<String[]>转为Stream<String>,然后把流相连接
        */
        flatmap = flatmap.stream()
                .map(s -> s.split(","))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        for (String name : flatmap) {
            System.out.println(name);
        }
    }

    /*allMatch（T->boolean）检测是否全部满足参数行为*/
    public static void allMatch(){
        List<User> list = users();
        boolean flag = list.stream()
                .allMatch(user -> user.getAge() >= 17);
        System.out.println(flag);
    }

    /*anyMatch（T->boolean）检测是否有任意元素满足给定的条件*/
    public static void anyMatch(){
        List<User> list = users();
        boolean flag = list.stream()
                .anyMatch(user -> user.getSex() == 1);
        System.out.println(flag);
    }

    /*noneMatchT->boolean）流中是否有元素匹配给定的 T -> boolean条件*/
    public static void noneMatch(){
        List<User> list = users();
        boolean flag = list.stream()
                .noneMatch(user -> user.getAddress().contains("郑州"));
        System.out.println(flag);
    }

    /*findFirst( ):找到第一个元素*/
    public static void findfirst(){
        List<User> list = users();
        Optional<User> optionalUser = list.stream()
                .sorted(Comparator.comparingInt(User::getAge))
                .findFirst();
        System.out.println(optionalUser.toString());
    }

    /*findAny( ):找到任意一个元素*/
    public static void findAny(){
        List<User> list = users();
//        Optional<User> optionalUser = list.stream()
//                .findAny();
        Optional<User> optionalUser = list.stream()
                .findAny();
        System.out.println(optionalUser.toString());
    }

    /*计算总数*/
    public static void count(){
        List<User> list = users();
        long count = list.stream().count();
        System.out.println(count);
    }

    /*最大值最小值*/
    public static void max_min(){
        List<User> list = users();
        Optional<User> max = list.stream()
                .collect(
                        Collectors.maxBy(
                                Comparator.comparing(User::getAge)
                        )
                );
        Optional<User> min = list.stream()
                .collect(
                        Collectors.minBy(
                                Comparator.comparing(User::getAge)
                        )
                );
        System.out.println("max--> " + max+"  min--> "+ min);
    }

    /*求和_平均值*/
    public static void sum_avg(){
        List<User>list = users();
        int totalAge = list.stream()
                .collect(Collectors.summingInt(User::getAge));
        System.out.println("totalAge--> "+ totalAge);

        /*获得列表对象金额， 使用reduce聚合函数,实现累加器*/
        BigDecimal totalMpney = list.stream()
                .map(User::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("totalMpney--> " + totalMpney);

        double avgAge = list.stream()
                .collect(Collectors.averagingInt(User::getAge));
        System.out.println("avgAge--> " + avgAge);
    }

    /*一次性得到元素的个数、总和、最大值、最小值*/
    public static void allVlaue(){
        List<User> list = users();
        IntSummaryStatistics statistics = list.stream()
                .collect(Collectors.summarizingInt(User::getAge));
        System.out.println(statistics);
    }

    /*拼接*/
    public static void join(){
        List<User> list = users();
        String names = list.stream()
                .map(User::getName)
                .collect(Collectors.joining(", "));
        System.out.println(names);
    }

    /*分组*/
    public static void group(){
        Map<Integer, List<User>> map = users().stream()
                .collect(Collectors.groupingBy(User::getSex));
        System.out.println(new Gson().toJson(map));
        System.out.println();
        Map<Integer, Map<Integer,List<User>>> map2 = users().stream()
                .collect(Collectors.groupingBy(User::getSex,
                        Collectors.groupingBy(User::getAge)));
        System.out.println(new Gson().toJson(map2));
    }

    /*分组合计*/
    public static void groupCount(){
        Map<Integer, Long> num = users().stream()
                .collect(Collectors.groupingBy(User::getSex, Collectors.counting()));
        System.out.println(num);


        Map<Integer, Long> num2 = users().stream()
                .filter(user -> user.getAge()>=18)
                .collect(Collectors.groupingBy(User::getSex, Collectors.counting()));
        System.out.println(num2);
    }

    /*分区*/
    public static void partitioningBy(){
        List<User> list = users();
        Map<Boolean, List<User>> part = list.stream()
                .collect(Collectors.partitioningBy(user -> user.getAge() <= 30));
        System.out.println(new Gson().toJson(part));
    }

    private static List<String> list = Arrays.asList("张三","李四","王二","麻子","九六","mak","张一");

    public static void main(String[] args) {

        list.stream().filter((String name)->{
            return name.startsWith("张");
        }).forEach((String i)->{
            System.out.println(i);
        });


        int i = 0;
    }
}
