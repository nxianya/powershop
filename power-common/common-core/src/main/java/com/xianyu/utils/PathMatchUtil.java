package com.xianyu.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.utils
 * @Author: xianyu
 * @CreateTime: 2023-06-18  14:44
 * @Description: 当URL路径可能携带变量或者通配符等特殊符号的复杂路径时,使用此工具类处理
 * @Version: 1.0
 */
@Component
public class PathMatchUtil {


    // 【路径通配符的处理】
    public static boolean isMatch(String[] patterns,String path){
        AntPathMatcher matcher = new AntPathMatcher();
        // 【循环判断路径是否符合通配符】
        for (String pattern : patterns) {
            if (matcher.match(pattern,path)) {
                return true;
            }
        }
        return false;
    }



}
