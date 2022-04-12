package top.moitoi.datacrawl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.moitoi.datacrawl.domain.po.SentimentKeyWord;

import java.util.List;

public interface SentimentKeyWordMapper extends BaseMapper<SentimentKeyWord> {

    List<SentimentKeyWord> findAll();
}
