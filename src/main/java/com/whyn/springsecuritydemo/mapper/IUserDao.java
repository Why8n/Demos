package com.whyn.springsecuritydemo.mapper;

import com.whyn.springsecuritydemo.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IUserDao {
    @Insert("insert into tb_user values( #{ id }, #{ name }, #{ password }, #{ role }, #{ authority })")
    int insert(User user);
    @Select("select * from tb_user where id = #{id}")
    User selectOneByPrimaryKey(Long id);
    @Select("select * from tb_user where name=#{ name }")
    User selectOneByName(String name);
}
