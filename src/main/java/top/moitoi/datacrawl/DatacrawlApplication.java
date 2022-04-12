package top.moitoi.datacrawl;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@MapperScan("top.moitoi.datacrawl.mapper")
@SpringBootApplication
public class DatacrawlApplication {

	public static void main(String[] args) {
		log.warn("------------------开始启动------------------");
		SpringApplication.run(DatacrawlApplication.class, args);
		log.warn("------------------启动成功------------------");
	}

}
