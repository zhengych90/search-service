package com.smc.sba.controller;


import com.smc.sba.dto.SectorDTO;
import com.smc.sba.entity.CompanyEntity;
import com.smc.sba.entity.StockPriceEntity;
import com.smc.sba.service.CompanyService;
import com.smc.sba.service.ExchangeService;
import com.smc.sba.service.IpoService;
import com.smc.sba.service.StockPriceService;
import com.smc.sba.utils.CommonResult;
import com.smc.sba.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private StockPriceService stockPriceService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ExchangeService exchangeService;
	@Autowired
	private IpoService ipoService;

	@GetMapping("/company")
	public CommonResult searchCompany() {
		return companyService.findAll();
	}

	@GetMapping("/company_code/{companyCode}")
	public CommonResult searchCompanyByCode(@PathVariable String companyCode) {
		return companyService.findByCode(companyCode);
	}

	@GetMapping("/company_name/{companyName}")
	public CommonResult searchCompanyByName(@PathVariable String companyName) {
		return companyService.findByName(companyName);
	}

	//Jack add for findByCompanyid 12/10/2019
	@GetMapping("/company_id/{companyid}")
	public CommonResult searchByCompanyid(@PathVariable("companyid") Integer companyid) {
		return companyService.findByCompanyid(companyid);
	}

	@GetMapping("/exchange")
	public CommonResult searchExchange() {
		return exchangeService.findAll();
	}

	@GetMapping("/exchange/{stockExchange}")
	public CommonResult searchExchange(@PathVariable String stockExchange) {
		return exchangeService.findByExchange(stockExchange);
	}

	@GetMapping("/exchange/id/{id}")
	public CommonResult searchExchange(@PathVariable int id) {
		return exchangeService.findById(id);
	}

	@GetMapping("/ipo")
	public CommonResult searchIpo() {
		return ipoService.findAll();
	}

	// @PostMapping("/{companyCode}/price/in_time")

	@GetMapping("/ipo/{id}")
	public CommonResult searchIpoById(@PathVariable int id) {
		return ipoService.findById(id);
	}

	@GetMapping("/ipo/stock_exchange/{stockExchange}")
	public CommonResult searchIpoBystock(@PathVariable String stockExchange) {
		return ipoService.findIPOByExchange(stockExchange);
	}

	@GetMapping("/ipo/company_name/{companyName}")
	public CommonResult searchIpoByName(@PathVariable String companyName) {
		return ipoService.findIPOByCompanyName(companyName);
	}


	@PostMapping("/comparison/{companyCode}")
	public CommonResult comparisonSingleCom(@PathVariable("companyCode") String code,
			@RequestBody Map params) throws Exception {
		LocalDateTime start = LocalDateTime
				.parse((CharSequence) params.get("start"),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime end = LocalDateTime
				.parse((CharSequence) params.get("end"),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return stockPriceService.comparisonSingleCompany(code, start, end);
	}

	@PostMapping("/comparison/companyAnd/{sectorName}")
	public CommonResult comparisonComAndSector(@PathVariable("sectorName") String sectorName,
			@RequestBody Map params) throws Exception {
		LocalDateTime start = LocalDateTime.parse((CharSequence) params.get("start"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime end = LocalDateTime.parse((CharSequence) params.get("end"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		List<CompanyEntity> companyEntityList = companyService.findComBySector(sectorName);
		List<String> stockCodeList = companyEntityList.stream().map(CompanyEntity::getCompanyCode)
				.collect(Collectors.toList());
		List<StockPriceEntity> stockPriceEntities = new ArrayList<>();
		stockPriceEntities = (List<StockPriceEntity>) stockPriceService
				.comparisonCompanyAndSector(stockCodeList, start, end);
		Map<LocalDateTime, List<StockPriceEntity>> group = stockPriceEntities.stream().
				collect(Collectors.groupingBy(StockPriceEntity::getDateTime));
		List<SectorDTO> sectorDTOS = new ArrayList<>();
		for (Map.Entry<LocalDateTime, List<StockPriceEntity>> entry : group.entrySet()) {
			SectorDTO sectorDTO = new SectorDTO();
			BigDecimal sectorPrice = entry.getValue().stream().map(StockPriceEntity::getCurrentPrice)
					.reduce(BigDecimal.ZERO, BigDecimal::add)
					.divide(new BigDecimal(entry.getValue().size()), 2, ROUND_HALF_DOWN);
			sectorDTO.setLabel(entry.getKey());
			sectorDTO.setValue(sectorPrice);
			sectorDTOS.add(sectorDTO);
		}
		sectorDTOS.sort((o1, o2) -> o1.getLabel().compareTo(o2.getLabel()));
		return CommonResult.build(ResponseCode.SUCCESS, "SUCCESS!", sectorDTOS);
	}


}
