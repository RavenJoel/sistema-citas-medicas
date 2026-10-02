package citasmedicas;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Punto de entrada del sistema de citas médicas.
 */
public class Principal {

    public static void main(String[] args) {
        System.out.println("===== SISTEMA DE CITAS MÉDICAS =====");
        Scanner entrada = new Scanner(System.in);
        try {
            // Al crearse, ambos verifican la carpeta db, regeneran los archivos que falten y cargan los datos.
            ControlAcceso acceso = new ControlAcceso();
            Consultorio consultorio = new Consultorio(entrada);

            if (acceso.iniciarSesion(entrada)) {
                consultorio.mostrarMenu();
            } else {
                System.out.println("Se agotaron los intentos. Fin del programa.");
            }
        } catch (IOException e) {
            System.out.println("No se pudieron preparar los archivos de datos: " + e.getMessage());
        } catch (NoSuchElementException e) {
            // Ocurre si se cierra la entrada (por ejemplo, con Ctrl+D).
            System.out.println();
            System.out.println("Se cerró la entrada. Fin del programa.");
        } finally {
            entrada.close();
        }
    }
}
