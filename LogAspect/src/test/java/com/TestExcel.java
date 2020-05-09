package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.entity.PoiModel;

public class TestExcel {
	/**
	 * @param headers      标题集合 tilte的长度应该与list中的model的属性个数一致
	 * @param dataset      内容集合
	 * @param mergeColumns 合并单元格的列
	 */
	public static String createExcel(String[] headers, List<Map<String, String>> dataset, String[] mergeColumns) {
		if (headers.length == 0) {
			return null;
		}
		/* 初始化excel模板 */
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = null;
		try {
			sheet = workbook.createSheet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CellStyle cellStyle = workbook.createCellStyle();

		// 设置头部样式
        cellStyle = workbook.createCellStyle();
        // 设置字体大小 位置
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        Font font = workbook.createFont();
        //设置字体
        //font.setFontName("微软雅黑");
        //字体颜色
        font.setColor(HSSFColor.BLACK.index);// HSSFColor.VIOLET.index
        font.setFontHeightInPoints((short) 12);
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体增粗
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);//背景白色
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
		/* 初始化head，填值标题行（第一行） */
		Row row0 = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			/* 创建单元格，指定类型 */
			Cell cell_1 = row0.createCell(i, Cell.CELL_TYPE_STRING);
			cell_1.setCellValue(headers[i]);
			cell_1.setCellStyle(cellStyle);
		}

		List<PoiModel> poiModels = new ArrayList<PoiModel>();
		Iterator<Map<String, String>> iterator = dataset.iterator();
		
		
		int index = 1; // 这里1是从excel的第二行开始，第一行已经塞入标题了
		while (iterator.hasNext()) {
			Row row = sheet.createRow(index);
			// 取得当前这行的map，该map中以key，value的形式存着这一行值
			Map<String, String> map = iterator.next();
			// 循环列数，给当前行塞值
			for (int i = 0; i < headers.length; i++) {
				String old = "";
				// old存的是上一行统一位置的单元的值，第一行是最上一行了，所以从第二行开始记
				if (index > 1) {
					old = poiModels.get(i) == null ? "" : poiModels.get(i).getContent();
				}

				String value = map.get(headers[i]);
				CellRangeAddress cra = null;
				// 循环需要合并的列
				for (int j = 0; j < mergeColumns.length; j++) {
					PoiModel poiModel = null;
					if (index == 1) {
						poiModel = new PoiModel();
						poiModel.setOldContent(value);
						poiModel.setContent(value);
						poiModel.setRowIndex(1);
						poiModel.setCellIndex(i);
						poiModels.add(poiModel);
						old = value;
						break;
					}
					poiModel = poiModels.get(i);

					int rowStartIndex = poiModel.getRowIndex();
					int rowEndIndex = index - 1;
					int cellIndex = poiModel.getCellIndex();
					String content = poiModel.getContent();
					String preOldContent = poiModels.get(0).getOldContent();
					String preValue = map.get(headers[0]);
					Boolean isHeaderEquals = mergeColumns[j].equals(headers[i]);

					if (i == 0 && isHeaderEquals && !content.equals(value)) {
						if (rowStartIndex != rowEndIndex) {
							cra = new CellRangeAddress(rowStartIndex, rowEndIndex, cellIndex, cellIndex);
							sheet.addMergedRegion(cra);
						}
						// 重新记录该列的内容为当前内容，行标记改为当前行标记
						poiModel.setContent(value);
						poiModel.setRowIndex(index);
						poiModel.setCellIndex(i);
					} else if (i > 0 && isHeaderEquals) {
						if (!content.equals(value) || (content.equals(value) && !preOldContent.equals(preValue))) {
							if (rowStartIndex != rowEndIndex) {
								cra = new CellRangeAddress(rowStartIndex, rowEndIndex, cellIndex, cellIndex);
								sheet.addMergedRegion(cra);
							}
							poiModels.get(i).setContent(value);
							poiModels.get(i).setRowIndex(index);
							poiModels.get(i).setCellIndex(i);
						}
					}
					if (isHeaderEquals && index == dataset.size()) {
						if (i == 0) {
							if (content.equals(value)) {
								cra = new CellRangeAddress(rowStartIndex, index, cellIndex, cellIndex);
								sheet.addMergedRegion(cra);
							}
						} else if (i > 0) {
							if (content.equals(value) && preOldContent.equals(preValue)) {
								cra = new CellRangeAddress(rowStartIndex, index, cellIndex, cellIndex);
								sheet.addMergedRegion(cra);
							}
						}
					}
				}
				// 创建表格之后设置行高与列宽
				row.setHeightInPoints(14);
				
	            
	            
	           
	            
				
				Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
				cell.setCellValue(value);
				cell.setCellStyle(cellStyle);
				// 在每一个单元格处理完成后，把这个单元格内容设置为old内容
				poiModels.get(i).setOldContent(old);
			}
			index++;
		}
		
		/* 生成临时文件 */
		FileOutputStream out = null;
		String localPath = null;
		File tempFile = null;
		String fileName = String.valueOf(System.currentTimeMillis());
		try {
			tempFile = new File("F://" + fileName + ".xlsx");
			// tempFile = File.createTempFile(fileName,".xlsx");
			localPath = tempFile.getAbsolutePath();
			out = new FileOutputStream(localPath);
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return localPath;
	}

	public static void main(String[] args) throws IOException {
		String[] headers = { "单位名称", "IP", "IP分类", "危险程度" };
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("单位名称", "test");
		map.put("IP", "10.130.138.92");
		map.put("IP分类", "主机IP1");
		map.put("危险程度", "高危");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("单位名称", "test");
		map.put("IP", "10.130.138.96");
		map.put("IP分类", "主机IP2");
		map.put("危险程度", "高危");
		list.add(map);
		//list.add(map);
		map = new HashMap<String, String>();
		map.put("单位名称", "test111");
		map.put("IP", "10.130.138.96");
		map.put("IP分类", "主机IP");
		map.put("危险程度", "高危");
		list.add(map);
		//list.add(map);
		//list.add(map);
		//list.add(map);
		String[] regions = new String[] { "单位名称", "IP", "IP分类", "危险程度" };
		System.out.println(createExcel(headers, list, regions));
	}
}