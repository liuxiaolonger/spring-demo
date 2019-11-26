package com.etoc;

import com.etoc.bean.Article;
import com.etoc.bean.Book;
import com.etoc.repository.BookRepository;
//import io.searchbox.client.JestClient;
//import io.searchbox.core.Index;
//import io.searchbox.core.Search;
//import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootElasticsearchApplicationTests {
/*	  @Autowired
      JestClient jestClient;
	@Test
	public void contextLoads() throws IOException {
		Article article = new Article();
		article.setId(110);
		article.setAuthor("刘小龙");
		article.setCreatDate(new Date());
		article.setTitle("整个世界的孤独！！！");
		Index build = new Index.Builder(article).index("emp").type("article").id("110").build();
		jestClient.execute(build);
	}
	@Test
	public void search() throws IOException {
		String json="{\n" +
				"    \"query\": {\n" +
				"        \"match\": {\n" +
				"            \"id\": \"110\"\n" +
				"        }\n" +
				"    }\n" +
				"}";
		Search search = new Search.Builder(json).addIndex("emp").addType("article").build();
		SearchResult execute = jestClient.execute(search);
		System.out.println(execute.getJsonObject());
	}*/
     @Autowired
     BookRepository bookRepository;
	@Test
	public void search(){
//		Book book=new Book();
//		book.setId("110");
//		book.setAuthor("刘小龙");
//		book.setCreatDate(new Date());
//		book.setTitle("整个世界的孤独！！！");
//		book.setAge(1000);
//		bookRepository.index(book);
		List<Book> list = bookRepository.findByTitle("世界");
		System.out.println(list);

	}
}
