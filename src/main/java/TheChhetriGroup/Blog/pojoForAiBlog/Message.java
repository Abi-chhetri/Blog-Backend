package TheChhetriGroup.Blog.pojoForAiBlog;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Message {
    private String role;
    private String content;
}
