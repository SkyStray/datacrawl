package top.moitoi.datacrawl.service;

import top.moitoi.datacrawl.domain.bo.SentimentBo;

import java.util.List;

public interface SentimentService {

    String web(String url);

    List<String> findIndustry();

    String crawl(SentimentBo sentimentBo);
}
