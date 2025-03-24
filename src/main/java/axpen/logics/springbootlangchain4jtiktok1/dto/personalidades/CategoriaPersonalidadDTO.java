package axpen.logics.springbootlangchain4jtiktok1.dto.personalidades;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CategoriaPersonalidadDTO {
    private String id;          // Identificador único
    private String nombre;      // Nombre para mostrar
    private String descripcion;
    private String icono;
    private List<PersonalidadAutorDTO> autores = new ArrayList<>();
    private Map<String, String> atributos = new HashMap<>();
}