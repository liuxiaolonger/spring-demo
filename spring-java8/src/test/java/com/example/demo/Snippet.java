package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Stream
 * 
 * @author Admin
 *
 */
@SpringBootTest
public class Snippet {
	List<Book> books = Arrays.asList(new Book("5", "黄飞鸿1", "139", 16), new Book("1", "黄飞鸿2", "110", 12),
			new Book("2", "黄飞鸿3", "112", 13), new Book("3", "黄飞鸿4", "119", 14), new Book("4", "黄飞鸿5", "121", 15),
			new Book("1", "黄飞鸿6", "110", 12));

	@Test
	public void test1() {
		// 1得到一个流
		Stream<Book> stream1 = books.stream();
		// 2得到一个流
		Book[] arr = new Book[10];
		Stream<Book> stream2 = Arrays.stream(arr);
		// 3得到一个流
		Stream<Book> stream3 = Stream.of(new Book("5", "黄飞鸿1", "139", 16), new Book("1", "黄飞鸿2", "110", 12),
				new Book("2", "黄飞鸿3", "112", 13), new Book("3", "黄飞鸿4", "119", 14), new Book("4", "黄飞鸿5", "121", 15),
				new Book("2", "黄飞鸿3", "112", 13));
		// 4创建无限流
		Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2);
		stream4.limit(10).forEach(System.out::println);
		Stream<Double> generate = Stream.generate(() -> Math.random());
		generate.limit(10).forEach(System.out::println);
	}

	/**
	 * filter
	 */
	@Test
	public void test2() {
		// 中间操作
		Stream<Book> stream = books.stream().filter((x) -> {
			System.out.println("加工处理中");
			return x.getNum() > 12;
		});
		// 终止操作
		stream.forEach(System.out::println);
	}
	/**
	 * limit,filter
	 * 取前两位
	 */
	@Test
	public void test3() {
		// 中间操作
		books.stream().filter((x) -> {
			System.out.println("加工处理中");
			return x.getNum() > 12;
		}).limit(2).forEach(System.out::println);
	}
	/**
	 * distinct,filter
	 * 跳过前两位
	 */
	@Test
	public void test4() {
		// 中间操作
		books.stream().filter((x) -> {
			System.out.println("加工处理中");
			return x.getNum() > 12;
		}).distinct().forEach(System.out::println);
	}
	/**
	 * 
	 * map 改变集合中的对象生产一个新的对象
	 */
	@Test
	public void test5() {
		// 中间操作
		books.stream().map((x) -> {
			System.out.println("加工处理中");
			x.setBookId("10000");
			return x;
		}).forEach(System.out::println);
	}
	/**
	 * 
	 * map 改变集合中的对象生产一个新的对象
	 */
	@Test
	public void test6() {
		List<String> strs=Arrays.asList("fewrfew","rewrew","rwere");
		strs.stream().map(Snippet::getCharacterList).forEach((x)->{
			x.forEach(System.out::println);
		});
		
	}
	/**
	 * 
	 * map 改变集合中的对象生产一个新的对象
	 */
	@Test
	public void test7() {
		List<String> strs=Arrays.asList("fewrfew","rewrew","rwere");
		strs.stream().flatMap(Snippet::getCharacterList).forEach(
			(System.out::println);
		});
		
	}
	public static Stream<Character> getCharacterList(String str){
	    List<Character>  charList=new ArrayList<Character>(10);
	    for (Character character : str.toCharArray()) {
	    	charList.add(character.toUpperCase(character));
		}
		return charList.stream();
	}
}
