package axpen.logics.springbootlangchain4jtiktok1.controllers;

import axpen.logics.springbootlangchain4jtiktok1.dto.RefinamientoRequest;
import axpen.logics.springbootlangchain4jtiktok1.dto.RespuestaGeneradorIdeasDTO;
import axpen.logics.springbootlangchain4jtiktok1.services.GeneradorIdeasRedesSocialesService;
import axpen.logics.springbootlangchain4jtiktok1.services.InstruccionesPersonalidadService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Controller
@Slf4j
public class GeneradorIdeasRedesSocialesController {

    private final GeneradorIdeasRedesSocialesService generadorIdeasService;

    private final InstruccionesPersonalidadService instruccionesPersonalidadService;

    private final ChatLanguageModel geminiFlashModel;

    private final ChatLanguageModel geminiProModel;

    public GeneradorIdeasRedesSocialesController(GeneradorIdeasRedesSocialesService generadorIdeasService, InstruccionesPersonalidadService instruccionesPersonalidadService, ChatLanguageModel geminiFlashModel, ChatLanguageModel geminiProModel) {
        this.generadorIdeasService = generadorIdeasService;
        this.instruccionesPersonalidadService = instruccionesPersonalidadService;
        this.geminiFlashModel = geminiFlashModel;
        this.geminiProModel = geminiProModel;
    }

    // 1️⃣ PUNTO DE ENTRADA INICIAL
    // Cuando el usuario accede a http://localhost:8080/
    @GetMapping("/")
    public String index(Model model) {
        log.debug("Accediendo a la página principal");

        // 🖥️ Retornamos la vista "index.html"
        return "index";
    }

    // 2️⃣ GENERACIÓN INICIAL DE IDEAS
    // Cuando el usuario envía el formulario con un tema
    @PostMapping("/generar")
    public String generarContenido(
            @RequestParam String tema,            // 📝 Tema ingresado por el usuario
            @RequestParam(defaultValue = "flash") String modelType, // 🔄 Tipo de modelo (flash/pro)
            Model model,
            HttpSession session) {
        log.info("Iniciando generación de contenido para tema: {}", tema);

        try {
            // 🧠 SELECCIÓN DINÁMICA DEL MODELO
            // Elegimos entre el modelo rápido o el avanzado según la selección del usuario
            ChatLanguageModel selectedModel = modelType.equals("pro") ?
                    geminiProModel : geminiFlashModel;

            // 🔮 GENERACIÓN DE CONTENIDO
            // Llamamos al servicio que creará las ideas y hashtags
            // El servicio a su vez llamará a los servicios AI con los prompts
            RespuestaGeneradorIdeasDTO resultado =
                    generadorIdeasService.generarContenido(tema, selectedModel);

            // 💾 Guardamos el resultado en la sesión para uso posterior
            session.setAttribute("resultado", resultado);

            // 📤 Preparamos datos para la vista
            model.addAttribute("resultado", resultado);
            model.addAttribute("tema", tema);
            model.addAttribute("configuracionPersonalidades",
                    instruccionesPersonalidadService.getConfiguracionCompleta());

            // 🔄 Retornamos a la misma página pero ahora con resultados
            return "index";
        } catch (Exception e) {
            // 🚨 Manejo de errores
            log.error("Error al generar contenido: ", e);
            model.addAttribute("error", "Error al generar contenido: " + e.getMessage());
            return "index";
        }
    }

    // 3️⃣ REFINAMIENTO DE IDEAS (PERSONALIZACIÓN)
    // Esta es una API REST que se llama vía AJAX desde el frontend
    @PostMapping("/api/refinar")
    @ResponseBody  // 📡 Indica que devuelve datos JSON, no una vista
    public ResponseEntity<?> refinarContenido(
            @RequestBody RefinamientoRequest request,  // 📥 Datos enviados desde el frontend
            @RequestParam(defaultValue = "flash") String modelType) {
        log.info("Iniciando refinamiento de contenido");

        try {
            // 🧠 SELECCIÓN DINÁMICA DEL MODELO (igual que antes)
            ChatLanguageModel selectedModel = modelType.equals("pro") ?
                    geminiProModel : geminiFlashModel;

            // 🔮 REFINAMIENTO SEGÚN AUTORES SELECCIONADOS
            // Adaptamos la idea seleccionada según el estilo de cada autor elegido
            RespuestaGeneradorIdeasDTO refinamiento = generadorIdeasService.refinarContenido(
                    request.getIdeaSeleccionada(),     // 💡 Idea que eligió el usuario
                    request.getMotivacion(),           // 🎯 Autor de motivación seleccionado
                    request.getNegocios(),             // 💼 Autor de negocios seleccionado
                    request.getMarketing(),            // 📢 Autor de marketing seleccionado
                    selectedModel                      // 🧠 Modelo de IA seleccionado
            );

            // ✅ Retornamos los resultados como JSON
            return ResponseEntity.ok(refinamiento);
        } catch (Exception e) {
            // 🚨 En caso de error, devolvemos un mensaje de error estructurado
            log.error("Error en refinamiento: ", e);
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage(),
                    "timestamp", new Date()
            ));
        }
    }
}