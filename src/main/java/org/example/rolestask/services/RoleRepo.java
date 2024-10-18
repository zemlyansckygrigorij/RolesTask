package org.example.rolestask.services;

import jakarta.transaction.Transactional;
import org.example.rolestask.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * interface RoleRepo
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    @Modifying
    @Transactional // @Modifying annotation should be wrapped up with @Transactional
    @Query(  value = "update roles  set name = ?1 , description = ?2  where id = ?3",
            nativeQuery = true)
    int updateRoleById(String name,
                       String description,
                       Long id);

    @Query(value
            = "select count(*) "
            + "from roles r "
            + "where r.name = :name ",
            nativeQuery = true)
    int checkRoleExistByName(@Param("name") String name);
}
