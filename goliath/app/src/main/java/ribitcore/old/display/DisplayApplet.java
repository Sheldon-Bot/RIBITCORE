package ribitcore.old.display;

import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.PApplet;
import ribitcore.old.motor.MotorController;

import java.util.Date;

public class DisplayApplet extends PApplet {

    private final @NonNull MotorController motorController;

    private final @NonNull Date startupDate;

//    private final @NonNull Webcam webcam;

    public DisplayApplet(
            MotorController motorController
    ) {
//        Webcam.setDriver(new V4l4jDriver());
        this.motorController = motorController;
        this.startupDate = new Date();
//        this.webcam = Webcam.getDefault();
    }

    public void settings() {
        fullScreen(0);
        size(displayWidth, displayHeight);
    }

    public void draw() {
        background(200, 200, 255);

        fill(0);

        textSize(32);
        text("RIBIT", 100, 100);

        textSize(18);
        text("Robotic Interactive Broadcast & IoT Telecommunicator", 100, 130);

        textSize(20);
        text("Numbers for Nerds", 100, 885);

        textSize(15);
        text("Launched " + startupDate.toString(), 100, 900);
        text("CPU Temp: NA°C", 100, 915);
        text("Frames sent: 0", 100, 930);
        text("Frames recv: 0", 100, 945);

        //        BufferedImage image = webcam.getImage();
//
//        PImage pImage = new PImage(image);
//
//        image(pImage, 100, 300);
    }

}
