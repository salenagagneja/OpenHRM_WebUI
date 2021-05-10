package ui_functional_test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.raybiztech.commonexcelreport.ExcelReportGenerator;
public class testing {

public static void main(String[]args) throws ParserConfigurationException, IOException, SAXException
	{
	//ExcelReportGenerator.generateExcelReport(“Execution_Report.xlsx”, “D:\SEB\Assignment\Test_Report”);
	ExcelReportGenerator a = new ExcelReportGenerator();
	a.GenerateExcelReport("Execution_Report.xlsx");
	
	}	
}