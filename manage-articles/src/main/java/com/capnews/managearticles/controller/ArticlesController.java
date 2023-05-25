package com.capnews.managearticles.controller;

import com.capnews.managearticles.dto.ArticleRequest;
import com.capnews.managearticles.dto.ArticleResponse;
import com.capnews.managearticles.service.ArticleService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticlesController {

    private final ArticleService articleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createArticle(@ModelAttribute ArticleRequest articleRequest, @AuthenticationPrincipal Jwt jwt)
            throws IOException {
        String fileType = articleRequest.getFile().getContentType();

        if (!fileType.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type. Only images are allowed.");
        }

        articleService.createArticle(articleRequest, jwt);
        return ResponseEntity.status(HttpStatus.CREATED).body("GREAT!");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponse> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping(value= "/image/{imageName:.+}", name="articles.image")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        Path imagePath = Paths.get("images", imageName);
        byte[] imageBytes = Files.readAllBytes(imagePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<byte[]>(imageBytes, headers, HttpStatus.OK);
    }

}
