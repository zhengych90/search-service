package com.smc.sba.service;

import com.smc.sba.entity.StockPriceEntity;
import com.smc.sba.repository.CompanyRepository;
import com.smc.sba.repository.StockPriceRepository;
import com.smc.sba.utils.CommonResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smc.sba.utils.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName StockPriceService
 * @Description TODO
 * @Author YuChaoZheng
 * @Date 12/3/2019 9:40 AM
 * @Version 1.0
 **/
@Service
public class StockPriceService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StockPriceRepository stockPriceRepository;
	@Autowired
	private CompanyRepository companyRepository;

	/**
	 * Used by function UserLogin
	 */
	public CommonResult insert(List<StockPriceEntity> validDatas) {
		stockPriceRepository.saveAll(validDatas);
		String companyName = companyRepository
				.getCompanyNameByCode(validDatas.get(0).getCompanyCode());
		Map<String, String> map = new HashMap();
		map.put("record", String.valueOf(validDatas.size()));
		map.put("companyName", companyName);
		map.put("stockExchange", validDatas.get(0).getStockExchange());
		map.put("fromDate", "");
		map.put("toDate", "");
		return CommonResult.build(200, "upload success!", map);
	}

	/**
	 *
	 * @param code
	 * @param start
	 * @param end
	 * @return
	 */
	public CommonResult comparisonSingleCompany(String code, LocalDateTime start, LocalDateTime end){
		try {
			List<StockPriceEntity> result= stockPriceRepository.findByCompanyCodeAndDateTimeBetween(code,start,end);
			return CommonResult.build(200,"Get stock by company code and date",result);
		} catch (Exception e){
			logger.error("Fail to query company data:", e);
			return CommonResult.build(ResponseCode.ERROR_ACCESS_DB, "DB ERROR!");
		}
	}

	/**
	 *
	 * @param codes
	 * @param start
	 * @param end
	 * @return
	 */
	public List<StockPriceEntity> comparisonCompanyAndSector(List<String> codes, LocalDateTime start, LocalDateTime end){
		try {
			List<StockPriceEntity> result= stockPriceRepository.findAllByCompanyCodeInAndDateTimeBetween(codes,start,end);
			return result;
		} catch (Exception e){
			logger.error("Fail to query company data:", e);
			return null;
		}
	}

}
