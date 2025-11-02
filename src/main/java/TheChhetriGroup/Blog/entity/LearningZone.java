package TheChhetriGroup.Blog.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mongodb.lang.NonNull;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "learningZone")
@Getter
@Setter
public class LearningZone {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @NonNull
    @Indexed(unique = true)
    private String title;
    @NonNull
    private String content;
}
