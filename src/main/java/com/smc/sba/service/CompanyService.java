package com.smc.sba.service;

import com.smc.sba.entity.CompanyEntity;
import com.smc.sba.entity.StockPriceEntity;
import com.smc.sba.repository.CompanyRepository;
import com.smc.sba.repository.StockPriceRepository;
import com.smc.sba.utils.CommonResult;
import com.smc.sba.utils.ResponseCode;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CompanyService
 * @Description TODO
 * @Author YuChaoZheng
 * @Date 12/3/2019 15:40 AM
 * @Version 1.0
 **/
@Service
public class CompanyService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CompanyRepository companyRepository;

	/**
	 * Description: query all company.
	 *
	 * @param:
	 * @return: com.smc.sba.utils.CommonResult
	 * @auther: Yuchaozh
	 * @date: 2019/12/04 10:39
	 */
	public CommonResult findAll() {
		try {
			List<CompanyEntity> company = companyRepository.findAll();
			return CommonResult.build(ResponseCode.SUCCESS, "SUCCESS!", company);
		} catch (Exception e) {
			logger.error("Fail to query company data:", e);
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
	public CommonResult findByCode(String companyCode) {
		try {
			CompanyEntity company = companyRepository.findByCode(companyCode);
			return CommonResult.build(ResponseCode.SUCCESS, "SUCCESS!", company);
		} catch (Exception e) {
			logger.error("Fail to query company data:", e);
			return CommonResult.build(ResponseCode.ERROR_ACCESS_DB, "DB ERROR!");
		}
	}

	/**
	 * Description: query company by companyName.
	 *
	 * @param:
	 * @return: com.smc.sba.utils.CommonResult
	 * @auther: Yuchaozh
	 * @date: 2019/12/04 10:39
	 */
	public CommonResult findByName(String companyName) {
		try {
			CompanyEntity company = companyRepository.findByName(companyName);
			return CommonResult.build(ResponseCode.SUCCESS, "SUCCESS!", company);
		} catch (Exception e) {
			logger.error("Fail to query company data:", e);
			return CommonResult.build(ResponseCode.ERROR_ACCESS_DB, "DB ERROR!");
		}
	}

	public CommonResult findByCompanyid(Integer id) {
		try {
			CompanyEntity company = companyRepository.findById(id).get();
			return CommonResult.build(ResponseCode.SUCCESS, "SUCCESS!", company);
		} catch (Exception e) {
			logger.error("Fail to query company data:", e);
			return CommonResult.build(ResponseCode.ERROR_ACCESS_DB, "DB ERROR!");
		}
	}

	public List<CompanyEntity> findComBySector(String sector) {
		try {
			List<CompanyEntity> companyEntities = companyRepository.findAllBySectorName(sector);
			return companyEntities;
		} catch (Exception e) {
			logger.error("Fail to query company data:", e);
			return null;
		}

	}
}
