package com.capnews.managearticles.service;

import com.capnews.managearticles.dto.ArticleRequest;
import com.capnews.managearticles.dto.ArticleResponse;
import com.capnews.managearticles.model.Article;
import com.capnews.managearticles.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {


    private final ArticleRepository articleRepository;

    public void createArticle(ArticleRequest articleRequest) {
        Article article = Article.builder()
                .title(articleRequest.getTitle())
                .content(articleRequest.getContent())
                .build();

        articleRepository.save(article);
        log.info("Product {} is saved", article.getId());

    }

    public List<ArticleResponse> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::mapToArticleResponse).toList();
    }

    private ArticleResponse mapToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .build();
    }
}
