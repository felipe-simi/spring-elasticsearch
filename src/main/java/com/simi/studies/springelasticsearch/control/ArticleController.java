package com.simi.studies.springelasticsearch.control;

import com.simi.studies.springelasticsearch.database.ArticleRepository;
import com.simi.studies.springelasticsearch.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

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

  @GetMapping("/articles")
  public Page<Article> getByTitle(@RequestParam(required = false) final String title,
                                  @RequestParam(required = false)
                                  final Optional<Pageable> pageableParam) {
    final var pageable = pageableParam.orElse(Pageable.unpaged());
    return repository.findByTitle(title, pageable);
  }

}
