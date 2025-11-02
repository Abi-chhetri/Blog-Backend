package TheChhetriGroup.Blog.repository;

import TheChhetriGroup.Blog.entity.Blog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogRepository extends MongoRepository<Blog, ObjectId> {
    @Override
    void deleteById(ObjectId objectId);
}
