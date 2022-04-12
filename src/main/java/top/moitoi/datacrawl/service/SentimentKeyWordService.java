package top.moitoi.datacrawl.service;

import top.moitoi.datacrawl.domain.po.SentimentKeyWord;

import java.util.List;

public interface SentimentKeyWordService {
    List<SentimentKeyWord> findAll();
}
