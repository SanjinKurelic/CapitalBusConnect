package eu.sanjin.kurelic.cbc.repo.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:hibernate.properties")
@ComponentScan("eu.sanjin.kurelic.cbc.repo")
public class RepositoryConfiguration {

    private static final String ENTITY_PACKAGE = "eu.sanjin.kurelic.cbc.repo.entity";
    private final org.springframework.core.env.Environment environment;
    private ComboPooledDataSource _dataSource;
    private LocalSessionFactoryBean _sessionFactory;

    @Autowired
    public RepositoryConfiguration(org.springframework.core.env.Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        _dataSource = new ComboPooledDataSource();
        try {
            _dataSource.setDriverClass(Objects.requireNonNull(environment.getProperty(Environment.DRIVER)));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        _dataSource.setJdbcUrl(Objects.requireNonNull(environment.getProperty(Environment.URL)));
        _dataSource.setUser(Objects.requireNonNull(environment.getProperty(Environment.USER)));
        _dataSource.setPassword(Objects.requireNonNull(environment.getProperty(Environment.PASS)));
        // C3P0
        _dataSource.setInitialPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty(Environment.C3P0_MIN_SIZE))));
        _dataSource.setMinPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty(Environment.C3P0_MIN_SIZE))));
        _dataSource.setMaxPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty(Environment.C3P0_MAX_SIZE))));
        _dataSource.setMaxIdleTime(Integer.parseInt(Objects.requireNonNull(environment.getProperty(Environment.C3P0_TIMEOUT))));
        _dataSource.setMaxStatements(Integer.parseInt(Objects.requireNonNull(environment.getProperty(Environment.C3P0_MAX_STATEMENTS))));
        return _dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        _sessionFactory = new LocalSessionFactoryBean();
        _sessionFactory.setPackagesToScan(ENTITY_PACKAGE);
        _sessionFactory.setDataSource(dataSource());
        _sessionFactory.setHibernateProperties(hibernateProperties());
        return _sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty(Environment.SHOW_SQL, Objects.requireNonNull(environment.getProperty(Environment.SHOW_SQL)));
        properties.setProperty(Environment.DIALECT, Objects.requireNonNull(environment.getProperty(Environment.DIALECT)));
        properties.setProperty(Environment.JDBC_TIME_ZONE, Objects.requireNonNull(environment.getProperty(Environment.JDBC_TIME_ZONE)));
        return properties;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory().getObject());
    }

    @PreDestroy
    public void closeDatasource() {
        if (!Objects.isNull(_dataSource)) {
            _dataSource.close();
        }
        if (!Objects.isNull(_sessionFactory)) {
            _sessionFactory.destroy();
        }
    }

}
