package com.xianyu.utils;


import org.jcp.xml.dsig.internal.dom.Policy;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.utils
 * @Author: xianyu
 * @CreateTime: 2023-06-23  14:34
 * @Description: 线程工具类
 * @Version: 1.0
 */
public class ThreadPoolUtil {
    public static ThreadPoolExecutor getThreadPool(){
        return new ThreadPoolExecutor(
                4,Runtime.getRuntime().availableProcessors()*2+1,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

}
