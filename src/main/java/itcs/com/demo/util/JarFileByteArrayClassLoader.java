package itcs.com.demo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class JarFileByteArrayClassLoader extends ClassLoader {
    private final JarFile jarFile;

    public JarFileByteArrayClassLoader(JarFile jarFile) {
        super(Thread.currentThread().getContextClassLoader());
        this.jarFile = jarFile;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String path = name.replace('.', '/').concat(".class");

        byte[] classBytes = readFromJarFileByteArray(path);
        if (classBytes == null) {
            throw new ClassNotFoundException(name);
        }

        Class<?> clazz = defineClass(name, classBytes, 0, classBytes.length);
        int delimiter = name.lastIndexOf('.');
        String packageName = delimiter == -1 ? "" : name.substring(0, delimiter);
        if (Package.getPackage(packageName) == null) {
            definePackage(packageName, null, null, null, null, null, null, null);
        }

        return clazz;
    }

    private byte[] readFromJarFileByteArray(String path) {
        try {
            ZipEntry entry = jarFile.getEntry(path);
            InputStream zipInputStream = jarFile.getInputStream(entry);

            byte[] buffer = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            return byteArrayOutputStream.toByteArray();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}