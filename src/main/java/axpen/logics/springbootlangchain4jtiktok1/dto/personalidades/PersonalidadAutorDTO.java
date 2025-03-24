package axpen.logics.springbootlangchain4jtiktok1.dto.personalidades;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PersonalidadAutorDTO {
    private String id;          // Identificador único
    private String nombre;      // Nombre para mostrar
    private String descripcion;
    private String estilo;
    private String categoriaId; // Referencia a la categoría
    private Map<String, String> atributos = new HashMap<>();
}