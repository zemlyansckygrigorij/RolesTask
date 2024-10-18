package org.example.rolestask.services;

import lombok.RequiredArgsConstructor;
import org.example.rolestask.entity.Role;
import org.example.rolestask.web.model.request.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RoleComponentImpl implements RoleComponent{
    @Autowired
    RoleRepo repo;

    private Optional<Role> findById(Long id) {
        return this.repo.findById(id);
    }

    @Override
    public Role findByIdOrDie(Long id) {
        return findById(id)
                    .orElseThrow(() -> new RuntimeException("role with this id not found !"));
    }

    @Override
    public Role commit(RoleRequest request)  {
        if(repo.checkRoleExistByName(request.getName())>0) throw new RuntimeException("user with such name is exist");
        Role role = new Role();
        role.setDescription(request.getDescription());
        role.setName(request.getName());
        return this.repo.save(role);
    }

    @Override
    public List<Role> findAll() {
        return this.repo.findAll();
    }

    @Override
    public void deleteRoleById(Long id) {
        this.repo.deleteById(id);
    }

    @Override
    public void updateRoleById(Long id, RoleRequest request) {
        this.repo.updateRoleById(
                request.getName(),
                request.getDescription(),
                id);
    }
}
