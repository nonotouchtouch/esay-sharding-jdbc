package com.hanming.easy.sharding.common;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取dataSource
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class DataSourcesUtil {

    public static DataSource getDataSource(String fileName) throws IOException {
        //读取数据库配置信息文件
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        Properties properties = new Properties();
        properties.load(inputStream);


        //获取配置信息
        String driverClassName = (String) properties.get("driverClassName");
        String url = (String) properties.get("url");
        String user = (String) properties.get("user");
        String password = (String) properties.get("password");

        //创建数据源
        DruidDataSource druidDataSource=new DruidDataSource();
        druidDataSource.setUsername(user);
        druidDataSource.setPassword(password);
        druidDataSource.setUrl(url);
        druidDataSource.setDriverClassName(driverClassName);

        return druidDataSource;
    }
}
