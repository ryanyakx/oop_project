package com.g1t6.backend.Constant;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface constantRepo extends CrudRepository<constant , Integer> {
    
}
