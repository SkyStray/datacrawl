package top.moitoi.datacrawl.domain.po;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: IntelliJ IDEA / cosmeticapp
 * @className: SentimentKeyWord
 * @description: SentimentKeyWord
 * @author: 黄金元
 * @version: 1.0
 * @create: 2022-03-01 17:05:42
 **/
@Data
@Accessors(chain = true)
@Table ( name ="sentiment_key_word" )
public class SentimentKeyWord  implements Serializable {

	private static final long serialVersionUID =  3918824596411635615L;

	/**主键*/
   	@Id
	@Column(name = "id" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**关键字*/
   	@Column(name = "key_word" )
	private String keyWord;

	/**舆情中出现的次数*/
   	@Column(name = "num" )
	private Integer num;

	/**舆情id*/
   	@Column(name = "sentiment_id" )
	private Integer sentimentId;

	/**关键字id*/
   	@Column(name = "key_word_id" )
	private Integer keyWordId;

}
