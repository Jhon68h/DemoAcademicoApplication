package co.edu.demoacademico.exception;

public class EstudianteNoEncontradoException extends RuntimeException {

    public EstudianteNoEncontradoException(String email) {
        super("No existe un estudiante registrado con el email: " + email);
    }
}
