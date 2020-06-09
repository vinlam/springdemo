package com.entity;


/**
 * 
 * @ClassName:PoiModel
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: ZHOUPAN
 * @date 2019年1月25日 下午3:10:30
 *
 * @Copyright: 2018 www.zsplat.com Inc. All rights reserved.
 */
public class PoiModel {
    
    //内容
    private String content;
    //上一行同一位置内容
    private String oldContent;
    //行标
    private int rowIndex;
    //列标
    private int cellIndex;
 
    public String getOldContent() {
        return oldContent;
    }
 
    public void setOldContent(String oldContent) {
        this.oldContent = oldContent;
    }
 
    public String getContent() {
        return content;
    }
 
    public void setContent(String content) {
        this.content = content;
    }
 
    public int getRowIndex() {
        return rowIndex;
    }
 
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }
 
    public int getCellIndex() {
        return cellIndex;
    }
 
    public void setCellIndex(int cellIndex) {
        this.cellIndex = cellIndex;
    }
}