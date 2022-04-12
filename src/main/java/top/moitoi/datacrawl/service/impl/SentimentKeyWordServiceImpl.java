package top.moitoi.datacrawl.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.moitoi.datacrawl.domain.po.SentimentKeyWord;
import top.moitoi.datacrawl.mapper.SentimentKeyWordMapper;
import top.moitoi.datacrawl.service.SentimentKeyWordService;

import java.util.List;

/**
 * @program: IntelliJ IDEA / datacrawl
 * @className: SentimentKeyWordServiceImpl
 * @description:
 * @author: 黄金元
 * @version:
 * @create: 2022/03/02 15:51
 **/
@Slf4j
@Service
public class SentimentKeyWordServiceImpl implements SentimentKeyWordService {

    protected final static String KEYWORD = "KEYWORD";

    @Autowired
    private SentimentKeyWordMapper sentimentKeyWordMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<SentimentKeyWord> findAll() {
        List<SentimentKeyWord> sentimentKeyWords = null;
        sentimentKeyWords = (List<SentimentKeyWord>) redisTemplate.opsForValue().get(KEYWORD);
        if (sentimentKeyWords == null){
            sentimentKeyWords = sentimentKeyWordMapper.findAll();
            redisTemplate.opsForValue().set(KEYWORD,sentimentKeyWords);
        }
        return sentimentKeyWords;
    }
}
