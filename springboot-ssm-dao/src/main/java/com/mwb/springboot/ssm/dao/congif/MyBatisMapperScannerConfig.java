package com.mwb.springboot.ssm.dao.congif;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * MyBatis扫描接口，使用的tk.mybatis.spring.mapper.MapperScannerConfigurer，如果你不使用通用Mapper，可以改为org.xxx...
 */
// 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@Configuration
@Slf4j
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurerCpgMaster() {
        return this.mapperScannerConfigurer("sqlSessionFactoryCpgMaster");
    }

 /*   @Bean
    public MapperScannerConfigurer mapperScannerConfigurerCpgSlave() {
        return this.mapperScannerConfigurer("sqlSessionFactoryCpgSlave");
    }*/

    private MapperScannerConfigurer mapperScannerConfigurer(String sqlSessionFactoryName) {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(sqlSessionFactoryName);
        mapperScannerConfigurer.setBasePackage(
                "com.mwb.springboot.ssm.dao.mapper"
                /*"com.wanda.upp.cpg.website.dao.mapper.account,"
            + "com.wanda.upp.cpg.website.dao.mapper.bankcard,"
            + "com.wanda.upp.cpg.website.dao.mapper.common,"
            + "com.wanda.upp.cpg.website.dao.mapper.edge,"
            + "com.wanda.upp.cpg.website.dao.mapper.op.workflow"*/
                );  //mapper 类
        Properties properties = new Properties();
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }


}
