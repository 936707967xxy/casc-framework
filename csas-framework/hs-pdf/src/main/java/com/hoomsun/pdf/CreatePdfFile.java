package com.hoomsun.pdf;

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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
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
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

/**
 * 合同的工具类
 * 
 * @author zouyiwei
 */
public class CreatePdfFile {
	public static Logger logger = LoggerFactory.getLogger(CreatePdfFile.class);

	private static final String PLACEHOLDER_PREFIX = "#{";
	private static final String PLACEHOLDER_SUFFIX = "}";

	/**
	 * 创建pdf文档
	 * 
	 * @param xmlModelPath
	 *            xml文档的全路径 例如：D:/model/xxx.xml
	 * @param savePath
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
	public static boolean createObjectToPDF(String xmlModelPath, String savePath, String fontPath, String imagePath, Map<String, Object> params) {
		try {
			// 创建xml解析器
			SAXReader reader = new SAXReader();
			// 获取xml文件 /com/dercent/olsys/ftl/common_contract.xml
			InputStream inputStream = new FileInputStream(new File(xmlModelPath));
			org.dom4j.Document dom = reader.read(inputStream);
			// 获取跟节点
			Element root = dom.getRootElement();

			// 得到样式
			Element stylesElement = root.element("styles");
			Map<String, Style> styles = getStyle(stylesElement);

			// body
			Element body = root.element("body");
			PageSize pageSize = getPageSize(body.attributeValue("pagesize"));
			Float fontsize = parseFloat(body.attributeValue("fontsize"));
			Float marginTop = parseFloat(body.attributeValue("margin-top"));
			Float marginRight = parseFloat(body.attributeValue("margin-right"));
			Float marginBottom = parseFloat(body.attributeValue("margin-bottom"));
			Float marginLeft = parseFloat(body.attributeValue("margin-left"));

			// 创建文档
			PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);
			PdfDocument pdfDocument = new PdfDocument(new PdfWriter(savePath));
			pdfDocument.setDefaultPageSize(pageSize);
			Document document = new Document(pdfDocument);
			document.setFont(font);
			document.setFontSize((float) 10);
			document.setMargins(marginTop, marginRight, marginBottom, marginLeft);// 1磅约等于0.035厘米,1厘米约等于28.35磅。或者说，1英寸=72磅

			// 事件 页码
			pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new PageEventHandler(fontPath));

			// 全局样式
			Style defaultStyle = new Style();
			defaultStyle.setFont(font);
			defaultStyle.setFontSize(fontsize);

			// page
			List<Element> pages = body.elements("page");
			Iterator<Element> iterator = pages.iterator();
			while (iterator.hasNext()) {
				Element pg = iterator.next();

				// 得到所有的子元素
				Iterator<Element> childs = pg.elementIterator();
				while (childs.hasNext()) {
					Element node = childs.next();
					String nodeName = node.getName();// 节点名称

					// 标题
					if ("title".equals(nodeName)) {
						String titleValue = node.attributeValue("value");
						Paragraph paragraph = new Paragraph(titleValue);
						String style = node.attributeValue("style");
						setStyle(styles, paragraph, style);
						document.add(paragraph);
					}

					// 段落
					if ("p".equals(nodeName)) {
						String foreach = node.attributeValue("foreach");
						if (!StringUtils.isBlank(foreach) && StringUtils.equals("true", foreach)) {
							createForeachParagraph(params, styles, document, node);
						} else {
							createParagraph(params, styles, document, node);
						}
					}

					// 表格
					if ("table".equals(nodeName)) {
						Table table = null;
						String foreach = node.attributeValue("foreach");
						Float width = pageSize.getWidth() - marginRight - marginLeft;
						if (!StringUtils.isBlank(foreach) && StringUtils.equals("true", foreach)) {
							table = createTableAndForeachData(node, styles, params);
						} else {
							table = createTable(node, styles, params);
							table.setMarginBottom(0);
						}
						table.setWidth(width);
						document.add(table);
					}

					// 盒子
					if ("div".equals(nodeName)) {
						Iterator<Element> divP = node.elementIterator("p");
						String style = node.attributeValue("style");
						while (divP.hasNext()) {
							Element element = divP.next();
							createBox(params, styles, document, style, element);
						}
					}

					// 图片
					if ("img".equals(nodeName)) {
						if (StringUtils.isNotBlank(imagePath)) {
							String widthS = node.attributeValue("width");
							String heights = node.attributeValue("height");
							String xS = node.attributeValue("x");
							String yS = node.attributeValue("y");
//							String offset_yS = node.attributeValue("offset_y");

							float width = parseFloat(widthS);
							float height = parseFloat(heights);
							float x = parseFloat(xS);
							float y = parseFloat(yS);
//							float offsetX = parseFloat(offset_xS);
//							float offsetY = parseFloat(offset_yS);

							ImageData imageDate = ImageDataFactory.create(imagePath);
							Image image = new Image(imageDate);
							image.scaleToFit(width, height);
//							image.setFixedPosition(x, y);
							image.setMarginLeft(x);
							image.setMarginTop(y);
							document.add(image);
							// PdfCanvas canvas = new
							// PdfCanvas(pdfDocument.getPage(pdfDocument.getNumberOfPages()).newContentStreamBefore(),
							// pdfDocument.getLastPage().getResources(),
							// pdfDocument);
							// canvas.addImage(image, width, offsetX, offsetY,
							// height, x, y, false);
						}
					}
				}

				if (iterator.hasNext()) {
					document.add(new AreaBreak(AreaBreakType.NEXT_AREA));
				}
			}

			document.flush();
			document.close();
			return true;
		} catch (Exception e) {
			logger.error("###创建PDF合同失败！" + e.getMessage());
			return false;
		}
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月11日 <br>
	 * 描述： 段落循环
	 * 
	 * @param params
	 * @param styles
	 * @param document
	 * @param node
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private static void createForeachParagraph(Map<String, Object> params, Map<String, Style> styles, Document document, Element node) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String var = node.attributeValue("var");
		String item = node.attributeValue("item");
		String varStatus = node.attributeValue("varStatus");

		var = replacePlaceholders(var);
		item = replacePlaceholders(item);

		// 判断是不是基础类型的数据
		Object obj = params.get(var);
		List<?> list = new ArrayList<>();
		if (obj instanceof List) {
			list = (List<?>) params.get(var);
		}

		int index = 0;
		for (Object object : list) {
			Paragraph paragraph = new Paragraph();
			Iterator<Element> iterator = node.elementIterator();// spans
			while (iterator.hasNext()) {
				Element e = iterator.next();
				String name = e.getName();
				String value = e.getTextTrim();

				Map<String, Object> data = null;
				if (object instanceof Map) {
					data = (Map<String, Object>) object;
				} else {
					data = PropertyUtils.describe(object);
				}

				if (StringUtils.isNotBlank(varStatus)) {
					value = value.replaceAll(varStatus, item);
					data.put("index", index);
					data.put("count", index + 1);
				}

				value = resolvePlaceholders(item, value, data);

				Text text = new Text(value);
				String style = e.attributeValue("style");
				setStyle(styles, text, style);

				if ("lable".equals(name)) {
					text.setBold();
				}
				paragraph.add(text);
			}
			index = index + 1;
			String style = node.attributeValue("style");
			setStyle(styles, paragraph, style);
			paragraph.setMarginTop((float) 0);
			paragraph.setMultipliedLeading((float) 1.3);// 行间距
			Float firstLineIndent = parseFloat(node.attributeValue("first-line-indent"));// 首行缩进
			if (null != firstLineIndent) {
				paragraph.setFirstLineIndent(firstLineIndent);
			}
			document.add(paragraph);
		}
	}

	private static void createBox(Map<String, Object> params, Map<String, Style> styles, Document document, String boxStyle, Element element) {
		List<Element> elements = element.elements();
		Table table = new Table(elements.size());

		for (Element el : elements) {
			Cell cell = new Cell(1, 1);
			String text = el.getTextTrim();
			String fill = el.attributeValue("fill");

			if (StringUtils.isNotBlank(fill) && StringUtils.equals("true", fill)) {
				text = resolvePlaceholders(null, text, params);
			}
			Paragraph paragraph = new Paragraph(text);
			cell.add(paragraph);
			String style = el.attributeValue("style");
			Style cellstyle = styles.get(style);
			if (cellstyle != null) {
				cell.addStyle(cellstyle);
			}
			cell.setBorder(Border.NO_BORDER);
			table.addCell(cell);
		}
		Style style = styles.get(boxStyle);
		if (style != null) {
			table.addStyle(style);
		}

		document.add(table);
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月26日 <br>
	 * 描述： 穿件段落数据
	 * 
	 * @param params
	 * @param styles
	 * @param document
	 * @param defaultStyle
	 * @param node
	 */
	private static void createParagraph(Map<String, Object> params, Map<String, Style> styles, Document document, Element node) {
		String fill = node.attributeValue("fill");
		boolean isFill = false;
		if (StringUtils.isNotBlank(fill) && StringUtils.equals("true", fill)) {
			isFill = true;
		}

		Iterator<Element> iterator = node.elementIterator();
		Paragraph paragraph = new Paragraph();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			String name = e.getName();
			String value = e.getTextTrim();
			if (isFill) {
				value = resolvePlaceholders(null, value, params);
			}

			Text text = new Text(value);
			String style = e.attributeValue("style");
			setStyle(styles, text, style);

			if ("lable".equals(name)) {
				text.setBold();
			}

			paragraph.add(text);
		}

		String style = node.attributeValue("style");
		setStyle(styles, paragraph, style);
		paragraph.setMarginTop((float) 0);
		// paragraph.setFixedLeading((float)20);//固定行高
		paragraph.setMultipliedLeading((float) 1.3);// 行间距
		Float firstLineIndent = parseFloat(node.attributeValue("first-line-indent"));// 首行缩进
		if (null != firstLineIndent) {
			paragraph.setFirstLineIndent(firstLineIndent);
		}
		document.add(paragraph);
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

	/**
	 * 设置样式
	 * 
	 * @param styles
	 * @param defaultStyle
	 * @param paragraph
	 * @param style
	 */
	private static void setStyle(Map<String, Style> styles, Paragraph paragraph, String style) {
		if (!StringUtils.isBlank(style)) {
			Style pstyle = styles.get(style);
			if (pstyle != null) {
				paragraph.addStyle(pstyle);
			}
		}
	}

	private static void setStyle(Map<String, Style> styles, Text text, String style) {
		if (!StringUtils.isBlank(style)) {
			Style pstyle = styles.get(style);
			if (pstyle != null) {
				text.addStyle(pstyle);
			}
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

			Float paddingTop = parseFloat(e.attributeValue("padding-top"));
			Float paddingRight = parseFloat(e.attributeValue("padding-right"));
			Float paddingBottom = parseFloat(e.attributeValue("padding-bottom"));
			Float paddingLeft = parseFloat(e.attributeValue("padding-left"));

			Float height = parseFloat(e.attributeValue("height"));
			Float width = parseFloat(e.attributeValue("width"));
			Boolean underLine = parseBoolean(e.attributeValue("under-line"));// 下划线

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

			}
			if (underLine != null && underLine) {
				style.setUnderline();
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
			pageSize = PageSize.A1;
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
		for (Element e : cells) {
			int rowspan = Integer.parseInt(e.attributeValue("row"));
			int colspan = Integer.parseInt(e.attributeValue("clm"));// 合并数
			String styleKey = e.attributeValue("style");
			String text = e.getText();

			String fill = e.attributeValue("fill");
			if (StringUtils.isNotBlank(fill) && "true".equals(fill)) {
				text = resolvePlaceholders(null, text, data);
			}

			Cell cell = new Cell(rowspan, colspan);
			Paragraph paragraph = new Paragraph(text);
			cell.add(paragraph);
			if (!StringUtils.isBlank(styleKey)) {
				Style style = styles.get(styleKey);
				if (style != null) {
					cell.addStyle(style);
				}
			}
			String border = e.attributeValue("border");
			if (StringUtils.isNotBlank(border)) {
				if ("0".equals(border)) {
					cell.setBorder(Border.NO_BORDER);
				} else {
					cell.setBorder(new Border(Integer.parseInt(border)) {

						@Override
						public int getType() {
							return 0;
						}

						@Override
						public void drawCellBorder(PdfCanvas canvas, float x1, float y1, float x2, float y2, Side defaultSide) {

						}

						@Override
						public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, float borderRadius, Side defaultSide, float borderWidthBefore, float borderWidthAfter) {

						}

						@Override
						public void draw(PdfCanvas canvas, float x1, float y1, float x2, float y2, Side defaultSide, float borderWidthBefore, float borderWidthAfter) {

						}
					});
				}
			}
			table.addCell(cell);
		}

		String styleKey = element.attributeValue("style");
		Style style = styles.get(styleKey);
		if (style != null) {
			table.addStyle(style);
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
		List<Element> elements = element.elements();

		Integer row = Integer.parseInt(element.attributeValue("column"));
		String var = element.attributeValue("var");
		String item = element.attributeValue("item");

		var = replacePlaceholders(var);
		item = replacePlaceholders(item);

		// 判断是不是基础类型的数据
		Object obj = data.get(var);
		List<?> list = new ArrayList<>();
		if (obj instanceof List) {
			list = (List<?>) data.get(var);
		}

		Table table = new Table(row);
		List<Map<String, Object>> foreachItem = new ArrayList<>();
		for (Element e : elements) {
			int rowspan = Integer.parseInt(e.attributeValue("row"));
			int colspan = Integer.parseInt(e.attributeValue("clm"));// 合并数

			String styleKey = e.attributeValue("style");
			String text = e.getText();
			String fill = e.attributeValue("fill");

			Style style = styles.get(styleKey);
			if (!StringUtils.isBlank(fill) && StringUtils.equals("true", fill)) {
				Map<String, Object> map = new HashMap<>();
				map.put("text", text);
				map.put("style", style);
				map.put("rowspan", rowspan);
				map.put("colspan", colspan);
				foreachItem.add(map);
			} else {
				Cell cell = new Cell(rowspan, colspan);
				Paragraph paragraph = new Paragraph(text);
				cell.setTextAlignment(TextAlignment.CENTER);
				cell.add(paragraph);
				if (!StringUtils.isBlank(styleKey)) {
					if (style != null) {
						cell.addStyle(style);
					}
				}
				table.addCell(cell);
			}
		}

		// 循环添加数据
		for (Object value : list) {
			for (Map<String, Object> map : foreachItem) {
				int rowspan = Integer.parseInt(map.get("rowspan").toString());
				int colspan = Integer.parseInt(map.get("colspan").toString());// 合并数
				Style style = (Style) map.get("style");
				String text = map.get("text").toString();

				if (value instanceof Map) {
					text = resolvePlaceholders(item, text, (Map<String, Object>) value);
				} else {
					text = resolvePlaceholders(item, text, PropertyUtils.describe(value));
				}

				Cell cell = new Cell(rowspan, colspan);
				Paragraph paragraph = new Paragraph(text);
				cell.add(paragraph);
				if (style != null) {
					cell.addStyle(style);
				}
				cell.setTextAlignment(TextAlignment.CENTER);
				table.addCell(cell);
			}
		} 

		String styleKey = element.attributeValue("style");
		Style style = styles.get(styleKey);
		if (style != null) {
			table.addStyle(style);
		}
		return table;
	}

	public boolean checkBaseType(Object obj) {
		boolean isBase = false;
		if (obj instanceof Integer) {
			isBase = true;
		} else if (obj instanceof String) {
			isBase = true;
		} else if (obj instanceof Double) {
			isBase = true;
		} else if (obj instanceof Float) {
			isBase = true;
		} else if (obj instanceof Long) {
			isBase = true;
		} else if (obj instanceof Boolean) {
			isBase = true;
		} else if (obj instanceof Date) {
			isBase = true;
		}
		return isBase;
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
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月2日 <br>
	 * 描述： 页面事件
	 */
	public static class PageEventHandler implements IEventHandler {
		private String fontPath;

		public PageEventHandler(String fontPath) {
			this.fontPath = fontPath;
		}

		@Override
		public void handleEvent(Event event) {
			PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
			PdfDocument pdfDoc = docEvent.getDocument();
			PdfPage page = docEvent.getPage();
			Rectangle rectangle = page.getPageSize();
			Float width = rectangle.getWidth();
			PdfFont font = null;
			try {
				font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);
			} catch (IOException e) {
				e.printStackTrace();
			}
			PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
			@SuppressWarnings("resource")
			Canvas canvas = new Canvas(pdfCanvas, pdfDoc, page.getPageSize());
			canvas.setFont(font);
			
			Paragraph paragraph = new Paragraph("第" + pdfDoc.getPageNumber(page) + "页");
			paragraph.setFontSize(10f);
			canvas.showTextAligned(paragraph, width/2, 20, pdfDoc.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.BOTTOM, 0);
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

	public static void main(String[] args) throws IOException {
		ImageData imageData = ImageDataFactory.create("D:\\Contract_Test\\456.png");
		// float width = image.getWidth();
		// float height = image.getHeight();
		PageSize pageSize = PageSize.A4;

		PdfDocument pdfDoc = new PdfDocument(new PdfWriter("D:\\Contract_Test\\img.pdf"));
		pdfDoc.setDefaultPageSize(pageSize);
		Document document = new Document(pdfDoc);

		Paragraph paragraph = new Paragraph("HELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORDHELLO WORD");
		Image image = new Image(imageData);
		image.scaleToFit(80, 80);
		image.setMarginLeft(100);
		image.setMarginTop(-40);
		document.add(paragraph);
		document.add(image);
		// PdfCanvas canvas = new
		// PdfCanvas(pdfDoc.addNewPage().newContentStreamBefore(),
		// pdfDoc.getLastPage().getResources(), pdfDoc);
		// canvas.addImage(image, 80, 0, 0, 80, 40, 40, false);
		document.flush();
		document.close();
		pdfDoc.close();

		// PdfDocument pdfDoc = new PdfDocument(new
		// PdfReader("D:\\Contract_Test\\co\\XA20171225006.pdf"), new
		// PdfWriter("D:\\Contract_Test\\co\\XA20171225006.pdf"));
		// ImageData image =
		// ImageDataFactory.create("D:\\Contract_Test\\\\123.png");
		// Image imageModel = new Image(image);
		// AffineTransform at = AffineTransform.getTranslateInstance(36, 300);
		// at.concatenate(AffineTransform.getScaleInstance(imageModel.getImageScaledWidth(),
		// imageModel.getImageScaledHeight()));
		// PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
		// float[] matrix = new float[6];
		// at.getMatrix(matrix);
		// canvas.addImage(image, matrix[0], matrix[1], matrix[2], matrix[3],
		// matrix[4], matrix[5]);
		// pdfDoc.close();
	}
}
