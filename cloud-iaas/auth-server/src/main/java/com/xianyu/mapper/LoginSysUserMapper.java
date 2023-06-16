package com.xianyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xianyu.domain.LoginSysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface LoginSysUserMapper extends BaseMapper<LoginSysUser> {

    // 【根据用户Id获取用户所拥有的权限perms】
    Set<String> selectPermsByUserId(@Param("userId") Long userId);

}
