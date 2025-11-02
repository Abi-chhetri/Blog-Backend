package TheChhetriGroup.Blog.schedulers;

import TheChhetriGroup.Blog.services.BlogByAIApiCallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

@Component
@Slf4j
public class BlogFetcher {

    @Autowired
    private BlogByAIApiCallService byAIApiCallService;

    @Scheduled(cron = "0 0 13,19 * * *")
    public void fetchBlogs(){
        try {
            byAIApiCallService.blogFetch("AI, Cloud Computing, Cybersecurity, DevOps, Java, Python, Go");// your method that calls the API
        } catch (HttpServerErrorException e) {
            log.error("API server error: {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 5 13,19 * * *")
    public void fetchBlogs1(){
        try {
            byAIApiCallService.blogFetch("Rust, Web Development, Mobile Development, Data Science, Machine Learning, Gadgets, Networking");// your method that calls the API
        } catch (HttpServerErrorException e) {
            log.error("API server error: {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 10 13,19 * * *")
    public void fetchBlogs2(){
        try {
            byAIApiCallService.blogFetch("Hardware, Business Idea and Tips, VR, AR, IoT, Robotics, Blockchain, Open Source, SaaS, Business");// your method that calls the API
        } catch (HttpServerErrorException e) {
            log.error("API server error: {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 15 13,19 * * *")
    public void fetchBlogs3(){
        try {
            byAIApiCallService.blogFetch("Public Safety, Surveillance, Energy, Brain-Computer Interface, Healthcare, Quantum Computing, Edge Computing, Automation Tools");// your method that calls the API
        } catch (HttpServerErrorException e) {
            log.error("API server error: {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 20 13,19 * * *")
    public void fetchBlogs4(){
        try {
            byAIApiCallService.blogFetch("Productivity Tools, Collaboration Platforms, Gaming Tech, Audio/Video Tech, Fintech, Education Tech");// your method that calls the API
        } catch (HttpServerErrorException e) {
            log.error("API server error: {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
        }
    }


    @Scheduled(cron = "0 25 13,19 * * *")
    public void fetchBlogs5(){
        try {
            byAIApiCallService.blogFetch("Physics , Chemistry, Biology, History, Law, Astrology, Politics, Evolution of Human");//method that calls the API
        } catch (HttpServerErrorException e) {
            log.error("API server error: {}", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
        }
    }

}
