package com.capnews.managearticles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
    private String id;
    private String title;
    private String content;
    private String image;
    private String username;
    private String firstName;
    private String lastName;
    private String userId;
    private String profession;
}
