package br.com.netdeal.userregister.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.netdeal.userregister.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}