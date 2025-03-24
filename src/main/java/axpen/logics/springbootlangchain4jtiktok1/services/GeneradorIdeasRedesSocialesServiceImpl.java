package axpen.logics.springbootlangchain4jtiktok1.services;

import axpen.logics.springbootlangchain4jtiktok1.dto.RespuestaGeneradorIdeasDTO;
import axpen.logics.springbootlangchain4jtiktok1.dto.PersonalidadDTO;
import axpen.logics.springbootlangchain4jtiktok1.dto.personalidades.PersonalidadAutorDTO;
import axpen.logics.springbootlangchain4jtiktok1.services.ai.AdaptacionPersonalidadAIService_2;
import axpen.logics.springbootlangchain4jtiktok1.services.ai.GeneradorHashtagsRedesSocialesAIService_1_2;
import axpen.logics.springbootlangchain4jtiktok1.services.ai.GeneradorIdeasRedesSocialesAIService_1;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Implementación principal del servicio de generación de ideas para redes sociales.
 * Coordina las llamadas a los diferentes servicios AI y procesa los resultados.
 */
@Service
@Slf4j
public class GeneradorIdeasRedesSocialesServiceImpl implements GeneradorIdeasRedesSocialesService {

    // 📌 Dependencia inyectada para acceder a las configuraciones de personalidades
    private final InstruccionesPersonalidadService instruccionesPersonalidadService;

    /**
     * Constructor con inyección de dependencias automática de Spring.
     */
    public GeneradorIdeasRedesSocialesServiceImpl(InstruccionesPersonalidadService instruccionesPersonalidadService) {
        this.instruccionesPersonalidadService = instruccionesPersonalidadService;
    }

    /**
     * Genera el contenido inicial (ideas y hashtags) a partir de un tema.
     *
     * @param tema El tema sobre el que generar contenido
     * @param model El modelo de IA a utilizar (puede ser flash o pro)
     * @return El DTO con las ideas y hashtags generados
     */
    @Override
    public RespuestaGeneradorIdeasDTO generarContenido(String tema, ChatLanguageModel model) {
        log.info("Iniciando generación de contenido para tema: {}", tema);

        try {
            // 🔮 PASO 1: Creación dinámica de servicios AI con el modelo seleccionado
            // Esto es clave: langchain4j crea implementaciones en tiempo de ejecución
            var ideasAIService = AiServices.create(GeneradorIdeasRedesSocialesAIService_1.class, model);
            var hashtagsAIService = AiServices.create(GeneradorHashtagsRedesSocialesAIService_1_2.class, model);

            // 🔮 PASO 2: Generación del contenido usando los servicios AI creados
            // Cada servicio usa el mismo modelo pero con diferentes prompts
            var ideas = ideasAIService.generarIdeas(tema);
            var hashtags = hashtagsAIService.generarHashtags(tema);

            // 📦 PASO 3: Preparación de la respuesta combinando resultados
            RespuestaGeneradorIdeasDTO respuestaFinal = new RespuestaGeneradorIdeasDTO();
            respuestaFinal.setIdeas(ideas.getIdeas());
            respuestaFinal.setHashtags(hashtags.getHashtags());

            return respuestaFinal;
        } catch (Exception e) {
            // ⚠️ Manejo centralizado de errores
            log.error("Error generando contenido: ", e);
            throw new RuntimeException("Error al generar contenido: " + e.getMessage(), e);
        }
    }

    /**
     * Refina una idea seleccionada según los estilos de los autores elegidos.
     * Permite personalizar el contenido según diferentes perspectivas.
     *
     * @param ideaSeleccionada La idea base a refinar
     * @param motivacionAutorId ID del autor de motivación seleccionado (opcional)
     * @param negociosAutorId ID del autor de negocios seleccionado (opcional)
     * @param marketingAutorId ID del autor de marketing seleccionado (opcional)
     * @param model El modelo de IA a utilizar
     * @return El DTO con las respuestas personalizadas por categoría y autor
     */
    @Override
    public RespuestaGeneradorIdeasDTO refinarContenido(
            String ideaSeleccionada,
            String motivacionAutorId,
            String negociosAutorId,
            String marketingAutorId,
            ChatLanguageModel model) {

        log.info("Refinando contenido para idea seleccionada con autores seleccionados");

        try {
            // 🔮 PASO 1: Creamos el servicio AI para adaptar el contenido según estilos
            var personalidadAIService = AiServices.create(AdaptacionPersonalidadAIService_2.class, model);

            // 📦 PASO 2: Preparamos estructura para almacenar respuestas por categoría
            Map<String, Map<String, PersonalidadDTO>> respuestasPorCategoria = new HashMap<>();

            // 🔄 PASO 3: Procesamos cada categoría con su autor seleccionado (si existe)
            procesarCategoria("cat_motivacion", motivacionAutorId, ideaSeleccionada,
                    personalidadAIService, respuestasPorCategoria);
            procesarCategoria("cat_negocios", negociosAutorId, ideaSeleccionada,
                    personalidadAIService, respuestasPorCategoria);
            procesarCategoria("cat_marketing", marketingAutorId, ideaSeleccionada,
                    personalidadAIService, respuestasPorCategoria);

            // 📦 PASO 4: Preparamos la respuesta final
            RespuestaGeneradorIdeasDTO refinamientoFinal = new RespuestaGeneradorIdeasDTO();
            refinamientoFinal.setRespuestasPorCategoria(respuestasPorCategoria);

            return refinamientoFinal;
        } catch (Exception e) {
            log.error("Error refinando contenido: ", e);
            throw new RuntimeException("Error al refinar contenido: " + e.getMessage(), e);
        }
    }

    /**
     * Método auxiliar para procesar cada categoría y autor.
     * Si el autor existe, adapta la idea según su estilo.
     *
     * @param categoriaId ID de la categoría (motivación, negocios, marketing)
     * @param autorId ID del autor seleccionado (puede ser null)
     * @param ideaSeleccionada La idea base a adaptar
     * @param personalidadAIService El servicio AI para adaptación
     * @param respuestasPorCategoria Mapa donde se almacenan los resultados
     */
    private void procesarCategoria(
            String categoriaId,
            String autorId,
            String ideaSeleccionada,
            AdaptacionPersonalidadAIService_2 personalidadAIService,
            Map<String, Map<String, PersonalidadDTO>> respuestasPorCategoria) {

        // 📦 Inicializamos el mapa para esta categoría
        Map<String, PersonalidadDTO> respuestaCategoria = new HashMap<>();

        // 🔍 Solo procesamos si se seleccionó un autor
        if (autorId != null && !autorId.isEmpty()) {
            // 🔍 Buscamos la información del autor en la configuración
            Optional<PersonalidadAutorDTO> autorOpt = instruccionesPersonalidadService
                    .getConfiguracionCompleta()
                    .getAutorById(autorId);

            if (autorOpt.isPresent()) {
                PersonalidadAutorDTO autor = autorOpt.get();
                log.debug("Procesando autor: {} para categoría: {}", autor.getNombre(), categoriaId);

                // 🔮 PASO CLAVE: Adaptamos la idea según el estilo del autor
                // Aquí es donde langchain4j hace la magia de personalización
                PersonalidadDTO refinamiento = personalidadAIService.adaptarRespuesta(
                        ideaSeleccionada,        // La idea base
                        autor.getNombre(),       // Nombre del autor (ej: "Tony Robbins")
                        autor.getDescripcion()   // Estilo del autor (ej: "Energético, transformador...")
                );

                // 📦 Almacenamos el resultado para este autor
                respuestaCategoria.put(autorId, refinamiento);
                log.debug("Refinamiento completado para autor: {}", autor.getNombre());
            } else {
                log.warn("No se encontró el autor con ID: {}", autorId);
            }
        } else {
            log.debug("No se seleccionó autor para la categoría: {}", categoriaId);
        }

        // 📦 Añadimos las respuestas de esta categoría al mapa general
        respuestasPorCategoria.put(categoriaId, respuestaCategoria);
    }
}