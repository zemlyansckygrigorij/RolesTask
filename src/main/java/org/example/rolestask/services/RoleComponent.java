package org.example.rolestask.services;

import org.example.rolestask.entity.Role;
import org.example.rolestask.web.model.request.RoleRequest;
import java.util.List;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * service for work with table roles
 */
public interface RoleComponent {
    /**
     * Ищет роль по идентификатору и падает по ошибке, если не нашел.
     *
     * @param id идентификатор роль.
     * @return роль.
     */
    Role findByIdOrDie(Long id);

    /**
     * Сохраняет роль.
     *
     * @param request роль для сохранения.
     * @return сохраненный роль.
     */
    Role commit(RoleRequest request) throws Exception;

    /**
     * Находит всех роли.
     *
     * @return список ролей.
     */
    List<Role> findAll();

    /**
     * Удалить роль по идентификатору.
     *
     * @param id идентификатор роли.
     */
    void deleteRoleById(Long id);

    /**
     * Обновить роль по идентификатору.
     *
     * @param id идентификатор роли.
     * @param  request роль.
     */
    void updateRoleById(Long id, RoleRequest request);
}
