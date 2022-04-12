package top.moitoi.datacrawl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.moitoi.datacrawl.domain.po.Sentiment;

import java.util.List;

public interface SentimentMapper extends BaseMapper<Sentiment> {

    List<String> findIndustry();
}
