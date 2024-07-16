package code.service;

import code.entity.User;

import java.util.List;


public interface UserService {

    void addUser(User user);

    List<User> list();
}