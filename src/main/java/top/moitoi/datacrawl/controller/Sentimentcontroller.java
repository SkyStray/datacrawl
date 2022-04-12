package top.moitoi.datacrawl.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.moitoi.datacrawl.domain.bo.SentimentBo;
import top.moitoi.datacrawl.service.SentimentService;

import javax.validation.Valid;
import java.util.Map;


/**
 * @program: IntelliJ IDEA / datacrawl
 * @className: Testcontroller
 * @description: 测试
 * @author: 黄金元
 * @version: 1.0.0
 * @create: 2022/03/02 13:43
 **/
@RestController
@RequestMapping("sentiment")
public class Sentimentcontroller {

    @Autowired
    private SentimentService SentimentService;

    @PostMapping("crawl")
    public String crawl(@RequestBody @Valid SentimentBo sentimentBo){
        return SentimentService.crawl(sentimentBo);
    }

    @GetMapping("crawl")
    public String crawl(){
        return "进来了";
    }

}
