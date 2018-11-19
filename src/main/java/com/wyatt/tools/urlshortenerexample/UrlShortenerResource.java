package com.wyatt.tools.urlshortenerexample;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
public class UrlShortenerResource {

    Jedis jedis = new Jedis();

    @GetMapping("/{id}")
    public String getUrl(@PathVariable String id){

        String value = jedis.get(id);

        return value;
    }


    @PostMapping("/create")
    public String create(@RequestBody String url) throws UnsupportedEncodingException {


        UrlValidator validator = new UrlValidator(new String[] { "http", "https"}, UrlValidator.ALLOW_ALL_SCHEMES);

        url = URLDecoder.decode(url, "UTF-8");

        url = url.substring(0, url.length() - 1); //removes "=" from url

        if (validator.isValid(url)){

            String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();

            jedis.set(id, url);

            return id;
        }

        throw new RuntimeException(url);
    }
}
