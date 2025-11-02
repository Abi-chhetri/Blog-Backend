package TheChhetriGroup.Blog.services;

import TheChhetriGroup.Blog.Utils.JwtUtil;
import TheChhetriGroup.Blog.entity.Blog;
import TheChhetriGroup.Blog.entity.User;
import TheChhetriGroup.Blog.repository.BlogRepository;
import TheChhetriGroup.Blog.repository.UserRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepositoy userRepositoy;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveNewUser(User user){
        String date=LocalDate.now().toString();
        if( user.getUserCreatedDate() == null || user.getUserCreatedDate().isEmpty() ){
            user.setUserCreatedDate(date);
        }
        else{
            user.setUserCreatedDate(user.getUserCreatedDate());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        if(user.isNewsUpdate()){
            user.setNewsUpdate(true);
        }
        else{
            user.setNewsUpdate(false);
        }
        userRepositoy.save(user);
    }

    public void saveNewAdmin(User user){
        String date=LocalDate.now().toString();
        if(user.getUserCreatedDate()==null){
            user.setUserCreatedDate(date);
        }
        else{
            user.setUserCreatedDate(user.getUserCreatedDate());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepositoy.save(user);
    }


    public void saveUser(User user){
        userRepositoy.save(user);
    }


    public User findUserByUserName(String userName){
        return userRepositoy.findByUserName(userName);
    }

    public ResponseEntity<String> updateUserPassword(String name,User newCredential){
        User oldUser=findUserByUserName(name);
        if(oldUser!=null){
            if(!passwordEncoder.matches(newCredential.getPassword(),oldUser.getPassword())){
                oldUser.setPassword(newCredential.getPassword());
                saveNewUser(oldUser);
                emailService.sendPasswordChangeMail(oldUser.getEmail(), "Password Changed", "Hello " + newCredential.getUserName() + ",\n\n your password for TCG's Blogs has been successfully changed. If this wasn't you, please reset your password immediately or contact our support team for assistance. – The TCG's Blogs Team");
                return new ResponseEntity<>("Password has been successfully updated.", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Please use different password. This is you current password", HttpStatus.CONFLICT);
            }
        }
        else{
            return new ResponseEntity<>("Username is incorrect",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> updateUserUserName(String userName,User newCredential){
        User oldUser=findUserByUserName(userName);
        if(oldUser!=null){
            if(!oldUser.getUserName().equals(newCredential.getUserName())){
                oldUser.setUserName(newCredential.getUserName());
                saveUser(oldUser);
                emailService.sendPasswordChangeMail(oldUser.getEmail(), "Username Changed", "Hello " + oldUser.getUserName() + ", \n\n your Username for TCG's Blogs has been successfully changed to "+newCredential.getUserName()+". If this wasn't you, please reset your password immediately or contact our support team for assistance. – The TCG's Blogs Team");
                return new ResponseEntity<>("Username has been successfully changed to "+newCredential.getUserName(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Please use different username. This is you current username", HttpStatus.CONFLICT);
            }
        }
        else{
            return new ResponseEntity<>("Username or password is incorrect",HttpStatus.NOT_FOUND);
        }
    }

    public void deleteUserByUserName(User user){
        List<Blog> userBlog = user.getUserBlog();
        for(Blog eachBlog: userBlog){
            blogRepository.deleteById(eachBlog.getId());
        }
        userRepositoy.delete(user);
    }

    public void saveUserChoiceGenre(List<String> genre,User user){
        user.setUserBlogGenreChoices(genre);
        saveUser(user);
    }
}
