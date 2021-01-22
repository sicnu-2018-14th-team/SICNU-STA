package com.sicnu.sta.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class ConnectionFactoryConfig  {

    private DruidDataSource dataSource;

    private  static ConnectionFactoryConfig connectionFactoryConfig;

    private Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        // 这里需要自己进行加密
        // 本地
        String user = "root";
        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/sta?characterEncoding=utf8&useSSL=false&useAffectedRows=true&serverTimezone=CST";

         //云端
        //String user = "nonameless";
        //String password = "nonameless";
        //String url = "jdbc:mysql://112.74.79.124:3306/sta?characterEncoding=utf8&useSSL=false&useAffectedRows=true&serverTimezone=CST";
        //

        String driverClassName = "com.mysql.cj.jdbc.Driver";


        // 廖雪花
        //String user = "root";
        //String password = "jkxylxh@sicnu123456";
        //String url = "jdbc:mysql://139.9.139.191:3306/bjy_zyq?characterEncoding=utf8&useSSL=false&useAffectedRows=true&serverTimezone=CST";
        //
        //
        //String driverClassName = "com.mysql.jdbc.Driver";



        properties.put("driverClassName",driverClassName);
        properties.put("url",url);
        properties.put("username",user);
        properties.put("password",password);

        try {
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            init();
        }
        return  dataSource.getConnection();
    }

    public static Connection getDatabaseConnection() throws SQLException {
        if(connectionFactoryConfig==null){
            connectionFactoryConfig = new ConnectionFactoryConfig();
        }
        return connectionFactoryConfig.getConnection();

    }

    public void init(){
        try {
            if (dataSource != null)
                dataSource.close();
        } catch (Exception e) {
            log.error("初始化异常：", e);
        }
    }
}
