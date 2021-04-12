package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.Role;
import com.llb.fllbwebsite.exceptions.RoleNotFoundException;
import com.llb.fllbwebsite.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Iterable<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    public Role findRoleByName(String roleName){
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) throw new RoleNotFoundException("Role assigned don't exist");
        return role;
    }
}
