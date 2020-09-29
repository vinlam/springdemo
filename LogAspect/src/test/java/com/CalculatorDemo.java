package com;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import org.joda.time.Instant;

public class CalculatorDemo {
	public static void main(String[] args) {
		long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();

		LocalTime start = LocalTime.now();
		Calculator calculator = new ForLoopCalculator();
		long result = calculator.sumUp(numbers);
		LocalTime end = LocalTime.now();
		System.out.println("耗时：" + Duration.between(start, end).toMillis() + "ms");

		System.out.println("结果为：" + result);

		Duration du = Duration.ofHours(2);
		LocalTime l_time1 = LocalTime.now();

		// Display l_time1
		System.out.println("l_time1: " + l_time1);

		// adds this object (du) into the
		// given object (l_time1) i.e. we are adding
		// duration (2 hrs) in l_time1 hours unit
		LocalTime l_time2 = (LocalTime) du.addTo(l_time1);

		// Display l_time2
		System.out.println("l_time2: " + l_time2);

		// returns the duration between
		// Temporal1 and Temporal2 i.e. difference of
		// 2 hrs in between l_time1 and l_time2
		Duration diff_in_hrs = Duration.between(l_time1, l_time2);
		System.out.println("Duration.between(l_time1,l_time2): " + diff_in_hrs);
		// 输出：
		// 耗时：10ms
		// 结果为：50000005000000

		LocalDateTime localDateTime1 = LocalDateTime.of(2019, 11, 15, 0, 0);
		LocalDateTime localDateTime2 = LocalDateTime.of(2019, 11, 15, 10, 30);
		Duration d = Duration.between(localDateTime1, localDateTime2);
		System.out.println("days:" + d.toDays());
		System.out.println("hours:" + d.toHours());
		System.out.println("minutes:" + d.toMinutes());
		System.out.println("millis:" + d.toMillis());
		//days:0
		//hours:10
		//minutes:630
		//millis:37800000

		// long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();

		LocalTime start1 = LocalTime.now();
		Calculator calculator1 = new ExecutorServiceCalculator();
		long result1 = calculator1.sumUp(numbers);
		LocalTime end1 = LocalTime.now();
		System.out.println("耗时：" + Duration.between(start1, end1).toMillis() + "ms");

		System.out.println("结果为：" + result1); // 打印结果500500
		//耗时：26ms
		//结果为：50000005000000
		
		LocalTime start2 = LocalTime.now();
		Calculator calculator2 = new ForkJoinCalculator();
		long result2 = calculator2.sumUp(numbers);
		LocalTime end2 = LocalTime.now();
		System.out.println("耗时：" + Duration.between(start2, end2).toMillis() + "ms");

		System.out.println("结果为：" + result2); // 打印结果500500

		//并行流
		LocalTime start3 = LocalTime.now();
		long result3 = LongStream.rangeClosed(0, 10000000L).parallel().reduce(0, Long::sum);
		LocalTime end3 = LocalTime.now();
		System.out.println("耗时：" + Duration.between(start3, end3).toMillis() + "ms");

		System.out.println("结果为：" + result3); // 打印结果500500
		//耗时：25ms
		//结果为：50000005000000

		//耗时效率方面解释：Fork/Join 并行流等当计算的数字非常大的时候，优势才能体现出来。也就是说，如果你的计算比较小，或者不是CPU密集型的任务，不太建议使用并行处理

		//parallelStream其实就是一个并行执行的流.它通过默认的ForkJoinPool,可能提高你的多线程任务的速度
		//Stream具有平行处理能力，处理的过程会分而治之，也就是将一个大任务切分成多个小任务，这表示每个任务都是一个操作，因此像以下的程式片段
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		list.parallelStream().forEach(System.out::println);
		list.parallelStream().forEach((item)->{System.out.println(item);});
		//6,4,9,2,8,3,5,1,7
		//你得到的展示顺序不一定会是1、2、3、4、5、6、7、8、9，而可能是任意的顺序，就forEach()这个操作來讲，如果平行处理时，
		//希望最后顺序是按照原来Stream的数据顺序，那可以调用forEachOrdered()。例如

		list.parallelStream().forEachOrdered(System.out::println);
	}

}
