package co.edu.demoacademico;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Regla del Lab 03: el módulo matriculas solo puede hablar con otros módulos por Ports, nunca por sus repositorios.
class ArquitecturaMatriculasTest {

    @Test
    void matriculasNoImportaRepositoriosDeOtrosModulos() throws IOException {
        try (Stream<Path> files = Files.walk(Path.of("src/main/java/co/edu/demoacademico/matriculas"))) {
            List<String> violaciones = files.filter(p -> p.toString().endsWith(".java"))
                    .flatMap(p -> {
                        try { return Files.readAllLines(p).stream().map(l -> p.getFileName() + ": " + l); }
                        catch (IOException e) { throw new RuntimeException(e); }
                    })
                    .filter(l -> l.matches(".*import co\\.edu\\.demoacademico\\.(?!matriculas\\.)\\w+\\.repository\\..*"))
                    .toList();
            assertTrue(violaciones.isEmpty(), "Imports prohibidos: " + violaciones);
        }
    }
}
