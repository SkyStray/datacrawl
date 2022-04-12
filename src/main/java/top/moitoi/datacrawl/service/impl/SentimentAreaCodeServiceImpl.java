package top.moitoi.datacrawl.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.moitoi.datacrawl.domain.po.SentimentAreaCode;
import top.moitoi.datacrawl.mapper.SentimentAreaCodeMapper;
import top.moitoi.datacrawl.service.SentimentAreaCodeService;

import java.util.List;

/**
 * @program: IntelliJ IDEA / datacrawl
 * @className: SentimentAreaCodeServiceImpl
 * @description:
 * @author: 黄金元
 * @version:
 * @create: 2022/03/02 14:40
 **/
@Slf4j
@Service
public class SentimentAreaCodeServiceImpl implements SentimentAreaCodeService {

    protected static final String AREA_CODE = "AREA_CODE";

    @Autowired
    private SentimentAreaCodeMapper sentimentAreaCodeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SentimentAreaCode> findAll() {
        List<SentimentAreaCode> areaCodes = (List<SentimentAreaCode>) redisTemplate.opsForValue().get(AREA_CODE);
        if (areaCodes == null) {
            areaCodes = sentimentAreaCodeMapper.findAll();
            redisTemplate.opsForValue().set(AREA_CODE, areaCodes);
        }
        return areaCodes;
    }
}
