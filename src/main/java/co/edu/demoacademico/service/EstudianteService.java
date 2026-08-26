package co.edu.demoacademico.service;

import co.edu.demoacademico.exception.EmailRegistradoException;
import co.edu.demoacademico.exception.EstudianteNoEncontradoException;
import co.edu.demoacademico.model.Estudiante;
import co.edu.demoacademico.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public Estudiante crear(Estudiante estudiante) {

        // Regla: email único
        repository.findByEmail(estudiante.getEmail())
                .ifPresent(e -> {
                    throw new EmailRegistradoException(estudiante.getEmail());
                });

        // Persistencia vía Repository
        return repository.save(estudiante);
    }

    public List<Estudiante> listar() {

        // Consulta vía Repository
        return repository.findAll();
    }

    public Estudiante buscarPorEmail(String email) {

        // Consulta vía Repository
        return repository.findByEmail(email)
                .orElseThrow(() -> new EstudianteNoEncontradoException(email));
    }
}
