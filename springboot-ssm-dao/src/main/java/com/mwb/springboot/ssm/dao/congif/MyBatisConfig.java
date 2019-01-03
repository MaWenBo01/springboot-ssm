package com.mwb.springboot.ssm.dao.congif;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis基础配置
 */
@Configuration
@EnableTransactionManagement
@Slf4j
public class MyBatisConfig implements TransactionManagementConfigurer {

    @Resource(name = "cpgMaster")
    private DataSource dataSourceCpgMaster;
/*    @Resource(name = "cpgSlave")
    private DataSource dataSourceCpgSlave;*/


    @Bean(name = "sqlSessionFactoryCpgMaster")
    public SqlSessionFactory sqlSessionFactoryCpgMaster(@Qualifier("cpgMaster") DataSource dataSource) throws Exception{
        return this.sqlSessionFactory(dataSource);
    }

/*
    @Bean(name = "sqlSessionFactoryCpgSlave")
    public SqlSessionFactory sqlSessionFactoryCpgSlave(@Qualifier("cpgSlave") DataSource dataSource) throws Exception{
        return this.sqlSessionFactory(dataSource);
    }
*/

    private SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        ResourcePatternResolver resolver = null;
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        try {
            bean.setDataSource(dataSource);
            bean.setTypeAliasesPackage("com.wanda.upp.cpg.message.account.message,"
                + "com.wanda.upp.cpg.message.bankcard.message,"
                + "com.wanda.upp.cpg.message.channel.message,"
                + "com.wanda.upp.cpg.message.common.message,"
                + "com.wanda.upp.cpg.message.recon.message,"
                + "com.wanda.upp.cpg.message.edge.message");

            //分页插件
            PageHelper pageHelper = new PageHelper();
            Properties properties = new Properties();
            properties.setProperty("reasonable", "true");
            properties.setProperty("supportMethodsArguments", "true");
            properties.setProperty("returnPageInfo", "check");
            properties.setProperty("params", "count=countSql");
            pageHelper.setProperties(properties);
            //添加插件
            bean.setPlugins(new Interceptor[]{pageHelper});
            //添加XML目录
            resolver = new PathMatchingResourcePatternResolver();
            bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        } catch (Exception e) {
            log.error("website creates sqlSessionFactory异常：" + e.getMessage(), e);
            throw e;
        }
        return bean.getObject();
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("cpgMaster") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);

    }

    @Bean(name = "transactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("transactionManager") DataSourceTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Bean(name = "sqlSessionCpgMaster")
    public SqlSessionTemplate sqlSessionTemplateCpgMaster(@Qualifier("sqlSessionFactoryCpgMaster") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

   /* @Bean(name = "sqlSessionCpgSlave")
    public SqlSessionTemplate sqlSessionTemplateCpgSlave(@Qualifier("sqlSessionFactoryCpgSlave") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }*/

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSourceCpgMaster);
    }
}
