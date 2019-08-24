package com.bee.user.mapper;

import com.bee.user.pojo.MallUser;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface MallUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallUser record);

    MallUser selectByPrimaryKey(Integer id);

    List<MallUser> selectAll();

    int updateByPrimaryKey(MallUser record);
    
    MallUser selectUserByUsername(String username);
    
    MallUser selectUserByPhone(String phone);
    
    MallUser selectLogin(@Param("username") String username,@Param("password") String password,@Param("role") Integer role);
  
    MallUser selectQuestionByPhone(String phone);
    
    int updatePassword(@Param("newPassword") String newPassword ,@Param("userId") Integer userId);
    
    int updateByPrimaryKeySelective(MallUser mallUser);
    
    int checkEmailByUserId(@Param("email") String email,@Param("userId") Integer userId);

	int updateUserRole(@Param("userId") Integer userId,@Param("role") Integer role);
}