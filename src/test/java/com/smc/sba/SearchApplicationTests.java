package com.smc.sba;

import com.smc.sba.service.StockPriceService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchApplicationTests {

	@Autowired
	private StockPriceService stockPriceService;


	@Test
	public void testSingleCompanyChart() {
		LocalDateTime start = LocalDateTime.parse((CharSequence) "2019-11-30 16:55:55",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime end = LocalDateTime.parse((CharSequence) "2019-11-30 16:55:55",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		stockPriceService.comparisonSingleCompany("345902", start, end);
	}

}
