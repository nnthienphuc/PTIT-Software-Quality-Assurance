package com.nnthienphuc.intelligentbookstoreecommercewebsite.service;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Role;
import com.nnthienphuc.intelligentbookstoreecommercewebsite.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findById(String id) {
        return roleRepository.findById(id);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByRoleName(name);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role create(Role role) throws Exception {
        if (roleRepository.existsById(role.getRoleId())) {
            throw new Exception("Role already exists");
        }

        return roleRepository.save(role);
    }

    public void update(Role role) throws Exception {
        if (roleRepository.existsById(role.getRoleId())) {
            throw new Exception("Role already exists");
        }
    }

    public void delete(Role role) throws Exception {
        roleRepository.delete(role);
    }
}
