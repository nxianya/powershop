package com.xianyu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.domain
 * @Author: xianyu
 * @CreateTime: 2023-06-16  15:12
 * @Description: 角色授权身份
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_user")
public class LoginSysUser implements Serializable, UserDetails {
    // 【用户Id】
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;
    // 【用户名】
    @TableField(value = "username")
    private String userName;
    // 【用户密码】
    @TableField(value = "`password`")
    private String password;
    // 【状态】
    @TableField(value = "`status`")
    private Byte status;
    @TableField(value = "shop_id")
    private Long shopId;


    // 【返回权限信息的方法】
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 【通过方法返回空集合而不必创建对象,减少不必要的内存开销】
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    // 【判断是否没过期】
    @Override
    public boolean isAccountNonExpired() {
        return status==1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status==1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status==1;
    }

    // 【判断账户是否可用】
    @Override
    public boolean isEnabled() {
        return status==1;
    }

    // 【用户权限集合】
    @TableField(exist = false)
    private Set<String> perms;

    // 【处理权限集合中的数据(集合中存在逗号分隔的多个权限,需处理)】
    public void setPerms(Set<String> setPerms){
        Set<String> perms = new HashSet<>();
        setPerms.stream().forEach(perm->{
            if (perm.contains(",")){
                String[] split = perm.split(",");
                for (String s : split) {
                    perms.add(s);
                }
            }else {
                perms.add(perm);
            }
        });
        this.perms=perms;
    }
}
