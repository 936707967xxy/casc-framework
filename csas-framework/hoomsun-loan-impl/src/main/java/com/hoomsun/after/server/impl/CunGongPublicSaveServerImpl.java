/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.PublicSaveReq;
import com.hoomsun.after.api.model.table.PublicSave;
import com.hoomsun.after.api.model.table.PublicSaveLog;
import com.hoomsun.after.api.model.vo.CunGongPublicSaveResult;
import com.hoomsun.after.api.server.CunGongPublicSaveServer;
import com.hoomsun.after.api.util.DateUtils;
import com.hoomsun.after.api.util.StringUtil;
import com.hoomsun.after.api.util.autoCode;
import com.hoomsun.after.api.util.excel.secode.annotation.ExportExcel;
import com.hoomsun.after.api.util.excel.secode.excelUtil.ExcelUtils;
import com.hoomsun.after.dao.CunGongPublicSaveMapper;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月26日 <br>
 * 描述：
 */
@Service("CunGongPublicSaveServer")
public class CunGongPublicSaveServerImpl implements CunGongPublicSaveServer{
	
	private static final Logger LogCvt = Logger.getLogger(CunGongPublicSaveServerImpl.class);

	@Autowired
	private CunGongPublicSaveMapper gongPublicSaveMapper;
	
	/**
	 * 存公记录查询
	 */
	@Override
	public List<CunGongPublicSaveResult> queryCunGongPublicSave(PublicSaveReq req) {
		// TODO Auto-generated method stub
		List<CunGongPublicSaveResult>list=null;
		try {
			list=gongPublicSaveMapper.queryCunGongPublicSave(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("查询数据库异常"+e.getMessage());
		}
		return list;
	}

	/**
	 * 查询存公记录总条数
	 */
	@Override
	public Integer countCunGongPublicSave(PublicSaveReq req) {
		// TODO Auto-generated method stub
		Integer num=null;
		try {
			num=gongPublicSaveMapper.countCunGongPublicSave(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("查询数据库异常"+e.getMessage());
		}
		return num;
	}

	/**
	 * 导出存公记录
	 */
	@Override
	public void downloadCunGongPublicSave(PublicSaveReq req) {
		// TODO Auto-generated method stub
		try {
			req.setPageSize(10000);//导出一批10000条数据
			String fileName = "存公记录列表.xlsx";
            String title = "存公记录列表";
            ExportExcel excel=new ExportExcel(title, CunGongPublicSaveResult.class);
            //最大导出条数为50000条   [10000*5]
            Integer count=StringUtil.initConfigMaxRow("Max_row");
			for (int i = 1; i < count; i++) {
				req.setPage(i);
				List<CunGongPublicSaveResult>list=gongPublicSaveMapper.queryCunGongPublicSave(req);
				if(list!=null&&list.size()>0){
					list=ExcelUtils.excelCunGongPublicSave(list);
				}else{
					break;
				}
				excel=excel.setDataList(list);
			}
			excel.write(req.getResponse(), fileName);
			excel.dispose();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("查询数据库异常"+e.getMessage());
		}
	}

	/**
	 * 批量导入存公
	 */
	@Override
	public Json exportExcelCunGongPublicSave(InputStream is, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Json json=new Json();
		CunGongPublicSaveServerImpl impl=new CunGongPublicSaveServerImpl();
		try {
			String hostAddress = request.getRemoteAddr();
			SessionRouter session = LoginUtil.getLoginSession(request);
			String version="version2003";
			Workbook hwk = null;
			CunGongPublicSaveResult logDetails=null;
			if (autoCode.EXCEL_VERSION_XLS.equals(version)) {
				hwk = new HSSFWorkbook(is);
			} else if (autoCode.EXCEL_VERSION_XLSX.equals(version)) {
			} else {
				return null;
			}
			Sheet sh = hwk.getSheetAt(0);
			int rows = sh.getLastRowNum() + 1 - sh.getFirstRowNum();
			int count_total_index = 1;
			int totalNumber = StringUtil.getTotalNumber(rows-1);
			Integer count=0;
			Integer failCount=0;
			for (int t = 0; t < totalNumber; t++) {
				LogCvt.info("===============开始处理第===============" + (t + 1)+ "批 ;");
				int cols = 0;
				List<PublicSave>list=new ArrayList<PublicSave>();
				PublicSave publicSave=null;
				for (int i = count_total_index; i < (count_total_index + autoCode.batchNum); i++) {
					publicSave=new PublicSave();
					Row row = sh.getRow(i);
					if (row == null)
						continue;
					if (i == 0) {
						cols = row.getLastCellNum() - row.getFirstCellNum();
					}
					/**
					 * 流水号
					 */
					Object col1 = row.getCell(0);
					if(StringUtil.isNotEmpty(String.valueOf(col1))){
						publicSave.setRefnbr(String.valueOf(col1));
					}
					/**
					 * 交易日期
					 */
					Object col2 = row.getCell(1);
					if(StringUtil.isNotEmpty(String.valueOf(col2))){
						publicSave.setTransactionDate(DateUtils.stringToDate(String.valueOf(col2), DateUtils.DATE_8));
					}
					/**
					 * 贷方金额(存公存入金额)
					 */
					Object col3 = row.getCell(2);
					if(StringUtil.isNotEmpty(String.valueOf(col3))){
						String trsam=StringUtil.isEmptyValue(String.valueOf(col3), "0");
						publicSave.setTrsamtc(StringUtil.StringToBigDecimal(trsam));
					}
					/**
					 * 借贷标记(C:收入) 
					 */
					Object col4 = row.getCell(3);
					if(StringUtil.isNotEmpty(String.valueOf(col4))){
						publicSave.setAmtcdr(String.valueOf(col4).trim());
					}
					/**
					 * 收/付方名称（用户姓名）
					 */
					Object col5 = row.getCell(4);
					if(StringUtil.isNotEmpty(String.valueOf(col5))){
						publicSave.setRpynam(String.valueOf(col5));
					}
					/**
					 * 收/付方帐号（银行卡号）
					 */
					Object col6 = row.getCell(5);
					if(StringUtil.isNotEmpty(String.valueOf(col6))){
						publicSave.setRpyacc(String.valueOf(col6));
					}
					/**
					 *收/付方开户行名（银行名称）
					 */
					Object col7 = row.getCell(6);
					if(StringUtil.isNotEmpty(String.valueOf(col7))){
						publicSave.setRpybnk(String.valueOf(col7));
					}
					/**
					 *摘要
					 */
					Object col8 = row.getCell(7);
					if(StringUtil.isNotEmpty(String.valueOf(col8))){
						publicSave.setNaryur(String.valueOf(col8));
					}
					/**
					 *对应公户（7180）
					 */
					Object col9 = row.getCell(8);
					if(StringUtil.isNotEmpty(String.valueOf(col9))){
						publicSave.setCorporateBankAccount(String.valueOf(col9));
					}
					list.add(publicSave);
					}
				
				count_total_index = count_total_index + autoCode.batchNum;
				for (PublicSave publicSave2 : list) {
					publicSave2.setId(PrimaryKeyUtil.getPrimaryKey());
					publicSave2.setEtydat(DateUtils.dateToString(publicSave2.getTransactionDate(), DateUtils.DATE_8));
					publicSave2.setEtytim("");
					publicSave2.setTrsamt(publicSave2.getTrsamtc());
					publicSave2.setDataSources("结算导入");
					publicSave2.setVerificationStatus("0");
					try {
						PublicSaveLog log=new PublicSaveLog();
						if(!"C".equals(publicSave2.getAmtcdr())||!"7180".equals(publicSave2.getCorporateBankAccount())){
							log=impl.addPublicSaveLog(publicSave2, log);
							log.setErrorCode("E100");
							log.setErrorMsg("只允许导入借贷(收入),对应公户(7180)数据!");
							log.setHostIp(hostAddress);
							if(session!=null){
								log.setOprationId(session.getEmpWorkNum());
								log.setOprationName(session.getEmpName());
							}
							LogCvt.info("只允许导入借贷(收入),对应公户(7180)数据!"+publicSave2.getRefnbr());
							gongPublicSaveMapper.insertCunGongPublicSaveLog(log);
							failCount++;
						}else{
							logDetails=gongPublicSaveMapper.queryPublicSaveDetails(publicSave2);
							if(logDetails!=null){
								log=impl.addPublicSaveLog(publicSave2, log);
								log.setErrorCode("E300");
								log.setErrorMsg("此数据已导入,不可重复导入!");
								log.setHostIp(hostAddress);
								if(session!=null){
									log.setOprationId(session.getEmpWorkNum());
									log.setOprationName(session.getEmpName());
								}
								LogCvt.info("此数据已导入,不可重复导入"+publicSave2.getRefnbr());
								gongPublicSaveMapper.insertCunGongPublicSaveLog(log);
								failCount++;
							}else{
								count=gongPublicSaveMapper.insertCunGongPublicSave(publicSave2);
								if(count>0){
									LogCvt.info("流水号"+publicSave2.getRefnbr()+"导入成功......");
								}else{
									log=impl.addPublicSaveLog(publicSave2, log);
									log.setErrorCode("E200");
									log.setErrorMsg("导入数据库异常");
									log.setHostIp(hostAddress);
									if(session!=null){
										log.setOprationId(session.getEmpWorkNum());
										log.setOprationName(session.getEmpName());
									}
									LogCvt.info("导入数据库异常"+publicSave2.getRefnbr());
									gongPublicSaveMapper.insertCunGongPublicSaveLog(log);
									failCount++;
								}
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						json.setSuccess(false);
						json.setMsg("业务处理异常");
						e.printStackTrace();
					}
				}
				json.setSuccess(true);
				if(failCount>0){
					json.setMsg("部分导入成功,失败【"+failCount+"】条......");
				}else{
					json.setMsg("全部导入成功......");
				}
                 /**
				 * 此处延迟等待调用业务层处理完成再继续读取数据
				 */
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogCvt.info("===============第===============" + (t + 1)+ "批处理结束");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("解析Excel失败");
			throw new RuntimeException("解析Excel失败，错误原因为：" + e.getMessage());
		}
		return json;
	}

	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月28日 <br>
	 * 描述： 日志记录
	 * @param publicSave2
	 * @param log
	 * @return
	 */
	public PublicSaveLog addPublicSaveLog(PublicSave publicSave2,PublicSaveLog log){
		log.setId(PrimaryKeyUtil.getPrimaryKey());
		log.setRefnbr(publicSave2.getRefnbr());
		log.setTransactionDate(DateUtils.dateToString(publicSave2.getTransactionDate(), DateUtils.DATE_8));
		log.setAmtcdr(publicSave2.getAmtcdr());
		log.setTrsblv(String.valueOf(publicSave2.getTrsblv()));
		log.setCorporateBankAccount(publicSave2.getCorporateBankAccount());
		log.setDataSources(publicSave2.getDataSources());
		return log;
	}
	
}
