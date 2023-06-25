package com.xianyu.config;

import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.config
 * @Author: xianyu
 * @CreateTime: 2023-06-23  16:11
 * @Description: 文件上传配置类(七牛云)
 * @Version: 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
// 【实现配置文件的动态加载】
@RefreshScope
@ConfigurationProperties(prefix = "qiniuyun")
public class FileConfig {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domainName;
}
