package com.etoc.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
/**
 * java8特性
 */
import com.etoc.bean.Book;

public class LambdaTest {

	public void sendMsg3() {
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
		TreeSet<Integer> ts = new TreeSet<>(com);
	}

	public void sendMsg5() {
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
}
