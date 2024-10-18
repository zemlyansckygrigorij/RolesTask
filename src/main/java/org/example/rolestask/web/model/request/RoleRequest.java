package org.example.rolestask.web.model.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class RoleRequest
 * for work with /roles
 */
@Schema(description = "Данные пользователя")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    @Schema(description = "Имя роли")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Описание роли")
    @JsonProperty("description")
    private String description;
}
