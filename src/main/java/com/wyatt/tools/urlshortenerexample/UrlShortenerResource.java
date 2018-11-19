package com.wyatt.tools.urlshortenerexample;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RequestMapping("/rest/url")
@RestController
public class UrlShortenerResource {

    StringRedisTemplate template;

    @GetMapping("/{id}")
    public String getUrl(@PathVariable String id){

        String url = template.opsForValue().get(id);

        return url;
    }

    @PostMapping
    public String create(@RequestBody String url){

        UrlValidator validator = new UrlValidator(new String[] { "http", "https"});

        if (validator.is    Valid(url)){

            String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();

            template.opsForValue().set(id, url);

            return id;
        }

        throw new RuntimeException("URL Invalid: " + url);
    }

}
