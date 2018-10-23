package com.cegeka.services;

import com.cegeka.entities.User;
import com.cegeka.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repo;



    @Override
    public User getUser(String id) {
        return repo.findOne(id);
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public void addUser(User user) {
        repo.save(user);
    }

    @Override
    public void updateUser(User user) {

        repo.save(user);
    }

    @Override
    public void delete(String id) {
        repo.delete(id);
    }

    @Override
    public void changeStatusUser(String id) {
        User user = repo.findOne(id);
        if (user.isActive()) {
            user.setActive(false);
        } else {
            user.setActive(true);
        }
        repo.save(user);
    }
}
