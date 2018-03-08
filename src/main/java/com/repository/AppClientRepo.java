package com.repository;

import com.entity.AppClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppClientRepo extends CrudRepository<AppClient,Integer> {

}
