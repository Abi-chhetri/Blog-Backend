package TheChhetriGroup.Blog.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
public class User {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String profilePicture;
    @NonNull
    @Indexed(unique = true)
    private String userName;
    @NonNull
    private String password;
    @NonNull
    private String email;
    private List<String> roles;
    private boolean newsUpdate;
    private List<String> userBlogGenreChoices;
    @DBRef
    private List<Blog> userBlog=new ArrayList<>();
    private String userCreatedDate;
}
