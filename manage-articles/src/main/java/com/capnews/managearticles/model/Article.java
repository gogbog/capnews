package com.capnews.managearticles.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "article")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Article {
    @Id
    private String id;
    private String title;
    private String content;
    private String filePath;
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String profession;

}
