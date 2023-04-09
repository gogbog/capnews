package com.capnews.managearticles.repository;

import com.capnews.managearticles.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> {

}
