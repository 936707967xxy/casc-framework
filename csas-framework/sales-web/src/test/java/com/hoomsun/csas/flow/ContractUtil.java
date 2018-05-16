package com.hoomsun.csas.flow;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SysRepaymentPlan;
import com.hoomsun.core.server.inter.SysContractServerI;
import com.hoomsun.core.server.inter.SysRepaymentPlanServerI;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;
//import com.dercent.olsys.model.core.BorrowContract;
//import com.dercent.olsys.model.core.Customer;
//import com.dercent.olsys.model.core.RepaymentPlan;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

/**
 * 合同的工具类
 * 
 * @author zouyiwei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-cfg.xml")
public class ContractUtil {
	public static Logger logger = LoggerFactory.getLogger(ContractUtil.class);

	public static final String PLACEHOLDER_PREFIX = "#{";
	public static final String PLACEHOLDER_SUFFIX = "}";
	@Autowired
	private SysContractServerI sysContractServer;
	@Autowired
	private UserApplyServerI userApplyServer;
	@Autowired
	private SysRepaymentPlanServerI repaymentPlanServer;

	/**
	 * 创建pdf文档
	 * 
	 * @param xmlModelName
	 *            xml文档的全路径 例如：D:/model/xxx.xml
	 * @param saveName
	 *            保存的文件路径 例如：D:/model/xxx.pdf
	 * @param fontPath
	 *            中文字体文件的全路径 例如：D:/model/msyh.ttf
	 * @param imagePath
	 *            水印图片的全路径 例如：D:/model/xxx.png
	 * @param params
	 *            填写的参数 可用 org.apache.commons.beanutils.PropertyUtils
	 *            PropertyUtils.describe(BeanObject)将对象转换为Map
	 * @return
	 */
	public static boolean createContractToPDF(String xmlModelName, String saveName, String fontPath, String imagePath, Map<String, Object> params) {
		try {
			// 创建xml解析器
			SAXReader reader = new SAXReader();
			// 获取xml文件 /com/dercent/olsys/ftl/common_contract.xml
			InputStream inputStream = new FileInputStream(new File(xmlModelName));
			org.dom4j.Document dom = reader.read(inputStream);
			// 获取跟节点
			Element root = dom.getRootElement();

			// 得到样式
			Element stylesElement = root.element("styles");
			Map<String, Style> styles = getStyle(stylesElement);

			// 得到名为content的节点 pagesize="A4" fontsize="12" margin-top="72"
			// margin-right="90" margin-bottom="72"
			// margin-left="90"
			Element content = root.element("content");
			PageSize pageSize = getPageSize(content.attributeValue("pagesize"));
			Float fontsize = parseFloat(content.attributeValue("fontsize"));
			Float marginTop = parseFloat(content.attributeValue("margin-top"));
			Float marginRight = parseFloat(content.attributeValue("margin-right"));
			Float marginBottom = parseFloat(content.attributeValue("margin-bottom"));
			Float marginLeft = parseFloat(content.attributeValue("margin-left"));

			// 创建文档
			PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);
			PdfDocument pdfDocument = new PdfDocument(new PdfWriter(saveName));
			pdfDocument.setDefaultPageSize(pageSize);
			Document document = new Document(pdfDocument);
			document.setFont(font);
			document.setFontSize(fontsize);
			document.setMargins(marginTop, marginRight, marginBottom, marginLeft);// 1磅约等于0.035厘米,1厘米约等于28.35磅。或者说，1英寸=72磅

			// 事件
			pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new WatermarkingEventHandler(imagePath, fontPath));

			Style defaultStyle = new Style();
			defaultStyle.setFont(font);
			defaultStyle.setFontSize(fontsize);

			// 得到code节点 <code value="合同编号" marginright=100 fontsize=14/>
			Element code = content.element("code");
			String value = code.attributeValue("value");
			String fill = code.attributeValue("fill");
			if (!StringUtils.isBlank(fill) && StringUtils.equals("true", fill)) {// 存在替换项目
																					// 并且为true
																					// 进行数据替换
				value = resolvePlaceholders(null, value, params);
			}
			Paragraph paragraph = new Paragraph(value);
			String style = code.attributeValue("style");
			setStyle(styles, defaultStyle, paragraph, style);
			document.add(paragraph);

			// 标题
			Element title = content.element("title");
			String titleValue = title.attributeValue("value");
			paragraph = new Paragraph(titleValue);
			style = title.attributeValue("style");
			setStyle(styles, defaultStyle, paragraph, style);
			document.add(paragraph);

			// 摘要
			// createAbstract(styles, content, document, defaultStyle, params);

			// 段落 paragraphs
			createParagraphs(styles, content, document, defaultStyle, params);

			// 附件 accessory
			createAccessory(styles, content, document, defaultStyle, params);

			document.flush();
			document.close();

			return true;
		} catch (Exception e) {
			logger.error("###创建PDF合同失败！" + e.getMessage());
			return false;
		}
	}

	/**
	 * 将文件装换为流
	 * 
	 * @param srcFile
	 * @return
	 */
	public static byte[] getFileStream(String srcFile) {
		byte[] bytes = null;
		File file = new java.io.File(srcFile);

		if (!file.exists()) {
			return null;
		}

		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fileInputStream.read(b)) != -1) {
				bos.write(b, 0, n);
			}

			fileInputStream.close();
			bos.close();
			bytes = bos.toByteArray();
		} catch (Exception e) {
			logger.error("####将file文件转换为Byte[]异常!" + e.getMessage());
			return null;
		}

		return bytes;
	}

	/**
	 * 将PDF流装换为文件
	 * 
	 * @param dstFile
	 * @param stream
	 */
	public static boolean createPdfFile(String dstFile, byte[] stream) {
		boolean b = false;
		OutputStream os = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			os = new FileOutputStream(new File(dstFile));
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream(stream.length);
			arrayOutputStream.write(stream);
			bufferedOutputStream = new BufferedOutputStream(os);
			bufferedOutputStream.write(stream);
			b = true;
		} catch (Exception e) {
			logger.error("###创建PDF文件失败！" + e.getMessage());
			b = false;
		} finally {
			try {
				bufferedOutputStream.flush();
				bufferedOutputStream.close();

				os.flush();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return b;
	}

	@Test
	public void test() {
		// byte[] bs = getFileStream("D:/zouyiwei_table1.pdf");
		// createPdfFile("D:/zouyiwei_table1_file.pdf", bs);
		String str = "D:/dercent.olsys.upload/contract/sign/20170119990005.pdf";
		str = str.substring(str.indexOf("/dercent.olsys.upload"));
		System.out.println(str);
	}

	@Test
	public void readerXmlModelData() {
		try {
			// 创建xml解析器
			SAXReader reader = new SAXReader();
			// 获取xml文件 /com/dercent/olsys/ftl/common_contract.xml
			org.dom4j.Document dom = reader.read(new FileInputStream(new File("D:/common_contract.xml")));
			// 获取跟节点
			Element root = dom.getRootElement();

			// 得到样式
			Element stylesElement = root.element("styles");
			Map<String, Style> styles = getStyle(stylesElement);

			// 得到名为content的节点 pagesize="A4" fontsize="12" margin-top="72"
			// margin-right="90" margin-bottom="72"
			// margin-left="90"
			Element content = root.element("content");
			PageSize pageSize = getPageSize(content.attributeValue("pagesize"));
			Float fontsize = parseFloat(content.attributeValue("fontsize"));
			Float marginTop = parseFloat(content.attributeValue("margin-top"));
			Float marginRight = parseFloat(content.attributeValue("margin-right"));
			Float marginBottom = parseFloat(content.attributeValue("margin-bottom"));
			Float marginLeft = parseFloat(content.attributeValue("margin-left"));

			// 创建文档
			PdfFont font = PdfFontFactory.createFont("D:/msyh.ttf", PdfEncodings.IDENTITY_H);
			PdfDocument pdfDocument = new PdfDocument(new PdfWriter("D:/zouyiwei_test.pdf"));
			pdfDocument.setDefaultPageSize(pageSize);
			Document document = new Document(pdfDocument);
			document.setFont(font);
			document.setFontSize(fontsize);
			document.setMargins(marginTop, marginRight, marginBottom, marginLeft);// 1磅约等于0.035厘米,1厘米约等于28.35磅。或者说，1英寸=72磅

			// 事件 添加页码和水印
			pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new WatermarkingEventHandler("D:/watermark .png", "D:/msyh.ttf"));

			Style defaultStyle = new Style();
			defaultStyle.setFont(font);
			defaultStyle.setFontSize(fontsize);

			Map<String, Object> data = createData();
			@SuppressWarnings("unchecked")
			// Map<String, Object> data = PropertyUtils.describe(sysContract);
			// 得到code节点 <code value="合同编号" marginright=100 fontsize=14/>
			Element code = content.element("code");
			String value = code.attributeValue("value");
			String fill = code.attributeValue("fill");
			if (!StringUtils.isBlank(fill) && StringUtils.equals("true", fill)) {// 存在替换项目
																					// 并且为true
																					// 进行数据替换
				value = resolvePlaceholders(null, value, data);
			}

			Paragraph paragraph = new Paragraph(value);
			String style = code.attributeValue("style");
			setStyle(styles, defaultStyle, paragraph, style);
			document.add(paragraph);

			// 标题
			Element title = content.element("title");
			String titleValue = title.attributeValue("value");
			paragraph = new Paragraph(titleValue);
			style = title.attributeValue("style");
			setStyle(styles, defaultStyle, paragraph, style);
			document.add(paragraph);

			// 摘要
			// createAbstract(styles, content, document, defaultStyle, data);

			// 段落 paragraphs
			createParagraphs(styles, content, document, defaultStyle, data);

			// 附件 accessory
			createAccessory(styles, content, document, defaultStyle, data);

			document.flush();
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 摘要的创建
	 * 
	 * @param styles
	 *            所有的pdf文档文档样式
	 * @param content
	 *            content Element 正文对象
	 * @param document
	 *            pdf文档对象
	 * @param defaultStyle
	 *            默认的pdf文档样式
	 * @param data
	 *            填充的数据
	 */
	// private static void createAbstract(Map<String, Style> styles, Element
	// content, Document document, Style defaultStyle,
	// Map<String, Object> data) {
	//
	// Element abstractEl = content.element("abstract");
	// List<Element> childs = abstractEl.elements();
	// for (Element e : childs) {
	// String text = e.getText();
	// String fill = e.attributeValue("fill");
	// if (!StringUtils.isBlank(fill) && StringUtils.equals("true", fill))
	// {//存在替换项目 并且为true 进行数据替换
	// text = resolvePlaceholders(null,text, data);
	// }
	// Paragraph paragraph = new Paragraph(text);
	// String style = e.attributeValue("style");
	// setStyle(styles, defaultStyle, paragraph, style);
	// document.add(paragraph);
	// }
	// }

	/**
	 * 写入段落
	 * 
	 * @param styles
	 *            所有的pdf文档文档样式
	 * @param content
	 *            content Element 正文对象
	 * @param document
	 *            pdf文档对象
	 * @param defaultStyle
	 *            默认的pdf文档样式
	 * @param data
	 *            填充的数据
	 * @throws Exception
	 */
	private static void createParagraphs(Map<String, Style> styles, Element content, Document document, Style defaultStyle, Map<String, Object> data) throws Exception {
		String value;
		String fill;
		Paragraph paragraph;
		String style;
		List<Element> childs;
		Element paragraphs = content.element("paragraphs");
		childs = paragraphs.elements();
		for (Element e : childs) {
			String type = e.attributeValue("type");
			if (type.equals("table")) {
				Table table = null;
				String foreach = e.attributeValue("foreach");
				if (!StringUtils.isBlank(foreach) && StringUtils.equals("true", foreach)) {
					table = createTableAndForeachData(e, styles, data);
				} else {
					table = createTable(e, styles, data);
					table.setFontSize(10.5f);
					table.setMarginBottom(0);
				}
				document.add(table);
			} else {
				fill = e.attributeValue("fill");
				value = e.getText();
				if (!StringUtils.isBlank(fill) && StringUtils.equals("true", fill)) {// 存在替换项目
																						// 并且为true
																						// 进行数据替换
					value = resolvePlaceholders(null, value, data);
				}
				paragraph = new Paragraph(value);
				style = e.attributeValue("style");
				setStyle(styles, defaultStyle, paragraph, style);
				document.add(paragraph);
			}
		}
	}

	/**
	 * 创建附件
	 * 
	 * @param styles
	 *            所有的pdf文档文档样式
	 * @param content
	 *            content Element 正文对象
	 * @param document
	 *            pdf文档对象
	 * @param defaultStyle
	 *            默认的pdf文档样式
	 * @param data
	 *            填充的数据
	 * @throws Exception
	 */
	private static void createAccessory(Map<String, Style> styles, Element content, Document document, Style defaultStyle, Map<String, Object> data) throws Exception {
		String value;
		String fill;
		Paragraph paragraph;
		Element accessory = content.element("accessorys");
		if (accessory != null) {
			List<Element> accessorys = accessory.elements();
			for (Element element : accessorys) {
				document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

				Element accessoryTitle = element.element("title");
				String str = accessoryTitle.attributeValue("value");
				paragraph = new Paragraph(str);
				String style = accessoryTitle.attributeValue("style");
				setStyle(styles, defaultStyle, paragraph, style);
				document.add(paragraph);

				List<Element> childs = element.elements("paragraph");
				for (Element e : childs) {
					String type = e.attributeValue("type");
					if (type.equals("table")) {
						Table table = null;
						String foreach = e.attributeValue("foreach");
						if (!StringUtils.isBlank(foreach) && StringUtils.equals("true", foreach)) {
							table = createTableAndForeachData(e, styles, data);
						} else {
							table = createTable(e, styles, data);
							table.setFontSize(12f);
							table.setMarginBottom(0);
						}
						document.add(table);
					} else {
						fill = e.attributeValue("fill");
						value = e.getText();
						if (!StringUtils.isBlank(fill) && StringUtils.equals("true", fill)) {// 存在替换项目
																								// 并且为true
																								// 进行数据替换
							value = resolvePlaceholders(null, value, data);
						}
						paragraph = new Paragraph(value);
						style = e.attributeValue("style");
						setStyle(styles, defaultStyle, paragraph, style);
						document.add(paragraph);
					}
				}
			}
		}
	}

	/**
	 * 设置样式
	 * 
	 * @param styles
	 * @param defaultStyle
	 * @param paragraph
	 * @param style
	 */
	private static void setStyle(Map<String, Style> styles, Style defaultStyle, Paragraph paragraph, String style) {
		if (!StringUtils.isBlank(style)) {
			Style pstyle = styles.get(style) == null ? defaultStyle : styles.get(style);
			paragraph.addStyle(pstyle);
		}
	}

	/**
	 * 得到文档的样式
	 * 
	 * @param element
	 * @return
	 */
	public static Map<String, Style> getStyle(Element element) {
		if (element == null) {
			return null;
		}

		Map<String, Style> map = new HashMap<>();
		List<Element> styles = element.elements();
		for (Element e : styles) {
			String id = e.attributeValue("id");
			if (StringUtils.isBlank(id)) {
				continue;
			}
			Float fontsize = parseFloat(e.attributeValue("fontsize"));
			Float marginTop = parseFloat(e.attributeValue("margin-top"));
			Float marginRight = parseFloat(e.attributeValue("margin-right"));
			Float marginBottom = parseFloat(e.attributeValue("margin-bottom"));
			Float marginLeft = parseFloat(e.attributeValue("margin-left"));

			// paddingTop, paddingRight, paddingBottom, paddingLeft
			Float paddingTop = parseFloat(e.attributeValue("padding-top"));
			Float paddingRight = parseFloat(e.attributeValue("padding-right"));
			Float paddingBottom = parseFloat(e.attributeValue("padding-bottom"));
			Float paddingLeft = parseFloat(e.attributeValue("padding-left"));

			Float height = parseFloat(e.attributeValue("height"));
			Float width = parseFloat(e.attributeValue("width"));

			boolean bold = parseBoolean(e.attributeValue("bold"));
			TextAlignment textalignment = getTextAlignment(e.attributeValue("textalignment"));
			String border = e.attributeValue("border");

			Style style = new Style();
			if (textalignment != null) {
				style.setTextAlignment(textalignment);
			}
			if (height != null) {
				style.setHeight(height);
			}

			if (width != null) {
				style.setWidth(width);
			}

			if (fontsize != null) {
				style.setFontSize(fontsize);
			}
			if (marginTop != null) {
				style.setMarginTop(marginTop);
			}
			if (marginRight != null) {
				style.setMarginRight(marginRight);
			}
			if (marginBottom != null) {
				style.setMarginBottom(marginBottom);
			}
			if (marginLeft != null) {
				style.setMarginLeft(marginLeft);
			}

			if (paddingTop != null) {
				style.setPaddingTop(paddingTop);
			}
			if (paddingRight != null) {
				style.setPaddingRight(paddingRight);
			}
			if (paddingBottom != null) {
				style.setPaddingBottom(paddingBottom);
			}
			if (paddingLeft != null) {
				style.setPaddingLeft(paddingLeft);
			}

			if (bold) {
				style.setBold();
			}
			if (StringUtils.equalsIgnoreCase("no", border)) {
				style.setBorder(Border.NO_BORDER);
			}
			map.put(id, style);
		}
		return map;
	}

	private static Float parseFloat(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		} else {
			return Float.parseFloat(str);
		}
	}

	private static Boolean parseBoolean(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		} else {
			return Boolean.parseBoolean(str);
		}
	}

	private static TextAlignment getTextAlignment(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		str = str.toUpperCase();
		TextAlignment alignment = null;
		switch (str) {
		case "LEFT":
			alignment = TextAlignment.LEFT;
			break;
		case "RIGHT":
			alignment = TextAlignment.RIGHT;
			break;
		case "CENTER":
			alignment = TextAlignment.CENTER;
			break;
		case "JUSTIFIED":
			alignment = TextAlignment.JUSTIFIED;
			break;
		case "JUSTIFIED_ALL":
			alignment = TextAlignment.JUSTIFIED_ALL;
			break;
		default:
			alignment = null;
			break;
		}
		return alignment;
	}

	private static PageSize getPageSize(String pagesize) {
		PageSize pageSize = PageSize.A4;
		pagesize = pagesize.toUpperCase();
		switch (pagesize) {
		case "A1":
			pageSize = PageSize.A4;
			break;
		case "A2":
			pageSize = PageSize.A2;
			break;
		case "A3":
			pageSize = PageSize.A3;
			break;
		case "A4":
			pageSize = PageSize.A4;
			break;
		case "A5":
			pageSize = PageSize.A5;
			break;
		default:
			pageSize = PageSize.A4;
			break;
		}
		return pageSize;
	}

	/**
	 * 生成表格
	 * 
	 * @param element
	 * @return
	 */
	private static Table createTable(Element element, Map<String, Style> styles, Map<String, Object> data) {
		List<Element> cells = element.elements();
		Integer row = Integer.parseInt(element.attributeValue("column"));
		Table table = new Table(row);
		table.setHeight(200f);
		for (Element e : cells) {
			int rowspan = Integer.parseInt(e.attributeValue("row"));
			int colspan = Integer.parseInt(e.attributeValue("clm"));// 合并数
			String styleKey = e.attributeValue("style");
			String text = e.getText();

			text = resolvePlaceholders(null, text, data);

			Cell cell = new Cell(rowspan, colspan);
			Paragraph paragraph = new Paragraph(text).setFontSize(8f);
			cell.setTextAlignment(TextAlignment.LEFT);
			cell.add(paragraph);
			if (!StringUtils.isBlank(styleKey)) {
				Style style = styles.get(styleKey);
				if (style != null) {
					cell.addStyle(style);
				}
			}

			table.addCell(cell);
		}
		return table;
	}

	/**
	 * 创建要循环数据的table
	 * 
	 * @param element
	 * @param styles
	 * @param data
	 * @return
	 * @throws NoSuchMethodException
	 * @throws @throws
	 *             Exception
	 */
	@SuppressWarnings("unchecked")
	private static Table createTableAndForeachData(Element element, Map<String, Style> styles, Map<String, Object> data) throws Exception {
		List<Element> cells = element.elements();
		Integer row = Integer.parseInt(element.attributeValue("column"));
		String var = element.attributeValue("var");
		String item = element.attributeValue("item");

		var = replacePlaceholders(var);
		item = replacePlaceholders(item);

		List<?> obj = (List<?>) data.get(var);

		Table table = new Table(row);
		List<Map<String, Object>> foreachItem = new ArrayList<>();
		for (Element e : cells) {
			int rowspan = Integer.parseInt(e.attributeValue("row"));
			int colspan = Integer.parseInt(e.attributeValue("clm"));// 合并数

			String styleKey = e.attributeValue("style");
			String text = e.getText();
			String fill = e.attributeValue("fill");

			Style style = styles.get(styleKey);
			if (!StringUtils.isBlank(fill) && StringUtils.equals("true", fill)) {
				Map<String, Object> map = new HashMap<>();
				// text = replacePlaceholders(text);
				map.put("text", text);
				map.put("style", style);
				map.put("rowspan", rowspan);
				map.put("colspan", colspan);
				foreachItem.add(map);
			} else {
				Cell cell = new Cell(rowspan, colspan);

				Paragraph paragraph = new Paragraph(text).setFontSize(10.5f);
				cell.setTextAlignment(TextAlignment.LEFT);
				cell.add(paragraph);
				if (!StringUtils.isBlank(styleKey)) {
					if (style != null) {
						cell.addStyle(style);
					}
				}
				table.addCell(cell);
			}
		}
		if (null == obj) {
			return table;
		}
		System.out.println("=======" + obj);
		// 循环添加数据
		for (Object mapdata : obj) {
			for (Map<String, Object> map : foreachItem) {
				int rowspan = Integer.parseInt(map.get("rowspan").toString());
				int colspan = Integer.parseInt(map.get("colspan").toString());// 合并数
				Style style = (Style) map.get("style");
				String text = map.get("text").toString();

				text = resolvePlaceholders(item, text, PropertyUtils.describe(mapdata));// PropertyUtils.describe(borrowContract)

				Cell cell = new Cell(rowspan, colspan);
				Paragraph paragraph = new Paragraph(text);
				cell.add(paragraph);
				if (style != null) {
					cell.addStyle(style);
				}
				table.addCell(cell);
			}
		}

		return table;
	}

	@SuppressWarnings("unchecked")
	public static Object getValue(String item, String[] names, Map<String, Object> map) {
		Object obj = new Object();
		int size = names.length;
		for (int i = 0; i < size; i++) {
			String name = names[i];
			if (!StringUtils.isBlank(name)) {
				if (name.equals(item)) {
					continue;
				}
				obj = map.get(name);
				if (i < size - 1 && obj != null) {
					map = (Map<String, Object>) obj;
				}
			}
		}
		return obj;
	}

	/**
	 * 占位符替换
	 * 
	 * @param text
	 * @param data
	 * @return
	 */
	public static String resolvePlaceholders(String item, String text, Map<String, Object> data) {
		if (data == null || data.isEmpty()) {
			return text;
		}

		StringBuffer stringBuffer = new StringBuffer(text);
		int startIndex = stringBuffer.indexOf(PLACEHOLDER_PREFIX);
		while (startIndex != -1) {
			int endIndex = stringBuffer.indexOf(PLACEHOLDER_SUFFIX, startIndex + PLACEHOLDER_PREFIX.length());
			if (endIndex != -1) {
				String placeholder = stringBuffer.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);// 占位符中的key
				placeholder = placeholder.replace(".", "#");
				String[] names = placeholder.split("#");
				int nextIndex = endIndex + PLACEHOLDER_SUFFIX.length();
				// String propVal = data.get(placeholder).toString();//要替换成的值
				// System.out.println(ArrayUtils.toString(names));
				Object obj = getValue(item, names, data);
				String propVal = obj == null ? null : obj.toString();// 要替换成的值

				if (propVal != null) {
					stringBuffer.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
					nextIndex = startIndex + propVal.length();
				}
				startIndex = stringBuffer.indexOf(PLACEHOLDER_PREFIX, nextIndex);
			} else {
				startIndex = -1;
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 删除占位符
	 * 
	 * @param text
	 * @return
	 */
	public static String replacePlaceholders(String text) {
		if (StringUtils.isBlank(text)) {
			return null;
		} else {
			text = text.replace(PLACEHOLDER_PREFIX, "");
			text = text.replace(PLACEHOLDER_SUFFIX, "");
			return text;
		}

	}

	@Test
	public void manipulatePDF() {
		try {
			InputStream inputStream = new FileInputStream(new File("D:/zouyiwei.txt"));
			OutputStream outputStream = new FileOutputStream(new File("D:/zouyiwei.pdf"));

			PdfFont font = PdfFontFactory.createFont("D:/msyh.ttf", PdfEncodings.IDENTITY_H);

			PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
			pdfDocument.setDefaultPageSize(PageSize.A4);

			Document document = new Document(pdfDocument);
			document.setFont(font);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				if (line.equals("甲方（出借人）：")) {
					Table table = new Table(5);
					for (int i = 1; i <= 15; i++) {
						table.addCell(new Cell().add(new Paragraph("row " + i)));
					}
					document.add(table);
				}
				document.add(new Paragraph(line).setFont(font));
			}
			document.close();

			inputStream.close();
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	@Test
	public void addTag() {
		try {
			InputStream inputStream = new FileInputStream(new File("D:/zouyiwei.txt"));
			OutputStream outputStream = new FileOutputStream(new File("D:/zouyiwei_tag.txt"));

			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = reader.readLine()) != null) {
				line = "<paragraph type=\"p\">" + line + "</paragraph>\n";
				outputStream.write(line.getBytes());
			}

			inputStream.close();
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void table1() {
		try {
			PdfFont font = PdfFontFactory.createFont("D:/msyh.ttf", PdfEncodings.IDENTITY_H);

			PdfDocument pdfDocument = new PdfDocument(new PdfWriter("D:/zouyiwei_table1.pdf"));
			pdfDocument.setDefaultPageSize(PageSize.A4);
			Document document = new Document(pdfDocument);
			document.setFont(font);

			Table table = new Table(4);
			Cell cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("借款详细用途 ")));
			cell = new Cell(1, 3);
			table.addCell(cell.add(new Paragraph("买房")));

			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("借款金额")));
			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("人民币（大写）：一万元整")));
			cell = new Cell(1, 2);
			table.addCell(cell.add(new Paragraph("（小写）10000.00")));

			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("借款期限")));
			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("12个月，自起息日起算")));
			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("借款年利率")));
			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("10.00%")));

			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("起息日")));
			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("以实际放款日期为准，具体见《还款计划表》")));
			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("还款方式")));
			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("等额本息")));

			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("每月应还金额")));
			cell = new Cell(1, 3);
			table.addCell(cell.add(new Paragraph("人民币（小写）：880.00")));

			cell = new Cell(1, 1);
			table.addCell(cell.add(new Paragraph("还款日")));
			cell = new Cell(1, 3);
			Paragraph p1 = new Paragraph("1、还款日为每月的5号,15号,25号三个日期，起息日次月起开始还款;");
			Paragraph p2 = new Paragraph("2、具体还款日期详见系统生成的《还款计划表》;");
			Paragraph p3 = new Paragraph("3、还款须在还款日17:00前完成，还款日遇休息日（周六、周日）及法定节假日不顺延。");
			cell.add(p1);
			cell.add(p2);
			cell.add(p3);
			table.addCell(cell);

			document.add(table);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static class WatermarkingEventHandler implements IEventHandler {
		private String imagePath;
		private String fontPath;

		public WatermarkingEventHandler(String imagePath, String fontPath) {
			this.imagePath = imagePath;
			this.fontPath = fontPath;
		}

		@Override
		public void handleEvent(Event event) {
			PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
			PdfDocument pdfDoc = docEvent.getDocument();
			PdfPage page = docEvent.getPage();
			PdfFont font = null;
			try {
				font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);
			} catch (IOException e) {
				e.printStackTrace();
			}
			PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
			ImageData img = null;
			try {
				img = ImageDataFactory.create(imagePath);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			float w = img.getWidth();
			float h = img.getHeight();
			pdfCanvas.addImage(img, w, 45f, 0f, h, 297.5f - (w / 2), 421f - (h / 2), false);
			@SuppressWarnings("resource")
			Canvas canvas = new Canvas(pdfCanvas, pdfDoc, page.getPageSize());
			// canvas.setFontColor(Color.BLACK);
			canvas.setFontSize(60);
			canvas.setFont(font);
			// LIGHT_GRAY
			// canvas.showTextAligned(new Paragraph("dercent"), 298, 421,
			// pdfDoc.getPageNumber(page),
			// TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);
			Paragraph paragraph = new Paragraph("第" + pdfDoc.getPageNumber(page) + "页");
			paragraph.setFontSize(12f);
			canvas.showTextAligned(paragraph, 298, 20, pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0);
		}
	}

	// inputStream转outputStream
	public ByteArrayOutputStream parse(InputStream in) throws Exception {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			swapStream.write(ch);
		}
		return swapStream;
	}

	// outputStream转inputStream
	public ByteArrayInputStream parse(OutputStream out) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos = (ByteArrayOutputStream) out;
		ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
		return swapStream;
	}

	// inputStream转String
	public String parse_String(InputStream in) throws Exception {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			swapStream.write(ch);
		}
		return swapStream.toString();
	}

	// OutputStream 转String
	public String parse_String(OutputStream out) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos = (ByteArrayOutputStream) out;
		ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
		return swapStream.toString();
	}

	// String转inputStream
	public ByteArrayInputStream parse_inputStream(String in) throws Exception {
		ByteArrayInputStream input = new ByteArrayInputStream(in.getBytes());
		return input;
	}

	// String 转outputStream
	public ByteArrayOutputStream parse_outputStream(String in) throws Exception {
		return parse(parse_inputStream(in));
	}

	// 测试时构建数据
	public Map<String, Object> createData() {
		Map<String, Object> result = new HashMap<String, Object>();
		String applyId = "18540aadfaba4af8994b3e9a11f581c0";
		SysContract contract = sysContractServer.selectByApplyId(applyId);
		List<SysRepaymentPlan> repaymentPlans = replaymentPlans(applyId);// 还款计划表
		// UserApply userApply = userApplyServer.selectByPrimaryKey(applyId);
		// String custName = userApply.getCustName();
		// result.put("custName", custName);// 客户姓名
		if (null != repaymentPlans && repaymentPlans.size() > 0) {
			result.put("shouldAmt", repaymentPlans.get(0).getShouldAmt());// 每月还款额
			// result.put("bankManagerFee",
			// contract.getBankManagerFee());//账户管理费
			result.put("amt", repaymentPlans.get(0).getShouldAmt());// 本息
			result.put("repayment", repaymentPlans);
		}
		return result;
	}

	// 还款计划表
	public List<SysRepaymentPlan> replaymentPlans(String applyId) {
		List<SysRepaymentPlan> repaymentPlans = repaymentPlanServer.findByApplyId(applyId);
		for (SysRepaymentPlan rp : repaymentPlans) {
			// 期数
			rp.setShouldTerm(rp.getShouldTerm());
			// 计划还款日
			rp.setShouldDate(rp.getShouldDate());
			// 月还款额
			rp.setShouldAmt(rp.getShouldAmt());
			// 尚欠本金
			rp.setBal(rp.getBal());
			// 当期一次性还款金额
//			rp.setPreretutotalamt(rp.getPreretutotalamt());
		}
		return repaymentPlans;
	}

	@Test
	public void createDataTest() {
		// SysContract borrowContract = createData();
		// System.out.println(borrowContract.toString());
	}

}
