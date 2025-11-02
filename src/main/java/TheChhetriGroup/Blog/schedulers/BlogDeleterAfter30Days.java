package TheChhetriGroup.Blog.schedulers;


import TheChhetriGroup.Blog.entity.Blog;
import TheChhetriGroup.Blog.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@Slf4j
public class BlogDeleterAfter30Days {

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Scheduled(cron = "0 0 16 * * *")
    public void deleteOldBlogs(){
        try{
            List<Blog> all = blogRepository.findAll();
            for(Blog each:all){
               if(!each.isUser()){
                   LocalDate blogDate=LocalDate.parse(each.getDateTime());
                   LocalDate todayDate=LocalDate.now();
                   long daysBetween= ChronoUnit.DAYS.between(blogDate,todayDate);
                   if(daysBetween>=30){
                       blogRepository.deleteById(each.getId());
                   }
               }
            }
        } catch (Exception e) {
            log.error("There was an error while deleting the blog with its id ",e);
            throw new RuntimeException(e);
        }
    }
}
