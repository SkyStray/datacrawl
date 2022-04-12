package top.moitoi.datacrawl.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.moitoi.datacrawl.domain.bo.SentimentBo;
import top.moitoi.datacrawl.domain.po.Sentiment;
import top.moitoi.datacrawl.domain.po.SentimentAreaCode;
import top.moitoi.datacrawl.domain.po.SentimentKeyWord;
import top.moitoi.datacrawl.mapper.SentimentMapper;
import top.moitoi.datacrawl.service.SentimentAreaCodeService;
import top.moitoi.datacrawl.service.SentimentKeyWordService;
import top.moitoi.datacrawl.service.SentimentService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: IntelliJ IDEA / datacrawl
 * @className: SentimentServiceImpl
 * @description:
 * @author: 黄金元
 * @version:
 * @create: 2022/03/02 15:04
 **/
@Slf4j
@Service
public class SentimentServiceImpl implements SentimentService {

    private final static String INDUSTRY = "INDUSTRY";

    @Autowired
    private SentimentMapper sentimentMapper;

    @Autowired
    private SentimentAreaCodeService sentimentAreaCodeService;

    @Autowired
    private SentimentKeyWordService sentimentKeyWordService;

    @Autowired
    private RedisTemplate redisTemplate;


    @SneakyThrows
    @Override
    public String web(String url) {

        //String html = "https://www.spaqyq.com/info/20290.html";
        String html = url;
        Document document = Jsoup.connect(html).get();
        String text = document.text();
        if ("无访问权限，请联系网站管理员或返回网站首页。".equals(text)){
            //System.out.println("无访问权限，请联系网站管理员或返回网站首页。");
            return "无访问权限，请联系网站管理员或返回网站首页。";
        }
        //创建文件保存对象
        Sentiment sentiment = new Sentiment();
        Elements elements = document.select("div.infoWrap");

        //设置标题
        String title = elements.select("h1").text();
        sentiment.setTitle(title);
        //System.out.println("标题:"+title);

        //设置作者
        String author = elements.select("div.info > i").get(0).text().replace("作者：", "");
        sentiment.setAuthor(author);
        //System.out.println("作者:"+author);

        //设置来源
        String sourcesitename = elements.select("div.info > i").get(1).text().replace("来源：", "");
        sentiment.setSourcesitename(sourcesitename);
        //System.out.println("来源:"+sourcesitename);

        //设置发布时间
        String timeString = elements.select("div.info > i").get(2).text().replace("时间：", "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(timeString);
        long time = date.getTime();
        sentiment.setTime(String.valueOf(time));
        sentiment.setDate(date);
        //System.out.println("发布时间戳:"+time);
        //System.out.println("发布时间:"+date);

        //设置正文
        String content = elements.select("div.content").text();
        sentiment.setContent(content);
        System.out.println("正文:"+content);


        StringBuilder areaName = new StringBuilder();
        StringBuilder areaCode = new StringBuilder();
        List<SentimentAreaCode> areaCodes = sentimentAreaCodeService.findAll();
        Integer a = 0;
        for (SentimentAreaCode sentimentAreaCode : areaCodes) {
            if (content.contains(sentimentAreaCode.getAreaName())){
                areaName.append(sentimentAreaCode.getAreaName()).append(",");
                areaCode.append(sentimentAreaCode.getAreaCode()).append(",");
            }
        }
        String code = "["+areaCode.substring(0,areaCode.length()-1)+"]";
        ////log.info("区域:"+areaName);
        ////log.info("区域编码:"+code);
        //设置地区名
        sentiment.setAreaName(areaName.toString());
        //设置所属地域代码
        sentiment.setAreas(code);

        //关键字
        StringBuilder keyWord = new StringBuilder();
        List<SentimentKeyWord> sentimentKeyWords = sentimentKeyWordService.findAll();
        for (SentimentKeyWord sentimentKeyWord : sentimentKeyWords) {
            if (content.contains(sentimentKeyWord.getKeyWord())){
                keyWord.append(sentimentKeyWord.getKeyWord()).append(",");
            }
        }
        //log.info("关键字:"+keyWord);
        sentiment.setKeyword(keyWord.toString());

        //行业
        StringBuilder industry = new StringBuilder();
        List<String> industrys = findIndustry();
        for (String s : industrys) {
            if (content.contains(s)){
                industry.append(s).append(",");
            }
        }
        sentiment.setIndustry(industry.substring(0,industry.length()-1));
        //log.info("行业:"+sentiment.getIndustry());


        //设置图片
        StringBuilder imagesurls = new StringBuilder();
        Elements contentElements = elements.select("div.content");
        Elements imgs = contentElements.select("img[src]");
        if (imgs.size() != 0 ){
            for (int i = 0; i < imgs.size(); i++) {
                String src = imgs.attr("src");
                if (imgs.size()-1 == i){
                    imagesurls.append(src);
                }else {
                    imagesurls.append(src).append(",");
                }
            }
            sentiment.setImagesurls(imagesurls.toString());
            //System.out.println("图片:"+imagesurls.toString());
        }



        //设置文章URL
        sentiment.setUrl(html);
        //System.out.println("文章URL:"+html);

        //摘要
        String summary = elements.select("div.description > p").text();
        sentiment.setSummary(summary);
        //System.out.println("摘要:"+summary);

        //站点名
        if (html.contains("https://www.spaqyq.com/")){
            sentiment.setWebsitename("中国食品安全舆情");
            //System.out.println("站点名:"+"中国食品安全舆情");
        }

        //sentiment_list 表id
        sentiment.setSentimentid(1);
        //System.out.println("sentiment_list 表id:"+1);

        //是否有效
        sentiment.setInvalid(0);
        //System.out.println("是否有效:"+0);

        //是否是当地信息
        sentiment.setIsLocal(0);
        //System.out.println("是否是当地信息:"+0);


        System.out.println(sentiment.toString());
        return sentiment.toString();
    }

    @Override
    public List<String> findIndustry() {
        List<String> listNew = (List<String>) redisTemplate.opsForValue().get(INDUSTRY);
        if (listNew == null){
            List<String> list = sentimentMapper.findIndustry();
            List<String> resultList = new ArrayList<>();
            for (String s : list) {
                String[] split = s.split(",");
                for (String s1 : split) {
                    resultList.add(s1);
                }
            }
            listNew = delRepeat(resultList);
            redisTemplate.opsForValue().set(INDUSTRY,listNew);
        }
        return listNew;
    }

    @SneakyThrows
    @Override
    public String crawl(SentimentBo sentimentBo) {

        //String html = "https://www.spaqyq.com/info/20290.html";
        String html = sentimentBo.getUrl();
        Document document = Jsoup.connect(html).get();
        String text = document.text();
        if ("无访问权限，请联系网站管理员或返回网站首页。".equals(text)){
            //System.out.println("无访问权限，请联系网站管理员或返回网站首页。");
            return "无访问权限，请联系网站管理员或返回网站首页。";
        }
        //创建文件保存对象
        Sentiment sentiment = new Sentiment();
        Elements elements = document.select("div.infoWrap");

        //设置标题
        String title = elements.select("h1").text();
        sentiment.setTitle(title);
        //System.out.println("标题:"+title);

        //设置作者
        String author = elements.select("div.info > i").get(0).text().replace("作者：", "");
        sentiment.setAuthor(author);
        //System.out.println("作者:"+author);

        //设置来源
        String sourcesitename = elements.select("div.info > i").get(1).text().replace("来源：", "");
        sentiment.setSourcesitename(sourcesitename);
        //System.out.println("来源:"+sourcesitename);

        //设置发布时间
        String timeString = elements.select("div.info > i").get(2).text().replace("时间：", "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(timeString);
        long time = date.getTime();
        sentiment.setTime(String.valueOf(time));
        sentiment.setDate(date);
        //System.out.println("发布时间戳:"+time);
        //System.out.println("发布时间:"+date);

        //设置正文
        String content = elements.select("div.content").text();
        sentiment.setContent(content);
        System.out.println("正文:"+content);


        StringBuilder areaName = new StringBuilder();
        StringBuilder areaCode = new StringBuilder();
        List<SentimentAreaCode> areaCodes = sentimentAreaCodeService.findAll();
        Integer a = 0;
        for (SentimentAreaCode sentimentAreaCode : areaCodes) {
            if (content.contains(sentimentAreaCode.getAreaName())){
                areaName.append(sentimentAreaCode.getAreaName()).append(",");
                areaCode.append(sentimentAreaCode.getAreaCode()).append(",");
            }
        }
        String code = "["+areaCode.substring(0,areaCode.length()-1)+"]";
        ////log.info("区域:"+areaName);
        ////log.info("区域编码:"+code);
        //设置地区名
        sentiment.setAreaName(areaName.toString());
        //设置所属地域代码
        sentiment.setAreas(code);

        //关键字
        StringBuilder keyWord = new StringBuilder();
        List<SentimentKeyWord> sentimentKeyWords = sentimentKeyWordService.findAll();
        for (SentimentKeyWord sentimentKeyWord : sentimentKeyWords) {
            if (content.contains(sentimentKeyWord.getKeyWord())){
                keyWord.append(sentimentKeyWord.getKeyWord()).append(",");
            }
        }
        //log.info("关键字:"+keyWord);
        sentiment.setKeyword(keyWord.toString());

        //行业
        StringBuilder industry = new StringBuilder();
        List<String> industrys = findIndustry();
        for (String s : industrys) {
            if (content.contains(s)){
                industry.append(s).append(",");
            }
        }
        if (StrUtil.isNotEmpty(industry)){
            sentiment.setIndustry(industry.substring(0,industry.length()-1));
            //log.info("行业:"+sentiment.getIndustry());
        }


        //专题(分类)
        sentiment.setSyslid(sentimentBo.getSyslid());
        //log.info("专题(分类):"+sentiment.getSyslid());

        //褒贬
        sentiment.setSentimen(sentimentBo.getSentimen());
        //log.info("褒贬:"+sentiment.getSentimen());

        //是否是垃圾文
        sentiment.setCategory(sentimentBo.getCategory());
        //log.info("是否是垃圾文:"+sentiment.getSentimen());

        //设置图片
        StringBuilder imagesurls = new StringBuilder();
        Elements contentElements = elements.select("div.content");
        Elements imgs = contentElements.select("img[src]");
        if (imgs.size() != 0 ){
            for (int i = 0; i < imgs.size(); i++) {
                String src = imgs.attr("src");
                if (imgs.size()-1 == i){
                    imagesurls.append(src);
                }else {
                    imagesurls.append(src).append(",");
                }
            }
            sentiment.setImagesurls(imagesurls.toString());
            //System.out.println("图片:"+imagesurls.toString());
        }



        //设置文章URL
        sentiment.setUrl(html);
        //System.out.println("文章URL:"+html);

        //摘要
        String summary = elements.select("div.description > p").text();
        sentiment.setSummary(summary);
        //System.out.println("摘要:"+summary);

        //站点名
        if (html.contains("https://www.spaqyq.com/")){
            sentiment.setWebsitename("中国食品安全舆情");
            //System.out.println("站点名:"+"中国食品安全舆情");
        }

        //sentiment_list 表id
        sentiment.setSentimentid(1);
        //System.out.println("sentiment_list 表id:"+1);

        //是否有效
        sentiment.setInvalid(0);
        //System.out.println("是否有效:"+0);

        //是否是当地信息
        sentiment.setIsLocal(0);
        //System.out.println("是否是当地信息:"+0);


        System.out.println(sentiment.toString());
        return sentiment.toString();
    }

    // Set去重并方法
    public List<String> delRepeat(List<String> list) {
        List<String> listNew = new ArrayList<String>(new TreeSet<String>(list));
        return listNew;
    }
}
