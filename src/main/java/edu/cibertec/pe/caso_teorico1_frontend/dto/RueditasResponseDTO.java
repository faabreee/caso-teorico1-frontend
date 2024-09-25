package edu.cibertec.pe.caso_teorico1_frontend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RueditasResponseDTO(
        String id,
        String placa,
        String marca,
        String modelo,
        String numAsientos,
        String precio,
        String color) {
}
