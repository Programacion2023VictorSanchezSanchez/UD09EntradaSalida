package T09Tarea2;

import java.io.IOException;

public class Leer {
    public static int datoInt() {
        return Integer.parseInt(dato());
    }

    public static String dato() {
        try {
            byte[] buffer = new byte[255];
            int nbytes = System.in.read(buffer);
            return new String(buffer, 0, nbytes).trim();
        } catch (IOException e) {
            return "";
        }
    }
}
