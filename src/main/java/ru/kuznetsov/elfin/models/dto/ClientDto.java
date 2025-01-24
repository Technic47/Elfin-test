package ru.kuznetsov.elfin.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные о клиенте")
public class ClientDto {

    @Schema(description = "ИНН")
    @NotBlank(message = "ИНН обязателен")
    private String inn;

    @Schema(description = "Регион")
    @NotBlank(message = "Регион обязателен")
    private String region;

    @Schema(description = "Капитал")
    @NotBlank(message = "Капитал обязателен")
    private String capital;
}
