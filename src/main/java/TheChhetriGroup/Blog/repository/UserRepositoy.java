package TheChhetriGroup.Blog.repository;

import TheChhetriGroup.Blog.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepositoy extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);

    @Override
    void delete(User entity);
}
