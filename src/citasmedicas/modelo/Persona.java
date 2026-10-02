package citasmedicas.modelo;

/**
 * Datos que comparten el doctor, el paciente y el administrador.
 * Es abstracta: no se puede crear una Persona sola, solo sus subclases.
 */
public abstract class Persona implements Identificable, ConvertibleCsv {

    protected String id;
    protected String nombreCompleto;

    public Persona(String id, String nombreCompleto) throws DatoInvalidoException {
        this.id = validarTexto(id, "ID");
        this.nombreCompleto = validarTexto(nombreCompleto, "nombre completo");
    }

    @Override
    public String getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    // Cada subclase decide cómo se muestra (polimorfismo).
    public abstract String mostrarInfo();

    // Revisa que un texto no esté vacío ni lleve comas, porque la coma separa los campos del CSV.
    protected static String validarTexto(String valor, String campo) throws DatoInvalidoException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new DatoInvalidoException("El campo " + campo + " no puede estar vacío.");
        }
        if (valor.contains(",")) {
            throw new DatoInvalidoException("El campo " + campo + " no puede llevar comas.");
        }
        return valor.trim();
    }
}
