package com;


import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
 
//import util.ChartUtils;
 
/**
 * 使用poi 和 JFreeChart生成图标插入到excel中
 * @author jjc
 *
 */
public class PoiJfreeChart {
	private static void setBorder(HSSFCellStyle setBorder){
		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置居中
	}
	
	/**
	 * 单纯使用POI操作Excel
	 */
	private static void createExcelByPoi(){
		// 生成一个excel对象
		HSSFWorkbook wb = new HSSFWorkbook(); 
		// 生成excel格式对象
		HSSFCellStyle style = wb.createCellStyle();
		setBorder(style);
		// 生成一个名为Demo的sheet
		HSSFSheet sheet = wb.createSheet("Demo");
		// 创建第一行
		HSSFRow row = sheet.createRow((int) 0);
		// 创建第一列单元格
		HSSFCell cell = row.createCell((int) 0);
		cell.setCellValue("这是第一列单元格哦");
		cell.setCellStyle(style); // 设置单元格格式
		// 设置第一列自适应，此方法对于合并单元格仍然有效，从0开始，一定要先创建列，才能使用，不然没有效果
		sheet.autoSizeColumn(0, true); 
		cell = row.createCell((int) 1);
		cell.setCellValue("满分人数aaaaaaaaa");
		cell.setCellStyle(style);
		sheet.autoSizeColumn(1, true); 
		cell = row.createCell((int) 2);
		cell.setCellValue("最高分aaaaabbbbbb");
		cell.setCellStyle(style);
		sheet.autoSizeColumn(2, true); 
		cell = row.createCell((int) 3);
		cell.setCellValue("最低分aaaaaaaaaa");
		sheet.autoSizeColumn(3, true); 
		cell.setCellStyle(style);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("/Users/vinlam/123456.xls");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			wb.write(fos); // 写入到文本输出流中
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 生成图表，使用简单柱状图演示
	 */
	public static void createBar() {
		JFreeChart mBarChart = ChartFactory.createBarChart("班级分数段分布图", // 图表标题
				"班级", // 横轴
				"人数",// 纵轴
				GetDatasetBar(), PlotOrientation.VERTICAL, // 图表方向
				true, // 是否显示图例
				true, // 是否生成提示工具
				false); // 是否生成url连接
		// 图表标题设置
		TextTitle mTextTitle = mBarChart.getTitle();
		mTextTitle.setFont(new Font("黑体", Font.BOLD, 20));
 
		// 图表图例设置
		LegendTitle mLegend = mBarChart.getLegend();
		if (mLegend != null)
			mLegend.setItemFont(new Font("宋体", Font.CENTER_BASELINE, 15));
		//设置柱状图轴  
        CategoryPlot mPlot = (CategoryPlot)mBarChart.getPlot();
      //x轴  
        CategoryAxis mDomainAxis = mPlot.getDomainAxis();  
        //设置x轴标题的字体  
        mDomainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 15));  
        //设置x轴坐标字体  
        mDomainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 15));  
        //y轴  
        ValueAxis mValueAxis = mPlot.getRangeAxis();  
        //设置y轴标题字体  
        mValueAxis.setLabelFont(new Font("宋体", Font.PLAIN, 15));  
        //设置y轴坐标字体  
        mValueAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 15));  
        //柱体显示数值  
		BarRenderer3D mRenderer = new BarRenderer3D();
		mRenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		mRenderer.setItemLabelsVisible(true);
		mRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.CENTER_LEFT));
		mRenderer.setItemLabelAnchorOffset(10); 
		mRenderer.setItemLabelFont(new Font("宋体", Font.BOLD, 15)); 
		mPlot.setRenderer(mRenderer);
        ChartFrame mChartFrame = new ChartFrame("班级分数段分布图", mBarChart);  
        mChartFrame.pack();  
        mChartFrame.setVisible(true);
	}
	
	public static CategoryDataset GetDatasetBar() {
		
		DefaultCategoryDataset mDataset = new DefaultCategoryDataset();  
		mDataset.addValue(3, "不及格人数", "02班");  
		mDataset.addValue(39, "及格人数", "02班");  
		mDataset.addValue(7, "优秀人数", "02班");  
		mDataset.addValue(5, "不及格人数", "01班");  
		mDataset.addValue(9, "及格人数", "01班");  
		mDataset.addValue(18, "优秀人数", "01班");
		return mDataset;
	}
	
	/**
	 * 使用poi + JfreeChart 生成图表插入excel，使用柱状图演示
	 */
	private static void createByPoiAndJFreeChart(){
		// 生成一个excel对象
		HSSFWorkbook wb = new HSSFWorkbook(); 
		// 生成excel格式对象
		HSSFCellStyle style = wb.createCellStyle();
		setBorder(style);
		// 生成一个名为Demo的sheet
		HSSFSheet sheet = wb.createSheet("Demo");
		
		
		JFreeChart mBarChart = ChartFactory.createBarChart("Demo", // 图表标题
				"班级", // 横轴
				"人数",// 纵轴
				GetDatasetBar(),  // 数据来源
				PlotOrientation.VERTICAL, // 图表方向
				true, // 是否显示图例
				true, // 是否生成提示工具
				false); // 是否生成url连接
		// 图表标题设置
		TextTitle mTextTitle = mBarChart.getTitle();
		mTextTitle.setFont(new Font("黑体", Font.BOLD, 25));
 
		// 图表图例设置
		LegendTitle mLegend = mBarChart.getLegend();
		if (mLegend != null)
			mLegend.setItemFont(new Font("宋体", Font.CENTER_BASELINE, 15));
		// 设置柱状图轴
		CategoryPlot mPlot = (CategoryPlot) mBarChart.getPlot();
		// 3:设置抗锯齿，防止字体显示不清楚
		//ChartUtils.setAntiAlias(mBarChart);// 抗锯齿
		// x轴
		CategoryAxis mDomainAxis = mPlot.getDomainAxis();
		// 设置x轴标题的字体
		mDomainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 15));
		// 设置x轴坐标字体
		mDomainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 15));
		// y轴
		ValueAxis mValueAxis = mPlot.getRangeAxis();
		// 设置y轴标题字体
		mValueAxis.setLabelFont(new Font("宋体", Font.PLAIN, 15));
		// 设置y轴坐标字体
		mValueAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 15));
		// 柱体显示数值
		BarRenderer3D mRenderer = new BarRenderer3D();
		mRenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		mRenderer.setItemLabelsVisible(true);
		mRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.CENTER_LEFT));
		// 柱体显示数值向上偏移一点点
		mRenderer.setItemLabelAnchorOffset(10);  
		// 设置柱体显示数字格式
		mRenderer.setItemLabelFont(new Font("宋体", Font.BOLD, 15));  
		mPlot.setRenderer(mRenderer);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			 // 将图表写入到ByteArrayOutputStream流中
			ChartUtilities.writeChartAsJPEG(bos, mBarChart, 800, 700);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
		try {
			// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			// anchor主要用于设置图片的属性(1,1 表示图片左上角，15,31表示图片右下角，通过这个可以控制图片的大小)
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255, (short) 1, 1, (short) 15, 31);
			anchor.setAnchorType(3);
			// 插入图片
			patriarch.createPicture(anchor,
					wb.addPicture(bos.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("/Users/vinlam/jfreechartdemo.xls");
			try {
				wb.write(fos);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(fos!=null){
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
//		createExcelByPoi();	// 单纯使用POI创建excel
//		createBar(); // 单纯使用JfreeChart生成图表
		createByPoiAndJFreeChart(); // 使用POI+ JfreeChart 生成图表写入Excel
	}
}