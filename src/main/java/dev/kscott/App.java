package dev.kscott;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Hello world
 */
public class App {

    public static void main( String[] args )
    {
        new RobotManager().startFront();

    }

    public static File saveResource(String name) throws IOException {
        return saveResource(name, true);
    }

    public static File saveResource(String name, boolean replace) throws IOException {
        return saveResource(new File("."), name, replace);
    }

    public static File saveResource(File outputDirectory, String name) throws IOException {
        return saveResource(outputDirectory, name, true);
    }

    public static File saveResource(File outputDirectory, String name, boolean replace)
            throws IOException {
        File out = new File(outputDirectory, name);
        if (!replace && out.exists())
            return out;
        // Step 1:
        InputStream resource = App.class.getResourceAsStream(name);
        if (resource == null)
            throw new FileNotFoundException(name + " (resource not found)");
        // Step 2 and automatic step 4
        try(InputStream in = resource;
            OutputStream writer = new BufferedOutputStream(
                    new FileOutputStream(out))) {
            // Step 3
            byte[] buffer = new byte[1024 * 4];
            int length;
            while((length = in.read(buffer)) >= 0) {
                writer.write(buffer, 0, length);
            }
        }
        return out;
    }

}
