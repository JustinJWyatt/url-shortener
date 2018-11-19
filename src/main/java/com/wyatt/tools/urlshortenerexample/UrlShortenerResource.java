package com.wyatt.tools.urlshortenerexample;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
public class UrlShortenerResource {

    StringRedisTemplate template;

    @PostMapping("/create")
    public String create(@RequestBody String url) throws UnsupportedEncodingException {

        UrlValidator validator = new UrlValidator(new String[] { "http", "https"}, UrlValidator.ALLOW_ALL_SCHEMES);

        url = URLDecoder.decode(url, "UTF-8");

        url = url.substring(0, url.length() - 1);

        if (validator.isValid(url)){

            String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();

            return id;
        }

        throw new RuntimeException(url);
    }

}
