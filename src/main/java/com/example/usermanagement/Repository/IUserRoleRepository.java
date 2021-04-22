package com.example.usermanagement.Repository;

import com.example.usermanagement.Domain.Model.RoleType;
import com.example.usermanagement.Domain.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoleRepository  extends JpaRepository<Role, Long> {
    Role getByRoleType(RoleType roleType);
}
