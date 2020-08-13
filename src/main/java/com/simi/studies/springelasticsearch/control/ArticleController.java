package com.simi.studies.springelasticsearch.control;

import com.simi.studies.springelasticsearch.database.ArticleRepository;
import com.simi.studies.springelasticsearch.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@RestController
public class ArticleController {

  private final Pageable defaultPageRequest = PageRequest.of(0, 10);
  private final ArticleRepository repository;
  private final ElasticsearchRestTemplate elasticsearchTemplate;

  public ArticleController(final ArticleRepository repository,
                           ElasticsearchRestTemplate elasticsearchTemplate) {
    this.repository = repository;
    this.elasticsearchTemplate = elasticsearchTemplate;
  }

  @PostMapping("/articles")
  public Article create(@RequestBody final Article article) {
    return repository.save(article);
  }

  @GetMapping("/articles/title")
  public Page<Article> getByTitle(@RequestParam(required = false) final String title,
                                  @RequestParam(required = false)
                                  final Optional<Pageable> pageableParam) {
    final var pageable = pageableParam.orElse(defaultPageRequest);
    return repository.findByTitle(title, pageable);
  }

  @GetMapping("/articles/native")
  public SearchHits<Article> getByTitleElasticTemplate(
      @RequestParam(required = false) final String title,
      @RequestParam(required = false) final Optional<Pageable> pageableParam) {
    elasticsearchTemplate.indexOps(Article.class).create();
    final var pageable = pageableParam.orElse(defaultPageRequest);
    final var titleRegex = ".*" + title + ".*";
    final var searchQuery = new NativeSearchQueryBuilder()
        .withQuery(matchQuery("title", titleRegex).minimumShouldMatch("75%"))
        .withPageable(pageable)
        .build();
    return elasticsearchTemplate
        .search(searchQuery, Article.class, IndexCoordinates.of("blog"));
  }

  @GetMapping("/articles/authors")
  public Page<Article> getByAuthorsName(@RequestParam(required = false) final String authorName,
                                        @RequestParam(required = false)
                                        final Optional<Pageable> pageableParam) {
    final var pageable = pageableParam.orElse(defaultPageRequest);
    return repository.findByAuthorsName(authorName, pageable);
  }

}
