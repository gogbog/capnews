package com.capnews.managearticles.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {
    private String id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private MultipartFile file;

}
