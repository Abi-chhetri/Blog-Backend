package TheChhetriGroup.Blog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
public class TransactionManager {
    @Bean
    public PlatformTransactionManager transactionContext(MongoDatabaseFactory factory){
        return new MongoTransactionManager(factory);
    }
}
