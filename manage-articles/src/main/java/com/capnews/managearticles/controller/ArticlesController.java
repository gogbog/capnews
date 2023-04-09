package com.capnews.managearticles.controller;

import com.capnews.managearticles.dto.ArticleRequest;
import com.capnews.managearticles.dto.ArticleResponse;
import com.capnews.managearticles.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticlesController {

    private final ArticleService articleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createArticle(@RequestBody ArticleRequest articleRequest) {
        articleService.createArticle(articleRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponse> getAllArticles() {
        return articleService.getAllArticles();
    }
}
