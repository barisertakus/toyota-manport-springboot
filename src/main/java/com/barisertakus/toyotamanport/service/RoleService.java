package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.entity.Role;

public interface RoleService {
    Boolean save(Role role);
    long count();
}
