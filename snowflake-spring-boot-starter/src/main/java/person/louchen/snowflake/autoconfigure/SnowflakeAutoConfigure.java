package person.louchen.snowflake.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import person.louchen.snowflake.SnowflakeServiceImpl;
import person.louchen.snowflake.shadow.uid.impl.DefaultUidGenerator;
import person.louchen.snowflake.shadow.uid.worker.DisposableWorkerIdAssignerService;
import person.louchen.snowflake.shadow.uid.worker.dao.WorkerNodeDAO;
import person.louchen.snowflake.shadow.uid.worker.dao.WorkerNodeDAOImpl;

/**
 * Created by louchen on 2017/5/31.
 */
@Configuration
@ConditionalOnClass(SnowflakeServiceImpl.class)
@EnableConfigurationProperties(SnowflakeProperties.class)
public class SnowflakeAutoConfigure {

    @Autowired
    private SnowflakeProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "snowflake", value = "enabled", havingValue = "true")
    SnowflakeServiceImpl snowflakeService() {
        return new SnowflakeServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "snowflake", value = "enabled", havingValue = "true")
    DefaultUidGenerator defaultUidGenerator() {
        DefaultUidGenerator defaultUidGenerator = new DefaultUidGenerator();
        defaultUidGenerator.setWorkerIdAssignerService(disposableWorkerIdAssignerService());
        defaultUidGenerator.setTimeBits(properties.getTimeBits() == null ? 29 : properties.getTimeBits());
        defaultUidGenerator.setWorkerBits(properties.getWorkerBits() == null ? 21 : properties.getWorkerBits());
        defaultUidGenerator.setSeqBits(properties.getSeqBits() == null ? 13 : properties.getSeqBits());
        defaultUidGenerator.setEpochStr(properties.getEpochStr() == null ? "2016-09-20" : properties.getEpochStr());

        return defaultUidGenerator;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "snowflake", value = "enabled", havingValue = "true")
    DisposableWorkerIdAssignerService disposableWorkerIdAssignerService() {
        DisposableWorkerIdAssignerService disposableWorkerIdAssignerService = new DisposableWorkerIdAssignerService();

        return disposableWorkerIdAssignerService;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "snowflake", value = "enabled", havingValue = "true")
    WorkerNodeDAO workerNodeDAO() {
        WorkerNodeDAO workerNodeDAO = new WorkerNodeDAOImpl();

        return workerNodeDAO;
    }

}
