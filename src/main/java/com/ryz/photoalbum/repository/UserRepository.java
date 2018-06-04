package com.ryz.photoalbum.repository;

import com.ryz.photoalbum.pojo.PUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name="p_user")
public interface UserRepository extends CrudRepository<PUser,String> {


    PUser findPUserByNameAndPassword(String name,String password);

    PUser findPUserById(String id);


 /*
    public

    @Override
    List<PUser> findAll();

    PUser findByName(String name);

    int deleteById(String id);

    @Query("select u.name from User u where u.id= ?1")
    String findName(String id);*/
}
