package cn.bptop.metering.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.beans.PropertyVetoException;

@Configuration
public class DBconfig
{
    @Autowired
    private Environment environment;

    @Bean(name = "datasource")
    public ComboPooledDataSource dataSource() throws PropertyVetoException
    {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
//        dataSource.setDbType(environment.getProperty("spring.datasource.type"));
//        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
//        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
//        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
//        dataSource.setInitialSize(5);
//        dataSource.setMinIdle(5);
//        dataSource.setMaxActive(20);
//        dataSource.setMaxWait(60000);
//        dataSource.setTimeBetweenEvictionRunsMillis(60000);
//        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setDriverClass(environment.getProperty("spring.datasource.driver-class-name"));
        dataSource.setJdbcUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUser(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        dataSource.setMaxPoolSize(20);
        dataSource.setMinPoolSize(5);
        dataSource.setInitialPoolSize(10);
        dataSource.setMaxIdleTime(300);
        dataSource.setAcquireIncrement(5);
        dataSource.setIdleConnectionTestPeriod(60);
        return dataSource;
    }
}
