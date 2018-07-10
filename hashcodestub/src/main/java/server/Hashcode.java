package server;

import io.javalin.Javalin;

import java.util.Random;

public class Hashcode {

    public static void main(String[] args) {
        Javalin.create()
                .port(7070)
                .get("/hashcode", get -> get.result(getStandardResponse()))
                .start();
    }

    private static String getStandardResponse() {
        return "Standard";
    }

}
