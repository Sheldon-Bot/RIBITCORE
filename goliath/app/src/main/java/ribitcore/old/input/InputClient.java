package ribitcore.old.input;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import ribitcore.old.motor.MotorController;
import ribitcore.old.motor.MotorSpeed;

import java.net.URI;

public class InputClient extends WebSocketClient {

    private static final double MAX_SPEED = 1.0;

    private final @NonNull MotorController motorController;

    public InputClient(
            final @NonNull MotorController motorController
    ) {
        super(URI.create("ws://192.168.2.205:8080/input"));
        this.motorController = motorController;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("[connection] " + handshake.getHttpStatus());
    }

    @Override
    public void onMessage(final @NonNull String message) {
        final @NonNull JsonObject data = new JsonParser().parse(message).getAsJsonObject();

        System.out.println(data);

        if (data.get("command").getAsString().equals("drive")) {
            final float roll = data.get("roll").getAsFloat();
            final float pitch = data.get("pitch").getAsFloat();

            final boolean isJoystick = data.get("type").getAsString().equals("joystick");

            double leftMotorOutput = 0;
            double rightMotorOutput = 0;

            if (isJoystick) {
                final double y = cleanseValue(MotorSpeed.thousandsValueToOneValue(pitch));
                final double x = cleanseValue(MotorSpeed.thousandsValueToOneValue(roll));

                System.out.println("[xy] "+x+", "+y);

                double maxInput = Math.copySign(Math.max(Math.abs(y), Math.abs(x)), y);

                if (y >= 0) {
                    if (x >= 0) {
                        leftMotorOutput = maxInput;
                        rightMotorOutput = y - x;
                    } else {
                        leftMotorOutput = y + x;
                        rightMotorOutput = maxInput;
                    }
                } else {
                    if (x >= 0) {
                        leftMotorOutput = y + x;
                        rightMotorOutput = maxInput;
                    } else {
                        leftMotorOutput = maxInput;
                        rightMotorOutput = y - x;
                    }
                }
            }

            motorController.setValues(
                    leftMotorOutput,
                    rightMotorOutput * -1
            );
        }
    }

    private double cleanseValue(double value) {
        if (value <= -0.1 && value <= 0.1) {
            return 0;
        }

        return value;
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("[close] " + code + " - " + reason + " - " + remote);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

}
