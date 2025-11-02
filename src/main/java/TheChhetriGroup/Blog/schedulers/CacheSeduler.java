package TheChhetriGroup.Blog.schedulers;


import TheChhetriGroup.Blog.applicationCache.AppCache;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheSeduler {

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 */10 * * * *")
    public void scheduleAppCache(){
        appCache.cacheInitializer();
    }

}
