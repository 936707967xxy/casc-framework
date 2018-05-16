/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Result;
import com.hoomsun.risk.model.CommonPhone;
import com.hoomsun.risk.server.inter.CommonPhoneServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月29日 <br>
 * 描述：通话详单的业务数据
 */
@Service("commonPhoneServer")
public class CommonPhoneServerImpl implements CommonPhoneServerI {
	@Autowired
	private MongoTemplate mongoTemplate;
	

	@Override
	public Result createCommonPhone(MultipartFile fileUp) throws IOException {
		Workbook work = null;  
		try {
			/**
			 * 判断excel版本
			 */
			String fileName = fileUp.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf("."));  
			if(".xls".equals(fileType)){  
			    work = new HSSFWorkbook(fileUp.getInputStream());  //2003-  
			}else if(".xlsx".equals(fileType)){  
			    work = new XSSFWorkbook(fileUp.getInputStream());  //2007+  
			}else{  
				return new Result(1001, "文件格式上传有误");  
			} 
			/**
			 * 循环所有sheet表
			 */
			List<CommonPhone> upList = new ArrayList<CommonPhone>();
			for (int i = 0; i < work.getNumberOfSheets(); i++) {
				/**
				 * 循环每一行
				 */
				Sheet sheet = work.getSheetAt(i);
			    for (Row r : sheet) {
			    	if(r.getRowNum()<1){
			    		continue;
			    	}
			    	if(r.getCell(0)==null || r.getCell(0).getStringCellValue()==null || r.getCell(0).getStringCellValue().equals("")){
			    		continue; 
			    	}
			    	CommonPhone bean=new CommonPhone();
			    	//bean.setOrgType("3");
			    	bean.setOrgName(r.getCell(0).getStringCellValue());
			    	String column2 = r.getCell(1).getStringCellValue();//类型值
			    	switch (column2) {
					case "审核":
						bean.setCommType("1");
						break;
					case "催收":
						bean.setCommType("2");
						break;
					case "服务":
						bean.setCommType("3");
						break;
					default:
						bean.setCommType("4");//未知
						break;
					}
			    	
			    	bean.setCommTypeVal(r.getCell(1).getStringCellValue());
			    	Cell column3 = r.getCell(2);//电话列
			    	
			    	if (column3.getCellTypeEnum() == CellType.NUMERIC) {//数字
			    		if (DateUtil.isCellDateFormatted(column3)) {//时间
	                        
	                    } else {
	                    	DecimalFormat df = new DecimalFormat("0");
				    		bean.setPhoneNumber(df.format(r.getCell(2).getNumericCellValue()));
	                    }
			    	}else{
			    		bean.setPhoneNumber(r.getCell(2).getStringCellValue());
			    	}
			    	
			    	upList.add(bean);
			    }
			}
			
			for (CommonPhone commonPhone : upList) {
				mongoTemplate.save(commonPhone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (work != null) {
				work.close();
			}
		}
		return new Result(0000, "成功!");
	}
}
