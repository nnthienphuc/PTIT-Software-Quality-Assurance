package com.nnthienphuc.intelligentbookstoreecommercewebsite.repository;

import com.nnthienphuc.intelligentbookstoreecommercewebsite.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByRoleName(String Rolename);
}
