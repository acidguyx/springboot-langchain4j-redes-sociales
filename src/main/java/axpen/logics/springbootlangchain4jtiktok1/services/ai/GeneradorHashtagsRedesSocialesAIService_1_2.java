package axpen.logics.springbootlangchain4jtiktok1.services.ai;

import axpen.logics.springbootlangchain4jtiktok1.dto.HashtagsDTO;
import dev.langchain4j.service.UserMessage;

/**
 * Servicio AI para generar hashtags relevantes para un tema.
 * Se generan 5 hashtags, cada uno con nombre y una relevancia numérica.
 */
public interface GeneradorHashtagsRedesSocialesAIService_1_2 {

    @UserMessage("""
    Genera 5 hashtags relevantes para contenido sobre: {{it}}
    Para cada hashtag incluye:
    - nombre (sin el símbolo #)
    - relevancia (1-100)
    Formato JSON como este ejemplo:
    {
      "hashtags": [
        {
          "nombre": "ejemplo",
          "relevancia": 95
        }
      ]
    }
    """)
    HashtagsDTO generarHashtags(String tema);
}
