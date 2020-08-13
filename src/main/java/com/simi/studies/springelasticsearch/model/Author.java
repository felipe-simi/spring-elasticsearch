package com.simi.studies.springelasticsearch.model;

import org.springframework.data.elasticsearch.annotations.Field;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

public class Author {

  @Field(type = Text)
  private String name;

  public Author() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Author{" + "name='" + name + '\'' + '}';
  }

}
