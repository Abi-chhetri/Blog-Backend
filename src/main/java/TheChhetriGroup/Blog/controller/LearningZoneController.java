package TheChhetriGroup.Blog.controller;

import TheChhetriGroup.Blog.entity.LearningZone;
import TheChhetriGroup.Blog.services.LearningZoneService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("Learning-Zone")
public class LearningZoneController {

    @Autowired
    private LearningZoneService learningZoneService;

    @PostMapping
    public ResponseEntity<String> addLearningContent(@RequestBody LearningZone learningZone){
        try{
            learningZoneService.addContent(learningZone);
            return new ResponseEntity<>("Content has been Successfully added", HttpStatus.OK);
        }
        catch (Exception e){
            log.error("something went wrong while adding content.");
            return new ResponseEntity<>("Something went wrong while adding the content.\nPlease try again changing the format of data or try again later.",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteContentById(@PathVariable ObjectId id){
        try{
            learningZoneService.deleteContentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            return new ResponseEntity<>("Something went wrong while deleting the content or maybe invalid id.",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllContent(){
        try{
            List<LearningZone> all = learningZoneService.getAll();
            if(all.isEmpty()){
                log.info("No data in database");
                return new ResponseEntity<>("There isn't any content in the database.",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(all,HttpStatus.OK);
        } catch (Exception e) {
            log.info("Something went wrong while fetching data from db");
            return new ResponseEntity<>("Something went wrong while fetching data from db",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
