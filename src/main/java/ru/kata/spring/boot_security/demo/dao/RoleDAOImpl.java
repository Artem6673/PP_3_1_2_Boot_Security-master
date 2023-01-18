package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDAOImpl implements RoleDAO{
    @PersistenceContext
    private EntityManager entityManager;

    public RoleDAOImpl() {
    }

    @Override
    public Set<Role> getAllRoles() {
        return entityManager.createQuery("SELECT r from Role r", Role.class).getResultList().stream().collect(Collectors.toSet());
    }

    @Override
    public Role getRoleById(long id) {

        return entityManager.find(Role.class, id);
    }
}
