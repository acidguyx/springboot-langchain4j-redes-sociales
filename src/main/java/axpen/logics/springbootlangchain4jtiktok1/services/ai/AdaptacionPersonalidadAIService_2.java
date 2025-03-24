package axpen.logics.springbootlangchain4jtiktok1.services.ai;

import axpen.logics.springbootlangchain4jtiktok1.dto.PersonalidadDTO;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface AdaptacionPersonalidadAIService_2 {

    @SystemMessage("""
    Eres un experto en marketing digital y creación de contenido, especializado en:
    1. Desarrollo completo de ideas y conceptos
    2. Adaptación de contenido para diferentes plataformas sociales
    3. Creación de contenido en el estilo de influencers y expertos reconocidos
    
    Tu objetivo es:
    - Tomar la idea inicial y desarrollarla completamente
    - Crear contenido detallado y específico para cada plataforma
    - Mantener el estilo y filosofía del autor especificado
    - Generar ejemplos concretos y casos prácticos
    - Incluir llamados a la acción relevantes
    - Proporcionar hashtags estratégicos
    - Dar consejos específicos de implementación
    - Todo el contenido debe estar en español
    """)

    @UserMessage("""
    Desarrolla y adapta completamente esta idea según el estilo del autor:
    
    [Idea Base]
    {{contenidoOriginal}}
    
    [Información del Autor]
    Nombre: {{nombreAutor}}
    Estilo y Enfoque: {{descripcionAutor}}
    
    Para cada plataforma social, debes:
    1. Desarrollar la idea completamente, no solo adaptarla
    2. Crear contenido específico y detallado
    3. Incluir ejemplos y casos prácticos
    4. Proporcionar consejos de implementación
    5. Generar hashtags relevantes
    6. Incluir elementos visuales sugeridos
    
    El contenido debe ser:
    - Detallado y accionable
    - Fiel al estilo del autor
    - Optimizado para cada plataforma
    - Práctico y aplicable
    - Engaging y compartible
    
    La respuesta debe incluir:
    1. Una versión expandida de la idea original
    2. Contenido específico para cada red social
    3. Consejos de implementación
    4. Estrategias de engagement
    5. Elementos visuales recomendados
    """)
    PersonalidadDTO adaptarRespuesta(
            @V("contenidoOriginal") String contenidoOriginal,
            @V("nombreAutor") String nombreAutor,
            @V("descripcionAutor") String descripcionAutor);
}