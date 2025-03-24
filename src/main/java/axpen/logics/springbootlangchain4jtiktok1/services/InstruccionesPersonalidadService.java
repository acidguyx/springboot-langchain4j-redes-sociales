package axpen.logics.springbootlangchain4jtiktok1.services;

import axpen.logics.springbootlangchain4jtiktok1.dto.personalidades.CategoriaPersonalidadDTO;
import axpen.logics.springbootlangchain4jtiktok1.dto.personalidades.ConfiguracionPersonalidadesDTO;
import axpen.logics.springbootlangchain4jtiktok1.dto.personalidades.PersonalidadAutorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InstruccionesPersonalidadService {
    private final ConfiguracionPersonalidadesDTO configuracion;

    public InstruccionesPersonalidadService() {
        this.configuracion = inicializarConfiguracion();
        log.info("Servicio de personalidades inicializado con {} categorías",
                configuracion.getCategorias().size());
    }

    private ConfiguracionPersonalidadesDTO inicializarConfiguracion() {
        ConfiguracionPersonalidadesDTO config = new ConfiguracionPersonalidadesDTO();

        // Configuración global
        config.getConfiguracionGlobal().put("version", "1.0");
        config.getConfiguracionGlobal().put("ultimaActualizacion", "2024-02-20");

        // Crear categoría: Motivación
        CategoriaPersonalidadDTO motivacion = new CategoriaPersonalidadDTO();
        motivacion.setId("cat_motivacion");
        motivacion.setNombre("Motivación");
        motivacion.setDescripcion("Expertos en motivación y desarrollo personal");
        motivacion.setIcono("🎯");
        motivacion.getAutores().addAll(Arrays.asList(
                crearAutor("autor_tony_robbins", "Tony Robbins",
                        "Energético, transformador, con llamados a la acción potentes",
                        motivacion.getId()),
                crearAutor("autor_les_brown", "Les Brown",
                        "Inspirador, con storytelling emocional y mensajes de superación",
                        motivacion.getId())
        ));

        // Crear categoría: Negocios
        CategoriaPersonalidadDTO negocios = new CategoriaPersonalidadDTO();
        negocios.setId("cat_negocios");
        negocios.setNombre("Negocios");
        negocios.setDescripcion("Líderes empresariales y visionarios");
        negocios.setIcono("💼");
        negocios.getAutores().addAll(Arrays.asList(
                crearAutor("autor_elon_musk", "Elon Musk",
                        "Disruptivo, provocativo y futurista",
                        negocios.getId()),
                crearAutor("autor_warren_buffett", "Warren Buffett",
                        "Sabiduría práctica, analogías simples y enfoque a largo plazo",
                        negocios.getId()),
                crearAutor("autor_bill_gates", "Bill Gates",
                        "Analítico, basado en datos y orientado a soluciones",
                        negocios.getId())
        ));

        // Crear categoría: Marketing
        CategoriaPersonalidadDTO marketing = new CategoriaPersonalidadDTO();
        marketing.setId("cat_marketing");
        marketing.setNombre("Marketing");
        marketing.setDescripcion("Expertos en marketing y comunicación");
        marketing.setIcono("📢");
        marketing.getAutores().addAll(Arrays.asList(
                crearAutor("autor_seth_godin", "Seth Godin",
                        "Marketing conceptual, con insights únicos e historias memorables",
                        marketing.getId()),
                crearAutor("autor_gary_vaynerchuk", "Gary Vaynerchuk",
                        "Directo, práctico, crudo y auténtico",
                        marketing.getId()),
                crearAutor("autor_neil_patel", "Neil Patel",
                        "Enfocado en datos, consejos accionables y resultados",
                        marketing.getId())
        ));

        config.getCategorias().addAll(Arrays.asList(motivacion, negocios, marketing));
        return config;
    }

    private PersonalidadAutorDTO crearAutor(String id, String nombre, String descripcion, String categoriaId) {
        PersonalidadAutorDTO autor = new PersonalidadAutorDTO();
        autor.setId(id);
        autor.setNombre(nombre);
        autor.setDescripcion(descripcion);
        autor.setCategoriaId(categoriaId);
        autor.getAtributos().put("idiomaPrincipal", "es");
        return autor;
    }

    public ConfiguracionPersonalidadesDTO getConfiguracionCompleta() {
        return configuracion;
    }

}