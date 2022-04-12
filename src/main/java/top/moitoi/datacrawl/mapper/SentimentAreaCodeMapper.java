package top.moitoi.datacrawl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.moitoi.datacrawl.domain.po.SentimentAreaCode;

import java.util.List;

public interface SentimentAreaCodeMapper extends BaseMapper<SentimentAreaCode> {
    List<SentimentAreaCode> findAll();
}
