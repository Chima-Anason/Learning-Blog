package com.llb.fllbwebsite.controllers;


import com.llb.fllbwebsite.domain.Role;
import com.llb.fllbwebsite.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    // Get all roles  [ @route: /api/roles/all  @access: private]
    @GetMapping("/all")
    public ResponseEntity<Iterable<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.findAllRoles(), HttpStatus.OK);
    }
}
