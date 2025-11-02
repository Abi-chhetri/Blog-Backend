package TheChhetriGroup.Blog.repository;

import TheChhetriGroup.Blog.entity.ApplicationCache;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationCacheRepository extends MongoRepository<ApplicationCache,Object> {
}
