package com.smc.sba.service;

import com.smc.sba.entity.IPODetailEntity;
import com.smc.sba.entity.StockExchangeEntity;
import com.smc.sba.repository.ExchangeRepository;
import com.smc.sba.repository.IpoRepository;
import com.smc.sba.utils.CommonResult;
import com.smc.sba.utils.ResponseCode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName IpoService
 * @Description TODO
 * @Author YuChaoZheng
 * @Date 12/4/2019 15:40 AM
 * @Version 1.0
 **/
@Service
public class IpoService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private IpoRepository ipoRepository;

	/**
	 * Description: query all ipoDetails.
	 *
	 * @param:
	 * @return: com.smc.sba.utils.CommonResult
	 * @auther: Yuchaozh
	 * @date: 2019/12/04 10:39
	 */
	public CommonResult findAll() {
		try {
			List<IPODetailEntity> ipos = ipoRepository.findAll();
			for (IPODetailEntity ipo : ipos) {
				ipo.setOpenDateTime(df.format(df.parse(ipo.getOpenDateTime())));
			}
			return CommonResult.build(ResponseCode.SUCCESS, "SUCCESS!", ipos);
		} catch (Exception e) {
			logger.error("Fail to query ipoDetails data:", e);
			return CommonResult.build(ResponseCode.ERROR_ACCESS_DB, "DB ERROR!");
		}
	}

	public CommonResult findById(int id) {
		try {
			IPODetailEntity ipo = ipoRepository.findById(id).get();
			ipo.setOpenDateTime(df.format(df.parse(ipo.getOpenDateTime())));
			return CommonResult.build(ResponseCode.SUCCESS, "SUCCESS!", ipo);
		} catch (Exception e) {
			logger.error("Fail to query ipoDetails data:", e);
			return CommonResult.build(ResponseCode.ERROR_ACCESS_DB, "DB ERROR!");
		}
	}

	/**
	 * Description: query company by companyCode.
	 *
	 * @param:
	 * @return: com.smc.sba.utils.CommonResult
	 * @auther: Yuchaozh
	 * @date: 2019/12/04 10:39
	 */
	public CommonResult findIPOByExchange(String exchange) {
		try {
			IPODetailEntity ipoDetail = ipoRepository.findIPOByExchange(exchange);
			return CommonResult.build(ResponseCode.SUCCESS, "SUCCESS!", ipoDetail);
		} catch (Exception e) {
			logger.error("Fail to query ipoDetail data:", e);
			return CommonResult.build(ResponseCode.ERROR_ACCESS_DB, "DB ERROR!");
		}
	}

	/**
	 * Description: query company by companyCode.
	 *
	 * @param:
	 * @return: com.smc.sba.utils.CommonResult
	 * @auther: Yuchaozh
	 * @date: 2019/12/04 10:39
	 */
	public CommonResult findIPOByCompanyName(String companyName) {
		try {
			IPODetailEntity ipoDetail = ipoRepository.findIPOByCompanyName(companyName);
			return CommonResult.build(ResponseCode.SUCCESS, "SUCCESS!", ipoDetail);
		} catch (Exception e) {
			logger.error("Fail to query ipoDetail data:", e);
			return CommonResult.build(ResponseCode.ERROR_ACCESS_DB, "DB ERROR!");
		}
	}

}
