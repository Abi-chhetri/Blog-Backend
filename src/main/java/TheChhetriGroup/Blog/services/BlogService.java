package TheChhetriGroup.Blog.services;

import TheChhetriGroup.Blog.entity.Blog;
import TheChhetriGroup.Blog.entity.User;
import TheChhetriGroup.Blog.repository.BlogRepository;
import TheChhetriGroup.Blog.repository.UserRepositoy;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepositoy userRepositoy;

    @Autowired
    private UserService userService;

    @Transactional
    public boolean saveBlog(Blog newBlog, String userName){
        User userExist = userRepositoy.findByUserName(userName);
        if(userExist!=null){
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            newBlog.setDateTime(formattedDateTime);
            newBlog.setUser(true);
            Blog save = blogRepository.save(newBlog);
            userExist.getUserBlog().add(save);
            userService.saveUser(userExist);
            return true;
        }
        else {
            return false;
        }
    }

    @Transactional
    public void deleteById(User user,ObjectId id){
        try{
            User userInDb=userService.findUserByUserName(user.getUserName());
            userInDb.getUserBlog().removeIf(e->e.getId().equals(id));
            blogRepository.deleteById(id);
            userService.saveUser(userInDb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBlogOfUser(Blog newBlog, Blog oldBlog){
        oldBlog.setTitle(newBlog.getTitle());
        oldBlog.setContent(newBlog.getContent());
        oldBlog.setGenre(newBlog.getGenre());
        blogRepository.save(oldBlog);
    }

    public List<Blog> getBlogsOfUser(User user){
        return user.getUserBlog().stream().toList();
    }
}
