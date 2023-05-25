package com.capnews.managearticles.repository;

import com.capnews.managearticles.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ArticleRepository extends MongoRepository<Article, String> {

     List<Article> findByUserId(String userId);
}
