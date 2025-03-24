package axpen.logics.springbootlangchain4jtiktok1.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * DTO para encapsular la respuesta final de la aplicación.
 * Contiene:
 * - Las ideas de contenido generadas.
 * - Los hashtags sugeridos.
 * - Un mapa de puntos de vista adaptados, clasificados por categoría (ej. motivación, negocios, marketing).
 *   Cada categoría contiene un mapa con el nombre del influencer y su respuesta adaptada.
 */
@Data
public class RespuestaGeneradorIdeasDTO {

    private List<IdeasContenidoDTO.IdeaDTO> ideas;
    private List<HashtagsDTO.HashtagDTO> hashtags;
    // Cada categoría asocia el ID del autor al objeto completo PersonalidadDTO
    private Map<String, Map<String, PersonalidadDTO>> respuestasPorCategoria;
}