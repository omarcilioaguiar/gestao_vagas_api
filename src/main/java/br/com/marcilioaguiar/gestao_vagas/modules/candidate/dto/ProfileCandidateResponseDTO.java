package br.com.marcilioaguiar.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {
    
    @Schema(example = "Desenolvedor(a) Java")
    private String description;

    @Schema(example = "mario")
    private String username;

    @Schema(example = "mario@gmail.com")
    private String email;

    private UUID id;
    
    @Schema(example = "Maria de Souza")
    private String name;

}
