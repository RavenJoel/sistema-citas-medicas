package citasmedicas.modelo;

/**
 * Se lanza cuando un dato capturado o leído del archivo no es válido.
 */
public class DatoInvalidoException extends Exception {

    private static final long serialVersionUID = 1L;

    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
