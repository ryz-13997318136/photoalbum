package com.ryz.photoalbum.service;

import com.ryz.photoalbum.pojo.PUser;
import com.ryz.photoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(PUser user){

        user.setId(String.valueOf(System.currentTimeMillis()));

        user.setRegisterDate(new Date());
        userRepository.save(user);
    }

    public PUser login(String name,String password) throws Exception {
        PUser user = userRepository.findPUserByNameAndPassword(name,password);
        if(user == null){
            throw new Exception("找不到用户");
        }
        return user;
    }

    public PUser findPUserById(String id){
        return userRepository.findPUserById(id);
    }

    public void saveEdit(PUser user){
        userRepository.save(user);
    }
   /* public List<PUser> findAll(){
        return userRepository.findAll();
    }
    @Transactional(rollbackFor = Exception.class)
    public void save(PUser user){
        if("".equals(user.getId())){
            user.setId(String.valueOf(System.currentTimeMillis()));
        }
        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void init(){

    }

    public PUser findUserById(String id){
        return userRepository.findUserById(id);
    }


    public String findName(String id){
        return userRepository.findName(id);
    }*/
}
