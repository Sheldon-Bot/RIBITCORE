package dev.kscott.sheldonbot;

import java.io.IOException;

/**
 * Hello world
 */
public class App {

    public static void main(String[] args) {
        try {
            new RobotManager();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
