package citasmedicas.modelo;

public class Administrador extends Persona {

    private String contrasena;

    public Administrador(String id, String nombreCompleto, String contrasena) throws DatoInvalidoException {
        super(id, nombreCompleto);
        this.contrasena = validarTexto(contrasena, "contraseña");
    }

    // La contraseña nunca sale de la clase: solo se responde si coincide o no.
    public boolean validarContrasena(String c) {
        return contrasena.equals(c);
    }

    @Override
    public String mostrarInfo() {
        return "[" + id + "] Administrador: " + nombreCompleto;
    }

    // Formato en administradores.csv: id,nombreCompleto,contrasena
    @Override
    public String toCsv() {
        return id + "," + nombreCompleto + "," + contrasena;
    }

    public static Administrador desdeCsv(String linea) throws DatoInvalidoException {
        String[] datos = linea.split(",");
        if (datos.length != 3) {
            throw new DatoInvalidoException("Línea de administrador con formato incorrecto: " + linea);
        }
        return new Administrador(datos[0], datos[1], datos[2]);
    }
}
