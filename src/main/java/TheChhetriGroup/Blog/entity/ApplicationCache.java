package TheChhetriGroup.Blog.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "app_cache")
@Getter
@Setter
public class ApplicationCache {
    private String key;
    private String value;
}
