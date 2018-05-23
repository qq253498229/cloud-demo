package com.example.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author wangbin
 */
@Mapper
public interface UserMapper {
  /**
   * 通过id查询用户
   *
   * @param id
   * @return
   */
  @Select("select * from t_user where id = #{id}")
  User findById(@Param("id") String id);
}
