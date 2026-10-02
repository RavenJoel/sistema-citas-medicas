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
            // Al crearse, verifica la carpeta db, regenera los archivos que falten y carga los datos.
            Consultorio consultorio = new Consultorio(entrada);
            consultorio.mostrarMenu();
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
