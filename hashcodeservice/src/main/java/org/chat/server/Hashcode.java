package org.chat.server;

import io.javalin.Javalin;

import java.util.Random;

public class Hashcode {

    public static void main(String[] args) {
        Javalin.create()
                .port(7070)
                .get("/hashcode", get -> get.result(getNewMd5()))
                .start();
    }

    private static String getNewMd5() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
//        return DigestUtils.md5(String.format("%04d", random.nextInt(10000))).toString();
    }
}
