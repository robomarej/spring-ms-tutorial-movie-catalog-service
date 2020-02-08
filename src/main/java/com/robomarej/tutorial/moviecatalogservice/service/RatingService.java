package com.robomarej.tutorial.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.robomarej.tutorial.moviecatalogservice.model.Rating;
import com.robomarej.tutorial.moviecatalogservice.model.UserRating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRatings")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
        return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
    }

    private UserRating getFallbackUserRatings(String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Collections.singletonList(new Rating("0", 0)));
        return userRating;
    }
}
