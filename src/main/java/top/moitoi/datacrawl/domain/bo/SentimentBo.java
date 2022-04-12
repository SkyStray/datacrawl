package top.moitoi.datacrawl.domain.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @program: IntelliJ IDEA / datacrawl
 * @className: SentimentBo
 * @description: 中国舆情网 - 网页参数
 * @author: 黄金元
 * @version: 1.0.0
 * @create: 2022/03/03 14:44
 **/
@Data
@Accessors(chain = true)
public class SentimentBo {
    /**
     * 浏览url地址
     */
    @NotBlank(message = "浏览url地址不能为空")
    private String url;
    /**
     * 专题(分类)
     * 正面/负面/空
     */
    @NotBlank(message = "专题(分类)不能为空")
    private String syslid;
    /**
     * 褒贬
     */
    @NotBlank(message = "褒贬不能为空")
    private String sentimen;
    /**
     * 是否是垃圾文
     */
    @NotBlank(message = "是否是垃圾文不能为空")
    private String category;
}
