package com.capnews.managearticles.service;

import com.capnews.managearticles.controller.ArticlesController;
import com.capnews.managearticles.dto.ArticleRequest;
import com.capnews.managearticles.dto.ArticleResponse;
import com.capnews.managearticles.model.Article;
import com.capnews.managearticles.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void createArticle(ArticleRequest articleRequest, Jwt jwt) throws IOException {

        MultipartFile file = articleRequest.getFile();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uploadDir = "images/";

        // Create the images folder if it doesn't exist
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdir();
        }

        // Save the file to the images folder
        String filePath = uploadDir + fileName;

        // Save the file to the images folder
        Files.copy(file.getInputStream(), Path.of(filePath));

        // Map<String, Object> claims = jwt.getClaims();
        //
        // for (Map.Entry<String, Object> entry : claims.entrySet()) {
        // String key = entry.getKey();
        // Object value = entry.getValue();
        // System.out.println(key + ": " + value);
        // }

        String username = jwt.getClaimAsString("preferred_username");
        String name = jwt.getClaimAsString("name");
        String firstName = jwt.getClaimAsString("given_name");
        String lastName = jwt.getClaimAsString("family_name");
        String profession = jwt.getClaimAsString("profession");
        String userId = jwt.getSubject();

        // Create the article object and set the file path
        Article article = Article.builder()
                .title(articleRequest.getTitle())
                .content(articleRequest.getContent())
                .filePath(fileName)
                .userId(userId)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .profession(profession)
                .build();

        articleRepository.save(article);
        log.info("Article {} is saved", article.getId());
    }

    public List<ArticleResponse> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::mapToArticleResponse).toList();
    }

    public String getArticleImageUrl(String imageName) {
        UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodName(
                ArticlesController.class, "getImage", imageName).buildAndExpand();
        return uriComponents.toUriString();
    }

    private ArticleResponse mapToArticleResponse(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .image(getArticleImageUrl(article.getFilePath()))
                .username(article.getUsername())
                .firstName(article.getFirstName())
                .lastName(article.getLastName())
                .userId(article.getUserId())
                .profession(article.getProfession())
                .build();
    }

    public void updateFirstNameForArticlesByUserId(String userId, String firstName) {
        List<Article> articles = articleRepository.findByUserId(userId);
        articles.forEach(article -> {
            article.setFirstName(firstName);
            articleRepository.save(article);
        });
        log.info("Name updated for articles with userId: {}", userId);
    }

    public void updateLastNameForArticlesByUserId(String userId, String lastName) {
        List<Article> articles = articleRepository.findByUserId(userId);
        articles.forEach(article -> {
            article.setLastName(lastName);
            articleRepository.save(article);
        });
        log.info("Name updated for articles with userId: {}", userId);
    }

    public void deleteArticlesByUserId(String userId) {
        List<Article> articles = articleRepository.findByUserId(userId);
        articles.forEach(article -> articleRepository.delete(article));
        log.info("Articles deleted for userId: {}", userId);
    }

}
