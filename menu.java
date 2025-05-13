import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class menu {
    public static void main(String[] args) {
        String nombreArchivo = "recepcion.dat";
        File archivo = new File(nombreArchivo);
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
                FileOutputStream fos = new FileOutputStream(archivo);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                DataOutputStream dos = new DataOutputStream(bos);
                dos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean continuar = true;
        Scanner scanner = new Scanner(System.in);
        while (continuar) {
            try {
                System.out.println("\n=== MENU ===");
                System.out.println("1. Recepcion de computadoras");
                System.out.println("2. Inspeccion de computadoras");
                System.out.println("3. Reparacion de computadoras");
                System.out.println("4. Control de calidad");
                System.out.println("5. Entrega de la computadora");
                System.out.println("6. Historial de computadoras");
                System.out.println("0. Salir");
                System.out.println("Ingrese una opcion: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                case 1:
                RecepcionComputadora(archivo, scanner);
                break;
                case 2:
                break;
                case 3:
                break;
                case 4:
                break;
                case 5:
                break;
                case 6:
                break;
                case 0:
                continuar = false;
                System.out.println("Saliendo del programa...");
                break;
                default:
                System.out.println("Opción no válida. Por favor, elija una opción válida");
                }
                
            } catch (Exception e) {
                System.out.println("Error: Ingrese un dato validado.");
                scanner.nextLine();
            }
    }
}

public static void RecepcionComputadora(File archivo, Scanner scanner) {
        Queue<String> cola = new LinkedList<>();
        int contadorID = 1;
        boolean continuar = true;

        // Cargar la cola existente si el archivo ya existe
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                cola = (Queue<String>) ois.readObject();
                contadorID = cola.size() + 1; // continuar numeración
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("No se pudo cargar la cola existente. Se iniciará una nueva.");
            }
        }

        while (continuar) {
            try {
                System.out.println("\n === RECEPCIÓN DE COMPUTADORAS ===");
                System.out.println("1. Visualizar computadoras en cola");
                System.out.println("2. Ingreso de computadora");
                System.out.println("3. Finalizar revisión");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer

                switch (opcion) {
                    case 1:
                        System.out.println("\nComputadoras en cola:");
                        if (cola.isEmpty()) {
                            System.out.println("No hay computadoras en cola.");
                        } else {
                            for (String entrada : cola) {
                                System.out.println(entrada);
                            }
                        }
                        break;

                    case 2:
                        System.out.println("Ingrese la descripción del problema:");
                        String descripcion = scanner.nextLine();

                        LocalDate fechaActual = LocalDate.now();
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        String fecha = fechaActual.format(formato);

                        System.out.println("Ingrese el nombre del cliente: ");
                        String nombre = scanner.nextLine();

                        System.out.println("Ingrese el correo electrónico del cliente: ");
                        String correo = scanner.nextLine();

                        System.out.println("Ingrese el número de teléfono del cliente (solo números): ");
                        int telefono = scanner.nextInt();
                        scanner.nextLine(); // limpiar buffer

                        if (telefono > 0) {
                            String cliente = contadorID + "      " + descripcion + "      " + fecha + "      " + nombre + "      " + correo + "      " + telefono;
                            cola.add(cliente);
                            contadorID++;

                            // Guardar cola actualizada en archivo binario
                            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
                                oos.writeObject(cola);
                                System.out.println("Datos guardados correctamente en archivo binario.");
                            } catch (IOException e) {
                                System.out.println("Error al guardar los datos: " + e.getMessage());
                            }
                        } else {
                            limpiarConsola();
                            System.out.println("Error: Ingrese un número de teléfono válido.");
                        }
                        break;

                    case 3:
                        continuar = false;
                        System.out.println("Finalizando recepción...");
                        break;

                    default:
                        System.out.println("Opción inválida.");
                }

            } catch (Exception e) {
                System.out.println("Error en la entrada. Intente de nuevo.");
                scanner.nextLine(); // limpiar buffer de entrada
            }
        }
    }

public static void limpiarConsola() {
    for (int i = 0; i < 50; i++) {
        System.out.println();
    }
}
}
