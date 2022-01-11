package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.entity.Role;
import com.barisertakus.toyotamanport.repository.RoleRepository;
import com.barisertakus.toyotamanport.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Boolean save(Role role) {
        roleRepository.save(role);
        return true;
    }

    @Override
    public long count() {
        return roleRepository.count();
    }
}
