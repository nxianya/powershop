package com.xianyu.vo;

import com.xianyu.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.vo
 * @Author: xianyu
 * @CreateTime: 2023-06-20  10:23
 * @Description: 菜单及权限,此类定义哪些角色登录之后能看到哪些内容
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜单和权限VO")
public class MenuAndAuth {
    @ApiModelProperty("权限集合")
    private Set<String> authorities;
    @ApiModelProperty("菜单集合")
    private List<SysMenu> menuList;
}
