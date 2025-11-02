package TheChhetriGroup.Blog.controller;

import TheChhetriGroup.Blog.entity.User;
import TheChhetriGroup.Blog.services.EmailService;
import TheChhetriGroup.Blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final List<String> GENRES = Arrays.asList("AI", "Cloud Computing", "Cybersecurity", "DevOps", "Java", "Python", "Go", "Rust", "Web Development", "Mobile Development", "Data Science", "Machine Learning", "Gadgets", "Networking", "Hardware", "VR", "AR", "IoT", "Robotics", "Blockchain", "Open Source", "SaaS", "Public Safety", "Surveillance", "Energy", "Brain-Computer Interface", "Healthcare", "Quantum Computing", "Edge Computing", "Automation Tools", "Productivity Tools", "Collaboration Platforms", "Gaming Tech", "Audio/Video Tech", "Fintech", "Education Tech", "Business", "Business Idea and Tips", "Physics" , "Chemistry", "Biology", "History", "Law", "Astrology", "Politics", "Evolution of Human");

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody User updateUser){
        String userName=SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.updateUserPassword(userName,updateUser);
    }

    @PutMapping("/updateUserName")
    public ResponseEntity<String> updateUserName(@RequestBody User updateUser){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.updateUserUserName(userName,updateUser);
    }


    @GetMapping("/getGenre")
    public List<String> getGenre(){
        return GENRES;
    }

    @GetMapping("/getUserDetails")
    public ResponseEntity<?> getUserDetails(){
        String userName=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userService.findUserByUserName(userName);

        if(user != null){
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("userName", userName);
            userDetails.put("email", user.getEmail());
            userDetails.put("userCreatedDate", user.getUserCreatedDate());
            userDetails.put("userBlog", user.getUserBlog());
            userDetails.put("newsUpdate", user.isNewsUpdate());
            System.out.println(user.getUserCreatedDate());
            return new ResponseEntity<>(userDetails, HttpStatus.OK); // Use OK (200) not FOUND (302)
        }
        return new ResponseEntity<>(Arrays.asList("Not Found"),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/chooseGenre")
    public ResponseEntity<String> chooseGenre(@RequestBody List<String> userGenreChoice){
        String userName=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userService.findUserByUserName(userName);
        if(user!=null){
            userService.saveUserChoiceGenre(userGenreChoice,user);
            return new ResponseEntity<>("Thank you for providing the topics of blog you are intrested in.",HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("User not found",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/newsUpdate")
    public ResponseEntity<String> newsUpdate(){
        String userName=SecurityContextHolder.getContext().getAuthentication().getName();
        User userByUserName = userService.findUserByUserName(userName);
        if(userByUserName != null){
            if(userByUserName.isNewsUpdate()){
                userByUserName.setNewsUpdate(false);
                userService.saveUser(userByUserName);
                emailService.sendMail(userByUserName.getEmail(),"Thank You for Subscribing!","Thank you for subscribing to TCG’s Blog — we’re thrilled to have you on board! You’ll now receive the latest blog updates, stories, and insights straight to your inbox.");
                return new ResponseEntity<>("UnSubscribed",HttpStatus.OK);
            }
            else {
                userByUserName.setNewsUpdate(true);
                userService.saveUser(userByUserName);
                emailService.sendMail(userByUserName.getEmail(), "Unsubscribed Newsupdate of TCG's blogs.", "You have successfully unsubscribed from TCG’s Blog. We’re sorry to see you go — you will no longer receive our news updates or stories. If you change your mind, you can resubscribe anytime through our website.");
                return new ResponseEntity<>("Subscribed", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }


    @DeleteMapping
    public ResponseEntity<String> deleteUser(){
        String userName=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userService.findUserByUserName(userName);
        if(user!=null){
            userService.deleteUserByUserName(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>("User doesn't exist",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        String userName=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userService.findUserByUserName(userName);
        final String UPLOAD_DIR = "uploads/"; // folder to store files
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        // Create upload directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Save the file with a unique name
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the URL for the frontend
        String imageUrl = "/uploads/" + filename;
        user.setProfilePicture(imageUrl);
        userService.saveUser(user);
        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
    }
}
