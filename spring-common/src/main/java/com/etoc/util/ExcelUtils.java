package com.etoc.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;*/
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelException;

/**
 * 
 * Excel导出封装类 分利用反射和JSONObject(POI版本为最新版3.17) <功能详细描述>
 *
 * @author chuyh
 * @version [版本号, 2018年8月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ExcelUtils<T> {
	public static String NO_DEFINE = "no_define";// 未定义的字段
	public static String DEFAULT_DATE_PATTERN = "yyyy年MM月dd日";// 默认日期格式
	public static int DEFAULT_COLOUMN_WIDTH = 17;

	/**
	 * 基于Excel 2007模板写入数据(利用java反射)
	 * 
	 * @Title: writeExcel
	 * @param：@param file 模板文件
	 * @param：@param dataSet 数据集
	 * @param：@throws IOException
	 * @param：@throws NoSuchMethodException
	 * @param：@throws SecurityException
	 * @param：@throws InvocationTargetException
	 * @return：void
	 */
	@SuppressWarnings("unused")
	public void writeExcel(File file, Collection<T> dataSet, String name) throws IOException, NoSuchMethodException, SecurityException, InvocationTargetException {
		// XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(name);
		// 写入内容
		Iterator<T> iterator = dataSet.iterator();
		int index = 1;
		while (iterator.hasNext()) {
			XSSFRow row = sheet.createRow(index);
			T t = (T) iterator.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();

			for (short i = 0; i < fields.length; i++) {
				if (i == 0) {
					XSSFCell cell = row.createCell(i);
					cell.setCellValue(index);
					cell = row.createCell(i + 1);
					fields[i].setAccessible(true);
					try {
						String fieldName = fields[i].getName();
						String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						Object valueObject = fields[i].get(t);
						Class<? extends Object> tCls = t.getClass();
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						if (valueObject instanceof String) {
							cell.setCellValue(valueObject.toString());
						} else {
							cell.setCellValue(valueObject + "");
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else {
					XSSFCell cell = row.createCell(i + 1);
					fields[i].setAccessible(true);
					try {
						String fieldName = fields[i].getName();
						String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						Object valueObject = fields[i].get(t);
						Class<? extends Object> tCls = t.getClass();
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						if (valueObject instanceof String) {
							if (valueObject == null) {
								cell.setCellValue("");
							} else {
								cell.setCellValue(valueObject.toString());
							}
						} else if (valueObject instanceof BigDecimal) {
							BigDecimal vDecimal = (BigDecimal) value;
							cell.setCellValue(vDecimal.doubleValue());
						} else if (valueObject instanceof Integer) {
							cell.setCellValue((Integer) valueObject);
						} else if (valueObject instanceof Double) {
							cell.setCellValue((Double) valueObject);
						} else {
							if (valueObject == null) {
								cell.setCellValue("");
							} else {
								cell.setCellValue(valueObject.toString());
							}

						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			index++;
		}
		OutputStream outputStream = new FileOutputStream(file);
		workbook.write(outputStream);
		outputStream.close();
	}

	/**
     * 基于Excel 2007模板带标题写入数据(利用java反射)
     * 
     * @Title: writeExcel 
     * @param：@param file 模板文件 
     * @param：@param dataSet 数据集 
     * @param：@param dataSet 第一列标题集合
     * @param：@throws IOException 
     * @param：@throws NoSuchMethodException
     * @param：@throws SecurityException 
     * @param：@throws InvocationTargetException 
     * @return：void  
     */
    @SuppressWarnings("unused")
    public void writeExcel(File file, Collection<T> dataSet,String[] titles,String name)
            throws IOException, NoSuchMethodException, SecurityException, InvocationTargetException {
        //XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(name);
        // 写入内容
        Iterator<T> iterator = dataSet.iterator();
        //第一行写入标题
        XSSFRow firstRow = sheet.createRow(0);
        for(int t=0;t<titles.length;t++) {
            XSSFCell cell = firstRow.createCell(t);
            cell.setCellValue(titles[t]);
        }
        
        int index = 1;
        while (iterator.hasNext()) {
            XSSFRow row = sheet.createRow(index);
            T t = (T) iterator.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();

            for (short i = 0; i < fields.length; i++) {
                if (i == 0) {
                    XSSFCell cell = row.createCell(i);
                    cell.setCellValue(index);
                    cell = row.createCell(i + 1);
                    fields[i].setAccessible(true);
                    try {
                        String fieldName = fields[i].getName();
                        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Object valueObject = fields[i].get(t);
                        Class<? extends Object> tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                        Object value = getMethod.invoke(t, new Object[] {});
                        if (valueObject instanceof String) {
                            cell.setCellValue(valueObject.toString());
                        } else {
                            cell.setCellValue(valueObject + "");
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    XSSFCell cell = row.createCell(i + 1);
                    fields[i].setAccessible(true);
                    try {
                        String fieldName = fields[i].getName();
                        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Object valueObject = fields[i].get(t);
                        Class<? extends Object> tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                        Object value = getMethod.invoke(t, new Object[] {});
                        if (valueObject instanceof String) {
                            if (valueObject == null) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue(valueObject.toString());
                            }
                        } else if (valueObject instanceof BigDecimal) {
                            BigDecimal vDecimal = (BigDecimal) value;
                            cell.setCellValue(vDecimal.doubleValue());
                        } else if (valueObject instanceof Integer) {
                            cell.setCellValue((Integer) valueObject);
                        } else if (valueObject instanceof Double) {
                            cell.setCellValue((Double) valueObject);
                        } else {
                            if (valueObject == null) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue(valueObject.toString());
                            }

                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            index++;
        }
        OutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
    }
	/**
	 * 下载Excel
	 * 
	 * @param request
	 * @param response
	 * @param list     要导出的数据
	 * @param model    模板名称
	 * @param name     导出Excel文件名
	 * @return
	 * @throws IOException
	 */
	public void download(HttpServletRequest request, HttpServletResponse response, List<T> list, String model, String name) throws IOException {
		ServletOutputStream out = null;
		FileInputStream inputStream = null;
		try {
			response.setContentType("multipart/form-data");
			String path = ExcelUtils.class.getResource("").getPath().substring(1);// 获取模板路径
			// path += model + ".xlsx";// excel模板
			String fileName = name + "_" + DateUtil.convertDate2String(DateUtil.currentDate(), DateUtil.DATE_FORMAT) + ".xlsx";
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
			// FileUtils.Copy(path, path + fileName);
			File file = new File(path + fileName);
			writeExcel(file, list, name);// 组装数据
			out = response.getOutputStream();
			inputStream = new FileInputStream(file);
			int b = 0;
			byte[] buffer = new byte[4096];
			while ((b = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
			out.close();
			out.flush();
		}
	}

	/**
	 * 基于Excel 2003模板写入数据(利用java反射) <功能详细描述>
	 * 
	 * @param file    文件
	 * @param dataSet 数据集合
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unused")
	public void writeExcel2003(File file, Collection<T> dataSet) throws IOException, NoSuchMethodException, SecurityException, InvocationTargetException {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = workbook.getSheetAt(0);

		// 写入内容
		Iterator<T> iterator = dataSet.iterator();
		int index = 1;
		while (iterator.hasNext()) {
			HSSFRow row = sheet.createRow(index);
			T t = (T) iterator.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();

			for (short i = 0; i < fields.length; i++) {
				if (i == 0) {
					@SuppressWarnings("deprecation")
					HSSFCell cell = row.createCell(i);
					cell.setCellValue(index);
					cell = row.createCell(i + 1);
					fields[i].setAccessible(true);
					try {
						String fieldName = fields[i].getName();
						String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						Object valueObject = fields[i].get(t);

						Class<? extends Object> tCls = t.getClass();
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						if (valueObject instanceof String) {
							cell.setCellValue(valueObject.toString());
						} else {
							cell.setCellValue(valueObject + "");
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else {
					HSSFCell cell = row.createCell(i + 1);
					fields[i].setAccessible(true);
					try {
						String fieldName = fields[i].getName();
						String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						Object valueObject = fields[i].get(t);

						Class<? extends Object> tCls = t.getClass();
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						if (valueObject instanceof String) {
							if (valueObject == null) {
								cell.setCellValue("");
							} else {
								cell.setCellValue(valueObject.toString());
							}

						} else if (valueObject instanceof BigDecimal) {
							BigDecimal vDecimal = (BigDecimal) value;
							cell.setCellValue(vDecimal.doubleValue());
						} else if (valueObject instanceof Integer) {
							cell.setCellValue((Integer) valueObject);
						} else if (valueObject instanceof Double) {
							cell.setCellValue((Double) valueObject);
						} else {
							if (valueObject == null) {
								cell.setCellValue("");
							} else {
								cell.setCellValue(valueObject.toString());
							}

						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			index++;
		}
		OutputStream outputStream = new FileOutputStream(file);
		workbook.write(outputStream);

		outputStream.close();
	}

	/**
	 * 导出Excel 97(.xls)格式 ，少量数据
	 * 
	 * @param title       标题行
	 * @param headMap     属性-列名
	 * @param jsonArray   数据集
	 * @param datePattern 日期格式，null则用默认日期格式
	 * @param colWidth    列宽 默认 至少17个字节
	 * @param out         输出流
	 */
	public static void exportExcel(String title, Map<String, String> headMap, JSONArray jsonArray, String datePattern, int colWidth, OutputStream out) {
		if (datePattern == null)
			datePattern = DEFAULT_DATE_PATTERN;
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		workbook.createInformationProperties();
		/*
		 * workbook.getDocumentSummaryInformation().setCompany("*****公司");
		 * SummaryInformation si = workbook.getSummaryInformation();
		 * si.setAuthor("chuyh"); // 填加xls文件作者信息 si.setApplicationName("导出程序"); //
		 * 填加xls文件创建程序信息 si.setLastAuthor("最后保存者信息"); // 填加xls文件最后保存者信息
		 * si.setComments("chuyh is a programmer!"); // 填加xls文件作者信息
		 * si.setTitle("POI导出Excel"); // 填加xls文件标题信息 si.setSubject("POI导出Excel");//
		 * 填加文件主题信息 si.setCreateDateTime(new Date());
		 */
		// 表头样式
		HSSFCellStyle titleStyle = workbook.createCellStyle();
//		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		HSSFFont titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 20);
//		titleFont.setBold(true);// 设置加粗
		titleStyle.setFont(titleFont);
		// 列头样式
		HSSFCellStyle headerStyle = workbook.createCellStyle();
//		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerStyle.setBorderTop(CellStyle.BORDER_THIN);
//		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		HSSFFont headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 12);
//		headerFont.setBold(true);
		headerStyle.setFont(headerFont);
		// 单元格样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		// cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
//		cellStyle.setAlignment(HorizontalAlignment.CENTER);
//		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont cellFont = workbook.createFont();
//		cellFont.setBold(true);
		cellStyle.setFont(cellFont);
		// 生成一个(带标题)表格
		HSSFSheet sheet = workbook.createSheet();
		// 声明一个画图的顶级管理器
		/*
		 * HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); // 定义注释的大小和位置,详见文档
		 * HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0,
		 * 0, (short) 4, 2, (short) 6, 5)); // 设置注释内容 comment.setString(new
		 * HSSFRichTextString("可以在POI中添加注释！")); // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		 * comment.setAuthor("chuyh");
		 */
		// 设置列宽
		int minBytes = colWidth < DEFAULT_COLOUMN_WIDTH ? DEFAULT_COLOUMN_WIDTH : colWidth;// 至少字节数
		int[] arrColWidth = new int[headMap.size()];
		// 产生表格标题行,以及设置列宽
		String[] properties = new String[headMap.size()];
		String[] headers = new String[headMap.size()];
		int ii = 0;
		for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();) {
			String fieldName = iter.next();

			properties[ii] = fieldName;
			headers[ii] = fieldName;

			int bytes = fieldName.getBytes().length;
			arrColWidth[ii] = bytes < minBytes ? minBytes : bytes;
			sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
			ii++;
		}
		// 遍历集合数据，产生数据行
		int rowIndex = 0;
		for (Object obj : jsonArray) {
			if (rowIndex == 65535 || rowIndex == 0) {
				if (rowIndex != 0)
					sheet = workbook.createSheet();// 如果数据超过了，则在第二页显示

				HSSFRow titleRow = sheet.createRow(0);// 表头 rowIndex=0
				titleRow.createCell(0).setCellValue(title);
				titleRow.getCell(0).setCellStyle(titleStyle);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));

				HSSFRow headerRow = sheet.createRow(1); // 列头 rowIndex =1
				for (int i = 0; i < headers.length; i++) {
					headerRow.createCell(i).setCellValue(headers[i]);
					headerRow.getCell(i).setCellStyle(headerStyle);

				}
				rowIndex = 2;// 数据内容从 rowIndex=2开始
			}
			JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
			HSSFRow dataRow = sheet.createRow(rowIndex);
			for (int i = 0; i < properties.length; i++) {
				HSSFCell newCell = dataRow.createCell(i);

				Object o = jo.get(properties[i]);
				String cellValue = "";
				if (o == null)
					cellValue = "";
				else if (o instanceof Date)
					cellValue = new SimpleDateFormat(datePattern).format(o);
				else
					cellValue = o.toString();

				newCell.setCellValue(cellValue);
				newCell.setCellStyle(cellStyle);
			}
			rowIndex++;
		}
		// 自动调整宽度
		/*
		 * for (int i = 0; i < headers.length; i++) { sheet.autoSizeColumn(i); }
		 */
		try {
			workbook.write(out);
			// workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出Excel 2007 OOXML (.xlsx)格式
	 * 
	 * @param title       标题行
	 * @param headMap     属性-列头
	 * @param jsonArray   数据集
	 * @param datePattern 日期格式，传null值则默认 年月日
	 * @param colWidth    列宽 默认 至少17个字节
	 * @param out         输出流
	 */
	public static void exportExcelX(String title, Map<String, String> headMap, JSONArray jsonArray, String datePattern, int colWidth, OutputStream out) {
		if (datePattern == null)
			datePattern = DEFAULT_DATE_PATTERN;
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);// 缓存
		workbook.setCompressTempFiles(true);
		// 表头样式
		CellStyle titleStyle = workbook.createCellStyle();
//		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		Font titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 20);
//		titleFont.setBold(true);
		titleStyle.setFont(titleFont);
		// 列头样式
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerStyle.setBorderTop(CellStyle.BORDER_THIN);
//		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 12);
//		headerFont.setBold(true);
		headerStyle.setFont(headerFont);
		// 单元格样式
		CellStyle cellStyle = workbook.createCellStyle();
		// cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
//		cellStyle.setAlignment(HorizontalAlignment.CENTER);
//		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		Font cellFont = workbook.createFont();
//		cellFont.setBold(true);
		cellStyle.setFont(cellFont);
		// 生成一个(带标题)表格
//		SXSSFSheet sheet = workbook.createSheet();
		// 设置列宽
		int minBytes = colWidth < DEFAULT_COLOUMN_WIDTH ? DEFAULT_COLOUMN_WIDTH : colWidth;// 至少字节数
		int[] arrColWidth = new int[headMap.size()];
		// 产生表格标题行,以及设置列宽
		String[] properties = new String[headMap.size()];
		String[] headers = new String[headMap.size()];
		int ii = 0;
		for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();) {
			String fieldName = iter.next();

			properties[ii] = fieldName;
			headers[ii] = headMap.get(fieldName);

			int bytes = fieldName.getBytes().length;
			arrColWidth[ii] = bytes < minBytes ? minBytes : bytes;
//			sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
			ii++;
		}
		// 遍历集合数据，产生数据行
		int rowIndex = 0;
		for (Object obj : jsonArray) {
			if (rowIndex == 65535 || rowIndex == 0) {
				if (rowIndex != 0)
//					sheet = workbook.createSheet();// 如果数据超过了，则在第二页显示

//				SXSSFRow titleRow = sheet.createRow(0);// 表头 rowIndex=0
//				titleRow.createCell(0).setCellValue(title);
//				titleRow.getCell(0).setCellStyle(titleStyle);
//				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));

//				SXSSFRow headerRow = sheet.createRow(1); // 列头 rowIndex =1
//				for (int i = 0; i < headers.length; i++) {
//					headerRow.createCell(i).setCellValue(headers[i]);
//					headerRow.getCell(i).setCellStyle(headerStyle);
//
//				}
					rowIndex = 2;// 数据内容从 rowIndex=2开始
			}
			JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
//			SXSSFRow dataRow = (SXSSFRow) sheet.createRow(rowIndex);
			for (int i = 0; i < properties.length; i++) {
//				SXSSFCell newCell = (SXSSFCell) dataRow.createCell(i);

				Object o = jo.get(properties[i]);
				String cellValue = "";
				if (o == null)
					cellValue = "";
				else if (o instanceof Date)
					cellValue = new SimpleDateFormat(datePattern).format(o);
				else if (o instanceof Float || o instanceof Double)
					cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				else
					cellValue = o.toString();

//				newCell.setCellValue(cellValue);
//				newCell.setCellStyle(cellStyle);
			}
			rowIndex++;
		}
		// 自动调整宽度
		/*
		 * for (int i = 0; i < headers.length; i++) { sheet.autoSizeColumn(i); }
		 */
		try {
			workbook.write(out);
//			workbook.close();
//			workbook.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 2007版本excel数据导出 web方式 <功能详细描述>
	 * 
	 * @param title    标题
	 * @param headMap  属性-列头 必须和对象中的列名称一致
	 * @param ja       json数组
	 * @param response
	 * @see [类、类#方法、类#成员]
	 */
	public static void downloadExcelFile(String fileName, String title, Map<String, String> headMap, JSONArray ja, HttpServletResponse response) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ExcelUtils.exportExcelX(title, headMap, ja, null, 0, os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
			response.setContentLength(content.length);
			ServletOutputStream outputStream = response.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(outputStream);
			byte[] buff = new byte[8192];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);

			}
			bis.close();
			bos.close();
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// public static void main(String[] args) throws IOException {
	// int count = 100000;
	// JSONArray ja = new JSONArray();
	// for(int i=0;i<100000;i++){
	// Student s = new Student();
	// s.setName("POI"+i);
	// s.setAge(i);
	// s.setBirthday(new Date());
	// s.setHeight(i);
	// s.setWeight(i);
	// s.setSex(i/2==0?false:true);
	// ja.add(s);
	// }
	// Map<String,String> headMap = new LinkedHashMap<String,String>();
	// headMap.put("name","姓名");
	// headMap.put("age","年龄");
	// headMap.put("birthday","生日");
	// headMap.put("height","身高");
	// headMap.put("weight","体重");
	// headMap.put("sex","性别");
	//
	// String title = "测试";
	// /*
	// OutputStream outXls = new FileOutputStream("E://a.xls");
	// System.out.println("正在导出xls....");
	// Date d = new Date();
	// ExcelUtil.exportExcel(title,headMap,ja,null,outXls);
	// System.out.println("共"+count+"条数据,执行"+(new
	// Date().getTime()-d.getTime())+"ms");
	// outXls.close();*/
	// //
	// OutputStream outXlsx = new FileOutputStream("E://b.xlsx");
	// System.out.println("正在导出xlsx....");
	// Date d2 = new Date();
	// ExcelUtil.exportExcelX(title,headMap,ja,null,0,outXlsx);
	// System.out.println("共"+count+"条数据,执行"+(new
	// Date().getTime()-d2.getTime())+"ms");
	// outXlsx.close();
	//
	// }

	/**
	 * 创建Excel表格
	 * 
	 * @param sheetName sheet名称
	 * @param title     标题
	 * @param values    内容
	 * @param wb        HSSFWorkbook对象
	 * @return
	 */
	public static void exportHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb, String filePath, String fileName) {

		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if (wb == null) {
			wb = new HSSFWorkbook();
		}

		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);

		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
		HSSFRow row = sheet.createRow(0);

		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		// 声明列对象
		HSSFCell cell = null;

		// 创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}

		// 创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < values[i].length; j++) {
				// 将内容按顺序赋给对应的列对象
				row.createCell(j).setCellValue(values[i][j]);
			}
		}

		FileOutputStream fileOutputStream = null;
		try {
			// 如果文件路径不存在则创建
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 在服务器上新建表格名称为fileName
			fileOutputStream = new FileOutputStream(new File(filePath, fileName));
			// 把表格值赋值到新建的fileName表格中
			wb.write(fileOutputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					throw new ChannelException("关闭excel文件流失败!", e);
				}
			}
		}
	}

	public static void download_new(String path, HttpServletResponse response) {
		response.setContentType("application/x-json;charset=UTF-8");// 设置response内容的类型
		response.setCharacterEncoding("UTF-8"); 
		try {
			File file = new File(path);// 构造要下载的文件
			if (file.exists()) {
				InputStream ins = new FileInputStream(file);// 构造一个读取文件的IO流对象
				BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
				OutputStream outs = response.getOutputStream();// 获取文件输出IO流
				BufferedOutputStream bouts = new BufferedOutputStream(outs);
//				response.setHeader("Content-disposition", "attachment;filename=" + file.getName());// 设置头部信息
				String codedFileName  = new String(file.getName().getBytes("gbk"), "iso8859-1");// 设置头部信息
				response.setHeader("Content-Disposition", "attachment;filename="+ codedFileName);
				int bytesRead = 0;
				byte[] buffer = new byte[102400];
				// 开始向网络传输文件流
				while ((bytesRead = bins.read(buffer, 0, 102400)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();// 这里一定要调用flush()方法
				ins.close();
				bins.close();
				outs.close();
				bouts.close();
			} else {
				throw new ChannelException("file not exist");
			}
		} catch (Exception e) {
			throw new ChannelException("下载失败！", e);
		}
	}

}
