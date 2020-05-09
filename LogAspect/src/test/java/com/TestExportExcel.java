package com;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.util.MSExcelUtil;

public final class TestExportExcel {
	public static void main(String[] args) throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("table");// 创建table工作薄
		
		//收款单号	善付通订单号	外联订单号	外联收款单号	付款方	收款总金额(元)	付款银行	付款卡号	付款日期	付款类型	支付方式	支付渠道	手续费金额	状态	核实状态
		//善付通收款单号，与付款单号对应	善付通订单号，合并付款情况下，同一笔收款单号可对应多笔订单	订单对应的外联第三方订单号	付款单表第三方预付款单号	付款方名称	收款金额	付款银行，按照融易付返回值显示	付款卡号，按照融易付返回值显示	付款日期	预付货款/订单付款	龙支付/微信/支付宝….按照融易付返回字段显示	PC端支付/移动端支付/小程序支付	按照融易付返回值显示	成功	

		Object[][] datas = { { "区域产品销售额", "", "","" }, { "区域", "总销售额(万元)", "总利润(万元)简单的表格" ,"日期"}, { "江苏省", "72020042509255453941", "72020042509383254111","2020-05-09"}, { "江苏省", "72020042509255453943", "72020042509383254112","2020-05-09"},{ "广东省", 3000, 690,"2020-05-09" } };
		HSSFRow row;
		HSSFCell cell;

		short colorIndex = 10;
		HSSFPalette palette = wb.getCustomPalette();
		Color rgb = Color.GREEN;
		short bgIndex = colorIndex++;
		palette.setColorAtIndex(bgIndex, (byte) rgb.getRed(), (byte) rgb.getGreen(), (byte) rgb.getBlue());
		short bdIndex = colorIndex++;
		rgb = Color.BLACK;
		palette.setColorAtIndex(bdIndex, (byte) rgb.getRed(), (byte) rgb.getGreen(), (byte) rgb.getBlue());

		for (int i = 0; i < datas.length; i++) {
			row = sheet.createRow(i);// 创建表格行
			for (int j = 0; j < datas[i].length; j++) {
				cell = row.createCell(j);// 根据表格行创建单元格
				cell.setCellValue(String.valueOf(datas[i][j]));

				HSSFCellStyle cellStyle = wb.createCellStyle();
				if (i == 0 || i == 1) {
					cellStyle.setFillForegroundColor(bgIndex); // bgIndex 背景颜色下标值
					cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				}

				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				// bdIndex 边框颜色下标值
				cellStyle.setBottomBorderColor(bdIndex);
				cellStyle.setLeftBorderColor(bdIndex);
				cellStyle.setRightBorderColor(bdIndex);
				cellStyle.setTopBorderColor(bdIndex);

				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

				if (i == datas.length - 1 && j == datas[0].length - 1) {
					HSSFFont font = wb.createFont();
					font.setItalic(true);
					font.setUnderline(HSSFFont.U_SINGLE);
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					font.setFontHeightInPoints((short) 14);
					cellStyle.setFont(font);
				}
				cell.setCellStyle(cellStyle);
			}
		}

		// 加入图片
		byte[] bt = FileUtils.readFileToByteArray(new File("F://tt.png"));
		int pictureIdx = wb.addPicture(bt, Workbook.PICTURE_TYPE_PNG);
		CreationHelper helper = wb.getCreationHelper();
		Drawing drawing = sheet.createDrawingPatriarch();
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setDx1(MSExcelUtil.pixel2WidthUnits(60));
		anchor.setDy1(MSExcelUtil.pixel2WidthUnits(60));
		anchor.setCol1(0);
		anchor.setRow1(4);
		anchor.setCol2(3);
		anchor.setRow2(25);
		drawing.createPicture(anchor, pictureIdx);

		// 合并单元格
		CellRangeAddress region = new CellRangeAddress(0, // first row
				0, // last row
				0, // first column
				2 // last column
		);
		// 合并单元格
		CellRangeAddress region1 = new CellRangeAddress(2, // first row
				3, // last row
				0, // first column
				0 // last column
				);
		// 合并单元格
		CellRangeAddress region2 = new CellRangeAddress(2, // first row
				3, // last row
				2, // first column
				0 // last column
				);
		sheet.addMergedRegion(region);
		sheet.addMergedRegion(region1);
		sheet.addMergedRegion(region2);

		// 创建表格之后设置行高与列宽
		for (int i = 0; i < datas.length; i++) {
			row = sheet.getRow(i);
			row.setHeightInPoints(30);
		}
		for (int j = 0; j < datas[0].length; j++) {
			sheet.setColumnWidth(j, MSExcelUtil.pixel2WidthUnits(160));
		}
		wb.write(new FileOutputStream("F://e1.xls"));
	}
}