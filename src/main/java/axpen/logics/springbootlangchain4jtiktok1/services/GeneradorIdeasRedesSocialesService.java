package axpen.logics.springbootlangchain4jtiktok1.services;

import axpen.logics.springbootlangchain4jtiktok1.dto.RespuestaGeneradorIdeasDTO;
import dev.langchain4j.model.chat.ChatLanguageModel;

/**
 * Interfaz del servicio principal para generar contenido para redes sociales.
 * Incluye la generación inicial y el refinamiento de la idea seleccionada.
 */
public interface GeneradorIdeasRedesSocialesService {
    RespuestaGeneradorIdeasDTO generarContenido(String tema, ChatLanguageModel model);

    RespuestaGeneradorIdeasDTO refinarContenido(
            String ideaSeleccionada,
            String motivacion,
            String negocios,
            String marketing,
            ChatLanguageModel model);
}