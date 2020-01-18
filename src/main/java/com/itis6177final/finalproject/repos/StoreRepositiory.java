package com.itis6177final.finalproject.repos;

import com.itis6177final.finalproject.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepositiory extends JpaRepository<Store, Integer> {

}
