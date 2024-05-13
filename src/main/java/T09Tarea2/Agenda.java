package T09Tarea2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agenda {
    private List<Contacto> contactos;
    private final String archivo = "agenda.dat";

    public Agenda() {
        contactos = new ArrayList<>();
        // Intentar cargar los contactos desde el archivo
        try {
            cargarContactos();
        } catch (IOException e) {
            System.err.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    // Método para cargar los contactos desde el archivo
    private void cargarContactos() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                contactos.add(Contacto.leerContacto(raf));
            }
        }
    }

    // Método para guardar un contacto en el archivo
    private void guardarContacto(Contacto contacto) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.seek(raf.length());
            Contacto.escribirContacto(raf, contacto);
        }
    }

    // Método para encontrar la posición de un contacto en la lista
    private int encontrarPosicion(Contacto contacto) {
        return contactos.indexOf(contacto);
    }

    // Método para modificar un contacto tanto en la lista como en el archivo
    public void modificarContacto(Contacto contacto) throws IOException {
        int posicion = encontrarPosicion(contacto);
        if (posicion != -1) {
            contactos.set(posicion, contacto); // Actualizar en la lista
            try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
                Contacto.escribirContacto(raf, contacto, posicion); // Actualizar en el archivo
            }
        }
    }

    // Método para borrar un contacto tanto de la lista como del archivo
    public void borrarContacto(Contacto contacto) throws IOException {
        int posicion = encontrarPosicion(contacto);
        if (posicion != -1) {
            // Marcar los campos del contacto como "xxxxxxxx"
            contacto.setNombre("xxxxxxxxxxxxxxxxxxxx");
            contacto.setDireccion("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            contacto.setTelefono("xxxxxxxxxx");
            contactos.set(posicion, contacto); // Actualizar en la lista
            try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
                Contacto.escribirContacto(raf, contacto, posicion); // Actualizar en el archivo
            }
        }
    }

    // Método para buscar un contacto por su ID
    public Contacto buscarContacto(int id) {
        for (Contacto contacto : contactos) {
            if (contacto.getId() == id) {
                return contacto;
            }
        }
        return null; // Si no se encuentra el contacto
    }

    // Método para ordenar la agenda por el campo "nombre"
    public void ordenarAgenda() throws IOException {
        Collections.sort(contactos); // Ordenar la lista
        // Guardar la lista ordenada en el archivo
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.setLength(0); // Limpiar el archivo
            for (Contacto contacto : contactos) {
                Contacto.escribirContacto(raf, contacto); // Escribir los contactos ordenados en el archivo
            }
        }
    }

    // Método para mostrar todos los contactos en la agenda
    public void mostrarAgenda() {
        for (Contacto contacto : contactos) {
            System.out.println(contacto);
        }
    }

    // Método para mostrar los contactos cuyo nombre contenga una determinada cadena
    public void mostrarCoincidencias(String nombre) {
        contactos.stream()
                .filter(contacto -> contacto.getNombre().contains(nombre))
                .forEach(System.out::println);
    }

    // Método para crear un nuevo contacto
    public void crearContacto(Contacto contacto) throws IOException {
        if (!contactos.contains(contacto)) {
            contactos.add(contacto); // Agregar a la lista
            guardarContacto(contacto); // Guardar en el archivo
        } else {
            System.out.println("El contacto ya existe en la agenda.");
        }
    }

    // Método para el menú de la agenda
    public void menu() throws IOException {
        int opcion;
        do {
            System.out.println("\nMenú:");
            System.out.println("1. Mostrar Agenda");
            System.out.println("2. Ordenar Agenda");
            System.out.println("3. Crear Contacto");
            System.out.println("4. Modificar Contacto");
            System.out.println("5. Buscar Contacto");
            System.out.println("6. Salir");
            System.out.print("Ingrese su opción: ");
            opcion = Leer.datoInt();
            switch (opcion) {
                case 1:
                    mostrarAgenda();
                    break;
                case 2:
                    ordenarAgenda();
                    System.out.println("Agenda ordenada correctamente.");
                    break;
                case 3:
                    crearContacto(crearNuevoContacto());
                    break;
                case 4:
                    modificarContacto(crearNuevoContacto());
                    break;
                case 5:
                    System.out.print("Ingrese el ID del contacto a buscar: ");
                    int id = Leer.datoInt();
                    Contacto contacto = buscarContacto(id);
                    if (contacto != null) {
                        System.out.println("Contacto encontrado: " + contacto);
                    } else {
                        System.out.println("Contacto no encontrado.");
                    }
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }

    // Método para crear un nuevo contacto solicitando datos al usuario
    private Contacto crearNuevoContacto() {
        System.out.print("Ingrese el ID del contacto: ");
        int id = Leer.datoInt();
        System.out.print("Ingrese el nombre del contacto: ");
        String nombre = Leer.dato();
        System.out.print("Ingrese la dirección del contacto: ");
        String direccion = Leer.dato();
        System.out.print("Ingrese el teléfono del contacto: ");
        String telefono = Leer.dato();
        return new Contacto(id, nombre, direccion, telefono);
    }



    public static void main(String[] args) throws IOException {
        Agenda agenda = new Agenda();
        agenda.menu();
    }
}



