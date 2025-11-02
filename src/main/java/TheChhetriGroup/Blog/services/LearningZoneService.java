package TheChhetriGroup.Blog.services;


import TheChhetriGroup.Blog.entity.LearningZone;
import TheChhetriGroup.Blog.repository.LearningZoneRepository;
import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LearningZoneService {
    @Autowired
    private LearningZoneRepository learningZoneRepository;

    public void addContent(LearningZone learningZone){
        try{
            learningZoneRepository.save(learningZone);
        }
        catch (DuplicateKeyException e){
            log.error("Duplicate key entry MongoDB exception.");
            throw new RuntimeException();
        }
    }

    public void deleteContentById(ObjectId id){
      boolean exist=learningZoneRepository.existsById(id);
      if(!exist){
          log.error("The id is wrong. Not Found in the database.");
          throw new RuntimeException();
      }
      learningZoneRepository.deleteById(id);
    }

    public List<LearningZone> getAll(){
        return learningZoneRepository.findAll();
    }
}
