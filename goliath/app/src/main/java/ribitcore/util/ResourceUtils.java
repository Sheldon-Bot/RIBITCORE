package ribitcore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * A helper to copy resources from a JAR file into a directory.
 * Source: https://stackoverflow.com/a/58318009
 */
public final class ResourceUtils {

    /**
     * URI prefix for JAR files.
     */
    private static final String JAR_URI_PREFIX = "jar:file:";

    /**
     * The default buffer size.
     */
    private static final int BUFFER_SIZE = 8 * 1024;

    /**
     * Copies a set of resources into a temporal directory, optionally preserving
     * the paths of the resources.
     * @param preserve Whether the files should be placed directly in the
     *  directory or the source path should be kept
     * @param paths The paths to the resources
     * @return The temporal directory
     * @throws IOException If there is an I/O error
     */
    public static File copyResourcesToTempDir(final boolean preserve,
                                              final String... paths)
            throws IOException {
        final File parent = new File(System.getProperty("java.io.tmpdir"));
        File directory;
        do {
            directory = new File(parent, String.valueOf(System.nanoTime()));
        } while (!directory.mkdir());
        return copyResourcesToDir(directory, preserve, paths);
    }

    /**
     * Copies a set of resources into a directory, preserving the paths
     * and names of the resources.
     * @param directory The target directory
     * @param preserve Whether the files should be placed directly in the
     *  directory or the source path should be kept
     * @param paths The paths to the resources
     * @return The temporal directory
     * @throws IOException If there is an I/O error
     */
    public static File copyResourcesToDir(final File directory, final boolean preserve,
                                          final String... paths) throws IOException {
        for (final String path : paths) {
            final File target;
            if (preserve) {
                target = new File(directory, path);
                target.getParentFile().mkdirs();
            } else {
                target = new File(directory, new File(path).getName());
            }
            writeToFile(
                    Thread.currentThread()
                            .getContextClassLoader()
                            .getResourceAsStream(path),
                    target
            );
        }
        return directory;
    }

    /**
     * Copies a resource directory from inside a JAR file to a target directory.
     * @param source The JAR file
     * @param path The path to the directory inside the JAR file
     * @param target The target directory
     * @throws IOException If there is an I/O error
     */
    public static void copyResourceDirectory(final JarFile source, final String path,
                                             final File target) throws IOException {
        final Enumeration<JarEntry> entries = source.entries();
        final String newpath = String.format("%s/", path);
        while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();
            if (entry.getName().startsWith(newpath) && !entry.isDirectory()) {
                final File dest =
                        new File(target, entry.getName().substring(newpath.length()));
                final File parent = dest.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }
                writeToFile(source.getInputStream(entry), dest);
            }
        }
    }

    /**
     * The JAR file containing the given class.
     * @param clazz The class
     * @return The JAR file or null
     * @throws IOException If there is an I/O error
     */
    public static Optional<JarFile> jar(final Class<?> clazz) throws IOException {
        final String path =
                String.format("/%s.class", clazz.getName().replace('.', '/'));
        final URL url = clazz.getResource(path);
        Optional<JarFile> optional = Optional.empty();
        if (url != null) {
            final String jar = url.toString();
            final int bang = jar.indexOf('!');
            if (jar.startsWith(ResourceUtils.JAR_URI_PREFIX) && bang != -1) {
                optional = Optional.of(
                        new JarFile(
                                jar.substring(ResourceUtils.JAR_URI_PREFIX.length(), bang)
                        )
                );
            }
        }
        return optional;
    }

    /**
     * Writes an input stream to a file.
     * @param input The input stream
     * @param target The target file
     * @throws IOException If there is an I/O error
     */
    private static void writeToFile(final InputStream input, final File target)
            throws IOException {
        final OutputStream output = Files.newOutputStream(target.toPath());
        final byte[] buffer = new byte[ResourceUtils.BUFFER_SIZE];
        int length = input.read(buffer);
        while (length > 0) {
            output.write(buffer, 0, length);
            length = input.read(buffer);
        }
        input.close();
        output.close();
    }

    /**
     * Gets the base location of the given class.
     * <p>
     * If the class is directly on the file system (e.g.,
     * "/path/to/my/package/MyClass.class") then it will return the base directory
     * (e.g., "file:/path/to").
     * </p>
     * <p>
     * If the class is within a JAR file (e.g.,
     * "/path/to/my-jar.jar!/my/package/MyClass.class") then it will return the
     * path to the JAR (e.g., "file:/path/to/my-jar.jar").
     * </p>
     *
     * @param c The class whose location is desired.
     * @see ResourceUtils#urlToFile(URL) to convert the result to a {@link File}.
     */
    public static URL getLocation(final Class<?> c) {
        if (c == null) return null; // could not load the class

        // try the easy way first
        try {
            final URL codeSourceLocation =
                    c.getProtectionDomain().getCodeSource().getLocation();
            if (codeSourceLocation != null) return codeSourceLocation;
        }
        catch (final SecurityException e) {
            // NB: Cannot access protection domain.
        }
        catch (final NullPointerException e) {
            // NB: Protection domain or code source is null.
        }

        // NB: The easy way failed, so we try the hard way. We ask for the class
        // itself as a resource, then strip the class's path from the URL string,
        // leaving the base path.

        // get the class's raw resource path
        final URL classResource = c.getResource(c.getSimpleName() + ".class");
        if (classResource == null) return null; // cannot find class resource

        final String url = classResource.toString();
        final String suffix = c.getCanonicalName().replace('.', '/') + ".class";
        if (!url.endsWith(suffix)) return null; // weird URL

        // strip the class's path from the URL string
        final String base = url.substring(0, url.length() - suffix.length());

        String path = base;

        // remove the "jar:" prefix and "!/" suffix, if present
        if (path.startsWith("jar:")) path = path.substring(4, path.length() - 2);

        try {
            return new URL(path);
        }
        catch (final MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts the given {@link URL} to its corresponding {@link File}.
     * <p>
     * This method is similar to calling {@code new File(url.toURI())} except that
     * it also handles "jar:file:" URLs, returning the path to the JAR file.
     * </p>
     *
     * @param url The URL to convert.
     * @return A file path suitable for use with e.g. {@link FileInputStream}
     * @throws IllegalArgumentException if the URL does not correspond to a file.
     * @see <a href="https://stackoverflow.com/a/12733172">Code source</a>
     */
    public static File urlToFile(final URL url) {
        return url == null ? null : urlToFile(url.toString());
    }

    /**
     * Converts the given URL string to its corresponding {@link File}.
     *
     * @param url The URL to convert.
     * @return A file path suitable for use with e.g. {@link FileInputStream}
     * @throws IllegalArgumentException if the URL does not correspond to a file.
     * @see <a href="https://stackoverflow.com/a/12733172">Code source</a>
     */
    public static File urlToFile(final String url) {
        String path = url;
        if (path.startsWith("jar:")) {
            // remove "jar:" prefix and "!/" suffix
            final int index = path.indexOf("!/");
            path = path.substring(4, index);
        }
        try {
            if (OSType.isWindows() && path.matches("file:[A-Za-z]:.*")) {
                path = "file:/" + path.substring(5);
            }
            return new File(new URL(path).toURI());
        } catch (final MalformedURLException e) {
            // NB: URL is not completely well-formed.
        } catch (final URISyntaxException e) {
            // NB: URL is not completely well-formed.
        }
        if (path.startsWith("file:")) {
            // pass through the URL as-is, minus "file:" prefix
            path = path.substring(5);
            return new File(path);
        }
        throw new IllegalArgumentException("Invalid URL: " + url);
    }

}
