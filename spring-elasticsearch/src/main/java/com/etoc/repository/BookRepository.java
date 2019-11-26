package com.etoc.repository;

import com.etoc.bean.Book;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Admin on 2019/4/21.
 */

@Component
public interface BookRepository extends ElasticsearchRepository<Book,String> {
    public List<Book>   findByTitle(String title);

    <S extends Book> S index(S var1);

    Iterable<Book> search(QueryBuilder var1);

    Page<Book> search(QueryBuilder var1, Pageable var2);

    Page<Book> search(SearchQuery var1);

    Page<Book> searchSimilar(Book var1, String[] var2, Pageable var3);

    void refresh();

    Class<Book> getEntityClass();
}
