package ui_functional_test;

import java.io.*;
import java.util.Optional;

import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.sun.jna.platform.unix.X11.Font;

import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
    
public class excel_utils {
	
	   ExtentReports extent;
	   ExtentTest logger;
	   private XSSFSheet ExcelWSheet;
	   private XSSFWorkbook ExcelWBook;
	   //private XSSFSheet ExcelWSheet;
	   //private XSSFWorkbook ExcelWBook;
	   public String tc_name="Not Set";
	   private int tc_status_row = 1;
	   private int Row_num, Col_num;
	   
	   private FileOutputStream fos = null;
	   private FileInputStream fis = null;
   
   //Constructor to connect to the Excel with sheetname and Path
   public excel_utils(String Path, String SheetName, String Action) throws Exception {
   
      try {
         // Open the Excel file
        
         
         // Access the required test data sheet
        
         Row_num = 1;
         Col_num = 0;
         if(Action == "Write") {
        	 try {
        	 fis = new FileInputStream(Path);
        	 ExcelWBook = new XSSFWorkbook(fis);
        	 //ExcelWBook = new XSSFWorkbook(ExcelFile);
        	 ExcelWSheet = ExcelWBook.getSheet(SheetName);}
        	 catch(Exception e) {
        		 System.out.println("Error: "+e.getMessage());
        	 }
        	 //new_tc = true;
         }
         else {
        	 FileInputStream ExcelFile = new FileInputStream(Path);
             ExcelWBook = new XSSFWorkbook(ExcelFile); 
             ExcelWSheet = ExcelWBook.getSheet(SheetName);
         }
         
         
      } catch (Exception e) {
    	  System.out.println(e.getMessage());
         throw (e);
      }
   }
      
   //This method is to set the rowcount of the excel.
   public int excel_get_rows() throws Exception {
   
      try {
         return ExcelWSheet.getPhysicalNumberOfRows();
      } catch (Exception e) {
         throw (e);
      }
   }
   
   //This method to get the data and get the value as strings.
   public String getCellDataasstring(int RowNum, int ColNum) throws Exception {
   
      try {
         String CellData =
            ExcelWSheet.getRow(RowNum).getCell(ColNum).getStringCellValue();
         //System.out.println("The value of CellData " + CellData);
         return CellData;
      } catch (Exception e) {
    	  System.out.println(e);
         return "Errors in Getting Cell Data";
      }
   }
   
   //This method to get the data and get the value as number.
   public double getCellDataasnumber(int RowNum, int ColNum) throws Exception {
   
      try {
         double CellData =
            ExcelWSheet.getRow(RowNum).getCell(ColNum).getNumericCellValue();
         System.out.println("The value of CellData " + CellData);
         return CellData;
      } catch (Exception e) {
    	  System.out.println(e);
         return 000.00;
      }
   }
   
   public void report_StepStatus(String step_name, String step_description, String step_status){
	   //System.out.println(step_number);
	   System.out.println("step Report");
	   try {
		
		  // FileInputStream fis = this.fis;
	   
		CellStyle style_pass = ExcelWBook.createCellStyle();
		style_pass.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		style_pass.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle style_fail = ExcelWBook.createCellStyle();
		style_fail.setFillForegroundColor(IndexedColors.RED.getIndex());
		style_fail.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle style_other = ExcelWBook.createCellStyle();
		style_other.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style_other.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		   if(step_description.equalsIgnoreCase("new_testcase")) {
			   System.out.println(ExcelWSheet.getRow(0).getCell(0).getStringCellValue());
			   this.tc_status_row = ExcelWSheet.getLastRowNum() +1;
			   //this.Row_num = this.tc_status_row;
			   XSSFRow row = ExcelWSheet.getRow(0);
			   
			   ExcelWSheet.createRow(1);
			   row = ExcelWSheet.getRow(this.tc_status_row);
			   //Create and Update value  for cell TC name
			   XSSFCell cell = row.createCell(1);
			   cell.setCellType(CellType.STRING);
			   cell.setCellValue(this.tc_name);
			   cell.setCellStyle(style_other);
			   
		   }
		   
		   else if(step_description.equalsIgnoreCase("end_testcase")) {
			  // this.tc_status_row = ExcelWSheet.getLastRowNum() +1;
			   XSSFRow row = ExcelWSheet.getRow(this.tc_status_row);
			   
			   //ExcelWSheet.createRow(Row_num);
			   //row = ExcelWSheet.getRow(Row_num);
			   //Create and Update value  for cell TC name
			   XSSFCell cell = row.createCell(5);
			   cell.setCellType(CellType.STRING);
			   cell.setCellValue(step_status);
			   
			   if(step_status.equalsIgnoreCase("pass")){
				   cell.setCellStyle(style_pass);
			   }else if(step_status.equalsIgnoreCase("fail")){
				   cell.setCellStyle(style_fail);}
				else {
					cell.setCellStyle(style_other);   
				 }
			   
			   fis.close();
			   
			   FileOutputStream fos = new FileOutputStream("D:\\SEB\\Assignment\\Test_Report\\Execution_Report.xlsx");
			   ExcelWBook.write(fos);
			   fos.close(); 
		   }
	   }catch(Exception e) {
		   System.out.println(e.getCause() + " : " +e.getClass()+": msg"+e.getMessage());
		   
	   }
 }
	   
	   
   public void report_TCSummary(String tc_name, String tc_status){
	   try {
		   //FileInputStream fis = new FileInputStream("D:\\SEB\\Assignment\\Test_Report\\Execution_Report.xlsx");
		   //XSSFWorkbook workbook =new XSSFWorkbook(fis);
		   //XSSFSheet sheet = workbook.getSheet("Summary");
		   // XSSFCellStyle style = new XSSFCellStyle(new StylesTable());
		   // style.setFillBackgroundColor(IndexedColors.RED.getIndex());
		   // style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			CellStyle style_pass = ExcelWBook.createCellStyle();
			style_pass.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			style_pass.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			CellStyle style_fail = ExcelWBook.createCellStyle();
			style_fail.setFillForegroundColor(IndexedColors.RED.getIndex());
			style_fail.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    
		   XSSFRow row = ExcelWSheet.getRow(0);
		   
		   Row_num = ExcelWSheet.getLastRowNum() +1;
		   
		   System.out.println("value (0,0) = " + row.getCell(1).getStringCellValue()+ "/n RowNum: "+Row_num);
		   ExcelWSheet.createRow(Row_num);
		   row = ExcelWSheet.getRow(Row_num);
		   
		   	
		   
		   
		   XSSFCell cell = row.createCell(1);
		   cell.setCellType(CellType.STRING);
		   cell.setCellValue(tc_name);
		   //cell.setCellStyle(style);
		   cell = row.createCell(2);
		   cell.setCellType(CellType.STRING);
		   cell.setCellValue(tc_status);
		   if(tc_status == "Pass") {
			   cell.setCellStyle(style_pass); 
		   }
		   else if(tc_status == "Fail"){
			   cell.setCellStyle(style_fail);
		   }
		   
		   fis.close();
		   FileOutputStream fos = new FileOutputStream("D:\\SEB\\Assignment\\Test_Report\\Execution_Report.xlsx");
		   ExcelWBook.write(fos);
		   fos.close();
		
		
	   }
	   catch(Exception e) {
		   System.out.println(e.getCause() + " : " +e.getClass()+": msg"+e.getMessage());
   }
}
}
