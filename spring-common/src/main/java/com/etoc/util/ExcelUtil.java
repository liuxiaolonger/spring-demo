package com.etoc.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.etoc.annotation.IsNeeded;


/**
 * excel工具(导入分为带注解和不带注解2种方式)
 * 
 * @author chuyh
 *
 */
public class ExcelUtil {
	private static int totalRows = 0;// 总行数

	private static int totalCells = 0;// 总列数

	private static String errorInfo;// 错误信息

	/** 无参构造方法 */
	public ExcelUtil() {
	}

	public static int getTotalRows() {
		return totalRows;
	}

	public static int getTotalCells() {
		return totalCells;
	}

	public static String getErrorInfo() {
		return errorInfo;
	}

	/**
	 * 
	 * 根据流读取Excel文件
	 * 
	 * 
	 * @param inputStream
	 * @param isExcel2003
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<List<String>> read(InputStream inputStream, boolean isExcel2003) throws IOException {

		List<List<String>> dataLst = null;

		/** 根据版本选择创建Workbook的方式 */
		Workbook wb = null;

		if (isExcel2003) {
			wb = new HSSFWorkbook(inputStream);
		} else {
			wb = new XSSFWorkbook(inputStream);
		}
		dataLst = readDate(wb);

		return dataLst;
	}

	/**
	 * 
	 * 读取数据
	 * 
	 * @param wb
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private List<List<String>> readDate(Workbook wb) {

		List<List<String>> dataLst = new ArrayList<List<String>>();

		/** 得到第一个shell */
		Sheet sheet = wb.getSheetAt(0);

		/** 得到Excel的行数 */
		totalRows = sheet.getPhysicalNumberOfRows();

		/** 得到Excel的列数 */
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		/** 循环Excel的行 */
		for (int r = 0; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			List<String> rowLst = new ArrayList<String>();

			/** 循环Excel的列 */
			for (int c = 0; c < getTotalCells(); c++) {

				Cell cell = row.getCell(c);
				String cellValue = "";

				if (null != cell) {
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						cellValue = cell.getNumericCellValue() + "";
						break;

					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;

					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;

					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;

					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;

					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;

					default:
						cellValue = "未知类型";
						break;
					}
				}

				rowLst.add(cellValue);
			}

			/** 保存第r行的第c列 */
			dataLst.add(rowLst);
		}

		return dataLst;
	}

	/**
	 * 
	 * 按指定坐标读取实体数据 <按顺序放入带有注解的实体成员变量中>
	 * 
	 * @param wb
	 *            工作簿
	 * @param t
	 *            实体
	 * @param in
	 *            输入流
	 * @param integers
	 *            指定需要解析的坐标
	 * @return T 相应实体
	 * @throws IOException
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unused")
	public static <T> T readDateT(Workbook wb, T t, InputStream in, Integer[]... integers)
			throws IOException, Exception {
		// 获取该工作表中的第一个工作表
		Sheet sheet = wb.getSheetAt(0);

		// 成员变量的值
		Object entityMemberValue = "";

		// 所有成员变量
		Field[] fields = t.getClass().getDeclaredFields();
		// 列开始下标
		int startCell = 0;

		/** 循环出需要的成员 */
		for (int f = 0; f < fields.length; f++) {

			fields[f].setAccessible(true);
			String fieldName = fields[f].getName();
			boolean fieldHasAnno = fields[f].isAnnotationPresent(IsNeeded.class);
			// 有注解
			if (fieldHasAnno) {
				IsNeeded annotation = fields[f].getAnnotation(IsNeeded.class);
				boolean isNeeded = annotation.isNeeded();

				// Excel需要赋值的列
				if (isNeeded) {

					// 获取行和列
					int x = integers[startCell][0] - 1;
					int y = integers[startCell][1] - 1;

					Row row = sheet.getRow(x);
					Cell cell = row.getCell(y);

					if (row == null) {
						continue;
					}

					// Excel中解析的值
					String cellValue = getCellValue(cell);
					// 需要赋给成员变量的值
					entityMemberValue = getEntityMemberValue(entityMemberValue, fields, f, cellValue);
					// 赋值
					PropertyUtils.setProperty(t, fieldName, entityMemberValue);
					// 列的下标加1
					startCell++;
				}
			}

		}

		return t;
	}

	/**
	 * 
	 * 读取列表数据 <按顺序放入带有注解的实体成员变量中>
	 * 
	 * @param wb
	 *            工作簿
	 * @param t
	 *            实体
	 * @param beginLine
	 *            开始行数
	 * @param totalcut
	 *            结束行数减去相应行数
	 * @param totalcut 获取第几个sheet的数据(从1开始)
	 * @return List<T> 实体列表
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> readDateListT(Workbook wb, T t, int beginLine, int totalcut,int sheetNum) throws Exception {
		List<T> listt = new ArrayList<T>();

		/** 得到第一个shell */
		Sheet sheet = wb.getSheetAt(sheetNum-1);

		/** 得到Excel的行数 */
		totalRows = sheet.getPhysicalNumberOfRows();

		/** 得到Excel的列数 */
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		/** 循环Excel的行 */
		for (int r = beginLine - 1; r < totalRows - totalcut; r++) {
			Object newInstance = t.getClass().newInstance();
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			// 成员变量的值
			Object entityMemberValue = "";

			// 所有成员变量
			Field[] fields = t.getClass().getDeclaredFields();
			// 列开始下标
			int startCell = 0;

			for (int f = 0; f < fields.length; f++) {

				fields[f].setAccessible(true);
				String fieldName = fields[f].getName();
				boolean fieldHasAnno = fields[f].isAnnotationPresent(IsNeeded.class);
				// 有注解
				if (fieldHasAnno) {
					IsNeeded annotation = fields[f].getAnnotation(IsNeeded.class);
					boolean isNeeded = annotation.isNeeded();
					// Excel需要赋值的列
					if (isNeeded) {
						Cell cell = row.getCell(startCell);
						String cellValue = getCellValue(cell);
						entityMemberValue = getEntityMemberValue(entityMemberValue, fields, f, cellValue);
						// 赋值
						PropertyUtils.setProperty(newInstance, fieldName, entityMemberValue);
						// 列的下标加1
						startCell++;
					}
				}

			}

			listt.add((T) newInstance);
		}

		return listt;
	}

	/**
	 * 
	 * 根据Excel表格中的数据判断类型得到值
	 * 
	 * @param cell
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private static String getCellValue(Cell cell) {
		String cellValue = "";

		if (null != cell) {
			// 以下是判断数据的类型
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					Date theDate = cell.getDateCellValue();
					SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
					cellValue = dff.format(theDate);
				} else {
					DecimalFormat df = new DecimalFormat("0");
					cellValue = df.format(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				cellValue = cell.getStringCellValue();
				break;

			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				cellValue = cell.getBooleanCellValue() + "";
				break;

			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				cellValue = cell.getCellFormula() + "";
				break;

			case HSSFCell.CELL_TYPE_BLANK: // 空值
				cellValue = "";
				break;

			case HSSFCell.CELL_TYPE_ERROR: // 故障
				cellValue = "非法字符";
				break;

			default:
				cellValue = "未知类型";
				break;
			}

		}
		return cellValue;
	}

	/**
	 * 
	 * 根据实体成员变量的类型得到成员变量的值
	 * 
	 * @param realValue
	 * @param fields
	 * @param f
	 * @param cellValue
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private static Object getEntityMemberValue(Object realValue, Field[] fields, int f, String cellValue) {
		String type = fields[f].getType().getName();
		switch (type) {
		case "char":
		case "java.lang.Character":
		case "java.lang.String":
			realValue = cellValue;
			break;
		case "java.util.Date":
			realValue = StringUtils.isBlank(cellValue) ? null : DateUtil.strToDate(cellValue, DateUtil.YYYY_MM_DD);
			break;
		case "java.lang.Integer":
		case "int":
			realValue = StringUtils.isBlank(cellValue) ? null : Integer.valueOf(cellValue);
			break;
		
		case "float":
		case "double":
		case "java.lang.Double":
		case "java.lang.Float":
		case "java.lang.Long":
		case "java.lang.Short":
		case "java.math.BigDecimal":
			realValue = StringUtils.isBlank(cellValue) ? null : new BigDecimal(cellValue);
			break;
		default:
			break;
		}
		return realValue;
	}

	/**
	 * 
	 * 根据路径或文件名选择Excel版本
	 * 
	 * 
	 * @param filePathOrName
	 * @param in
	 * @return
	 * @throws IOException
	 * @see [类、类#方法、类#成员]
	 */
	public static Workbook chooseWorkbook(String filePathOrName, InputStream in) throws IOException {
		/** 根据版本选择创建Workbook的方式 */
		Workbook wb = null;
		boolean isExcel2003 = ExcelVersionUtil.isExcel2003(filePathOrName);

		if (isExcel2003) {
			wb = new HSSFWorkbook(in);
		} else {
			wb = new XSSFWorkbook(in);
		}

		return wb;
	}

	static class ExcelVersionUtil {

		/**
		 * 
		 * 是否是2003的excel，返回true是2003
		 * 
		 * 
		 * @param filePath
		 * @return
		 * @see [类、类#方法、类#成员]
		 */
		public static boolean isExcel2003(String filePath) {
			return filePath.matches("^.+\\.(?i)(xls)$");

		}

		/**
		 * 
		 * 是否是2007的excel，返回true是2007
		 * 
		 * 
		 * @param filePath
		 * @return
		 * @see [类、类#方法、类#成员]
		 */
		public static boolean isExcel2007(String filePath) {
			return filePath.matches("^.+\\.(?i)(xlsx)$");

		}

	}

	public static class DateUtil {

		// ======================日期格式化常量=====================//

		public static final String YYYY_MM_DDHHMMSS = "yyyy-MM-dd HH:mm:ss";

		public static final String YYYY_MM_DD = "yyyy-MM-dd";

		public static final String YYYY_MM = "yyyy-MM";

		public static final String YYYY = "yyyy";

		public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

		public static final String YYYYMMDD = "yyyyMMdd";

		public static final String YYYYMM = "yyyyMM";

		public static final String YYYYMMDDHHMMSS_1 = "yyyy/MM/dd HH:mm:ss";

		public static final String YYYY_MM_DD_1 = "yyyy/MM/dd";

		public static final String YYYY_MM_1 = "yyyy/MM";

		/**
		 * 
		 * 自定义取值，Date类型转为String类型
		 * 
		 * @param date
		 *            日期
		 * @param pattern
		 *            格式化常量
		 * @return
		 * @see [类、类#方法、类#成员]
		 */
		public static String dateToStr(Date date, String pattern) {
			SimpleDateFormat format = null;

			if (null == date)
				return null;
			format = new SimpleDateFormat(pattern, Locale.getDefault());

			return format.format(date);
		}

		/**
		 * 将字符串转换成Date类型的时间
		 * <hr>
		 * 
		 * @param s
		 *            日期类型的字符串<br>
		 *            datePattern :YYYY_MM_DD<br>
		 * @return java.util.Date
		 */
		public static Date strToDate(String s, String pattern) {
			if (s == null) {
				return null;
			}
			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				date = sdf.parse(s);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
	}

	// 以上为注解形式的导入
	// =============================================================================================
	// 以下为另一个导入导出的方式
	/*
	 * public static String NO_DEFINE = "no_define";//未定义的字段 public static
	 * String DEFAULT_DATE_PATTERN="yyyy-MM-dd";//默认日期格式 public static int
	 * DEFAULT_COLOUMN_WIDTH = 17;
	 * 
	 * private final static String excel2003L =".xls"; //2003- 版本的excel private
	 * final static String excel2007U =".xlsx"; //2007+ 版本的excel
	 *//**
		 * Excel导入
		 */
	/*
	 * public static List<List<Object>> getBankListByExcel(InputStream in,
	 * String fileName) throws Exception{ List<List<Object>> list = null;
	 * //创建Excel工作薄 Workbook work = getWorkbook(in,fileName); if(null == work){
	 * throw new Exception("创建Excel工作薄为空！"); } Sheet sheet = null; Row row =
	 * null; Cell cell = null; list = new ArrayList<List<Object>>();
	 * //遍历Excel中所有的sheet for (int i = 0; i < work.getNumberOfSheets(); i++) {
	 * sheet = work.getSheetAt(i); if(sheet==null){continue;} //遍历当前sheet中的所有行
	 * //包涵头部，所以要小于等于最后一列数,这里也可以在初始值加上头部行数，以便跳过头部 for (int j =
	 * sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) { //读取一行 row =
	 * sheet.getRow(j); //去掉空行和表头
	 * if(row==null||row.getFirstCellNum()==j){continue;} //遍历所有的列 List<Object>
	 * li = new ArrayList<Object>(); for (int y = row.getFirstCellNum(); y <
	 * row.getLastCellNum(); y++) { cell = row.getCell(y);
	 * li.add(getCellValue(cell)); } list.add(li); } } return list; }
	 *//**
		 * 描述：根据文件后缀，自适应上传文件的版本
		 */
	/*
	 * public static Workbook getWorkbook(InputStream inStr,String fileName)
	 * throws Exception{ Workbook wb = null; String fileType =
	 * fileName.substring(fileName.lastIndexOf("."));
	 * if(excel2003L.equals(fileType)){ wb = new HSSFWorkbook(inStr); //2003-
	 * }else if(excel2007U.equals(fileType)){ wb = new XSSFWorkbook(inStr);
	 * //2007+ }else{ throw new Exception("解析的文件格式有误！"); } return wb; }
	 *//**
		 * 描述：对表格中数值进行格式化
		 */
	/*
	 * public static Object getCellValue2(Cell cell){ Object value = null;
	 * DecimalFormat df = new DecimalFormat("0"); //格式化字符类型的数字 SimpleDateFormat
	 * sdf = new SimpleDateFormat("yyy-MM-dd"); //日期格式化 DecimalFormat df2 = new
	 * DecimalFormat("0.00"); //格式化数字 switch (cell.getCellType()) { case STRING:
	 * value = cell.getRichStringCellValue().getString(); break; case NUMERIC:
	 * if("General".equals(cell.getCellStyle().getDataFormatString())){ value =
	 * df.format(cell.getNumericCellValue()); }else
	 * if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){ value =
	 * sdf.format(cell.getDateCellValue()); }else{ value =
	 * df2.format(cell.getNumericCellValue()); } break; case BOOLEAN: value =
	 * cell.getBooleanCellValue(); break; case BLANK: value = ""; break;
	 * default: break; } return value; }
	 * 
	 * 
	 *//**
		 * 导出Excel 97(.xls)格式 ，少量数据
		 * 
		 * @param title
		 *            标题行
		 * @param headMap
		 *            属性-列名
		 * @param jsonArray
		 *            数据集
		 * @param datePattern
		 *            日期格式，null则用默认日期格式
		 * @param colWidth
		 *            列宽 默认 至少17个字节
		 * @param out
		 *            输出流
		 */
	/*
	 * public static void exportExcel(Map<String, String> headMap,JSONArray
	 * jsonArray,String datePattern,int colWidth, OutputStream out) {
	 * if(datePattern==null) datePattern = DEFAULT_DATE_PATTERN; // 声明一个工作薄
	 * HSSFWorkbook workbook = new HSSFWorkbook();
	 * workbook.createInformationProperties();
	 * workbook.getDocumentSummaryInformation().setCompany("*****公司");
	 * 
	 * //表头样式 HSSFCellStyle titleStyle = workbook.createCellStyle();
	 * titleStyle.setAlignment(HorizontalAlignment.CENTER); HSSFFont titleFont =
	 * workbook.createFont(); titleFont.setFontHeightInPoints((short) 20);
	 * titleFont.setBold(true); titleStyle.setFont(titleFont);
	 * 
	 * // 列头样式 HSSFCellStyle headerStyle = workbook.createCellStyle();
	 * headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 * headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 * headerStyle.setBorderBottom(BorderStyle.THIN);
	 * headerStyle.setBorderLeft(BorderStyle.THIN);
	 * headerStyle.setBorderRight(BorderStyle.THIN);
	 * headerStyle.setBorderTop(BorderStyle.THIN);
	 * headerStyle.setAlignment(HorizontalAlignment.CENTER); HSSFFont headerFont
	 * = workbook.createFont(); headerFont.setFontHeightInPoints((short) 12);
	 * headerFont.setBold(true); headerStyle.setFont(headerFont);
	 * 
	 * // 单元格样式 HSSFCellStyle cellStyle = workbook.createCellStyle();
	 * cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 * cellStyle.setBorderBottom(BorderStyle.THIN);
	 * headerStyle.setBorderLeft(BorderStyle.THIN);
	 * headerStyle.setBorderRight(BorderStyle.THIN);
	 * headerStyle.setBorderTop(BorderStyle.THIN);
	 * cellStyle.setAlignment(HorizontalAlignment.CENTER);
	 * cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); HSSFFont
	 * cellFont = workbook.createFont(); cellFont.setBold(true);
	 * cellStyle.setFont(cellFont);
	 * 
	 * // 生成一个(带标题)表格 HSSFSheet sheet = workbook.createSheet(); // 声明一个画图的顶级管理器
	 * HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); //
	 * 定义注释的大小和位置,详见文档 HSSFComment comment = patriarch.createComment(new
	 * HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5)); // 设置注释内容
	 * comment.setString(new HSSFRichTextString("可以在POI中添加注释！")); //
	 * 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容. comment.setAuthor("JACK"); //设置列宽 int
	 * minBytes =
	 * colWidth<DEFAULT_COLOUMN_WIDTH?DEFAULT_COLOUMN_WIDTH:colWidth;//至少字节数
	 * int[] arrColWidth = new int[headMap.size()]; // 产生表格标题行,以及设置列宽 String[]
	 * properties = new String[headMap.size()]; String[] headers = new
	 * String[headMap.size()]; int ii = 0; for (Iterator<String> iter =
	 * headMap.keySet().iterator(); iter .hasNext();) { String fieldName =
	 * iter.next();
	 * 
	 * properties[ii] = fieldName; headers[ii] = fieldName;
	 * 
	 * int bytes = fieldName.getBytes().length; arrColWidth[ii] = bytes <
	 * minBytes ? minBytes : bytes;
	 * sheet.setColumnWidth(ii,arrColWidth[ii]*256); ii++; } // 遍历集合数据，产生数据行 int
	 * rowIndex = 0; for (Object obj : jsonArray) { if(rowIndex == 65535 ||
	 * rowIndex == 0){ if ( rowIndex != 0 ) sheet =
	 * workbook.createSheet();//如果数据超过了，则在第二页显示
	 * 
	 * HSSFRow headerRow = sheet.createRow(0); //列头 rowIndex =0 for(int
	 * i=0;i<headers.length;i++) {
	 * headerRow.createCell(i).setCellValue(headers[i]);
	 * headerRow.getCell(i).setCellStyle(headerStyle);
	 * 
	 * } rowIndex = 1;//数据内容从 rowIndex=1开始 } JSONObject jo = (JSONObject)
	 * JSONObject.toJSON(obj); HSSFRow dataRow = sheet.createRow(rowIndex); for
	 * (int i = 0; i < properties.length; i++) { HSSFCell newCell =
	 * dataRow.createCell(i);
	 * 
	 * Object o = jo.get(properties[i]); String cellValue = ""; if(o==null)
	 * cellValue = ""; else if(o instanceof Date) cellValue = new
	 * SimpleDateFormat(datePattern).format(o); else cellValue = o.toString();
	 * 
	 * newCell.setCellValue(cellValue); newCell.setCellStyle(cellStyle); }
	 * rowIndex++; } // 自动调整宽度 for (int i = 0; i < headers.length; i++) {
	 * sheet.autoSizeColumn(i); } try { workbook.write(out); //
	 * workbook.close(); } catch (IOException e) { e.printStackTrace(); } }
	 * 
	 *//**
		 * 导出Excel 2007 OOXML (.xlsx)格式
		 * 
		 * @param title
		 *            标题行
		 * @param headMap
		 *            属性-列头
		 * @param jsonArray
		 *            数据集
		 * @param datePattern
		 *            日期格式，传null值则默认 年月日
		 * @param colWidth
		 *            列宽 默认 至少17个字节
		 * @param out
		 *            输出流
		 */
	/*
	 * public static void exportExcelX(Map<String, String> headMap,JSONArray
	 * jsonArray,String datePattern,int colWidth, OutputStream out) {
	 * if(datePattern==null) datePattern = DEFAULT_DATE_PATTERN; // 声明一个工作薄
	 * SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
	 * workbook.setCompressTempFiles(true);
	 * 
	 * //表头样式 CellStyle titleStyle = workbook.createCellStyle();
	 * titleStyle.setAlignment(HorizontalAlignment.CENTER); Font titleFont =
	 * workbook.createFont(); titleFont.setFontHeightInPoints((short) 20);
	 * titleFont.setBold(true);; titleStyle.setFont(titleFont);
	 * 
	 * // 列头样式 // 列头样式 HSSFCellStyle headerStyle = (HSSFCellStyle)
	 * workbook.createCellStyle();
	 * headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 * headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 * headerStyle.setBorderBottom(BorderStyle.THIN);
	 * headerStyle.setBorderLeft(BorderStyle.THIN);
	 * headerStyle.setBorderRight(BorderStyle.THIN);
	 * headerStyle.setBorderTop(BorderStyle.THIN);
	 * headerStyle.setAlignment(HorizontalAlignment.CENTER); HSSFFont headerFont
	 * = (HSSFFont) workbook.createFont();
	 * headerFont.setFontHeightInPoints((short) 12); headerFont.setBold(true);
	 * headerStyle.setFont(headerFont);
	 * 
	 * // 单元格样式 HSSFCellStyle cellStyle = (HSSFCellStyle)
	 * workbook.createCellStyle();
	 * cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 * cellStyle.setBorderBottom(BorderStyle.THIN);
	 * headerStyle.setBorderLeft(BorderStyle.THIN);
	 * headerStyle.setBorderRight(BorderStyle.THIN);
	 * headerStyle.setBorderTop(BorderStyle.THIN);
	 * cellStyle.setAlignment(HorizontalAlignment.CENTER);
	 * cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); HSSFFont
	 * cellFont = (HSSFFont) workbook.createFont(); cellFont.setBold(true);
	 * cellStyle.setFont(cellFont);
	 * 
	 * // 生成一个(带标题)表格 SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();
	 * //设置列宽 int minBytes =
	 * colWidth<DEFAULT_COLOUMN_WIDTH?DEFAULT_COLOUMN_WIDTH:colWidth;//至少字节数
	 * int[] arrColWidth = new int[headMap.size()]; // 产生表格标题行,以及设置列宽 String[]
	 * properties = new String[headMap.size()]; String[] headers = new
	 * String[headMap.size()]; int ii = 0; for (Iterator<String> iter =
	 * headMap.keySet().iterator(); iter .hasNext();) { String fieldName =
	 * iter.next();
	 * 
	 * properties[ii] = fieldName; headers[ii] = headMap.get(fieldName);
	 * 
	 * int bytes = fieldName.getBytes().length; arrColWidth[ii] = bytes <
	 * minBytes ? minBytes : bytes;
	 * sheet.setColumnWidth(ii,arrColWidth[ii]*256); ii++; } // 遍历集合数据，产生数据行 int
	 * rowIndex = 0; for (Object obj : jsonArray) { if(rowIndex == 65535 ||
	 * rowIndex == 0){ if ( rowIndex != 0 ) sheet = (SXSSFSheet)
	 * workbook.createSheet();//如果数据超过了，则在第二页显示
	 * 
	 * SXSSFRow headerRow = (SXSSFRow) sheet.createRow(0); //列头 rowIndex =0
	 * for(int i=0;i<headers.length;i++) {
	 * headerRow.createCell(i).setCellValue(headers[i]);
	 * headerRow.getCell(i).setCellStyle(headerStyle);
	 * 
	 * } rowIndex = 1;//数据内容从 rowIndex=1开始 } JSONObject jo = (JSONObject)
	 * JSONObject.toJSON(obj); SXSSFRow dataRow = (SXSSFRow)
	 * sheet.createRow(rowIndex); for (int i = 0; i < properties.length; i++) {
	 * SXSSFCell newCell = (SXSSFCell) dataRow.createCell(i);
	 * 
	 * Object o = jo.get(properties[i]); String cellValue = ""; if(o==null)
	 * cellValue = ""; else if(o instanceof Date) cellValue = new
	 * SimpleDateFormat(datePattern).format(o); else if(o instanceof Float || o
	 * instanceof Double) cellValue= new
	 * BigDecimal(o.toString()).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
	 * else cellValue = o.toString();
	 * 
	 * newCell.setCellValue(cellValue); newCell.setCellStyle(cellStyle); }
	 * rowIndex++; } // 自动调整宽度 for (int i = 0; i < headers.length; i++) {
	 * sheet.autoSizeColumn(i); } try { workbook.write(out); //
	 * workbook.close(); workbook.dispose(); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 * 
	 * 
	 * 
	 * //Web 导出excel public static void downloadExcelFile(String
	 * title,Map<String,String> headMap,JSONArray ja,HttpServletResponse
	 * response){ try { ByteArrayOutputStream os = new ByteArrayOutputStream();
	 * ExeclUtil.exportExcelX(headMap,ja,null,0,os); byte[] content =
	 * os.toByteArray(); InputStream is = new ByteArrayInputStream(content); //
	 * 设置response参数，可以打开下载页面 response.reset();
	 * 
	 * response.setContentType(
	 * "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
	 * ); response.setHeader("Content-Disposition", "attachment;filename="+ new
	 * String((title + ".xlsx").getBytes(), "iso-8859-1"));
	 * response.setContentLength(content.length); ServletOutputStream
	 * outputStream = response.getOutputStream(); BufferedInputStream bis = new
	 * BufferedInputStream(is); BufferedOutputStream bos = new
	 * BufferedOutputStream(outputStream); byte[] buff = new byte[8192]; int
	 * bytesRead; while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	 * bos.write(buff, 0, bytesRead);
	 * 
	 * } bis.close(); bos.close(); outputStream.flush(); outputStream.close();
	 * }catch (Exception e) { e.printStackTrace(); } }
	 */}
