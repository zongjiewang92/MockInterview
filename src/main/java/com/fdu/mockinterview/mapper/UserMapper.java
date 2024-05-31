package com.fdu.mockinterview.mapper;


import com.fdu.mockinterview.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

//    @Results({
//            @Result(property = "userId", column = "USER_ID"),
//            @Result(property = "username", column = "USERNAME"),
//            @Result(property = "password", column = "PASSWORD"),
//            @Result(property = "mobileNum", column = "PHONE_NUM")
//    })
//    @Select("select * from user")
//    List<User> list();
//
    @Select("SELECT * FROM user")
    List<User> findAll();
//
//    @Select("SELECT * FROM user WHERE id = #{id}")
//    User findById(Long id);
//
//    @Insert("INSERT INTO user(name, email) VALUES(#{name}, #{email})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    int insert(User user);
//
//    @Delete("DELETE FROM user WHERE id = #{id}")
//    int deleteById(Long id);
}