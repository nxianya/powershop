package com.xianyu.controller;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.xianyu.config.FileConfig;
import com.xianyu.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.controller
 * @Author: xianyu
 * @CreateTime: 2023-06-23  16:26
 * @Description: 文件上传控制层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/file")
@Api(tags = "文件上传")
@Slf4j
public class FileController {

    @Autowired
    private FileConfig fileConfig;

    @PostMapping("/upload/element")
    @ApiOperation("文件上传")
    public Result<String> uploadFile(MultipartFile file){
        // 【指定带有Region对象配置类,选择华南区】
        Configuration config = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(config);
        // 【读取文件上传配置类中的配置】
        String accessKey = fileConfig.getAccessKey();
        String secretKey = fileConfig.getSecretKey();
        String bucket = fileConfig.getBucket();
        String domainName = fileConfig.getDomainName();
        // 【生成文件名】
        String key=null;
        // 【身份验证】
        Auth auth = Auth.create(accessKey,secretKey);
        String upToken = auth.uploadToken(bucket);
        // 【文件上传】
        try {
            Response response = uploadManager.put(file.getInputStream(), key, upToken, null, null);
            // 【解析上传结果,将返回字符串(图片路径)转换为对象传至前端页面】
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            log.debug("putRet.key:{}\nputRet.hash:{}",putRet.key,putRet.hash);
            // 【定义完整的文件名(域名+hash值)】
            String result = domainName + "/" + putRet.hash;
            log.debug("result:{}",result);
            return Result.success("上传成功~",result);
        } catch (Exception e) {
            // 【打印错误日志,返回错误信息】
            log.error(e.getMessage());
            return Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        }
    }
}
