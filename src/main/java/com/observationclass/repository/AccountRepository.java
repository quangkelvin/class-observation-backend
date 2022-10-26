package com.observationclass.repository;

import com.observationclass.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndDeleteFlag(String email,Integer deleteFlag);

    List<Account> findAllByDeleteFlag(Integer deleteFlag);

    boolean existsByEmail(String email);

}