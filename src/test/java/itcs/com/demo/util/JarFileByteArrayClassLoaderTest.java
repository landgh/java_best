package itcs.com.demo.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JarFileByteArrayClassLoaderTest {

    private JarFileByteArrayClassLoader classLoader;
    private JarFile jarFile;

    @BeforeEach
    public void setUp() throws IOException {
        jarFile = mock(JarFile.class);
        classLoader = new JarFileByteArrayClassLoader(jarFile);
    }

    @Test
    public void testFindClass_ClassFound() throws Exception {
        String className = "itcs.com.demo.util.JarFileByteArrayClassLoaderTest$TestClass";
        String classPath = className.replace('.', '/').concat(".class");
        byte[] classData = new byte[] { 1, 2, 3, 4 };

        JarEntry jarEntry = new JarEntry(classPath);
        when(jarFile.getEntry(classPath)).thenReturn(jarEntry);
        when(jarFile.getInputStream(jarEntry)).thenReturn(new ByteArrayInputStream(classData));

        Class<?> clazz = classLoader.findClass(className);

        assertNotNull(clazz);
        assertEquals(className, clazz.getName());
    }

    @Test
    public void testFindClass_ClassNotFound() {
        String className = "itcs.com.demo.NonExistentClass";
        String classPath = className.replace('.', '/').concat(".class");

        when(jarFile.getEntry(classPath)).thenReturn(null);

        assertThrows(ClassNotFoundException.class, () -> classLoader.findClass(className));
    }

    @Test
    public void testFindClass_ClassIOException() throws IOException {
        String className = "itcs.com.demo.TestClass";
        String classPath = className.replace('.', '/').concat(".class");

        JarEntry jarEntry = new JarEntry(classPath);
        when(jarFile.getEntry(classPath)).thenReturn(jarEntry);
        when(jarFile.getInputStream(jarEntry)).thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> classLoader.findClass(className));
    }

    class TestClass {
        // Empty class
    }
}