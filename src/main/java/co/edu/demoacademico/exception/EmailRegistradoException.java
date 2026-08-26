package co.edu.demoacademico.exception;

public class EmailRegistradoException extends RuntimeException {

    public EmailRegistradoException(String email) {
        super("El email ya se encuentra registrado: " + email);
    }
}
