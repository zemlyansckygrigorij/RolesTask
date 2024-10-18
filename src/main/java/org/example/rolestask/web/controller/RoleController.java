package org.example.rolestask.web.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.rolestask.entity.Role;
import org.example.rolestask.services.RoleComponent;
import org.example.rolestask.web.model.request.RoleRequest;
import org.example.rolestask.web.model.response.RoleResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class RoleController
 * for work with /roles
 */
@RestController
@Validated
@Tag(name = "API работы с ролями",
        description = "Api work roles")
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleComponent component;

    @Operation(
            description = "Поиск роли по идентификатору",
            summary = "Retrieve role by id")
    @ApiResponse(responseCode = "200", description = "Get user by id")
    @GetMapping("/{id}")
    public RoleResponse findByIdOrDie(@PathVariable(name = "id") final Long id) {
        return new RoleResponse(component.findByIdOrDie(id));
    }

    @Operation(
            description = "Вставка роли",
            summary = "insert new role into database")
    @ApiResponse(responseCode = "200", description = "list of RoleResponse")
    @PostMapping()
    public RoleResponse commit(@RequestBody RoleRequest userRequest){
        Role role = new Role();
        try {
            role = component.commit(userRequest);
        }catch(Exception e){
            e.printStackTrace();
        }
        return  new RoleResponse(role);
    }

    @Operation(
            description = "Получение всех ролей",
            summary = "Retrieve all roles")
    @ApiResponse(responseCode = "200", description = "list of RoleResponse")
    @GetMapping()
    public List<RoleResponse> findAll(){
        return component.findAll().stream().map(RoleResponse::new).collect(Collectors.toList());
    }

    @Operation(
            description = "Удаление роли",
            summary = "delete role from database by id")
    @ApiResponse(responseCode = "200", description = "null")
    @DeleteMapping("/{id}")
    public void deleteRoleById(
            @PathVariable(name = "id") final long id) {
        try {
            component.deleteRoleById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Operation(
            description = "Обновление роли",
            summary = "update role by id")
    @ApiResponse(responseCode = "200", description = "null")
    @PutMapping("/{id}")
    public void updateRoleById(@RequestBody RoleRequest request,
                               @PathVariable(name = "id") final long id) {
        component.updateRoleById(id, request);
    }
}
