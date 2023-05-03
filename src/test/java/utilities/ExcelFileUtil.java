package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil 
{
Workbook wb;
//constructor for reading excel file
public ExcelFileUtil(String ExcelPath) throws Throwable
{
	FileInputStream fi = new FileInputStream(ExcelPath);
    wb = WorkbookFactory.create(fi);	
}
//counting no of rows in sheet
public int rowCount(String SheetName)
{
	return wb.getSheet(SheetName).getLastRowNum();	
}
//read cell data
public String getCellData(String SheetName,int row,int column)
{
	String data="";
	if(wb.getSheet(SheetName).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
	{
		//read int cell data
		int celldata = (int)wb.getSheet(SheetName).getRow(row).getCell(column).getNumericCellValue();
		data = String.valueOf(celldata);
	}else
	{
		data = wb.getSheet(SheetName).getRow(row).getCell(column).getStringCellValue();
	}
	return data;
}
//method for set cell data
    public void setCellData(String SheetName,int row,int column,String status,String WriteExcel) throws Throwable
    {
    	//get sheet from wb
    	Sheet ws = wb.getSheet(SheetName);
    	//get row from sheet
    	Row rownum = ws.getRow(row);
    	//create cell in a row 
    	Cell cell = rownum.createCell(column);
    	//write status
    	cell.setCellValue(status);
    	if(status.equalsIgnoreCase("Pass"))
    	{
    		CellStyle style = wb.createCellStyle();
    		Font font = wb.createFont();
    		font.setColor(IndexedColors.GREEN.getIndex());
    		font.setBold(true);
    		style.setFont(font);
    		font.setBoldweight(font.BOLDWEIGHT_BOLD);
    		rownum.getCell(column).setCellStyle(style);
    	}
    	else if(status.equalsIgnoreCase("Fail"))
    	{
    		CellStyle style = wb.createCellStyle();
    		Font font = wb.createFont();
    		font.setColor(IndexedColors.RED.getIndex());
    		font.setBold(true);
    		style.setFont(font);
    		font.setBoldweight(font.BOLDWEIGHT_BOLD);
    		rownum.getCell(column).setCellStyle(style);
    	}
    	else if(status.equalsIgnoreCase("Blocked"))
    	{
    		CellStyle style = wb.createCellStyle();
    		Font font = wb.createFont();
    		font.setColor(IndexedColors.BLUE.getIndex());
    		font.setBold(true);
    		style.setFont(font);
    		font.setBoldweight(font.BOLDWEIGHT_BOLD);
    		rownum.getCell(column).setCellStyle(style);
    	}
    	FileOutputStream fo = new FileOutputStream(WriteExcel);
    	wb.write(fo);
    	
      }
    
}
