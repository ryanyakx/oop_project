package com.g1t6.backend.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends CrudRepository<user , Integer>{

}