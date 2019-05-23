package org.zdd.bookmall.model.service;

import com.github.pagehelper.PageInfo;
import org.zdd.bookmall.common.pojo.BSResult;
import org.zdd.bookmall.model.entity.BookInfo;
import org.zdd.bookmall.model.entity.User;

import java.util.List;

public interface IUserService {

    BSResult login(String username, String password);

    BSResult checkUserExistByUsername(String username);

    BSResult saveUser(User user);

    BSResult activeUser(String activeCode);

    User addUser(User user);

    BSResult updateUser(User user);

    void delUser(int userId);

    List<User> findBusinesses(int roleId);

    List<User> findAllUsers();

    PageInfo<User> findBookListByCondition(int page, int pageSize);

    User findById(int userId);

    BSResult compareAndChange(int userId, String oldPassword, String newPassword);
}
