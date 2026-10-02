package citasmedicas.persistencia;

import citasmedicas.modelo.ConvertibleCsv;
import citasmedicas.modelo.DatoInvalidoException;
import citasmedicas.modelo.Identificable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Lógica común para guardar registros en un archivo CSV dentro de la carpeta db.
 * Cada repositorio concreto solo implementa cómo convertir una línea en su objeto.
 */
public abstract class RepositorioCsv<T extends Identificable & ConvertibleCsv> implements Repositorio<T> {

    protected Path archivo;
    protected List<T> elementos = new ArrayList<>();

    public RepositorioCsv(Path archivo) {
        this.archivo = archivo;
    }

    // Lee el archivo completo y pasa cada línea a la lista.
    public void cargar() throws IOException {
        verificarArchivo();
        elementos.clear();
        // try-with-resources: el archivo se cierra solo, aunque ocurra un error.
        try (BufferedReader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            String linea;
            int numero = 0;
            while ((linea = lector.readLine()) != null) {
                numero++;
                if (linea.trim().isEmpty()) {
                    continue;
                }
                try {
                    elementos.add(convertirLinea(linea));
                } catch (DatoInvalidoException e) {
                    // Una línea dañada no detiene el programa: se avisa y se sigue con las demás.
                    System.out.println("Aviso: se omitió la línea " + numero + " de "
                            + archivo.getFileName() + ". " + e.getMessage());
                }
            }
        }
    }

    // Agrega a la lista y escribe el archivo. Si no se puede guardar, deshace el cambio.
    @Override
    public void agregar(T elemento) throws DatoInvalidoException, IOException {
        if (existe(elemento.getId())) {
            throw new DatoInvalidoException("Ya existe un registro con el ID " + elemento.getId() + ".");
        }
        elementos.add(elemento);
        try {
            guardarArchivo();
        } catch (IOException e) {
            elementos.remove(elemento);
            throw e;
        }
    }

    @Override
    public T buscarPorId(String id) {
        for (T elemento : elementos) {
            if (elemento.getId().equalsIgnoreCase(id.trim())) {
                return elemento;
            }
        }
        return null;
    }

    @Override
    public boolean existe(String id) {
        return buscarPorId(id) != null;
    }

    @Override
    public List<T> listar() {
        return Collections.unmodifiableList(elementos);
    }

    // Reescribe el archivo con todos los elementos de la lista.
    protected void guardarArchivo() throws IOException {
        verificarArchivo();
        try (BufferedWriter escritor = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
            for (T elemento : elementos) {
                escritor.write(elemento.toCsv());
                escritor.newLine();
            }
        }
    }

    // Si la carpeta db o el archivo no existen, los crea vacíos.
    protected void verificarArchivo() throws IOException {
        Path carpeta = archivo.getParent();
        if (carpeta != null && !Files.exists(carpeta)) {
            Files.createDirectories(carpeta);
        }
        if (!Files.exists(archivo)) {
            Files.createFile(archivo);
            System.out.println("Se creó el archivo " + archivo + " porque no existía.");
        }
    }

    protected abstract T convertirLinea(String linea) throws DatoInvalidoException;
}
