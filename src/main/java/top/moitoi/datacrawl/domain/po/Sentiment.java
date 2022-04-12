package top.moitoi.datacrawl.domain.po;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: IntelliJ IDEA / cosmeticapp
 * @className: Sentiment
 * @description: Sentiment
 * @author: 黄金元
 * @version: 1.0
 * @create: 2022-03-01 14:52:45
 **/
@Data
@Accessors(chain = true)
@Table ( name ="sentiment" )
public class Sentiment  implements Serializable {

	private static final long serialVersionUID =  7255894572383670026L;

   	@Id
	@Column(name = "id" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**文本*/
   	@Column(name = "content" )
	private String content;

	/**图片信息*/
   	@Column(name = "ImagesUrls" )
	private String imagesurls = "null";

	/**点赞数*/
   	@Column(name = "likes" )
	private String likes = "0";

	/**作者*/
   	@Column(name = "author" )
	private String author;

	/**阅读量*/
   	@Column(name = "reds" )
	private String reds = "0";

	/**发布时间*/
   	@Column(name = "time" )
	private String time;

	/**文章URL*/
   	@Column(name = "url" )
	private String url;

	/**回复量*/
   	@Column(name = "reply" )
	private String reply = "0";

	/**文章id*/
   	@Column(name = "guid" )
	private String guid = IdUtil.simpleUUID();;

	/**文章类型*/
   	@Column(name = "style" )
	private String style = "NEWS";

	/**标题*/
   	@Column(name = "title" )
	private String title;

	/**摘要*/
   	@Column(name = "summary" )
	private String summary;

	/**关键字*/
   	@Column(name = "keyword" )
	private String keyword;

	/**行业*/
   	@Column(name = "industry" )
	private String industry;

	/**专题(分类)*/
   	@Column(name = "syslid" )
	private String syslid;

	/**褒贬*/
   	@Column(name = "sentimen" )
	private String sentimen;

	/**是否是垃圾文*/
   	@Column(name = "category" )
	private String category = "false";

	/**微博博主城市名称*/
   	@Column(name = "cityName" )
	private String cityname;

	/**微博博主省份*/
   	@Column(name = "provinceName" )
	private String provincename;

	/**微博博主性别*/
   	@Column(name = "genderName" )
	private String gendername;

	/**站点名*/
   	@Column(name = "webSiteName" )
	private String websitename;

	/**所属地域代码*/
   	@Column(name = "areas" )
	private String areas;

	/**sentiment_list 表id*/
   	@Column(name = "sentimentId" )
	private Integer sentimentid;

	/**时间*/
   	@Column(name = "date" )
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date date;

	/**是否有效.0:有效,1:无效*/
   	@Column(name = "invalid" )
	private Integer invalid;

	/**地区名*/
	//不晓得
   	@Column(name = "area_name" )
	private String areaName;

	/**来源*/
   	@Column(name = "sourceSiteName" )
	private String sourcesitename;

	/**是否是当地信息*/
   	@Column(name = "is_local" )
	private Integer isLocal;

	/**本地匹配关键字*/
   	@Column(name = "local_key" )
	private String localKey;

}
