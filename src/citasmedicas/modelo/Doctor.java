package citasmedicas.modelo;

public class Doctor extends Persona {

    private String especialidad;

    public Doctor(String id, String nombreCompleto, String especialidad) throws DatoInvalidoException {
        super(id, nombreCompleto);
        this.especialidad = validarTexto(especialidad, "especialidad");
    }

    public String getEspecialidad() {
        return especialidad;
    }

    @Override
    public String mostrarInfo() {
        return "[" + id + "] Dr(a). " + nombreCompleto + " - " + especialidad;
    }

    // Formato en doctores.csv: id,nombreCompleto,especialidad
    @Override
    public String toCsv() {
        return id + "," + nombreCompleto + "," + especialidad;
    }

    public static Doctor desdeCsv(String linea) throws DatoInvalidoException {
        String[] datos = linea.split(",");
        if (datos.length != 3) {
            throw new DatoInvalidoException("Línea de doctor con formato incorrecto: " + linea);
        }
        return new Doctor(datos[0], datos[1], datos[2]);
    }
}
