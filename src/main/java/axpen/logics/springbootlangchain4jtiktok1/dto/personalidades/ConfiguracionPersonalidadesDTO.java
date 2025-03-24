package axpen.logics.springbootlangchain4jtiktok1.dto.personalidades;

import lombok.Data;

import java.util.*;

@Data
public class ConfiguracionPersonalidadesDTO {
    private List<CategoriaPersonalidadDTO> categorias = new ArrayList<>();
    private Map<String, String> configuracionGlobal = new HashMap<>();

    public Optional<CategoriaPersonalidadDTO> getCategoriaById(String id) {
        return categorias.stream()
                .filter(cat -> cat.getId().equals(id))
                .findFirst();
    }

    public Optional<PersonalidadAutorDTO> getAutorById(String id) {
        return categorias.stream()
                .flatMap(cat -> cat.getAutores().stream())
                .filter(autor -> autor.getId().equals(id))
                .findFirst();
    }
}