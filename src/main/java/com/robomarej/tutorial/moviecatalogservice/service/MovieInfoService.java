package com.robomarej.tutorial.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.robomarej.tutorial.moviecatalogservice.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MovieInfoService {

    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public Movie getMovie(String movieId) {
        return restTemplate.getForObject("http://movie-info-service/movies/" + movieId, Movie.class);

    }

    private Movie getFallbackCatalogItem(String movieId) {
        return new Movie(movieId, "Movie name not found", "");
    }
}
