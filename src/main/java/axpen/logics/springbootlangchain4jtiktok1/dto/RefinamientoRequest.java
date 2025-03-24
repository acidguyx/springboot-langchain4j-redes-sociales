package axpen.logics.springbootlangchain4jtiktok1.dto;


import lombok.Data;

// Clase para el request AJAX
@Data
public class RefinamientoRequest {
    private String ideaSeleccionada;
    // IDs de los autores seleccionados
    private String motivacion;    // ID del autor de motivación
    private String negocios;      // ID del autor de negocios
    private String marketing;     // ID del autor de marketing
}