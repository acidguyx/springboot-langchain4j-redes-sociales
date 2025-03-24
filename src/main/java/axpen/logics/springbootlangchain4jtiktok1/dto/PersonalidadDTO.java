package axpen.logics.springbootlangchain4jtiktok1.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonalidadDTO {
    private String respuesta;
    private List<ContenidoRedSocialDTO> contenidoRedes;

    @Data
    public static class ContenidoRedSocialDTO {
        private String redSocial;           // "Instagram", "Twitter", "TikTok"
        private String contenido;           // El post o script principal
        private List<String> hashtags;      // Hashtags específicos para esta red
        private List<String> consejos;      // Tips de publicación
        private ExtrasDTO extras;           // Elementos adicionales según la red
    }

    @Data
    public static class ExtrasDTO {
        private String tipo_contenido;      // "Carrusel", "Reel", "Post", etc.
        private String elementos_visuales;   // "Imágenes", "Video", "GIF", etc.
        private String estrategia;          // Estrategia específica para la red
    }
}