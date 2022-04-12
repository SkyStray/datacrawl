package top.moitoi.datacrawl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.moitoi.datacrawl.domain.po.SentimentKeyWord;
import top.moitoi.datacrawl.service.SentimentKeyWordService;
import top.moitoi.datacrawl.service.SentimentService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class DatacrawlApplicationTests {

	@Autowired
	private SentimentService sentimentService;

	@Autowired
	private SentimentKeyWordService sentimentKeyWordService;

	@Test
	void test(){
		String url = "https://www.spaqyq.com/info/20823.html";
		sentimentService.web(url);
	}

	@Test
	void test01(){
		List<SentimentKeyWord> all = sentimentKeyWordService.findAll();
	}

	@Test
	void test02(){
		sentimentService.findIndustry();
	}

}
