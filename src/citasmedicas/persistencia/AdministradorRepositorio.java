package citasmedicas.persistencia;

import citasmedicas.modelo.Administrador;
import citasmedicas.modelo.DatoInvalidoException;
import java.io.IOException;
import java.nio.file.Paths;

public class AdministradorRepositorio extends RepositorioCsv<Administrador> {

    public static final String ID_POR_DEFECTO = "admin";
    public static final String CONTRASENA_POR_DEFECTO = "admin123";

    public AdministradorRepositorio() {
        super(Paths.get("db", "administradores.csv"));
    }

    // Si no hay ningún administrador, crea uno por defecto para poder entrar al sistema.
    @Override
    public void cargar() throws IOException {
        super.cargar();
        if (elementos.isEmpty()) {
            try {
                agregar(new Administrador(ID_POR_DEFECTO, "Administrador General", CONTRASENA_POR_DEFECTO));
                System.out.println("Se creó el administrador por defecto (ID: " + ID_POR_DEFECTO
                        + ", contraseña: " + CONTRASENA_POR_DEFECTO + ").");
            } catch (DatoInvalidoException e) {
                System.out.println("No se pudo crear el administrador por defecto: " + e.getMessage());
            }
        }
    }

    @Override
    protected Administrador convertirLinea(String linea) throws DatoInvalidoException {
        return Administrador.desdeCsv(linea);
    }
}
