package TheChhetriGroup.Blog.controller;


import TheChhetriGroup.Blog.entity.Blog;
import TheChhetriGroup.Blog.entity.User;
import TheChhetriGroup.Blog.repository.BlogRepository;
import TheChhetriGroup.Blog.services.BlogService;
import TheChhetriGroup.Blog.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogRepository blogRepository;


    @PostMapping
    public ResponseEntity<String> createBlog(@RequestBody Blog blog){
        SecurityContext context= SecurityContextHolder.getContext();
        String userName=context.getAuthentication().getName();
        boolean userFound = blogService.saveBlog(blog, userName);
        if(userFound){
            return new ResponseEntity<>("Blog has successfully been posted by "+userName, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("User not found",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteBlogById(@PathVariable ObjectId id){
        SecurityContext context=SecurityContextHolder.getContext();
        String userName=context.getAuthentication().getName();
        User user = userService.findUserByUserName(userName);
        List<Blog> blogs=user.getUserBlog().stream().filter(e->e.getId().equals(id)).toList();

        if(blogs.isEmpty()){
            return new ResponseEntity<>("Blog not found",HttpStatus.NOT_FOUND);
        }
        else{
            blogService.deleteById(user,id);
            return new ResponseEntity<>("Blog has been deleted successfully.",HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<String> updateBlog(@PathVariable ObjectId id,@RequestBody Blog blog){
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userService.findUserByUserName(userName);
        if(user!=null){
           List<Blog> listOfBlog = user.getUserBlog().stream().filter(e -> e.getId().equals(id)).toList();
          if(!listOfBlog.isEmpty()){
               blogService.updateBlogOfUser(blog,listOfBlog.get(0));
               return new ResponseEntity<>("Blog has been successfully updated",HttpStatus.ACCEPTED);
          }
          else{
              return new ResponseEntity<>("Blog not found. The given id of blog isn't of "+userName+". Please try again with correct blog id.",HttpStatus.NOT_FOUND);
          }

        }
        else{
            return new ResponseEntity<>("Something went wrong while updating.",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getBlogs(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUserName(userName);
        List<Blog> blogsOfUser = blogService.getBlogsOfUser(user);
        if (!blogsOfUser.isEmpty()) {
            return new ResponseEntity<>(blogsOfUser, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(userName + " has not posted the blog yet", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBlogs(){
        System.out.println("✅ Controller method reached - getAllBlogs()");
        List<Blog> blogs=blogRepository.findAll();
        if( blogs!=null && !blogs.isEmpty()){
            return new ResponseEntity<>(blogs,HttpStatus.FOUND);
        }
        else{
            return new ResponseEntity<>("There is no blogs yet.", HttpStatus.NOT_FOUND);
        }
    }
}
