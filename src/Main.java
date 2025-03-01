import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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
            Class<?> driverClass = myClassLoader.findClass("external_classes.Driver");
            Class<?> carClass = myClassLoader.findClass("external_classes.Car");
            String driverName = "Schumacher";
            int plateNumber = 333;
            String carMake = "Ferrari";
            Constructor<?> driverConstructor = driverClass.getConstructor(String.class);
            Object driverInstance = driverConstructor.newInstance(driverName);
            Constructor<?> carConstructor = carClass.getConstructor(driverClass, int.class, String.class);
            Object carInstance = carConstructor.newInstance(driverInstance, plateNumber, carMake);
            Method startEngineMethod = carClass.getMethod("startEngine");
            Method getDriverMethod = carClass.getMethod("getDriver");
            Method getPlateNumberMethod = carClass.getMethod("getPlateNumber");
            Method getMakeMethod = carClass.getMethod("getMake");
            System.out.println("Method invocation - startEngine: " + startEngineMethod.invoke(carInstance) + "\n");
            System.out.println("Method invocation - getDriverMethod: " + getDriverMethod.invoke(carInstance) + "\n");
            System.out.println("Method invocation - getPlateNumberMethod: " + getPlateNumberMethod.invoke(carInstance) + "\n");
            System.out.println("Method invocation - getMakeMethod: " + getMakeMethod.invoke(carInstance) + "\n");

            System.out.println("Car Class name: " + carClass.getName() + "\n");
            System.out.println("Car Fields: " + Arrays.toString(carClass.getDeclaredFields()) + "\n");
            System.out.println("Car Package: " + carClass.getPackage() + "\n");
            System.out.println("Car ClassLoader: " + carClass.getClassLoader().getClass() + "\n");
            System.out.println("Car Methods: " + Arrays.toString(carClass.getMethods()) + "\n");
        } catch (ClassNotFoundException e) {
            System.err.println("Class is not found " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception while loading a class  " + e.getMessage());
            e.printStackTrace();
        }
    }
}
