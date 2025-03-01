import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CustomClassLoader extends ClassLoader {
    private final String classPath;

    public CustomClassLoader(String classPath) {
        this.classPath = classPath;
    }

    public Class<?> findClass(String className) throws ClassNotFoundException {

        String absoluteClassPath = classPath + File.separator + className.replace(".", File.separator) + ".class";
        File classFile = new File(absoluteClassPath);

        if (!classFile.exists()) {
            throw new ClassNotFoundException("Class " + className + " not found at " + absoluteClassPath);
        }

        try (FileInputStream fileInputStream = new FileInputStream(classFile)) {
            int fileLengthInBytes = (int) classFile.length();
            byte[] fileInBytes = new byte[fileLengthInBytes];

            int readBytes = 0;
            while (readBytes < fileLengthInBytes) {
                int result = fileInputStream.read(fileInBytes, readBytes, fileLengthInBytes - readBytes);
                if (result == -1) {
                    throw new IOException("Expected " + fileLengthInBytes + " bytes, but read " + readBytes + " bytes.");
                }
                readBytes += result;
            }
            return defineClass(className, fileInBytes, 0, fileLengthInBytes);
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class " + className, e);
        }
    }
}
