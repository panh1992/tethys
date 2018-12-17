package org.athena.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * 分页实体
 */
@Getter
public class Page<T> {

    /**
     * 携带数据集合
     */
    @JsonProperty("content")
    private List<T> content;

    /**
     * 页码
     */
    @JsonProperty("page")
    private int page;

    /**
     * 每页条数
     */
    @JsonProperty("size")
    private int size;

    /**
     * 总条数
     */
    private long total;

    /**
     * 创建分页响应
     * @param content 存储集合
     * @param page  页码
     * @param size  每页数量
     * @param total 总记录数
     */
    private Page(List<T> content, int page, int size, long total) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.total = total;
    }

    public static <T> Page<T> of(List<T> content, int page, int size, long total) {
        return new Page(content, page, size, total);
    }

    /**
     * 总页数
     */
    @JsonProperty("total_pages")
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    /**
     * 总记录数
     */
    @JsonProperty("total_elements")
    public long getTotalElements() {
        return total;
    }

}
