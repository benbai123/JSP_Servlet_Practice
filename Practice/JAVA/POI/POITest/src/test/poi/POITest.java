package test.poi;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * required jar:
 * poi-3.8-20120326.jar - http://poi.apache.org/download.html
 *
 */
public class POITest {
	private static SimpleDateFormat _shortDateformatter = new SimpleDateFormat("yyyy-MM-dd");
	// the style for Date column
	private static CellStyle _dateCellStyle;
	// the style for Money cells
	private static CellStyle _moneyCells;
	// the style for Money cells with negative value
	private static CellStyle _moneyCellsNegative;
	// evaluator to evaluate formula cell
	private static FormulaEvaluator _evaluator;

	public static void main (String[] args) {
		try {
			// create a new workbook
			Workbook wb = new HSSFWorkbook();
			// create a sheet with name "Balance"
			Sheet sheet = wb.createSheet("Balance");
			// get fake datas
			List<DateInOut> datas = generateFakeData();

			// add title row
			addTitle(sheet);
			// create cell styles as needed
			createCellStylesAndEvaluator(wb);
			// add datas
			addDatas(sheet, datas);
			
			// adjust column width
			autosizeColumns(sheet);

			// save workbook as .xls file
			saveBalanceReport(wb, "workbook.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static List<DateInOut> generateFakeData () throws ParseException {
		Calendar c = Calendar.getInstance();
		Random rand = new Random();
		List<DateInOut> datas = new ArrayList<DateInOut>();
		// set start date
		c.setTime(_shortDateformatter.parse("2012-01-01"));
		// generate fake datas within 200 days
		while (c.get(Calendar.DAY_OF_YEAR) < 200) {
			DateInOut dio = new DateInOut(c.getTime());
			// add 1-5 records
			for (int i = 0; i < (rand.nextInt(5) + 1); i++) {
				dio.addInOut(rand.nextInt(1000), rand.nextInt(1000));
			}
			datas.add(dio);
			// increase date
			c.add(Calendar.DAY_OF_YEAR, rand.nextInt(3) + 1);
		}
		return datas;
	}
	// add titles
	private static void addTitle (Sheet sheet) {
		// create row (3rd row)
		Row row = sheet.createRow(2);
		// add value to 3rd cell
		row.createCell(2).setCellValue("Ben Bai's Balance (not real)");
		// merge cells
		sheet.addMergedRegion(// first row (0-based), last row (0-based), first column (0-based), last column (0-based)
			new CellRangeAddress(2, 2, 2, 4));
		// go to 4th row
		row = sheet.createRow(3);
		// add values to cells
		row.createCell(1).setCellValue("Date");
		row.createCell(2).setCellValue("Income");
		row.createCell(3).setCellValue("Expenditure");
		row.createCell(4).setCellValue("Balance");
		row.createCell(6).setCellValue("Grand Total of Balance");
	}
	// create style for Date cell
	private static void createCellStylesAndEvaluator(Workbook wb) {
		// CreationHelper for create CellStyle
		CreationHelper createHelper = wb.getCreationHelper();
		_dateCellStyle = wb.createCellStyle();
		// add date format
		_dateCellStyle.setDataFormat(
			createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
		// vertical align top
		_dateCellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);

		// money style ( >= 0)
		_moneyCells = wb.createCellStyle();
		_moneyCells.setDataFormat(
				createHelper.createDataFormat().getFormat("$__##,##0.##"));
		// money style ( < 0)
		Font font = wb.createFont();
		font.setColor(Font.COLOR_RED);
		_moneyCellsNegative = wb.createCellStyle();
		_moneyCellsNegative.setDataFormat(
				createHelper.createDataFormat().getFormat("$__##,##0.##"));
		_moneyCellsNegative.setFont(font);

		_evaluator = wb.getCreationHelper().createFormulaEvaluator();
	}
	private static void addDatas (Sheet sheet, List<DateInOut> datas) {
		int rowIdx = 4; // start from 5th row
		Row firstDataRow = null;
		for (DateInOut dio : datas) {
			Date date = dio.getDate();
			List<InOut> inoutList = dio.getInOut();
			int size = inoutList.size();

			// merge "Date" column as needed
			sheet.addMergedRegion( // first row (0-based), last row (0-based), first column (0-based), last column (0-based)
				new CellRangeAddress(rowIdx, rowIdx + size - 1, 1, 1));
			Row row = sheet.createRow(rowIdx);
			// keep first row for later use
			if (firstDataRow == null)
				firstDataRow = row;
			// set date value
			Cell c = row.createCell(1);
			c.setCellStyle(_dateCellStyle);
			c.setCellValue(date);

			for (InOut io : inoutList) {
				// income and expenditure
				Cell cell = row.createCell(2);
				cell.setCellValue(io.getIncome());
				setNumericStyle(cell, false);

				cell = row.createCell(3);
				cell.setCellValue(io.getExpenditure());
				setNumericStyle(cell, false);

				// formula for calculate balance of one data row
				String formula = "C"+(rowIdx+1) + "-D"+(rowIdx+1);
				cell = row.createCell(4);
				cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(formula);
				setNumericStyle(cell, true);
				
				// move to next row
				rowIdx++;
				row = sheet.createRow(rowIdx);
			}
			// add two empty column before next date
			rowIdx += 2;
		}
		// formula for calculate grand total of balance column
		String formula = "SUM(E5:E"+(rowIdx)+")";
		Cell gtb = firstDataRow.createCell(6);
		gtb.setCellType(HSSFCell.CELL_TYPE_FORMULA);
		gtb.setCellFormula(formula);
		setNumericStyle(gtb, true);
	}
	// set style to numeric cell
	private static void setNumericStyle (Cell cell, boolean isFormula) {
		double value = isFormula? getFormulaCellValue(cell) : cell.getNumericCellValue();
		if (value >= 0) {
			cell.setCellStyle(_moneyCells);
		} else {
			cell.setCellStyle(_moneyCellsNegative);
		}
	}
	// evaluate formula cell value
	private static double getFormulaCellValue (Cell cell) {
		_evaluator.evaluateFormulaCell(cell);
		return cell.getNumericCellValue();
	}
	// adjust column width
	private static void autosizeColumns (Sheet sheet) {
		// auto size not work with date
		sheet.setColumnWidth(1, 3000);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(6);
	}
	private static void saveBalanceReport (Workbook wb, String fileName) throws Exception {
		// create a new file
		FileOutputStream out = new FileOutputStream(fileName);
		// Write out the workbook
		wb.write(out);
		out.close();
	}
}
// one day with several income/expenditure pare
class DateInOut {
	private Date _date;
	private List<InOut> _inOut;
	public DateInOut (Date date) {
		_date = date;
		_inOut = new ArrayList<InOut>();
	}
	public void addInOut (int income, int expenditure) {
		_inOut.add(new InOut(income, expenditure));
	}

	public Date getDate () {
		return _date;
	}
	public List<InOut> getInOut () {
		return _inOut;
	}
}
// income/expenditure pare
class InOut {
	private int _income;
	private int _expenditure;
	public InOut (int income, int expenditure) {
		_income = income;
		_expenditure = expenditure;
	}

	public int getIncome () {
		return _income;
	}
	public int getExpenditure () {
		return _expenditure;
	}
}