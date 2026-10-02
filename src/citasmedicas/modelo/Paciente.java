package citasmedicas.modelo;

public class Paciente extends Persona {

    public Paciente(String id, String nombreCompleto) throws DatoInvalidoException {
        super(id, nombreCompleto);
    }

    @Override
    public String mostrarInfo() {
        return "[" + id + "] " + nombreCompleto;
    }

    // Formato en pacientes.csv: id,nombreCompleto
    @Override
    public String toCsv() {
        return id + "," + nombreCompleto;
    }

    public static Paciente desdeCsv(String linea) throws DatoInvalidoException {
        String[] datos = linea.split(",");
        if (datos.length != 2) {
            throw new DatoInvalidoException("Línea de paciente con formato incorrecto: " + linea);
        }
        return new Paciente(datos[0], datos[1]);
    }
}
