package com.robomarej.tutorial.moviecatalogservice.controller;

import com.robomarej.tutorial.moviecatalogservice.model.CatalogItem;
import com.robomarej.tutorial.moviecatalogservice.model.Movie;
import com.robomarej.tutorial.moviecatalogservice.model.UserRating;
import com.robomarej.tutorial.moviecatalogservice.service.MovieInfoService;
import com.robomarej.tutorial.moviecatalogservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class MovieCatalogController {

    private final MovieInfoService movieService;
    private final RatingService ratingService;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        UserRating userRating = ratingService.getUserRatings(userId);
        return userRating.getRatings().stream().map(rating -> {
            Movie movie = movieService.getMovie(rating.getMovieId());
            return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        }).collect(Collectors.toList());
    }


}
