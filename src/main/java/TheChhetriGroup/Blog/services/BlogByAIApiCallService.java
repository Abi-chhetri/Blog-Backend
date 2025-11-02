package TheChhetriGroup.Blog.services;

import TheChhetriGroup.Blog.applicationCache.AppCache;
import TheChhetriGroup.Blog.configuration.RestTemplateConfig;
import TheChhetriGroup.Blog.entity.Blog;
import TheChhetriGroup.Blog.pojoForAiBlog.OpenRouterResponse;
import TheChhetriGroup.Blog.repository.BlogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Slf4j
public class BlogByAIApiCallService {

    @Autowired
    private RestTemplateConfig restTemplateConfig;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AppCache appCache;

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Transactional
    public void blogFetch(String genres) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        String body = """
                {
                  "model": "mistralai/mistral-7b-instruct",
                  "messages": [
                    {
                      "role": "system",
                      "content": "You are a strict JSON generator. Respond ONLY with valid JSON. No explanations, no greetings, no notes. Output must be a JSON array only."
                    },
                    {
                      "role": "user",
                      "content": "Generate new trending or educative blog posts topics for these genres: %s. Each blog must: (1) be at least 200 words or more and must be fully explained blog, (2) be simple and engaging full content, (3) have a catchy title, and (4) include an 'image_url' field. The 'image_url' must be a valid, direct link from a trusted site such as Unsplash (https://images.unsplash.com/...), Pexels (https://images.pexels.com/...), or Pixabay (https://cdn.pixabay.com/...). The image URL must work directly inside an <img> tag without modification and don't include image url in content. If no suitable image is found, set 'image_url' to an empty string. The output must be a JSON array of objects with exactly these four fields: genre, title, content, image_url. Nothing else."
                    }
                  ]
                }
                """.formatted(genres);



        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<OpenRouterResponse> response = restTemplateConfig.restTemplate()
                .postForEntity(
                        appCache.getCachedData().get("AI_API"), //url
                        request,
                        OpenRouterResponse.class
                );

        String rawContent = response.getBody().getChoices()[0].getMessage().getContent();
        rawContent.replaceAll("(?s)^.*?\\[", "[")    // everything before first [ -> [
                .replaceAll("\\]\\s*```?$", "]")  // remove trailing backticks or spaces
                .trim();
        int start = rawContent.indexOf('[');
        int end = rawContent.lastIndexOf(']');
        if (start >= 0 && end >= 0 && end > start) {
            String jsonArray = rawContent.substring(start, end + 1);

            ObjectMapper mapper = new ObjectMapper();
            Blog[] blogs = mapper.readValue(jsonArray, Blog[].class);

            for (Blog blog : blogs) {
                blog.setDateTime(LocalDate.now().toString());
                blogRepository.save(blog);
            }
        } else {
            log.error("Could not find JSON array in API response");
        }
    }
}