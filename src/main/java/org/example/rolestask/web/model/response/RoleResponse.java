package org.example.rolestask.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.example.rolestask.entity.Role;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class RoleResponse
 * for send response
 */
@Schema(description = "Данные роли")
@Data
@Getter
@AllArgsConstructor
public class RoleResponse {
    @Schema(description = "Идентификатор роли")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Имя роли")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Описание роли")
    @JsonProperty("description")
    private String description;

    public RoleResponse(Role user) {
        this.id = user.getId();
        this.name = user.getName();
        this.description = user.getDescription();
    }
}
