package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
/**
 * lambda语法
 * @author Admin
 *
 */
@SpringBootTest
class SpringJava8ApplicationTests {
	List<Book> books = Arrays.asList(new Book("5", "黄飞鸿1", "139", 16), new Book("1", "黄飞鸿2", "110", 12),
			new Book("2", "黄飞鸿3", "112", 13), new Book("3", "黄飞鸿4", "119", 14), new Book("4", "黄飞鸿5", "121", 15),
			new Book("1", "黄飞鸿6", "110", 12));

	@Test
	public void test1() {
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
		TreeSet<Integer> ts = new TreeSet<>(com);
	}

	/**
	 * 排序
	 */
	@Test
	public void test2() {
		Collections.sort(books, (x, y) -> {
			if (x.getNum() == y.getNum()) {
				return x.getName().compareTo(y.getName());
			} else {
				return x.getNum().compareTo(y.getNum());
			}
		});
		for (Book book : books) {
			System.out.println(book);
		}
	}

	/**
	 * 无参数
	 */
	@Test
	public void test3() {
		int num = 2;
		Runnable runnable = () -> System.out.println("bbb" + num);
		runnable.run();
		Consumer<String> consumer = (x) -> System.out.println(x);
		consumer.accept("你是傻逼吗");
		Consumer<String> consumer1 = x -> System.out.println(x);
		consumer1.accept("你是傻逼吗");
	}

	/**
	 * 出参，入参
	 */
	@Test
	public void test4() {
		Comparator<Integer> comparator = (x, y) -> {
			return Integer.compare(x, y);
		};
		Comparator<Integer> comparator1 = Integer::compare;
		int compare = comparator1.compare(4, 5);
		System.out.println(compare);
	}

	/**
	 * 出参入参,过程不管
	 */
	@Test
	public void test5() {
		Calculation add = (x) -> {
			return x + 5;
		};
		Calculation subtraction = (x) -> {
			return x - 5;
		};
		Calculation multiplication = (x) -> {
			return x - 5;
		};
		Calculation division = (x) -> {
			return x - 5;
		};
		System.out.println("加" + add.calculation(20));
		System.out.println("减" + subtraction.calculation(20));
		System.out.println("乘" + multiplication.calculation(20));
		System.out.println("除" + division.calculation(20));
	}

	/**
	 * 做一个大小写的转换
	 */
	@Test
	public void test6() {
		Capitalization fun = (x) -> x.toLowerCase();
		Capitalization fun1 = (x) -> x.toUpperCase();
		Capitalization fun2 = (x) -> x.trim();
		String capitalization = fun.capitalization("OHIFJEOWIJjfeowijrfewoijioer");
		String capitalization2 = fun1.capitalization("OHIFJEOWIJjfeowijrfewoijioer");
		String capitalization3 = fun2.capitalization(" OHIFJEOWIJjfeowijrfewoijioer ");
		System.out.println("转成大写" + capitalization);
		System.out.println("转成小写" + capitalization2);
		System.out.println("去空格" + capitalization3);
	}

	/**
	 * 做一个大小写的转换
	 */
	@Test
	public void test7() {
		Functions<Long, Long> bbb = (x, y) -> x + y;
		System.out.println(bbb.getValue(10L, 10L));
	}

	/**
	 * 四大内置接口
	 * Consumer 消费型接口
	 * Supplier 供给型
	 * Function 函数类型
	 * Predicate 断言
	 */
	@Test
	public void test8() {
		Book book = new Book("2", "黄飞鸿3", "112", 13);
		Consumer<Integer> t = (x) ->System.out.println(x);
		//Supplier<String> supplier=()->"bbb";
		Supplier<String> supplier=book::getBookId;
		Function<Integer,Integer> fun=(x)->x*100; 
		Predicate<Book>  predicate=x->x.getNum()>3;
		
		boolean test = predicate.test(book);
		System.out.println("是否大于"+test);
	}

}
