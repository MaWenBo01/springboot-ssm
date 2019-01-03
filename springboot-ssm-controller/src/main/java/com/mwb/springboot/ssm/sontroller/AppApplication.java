package com.mwb.springboot.ssm.sontroller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@Slf4j
public class AppApplication  {

    public static void main(String[] args) {
        try {
            log.info("cpg_website 开始启动");
            System.setProperty("server.port", "9900");
            System.setProperty("spring.application.name", "cpg_website");
         /*   BaseApplication.main(args);*/
            SpringApplication.run(AppApplication.class, args);
            log.info("cpg_website 已经启动");
            log.info("本次启动的系统是cpg_website");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
