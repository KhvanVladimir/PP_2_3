package pp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pp.dao.UserDao;
import pp.dao.UserDaoImp;
import pp.service.UserServiceImp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan(value = "pp")

public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public PlatformTransactionManager getJpaTransactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setDataSource(getDataSource());
        tm.setEntityManagerFactory(getLocalContainerEntityManagerFactoryBean().getObject());
        return tm;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("pp");
        em.setDataSource(getDataSource());

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        ((HibernateJpaVendorAdapter)vendorAdapter).setGenerateDdl(true);
        ((HibernateJpaVendorAdapter)vendorAdapter).setShowSql(true);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(getPropertiesJPA());
        return em;
    }
    @Bean
    public Properties getPropertiesJPA(){
        Properties jpaProperties=new Properties();
        jpaProperties.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        jpaProperties.put("hibernate.show_sql",true);
        jpaProperties.put("hibernate.format_sql","false");
        jpaProperties.put("hibernate.hbm2ddl.auto","update");
        return jpaProperties;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(getDataSource());
        return initializer;
    }



    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }



}
