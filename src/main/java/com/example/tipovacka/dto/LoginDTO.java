package com.example.tipovacka.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data pro přihlášení hráče")
@Data
public class LoginDTO {
    
    @Schema(description = "Email hráče", example = "jan.novak@example.com", required = true)
    private String email;
    
    @Schema(description = "Heslo hráče", example = "heslo123", required = true)
    private String heslo;
} 