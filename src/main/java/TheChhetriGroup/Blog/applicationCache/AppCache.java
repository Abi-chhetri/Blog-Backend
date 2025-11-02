package TheChhetriGroup.Blog.applicationCache;


import TheChhetriGroup.Blog.entity.ApplicationCache;
import TheChhetriGroup.Blog.repository.ApplicationCacheRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Slf4j
public class AppCache {

    @Autowired
    private ApplicationCacheRepository applicationCacheRepository;

    private Map<String,String> cachedData;

    @PostConstruct
    public void cacheInitializer(){
        cachedData=new HashMap<>();
        List<ApplicationCache> cache=applicationCacheRepository.findAll();
        for(ApplicationCache all:cache){
            cachedData.put(all.getKey(), all.getValue());
        }
    }
}
