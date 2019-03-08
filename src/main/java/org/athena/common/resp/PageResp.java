package org.athena.common.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

/**
 * 分页实体
 */
@Getter
@ApiModel(description = "分页实体")
public class PageResp<T> {

    @ApiModelProperty("携带数据集合")
    private List<T> content;

    @ApiModelProperty("页码")
    private int page;

    @ApiModelProperty("每页记录数")
    private int size;

    /**
     * 总条数
     */
    @ApiModelProperty("总记录数")
    private long totalElements;

    /**
     * 创建分页响应
     *
     * @param content 存储集合
     * @param page    页码
     * @param size    每页数量
     * @param total   总记录数
     */
    private PageResp(List<T> content, int page, int size, long total) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = total;
    }

    public static <T> PageResp<T> of(List<T> content, int page, int size, long total) {
        return new PageResp(content, page, size, total);
    }

    @ApiModelProperty("总页数")
    @JsonProperty("totalPages")
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) getSize());
    }

}
