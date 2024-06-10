package com.fdu.mockinterview.mapper;


import com.fdu.mockinterview.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {


    @Select("SELECT * FROM user")
    List<User> findAll();

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Integer id);

    @Select("SELECT * FROM user WHERE user_name = #{userName, jdbcType=VARCHAR}")
    User findByUserName(String userName);

    @Select("SELECT COUNT(*) FROM user WHERE user_name = #{userName, jdbcType=VARCHAR}")
    Integer countByUserName(String userName);


    @Insert("INSERT INTO user(user_name, passwd, email, first_name, last_name) VALUES(#{userName}, #{passwd}, #{email}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE user SET user_name=#{userName}, passwd=#{passwd}, email=#{email} WHERE id=#{id}")
    void updateUser(User user);


    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(Integer id);
}