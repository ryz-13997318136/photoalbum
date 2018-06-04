package com.ryz.photoalbum.controller;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        File file = new File("/usr/photoalbum/all_image/2018-06-04%5C/22.png");
        System.out.println(file.exists());
        System.out.println(file.length());

        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
            System.out.println(os + " can't gunzip");
        }
    }
}
