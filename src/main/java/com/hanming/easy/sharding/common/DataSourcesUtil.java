package com.hanming.easy.sharding.common;



import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取真实dataSource
 * @author hanming.xiao
 * @date 2020-08-01
 */
public class DataSourcesUtil {

    /**
     * 真实数据源配置文件默认路径
     */
    public static String DATA_SOURCES_CONF_FILE_PATH = "dbconf/";

    /**
     * 默认配置格式
     */
    public static String FILE_SUFFIX_ = ".properties";

    /**
     * 通过文件名称获取默认目录下的数据源
     * @param fileName
     * @return
     * @throws IOException
     */
    public static DataSource getDataSourceFromDefaultPath(String fileName) throws IOException {
        return getDataSource(DATA_SOURCES_CONF_FILE_PATH+fileName+FILE_SUFFIX_);
    }


    /**
     * 通过文件路径自动获取数据源
     * @param filePath
     * @return
     * @throws IOException
     */
    public static DataSource getDataSource(String filePath) throws IOException {
        //读取数据库配置信息文件
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
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
