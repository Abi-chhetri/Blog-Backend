package TheChhetriGroup.Blog.schedulers;

import TheChhetriGroup.Blog.entity.User;
import TheChhetriGroup.Blog.repository.UserRepositoy;
import TheChhetriGroup.Blog.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class NotifyScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoy userRepositoy;

    String body = """
                    Hello,
                    
                    We hope you’re doing well! 🌟
                    
                    We are excited to inform you that **TCG’s Blog** has just published new articles and updates for you to explore. Discover the latest insights, stories, and tech updates curated to keep you informed and inspired.
                    
                    Visit our blog now and dive into the fresh content waiting for you!
                    
                    🔗 [Visit TCG’s Blog](https://yourblogurl.com)
                    
                    Thank you for being a valued subscriber. We appreciate your support and look forward to sharing more exciting content with you.
                    
                    Warm regards,
                    **The TCG’s Blog Team**
                   """;


    String subject = "New Blogs Alert! Check Out the Latest Updates on TCG’s Blog";



    @Scheduled(cron = "0 0 9 * * *")
    public void notifyUserBySendingMail() {
        try {
            List<User> users = userRepositoy.findAll();

            for (User eachUser : users) {
                if (eachUser.isNewsUpdate()) {
                    emailService.sendMail(eachUser.getEmail(), subject, body);
                }
            }

            log.info("Email sent for notifying updates");

        } catch (Exception e) {
            log.error("Unexpected error in scheduled task: {}", e.getMessage(), e);
        }
    }

}
