package axpen.logics.springbootlangchain4jtiktok1.services.ai;

import axpen.logics.springbootlangchain4jtiktok1.dto.IdeasContenidoDTO;
import dev.langchain4j.service.UserMessage;
/**
 * Servicio AI para generar ideas de contenido para redes sociales.
 * El prompt especifica que se deben generar 3 ideas con título, descripción y engagement.
 */
public interface GeneradorIdeasRedesSocialesAIService_1 {

    @UserMessage("""
    Genera 3 ideas de contenido para redes sociales sobre: {{it}}
    Cada idea debe incluir:
    - Título atractivo (máximo 60 caracteres)
    - Descripción breve (máximo 120 caracteres)
    - Puntuación de engagement estimada (1-100)
    Formato JSON como este ejemplo:
    {
      "ideas": [
        {
          "titulo": "Título de la idea",
          "descripcion": "Descripción breve",
          "engagement": 85
        }
      ]
    }
    """)
    IdeasContenidoDTO generarIdeas(String tema);
}
