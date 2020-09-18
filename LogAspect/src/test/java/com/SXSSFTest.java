package com;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * ClassName: SXSSFTest
 */
public class SXSSFTest {

	public static void main(String[] args) throws IOException {
		// 创建基于stream的工作薄对象的
		SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory,
													// exceeding rows will be
													// flushed to disk
		// SXSSFWorkbook wb = new SXSSFWorkbook();
		// wb.setCompressTempFiles(true); // temp files will be gzipped
		Sheet sh = wb.createSheet();
		// 使用createRow将信息写在内存中。
		for (int rownum = 0; rownum < 1000; rownum++) {
			Row row = sh.createRow(rownum);
			for (int cellnum = 0; cellnum < 10; cellnum++) {
				Cell cell = row.createCell(cellnum);
				String address = new CellReference(cell).formatAsString();
				cell.setCellValue(address);
			}

		}

		// Rows with rownum < 900 are flushed and not accessible
		// 当使用getRow方法访问的时候，将内存中的信息刷新到硬盘中去。
		for (int rownum = 0; rownum < 900; rownum++) {
			System.out.println(sh.getRow(rownum));
		}

		// ther last 100 rows are still in memory
		for (int rownum = 900; rownum < 1000; rownum++) {
			System.out.println(sh.getRow(rownum));
		}
		// 写入文件中
		FileOutputStream out = new FileOutputStream("/Users/vinlam/sxssf.xlsx");
		wb.write(out);
		// 关闭文件流对象
		out.close();
		System.out.println("基于流写入执行完毕!");
	}

}
