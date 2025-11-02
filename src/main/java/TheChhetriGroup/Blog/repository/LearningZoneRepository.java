package TheChhetriGroup.Blog.repository;

import TheChhetriGroup.Blog.entity.LearningZone;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LearningZoneRepository extends MongoRepository<LearningZone, ObjectId> {
}
