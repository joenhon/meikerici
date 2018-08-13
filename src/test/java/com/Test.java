package com;

import com.util.ustd;

import java.util.List;

public class Test {

    public static void main(String [] ages){
        ustd ustd=new ustd();
        System.out.println(ustd.newAddress());
        System.out.println(ustd.getAddresses());
        System.out.println(ustd.getBalance("mphabSMKCRK8pqaueVTYPypRP58pLDSU2w"));
        /*List<String> list=ustd.getAddresses();
        for (String add:list) {
            System.out.println(add);
        }*/
    }
}
