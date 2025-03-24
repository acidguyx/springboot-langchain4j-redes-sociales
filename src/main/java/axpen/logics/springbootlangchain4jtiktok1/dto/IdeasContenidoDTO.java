package axpen.logics.springbootlangchain4jtiktok1.dto;

import lombok.Data;
import java.util.List;

/**
 * DTO para almacenar las ideas de contenido generadas.
 * Contiene una lista de ideas, cada una con título, descripción y una puntuación de engagement.
 */
@Data
public class IdeasContenidoDTO {
    private List<IdeaDTO> ideas;

    @Data
    public static class IdeaDTO {
        // Título atractivo de la idea (máximo 60 caracteres)
        private String titulo;
        // Descripción breve de la idea (máximo 120 caracteres)
        private String descripcion;
        // Puntuación estimada de engagement (1-100)
        private Integer engagement;
    }
}
