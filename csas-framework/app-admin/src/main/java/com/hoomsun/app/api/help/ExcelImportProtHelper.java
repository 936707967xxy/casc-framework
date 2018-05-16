package com.hoomsun.app.api.help;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 *
 *
 * @author 刘栋梁
 * @since 2017-02-01
 * @see CommonsMultipartResolver
 * 
 *      Excel导入
 */

public class ExcelImportProtHelper {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 获得Excel单元格的内容
	 * 
	 * @param i
	 * @param row
	 * @return
	 */
	public static Object getCellValue(int i, Row row) {
		Object value = "";
		Cell cell = row.getCell(i);
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:   //字符
				value = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:  //数值
				if (DateUtil.isCellDateFormatted(cell)) {
					value = cell.getDateCellValue();
				} else {
					value = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_FORMULA:  //公式型
				CellStyle style = cell.getCellStyle();
				String tt = style.getDataFormatString();
				if (tt.replace("/", "-").equalsIgnoreCase("yyyy-mm-dd")) {
					value = new SimpleDateFormat("yyyy-MM-dd").format(cell
							.getDateCellValue());
				} else if (tt.replace("\\-", "-")
						.equalsIgnoreCase("yyyy-mm-dd")) {
					value = new SimpleDateFormat("yyyy-MM-dd").format(cell
							.getDateCellValue());
				} else if (tt.replace("/", "-").equalsIgnoreCase("m-d-yy")) {
					value = new SimpleDateFormat("yyyy-MM-dd").format(cell
							.getDateCellValue());
				} else {
					value = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BLANK:  //空值
				break;
			case Cell.CELL_TYPE_ERROR:  //错误
				value = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN: //布尔型 
				value = (cell.getBooleanCellValue() == true ? "Y" : "N");
				break;
			default:
				value = "";
			}
		}
		return value;
	}

	/**
	 * 获得Excel单元格的内容
	 * 
	 * @param SheetIndex
	 * @param file
	 * @return sheet 
	 * 里面提供方法获取所有参数 
	 * int rowtotal = sheet.getLastRowNum(); 
	 * Row row= sheet.getRow(i); 注意第一行/第二行不是表数据 属于说明
	 * 
	 */
	public Sheet recordExport(CommonsMultipartFile file, int SheetIndex)
			throws IOException {
		Sheet sheet = null;
		boolean isExcel2003 = true; // excel版本
		String fileName = file.getOriginalFilename(); // 文件名
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = null;
		try {
			is = file.getInputStream();
			Workbook book = (Workbook) (isExcel2003 ? new HSSFWorkbook(is)
					: new HSSFWorkbook(is));
			sheet = book.getSheetAt(SheetIndex); // 获取第几个页

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			is.close();
		}
		return sheet;
	}

}
