package com.simi.studies.springelasticsearch.database;

import com.simi.studies.springelasticsearch.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

  Page<Article> findByTitle(String authorName, Pageable pageable);

}
