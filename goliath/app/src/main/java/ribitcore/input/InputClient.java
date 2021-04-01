package ribitcore.input;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import ribitcore.motor.MotorController;
import ribitcore.motor.MotorSpeed;

import java.net.URI;

public class InputClient extends WebSocketClient {

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

            float leftMotorOutput;
            float rightMotorOutput;


            if (isJoystick) {
                motorController.setValues(
                        MotorSpeed.thousandsValueToOneValue(roll),
                        MotorSpeed.thousandsValueToOneValue(pitch)
                );
            }

        }
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
