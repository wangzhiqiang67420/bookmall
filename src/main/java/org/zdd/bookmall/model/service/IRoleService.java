package org.zdd.bookmall.model.service;

import org.zdd.bookmall.model.entity.Role;
import org.zdd.bookmall.common.pojo.BSResult;

import java.util.List;

public interface IRoleService {
    List<Role> findAllRoles();

    BSResult updateUserRoleRelationship(Integer userId, int[] roleIds);

    Role findById(int roleId);

    BSResult deleteById(int roleId);

    BSResult addRole(Role role);

    BSResult updateRole(Role role);

    BSResult updateRolesPrivilege(int[] ids, int roleId);
}

