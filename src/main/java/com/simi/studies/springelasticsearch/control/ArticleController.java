package com.simi.studies.springelasticsearch.control;

import com.simi.studies.springelasticsearch.database.ArticleRepository;
import com.simi.studies.springelasticsearch.model.Article;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

  private final ArticleRepository repository;

  public ArticleController(final ArticleRepository repository) {
    this.repository = repository;
  }

  @PostMapping("/articles")
  public Article create(@RequestBody final Article article) {
    return repository.save(article);
  }

}
