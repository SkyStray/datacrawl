package top.moitoi.datacrawl.domain.po;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: IntelliJ IDEA / cosmeticapp
 * @className: SentimentAreaCode
 * @description: SentimentAreaCode
 * @author: 黄金元
 * @version: 1.0
 * @create: 2022-03-02 13:37:53
 **/
@Data
@Accessors(chain = true)
@Table ( name ="sentiment_area_code" )
public class SentimentAreaCode  implements Serializable {

	private static final long serialVersionUID =  2016365689685620036L;

	/**主键*/
   	@Id
	@Column(name = "id" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**区域名*/
   	@Column(name = "area_name" )
	private String areaName;

	/**区域编码*/
   	@Column(name = "area_code" )
	private String areaCode;

}
