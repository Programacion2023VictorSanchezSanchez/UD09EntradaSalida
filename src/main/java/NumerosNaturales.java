import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NumerosNaturales {

    public ArrayList<Integer> listaNum;
    public static final String FICHERO = "numNaturales.txt";

    // Constructor
    public NumerosNaturales() {
        listaNum = new ArrayList<>();
        leerNumerosDesdeArchivo();
    }

    /**
     * Metodo para leer los números del fichero y guardarlos en una lista
     */
    private void leerNumerosDesdeArchivo() {
        try {
            File archivo = new File(FICHERO);
            if (archivo.exists()) {
                Scanner scanner = new Scanner(archivo);
                while (scanner.hasNextInt()) {
                    int numero = scanner.nextInt();
                    listaNum.add(numero);
                }
                scanner.close();
            } else {
                System.out.println("El archivo " + FICHERO + " no existe.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Metodo para leer los números introducidos por el usuario y guardarlos en una lista
     */
    public void leerNumerosUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce números enteros positivos (introduce -1 para terminar):");
        int numero;
        do {
            System.out.print("Número: ");
            numero = scanner.nextInt();
            if (numero != -1) {
                if (numero >= 0) {
                    listaNum.add(numero);
                } else {
                    System.out.println("Error: Debes introducir números positivos.");
                }
            }
        } while (numero != -1);
        scanner.close();
    }

    /**
     * Metodo para guardar la lista en el fichero
     */
    public void guardarLista() {
        try {
            FileWriter writer = new FileWriter(FICHERO);
            for (int numero : listaNum) {
                writer.write(numero + "\n");
            }
            writer.close();
            System.out.println("Lista guardada en " + FICHERO + " correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar la lista en el archivo: " + e.getMessage());
        }
    }


    /**
     * Mostramos la lista con un stream
     */
    public void mostrarList() {
        System.out.println("Lista de números naturales:");
        listaNum.stream().forEach(System.out::println);
    }


    /**
     * Metodo para calcular la media de la lista con un stream
     * @return media de la lista
     */
    public double media() {
        return listaNum.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    /**
     * Metodo para sacar el máximo de la lista con un stream
     * @return número máximo de la lista
     */
    public int max() {
        return listaNum.stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(Integer.MIN_VALUE);
    }


}
