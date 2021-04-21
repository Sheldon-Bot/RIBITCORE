package ribitcore.util;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.rmi.activation.UnknownObjectException;
import java.util.Locale;

/**
 * Identifies an operating system as an enum.
 */
public enum OSType {

    /**
     * The {@link OSType} representing Windows platforms.
     */
    WINDOWS,
    /**
     * The {@link OSType} representing Unix & Unix-like platforms (i.e. Linux).
     */
    UNIX,
    /**
     * The {@link OSType} representing MacOS platforms.
     */
    OSX,
    /**
     * The {@link OSType} representing unknown platforms.
     */
    UNKNOWN;

    /**
     * Returns the type of operating system the program is currently running on.
     * @return the operating system type as {@link OSType}. If the current operating system cannot be determined, this method
     * will return {@link OSType#UNKNOWN}.
     */
    public static OSType getOSType() {
        final @NonNull String osId = System.getProperty("os.name").toLowerCase(Locale.ROOT);

        if (osId.contains("win")) {
            return WINDOWS;
        } else if (osId.contains("osx")) {
            return OSX;
        } else if (osId.contains("nix") || osId.contains("nux") || osId.contains("aix")) {
            return UNIX;
        }

        return UNKNOWN;
    }

    /**
     *
     * @return
     */
    public static boolean isWindows() {
        return getOSType() == OSType.WINDOWS;
    }

    /**
     *
     * @return
     */
    public static boolean isUnix() {
        return getOSType() == OSType.UNIX;
    }

    /**
     *
     * @return
     */
    public static boolean isOSX() {
        return getOSType() == OSType.OSX;
    }

}
