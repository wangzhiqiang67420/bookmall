package org.zdd.bookmall.model.service;

import org.zdd.bookmall.common.pojo.ZTreeNode;
import org.zdd.bookmall.model.entity.Privilege;
import org.zdd.bookmall.common.pojo.BSResult;

import java.util.List;

public interface IPrivilegeService {


    List<ZTreeNode> getZTreeNodes();

    BSResult findById(int privId);

    BSResult updatePrivilege(Privilege privilege);

    BSResult addPrivilege(Privilege privilege);

    BSResult deleteById(int privId);

    List<ZTreeNode> getRolePrivileges(int roleId);
}
