package TheChhetriGroup.Blog.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blogs")
@Getter
@Setter
public class Blog {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private boolean isUser;
    private String dateTime;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private String genre;
    private String image_url;

}
