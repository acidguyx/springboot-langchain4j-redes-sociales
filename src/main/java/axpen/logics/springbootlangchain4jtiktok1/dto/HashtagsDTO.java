package axpen.logics.springbootlangchain4jtiktok1.dto;

import lombok.Data;
import java.util.List;

/**
 * DTO para almacenar los hashtags generados.
 * Contiene una lista de hashtags, cada uno con nombre y relevancia (1-100).
 */
@Data
public class HashtagsDTO {
    private List<HashtagDTO> hashtags;

    @Data
    public static class HashtagDTO {
        // Nombre del hashtag (sin el símbolo #)
        private String nombre;
        // Relevancia estimada del hashtag (1-100)
        private Integer relevancia;
    }
}
