import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.print("Please, enter the classPath of the directory \"external_classes\" : ");
        String classPath;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            classPath = reader.readLine();
        } catch (IOException e) {
            System.err.println("ClassPath can't be read: " + e.getMessage());
            return;
        }

        CustomClassLoader myClassLoader = new CustomClassLoader(classPath);

        try {
            Class<?> carClass = myClassLoader.findClass("external_classes.Car");

            System.out.println("Car Class name: " + carClass.getName());
            System.out.println("Car Fields: " + Arrays.toString(carClass.getDeclaredFields()));
            System.out.println("Car Package: " + carClass.getPackage());
            System.out.println("Car ClassLoader: " + carClass.getClassLoader().getClass());
            System.out.println("Car Methods: " + Arrays.toString(carClass.getMethods()));
        } catch (ClassNotFoundException e) {
            System.err.println("Class is not found " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception while loading a class  " + e.getMessage());
            e.printStackTrace();
        }
    }
}
