# Generador de Ideas para Redes Sociales con Spring Boot + langchain4j

🚀 Una aplicación Spring Boot que utiliza langchain4j para generar ideas de contenido para redes sociales, personalizar respuestas según diferentes estilos de autores, y crear contenido optimizado para distintas plataformas.

## 🌟 Características

- **Generación de ideas**: Crea automáticamente ideas de contenido a partir de un tema
- **Sugerencia de hashtags**: Genera hashtags relevantes para maximizar el alcance
- **Personalización por autor**: Adapta el contenido al estilo de diferentes personalidades
- **Multi-plataforma**: Optimiza el contenido para distintas redes sociales
- **Modelos intercambiables**: Cambia fácilmente entre modelos Gemini Flash y Pro

## 🧩 Arquitectura

El proyecto sigue una arquitectura de capas:

- **Frontend**: Thymeleaf + JavaScript
- **Controladores**: Gestionan las peticiones HTTP
- **Servicios**: Coordinan la lógica de negocio
- **Servicios AI**: Definen los prompts con anotaciones de langchain4j
- **DTOs**: Objetos para transferencia de datos
- **Configuración**: Gestión de propiedades y beans de Spring

## 🛠️ Tecnologías utilizadas

- **Spring Boot**: Framework base de la aplicación
- **langchain4j**: Biblioteca para interactuar con modelos de IA desde Java
- **Gemini AI**: Modelos de IA de Google para generar el contenido
- **Thymeleaf**: Motor de plantillas para el frontend
- **Lombok**: Reduce código repetitivo (getters, setters, etc.)

## 📋 Requisitos previos

- Java 21 o superior
- Gradle
- API Key de Google Gemini

## 🚀 Instalación y ejecución

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/acidguyx/springboot-langchain4j-redes-sociales.git
   cd springboot-langchain4j-redes-sociales


