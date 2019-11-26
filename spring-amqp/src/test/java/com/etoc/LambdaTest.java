package com.etoc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.etoc.bean.Book;
/**
 * java8特性
 * @author Admin
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LambdaTest {
	@Test
	public void test1() {
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
		TreeSet<Integer> ts = new TreeSet<>(com);
	}
	@Test
	public void test2() {
		List<Book> books = new ArrayList<Book>(10);
		Book b = new Book("1", "黄飞鸿", "110",12);
		Book b1 = new Book("2", "黄飞鸿", "112",13);
		Book b2 = new Book("3", "黄飞鸿", "119",14);
		Book b3 = new Book("4", "黄飞鸿", "121",15);
		Book b4 = new Book("5", "黄飞鸿", "139",16);
		books.add(b);
		books.add(b1);
		books.add(b2);
		books.add(b3);
		books.add(b4);
	}
	@Test
	public void test3() {
	int num=2;
	Runnable runnable = ()->System.out.println("bbb"+num);
	runnable.run();
	}
}
