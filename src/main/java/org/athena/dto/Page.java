package org.athena.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 分页实体
 */
public class Page<T> {

    /**
     * 携带数据集合
     */
    private List<T> content;

    /**
     * 页码
     */
    private int page;

    /**
     * 每页条数
     */
    private int size;

    /**
     * 总条数
     */
    private long total;

    @Deprecated
    public Page(List<T> content, int page, int size, long total) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.total = total;
    }

    public static <T> Page<T> of(List<T> content, int page, int size, long total) {
        return new Page(content, page, size, total);
    }

    @JsonProperty("content")
    public List<T> getContent() {
        return content;
    }

    @JsonProperty("page")
    public int getPage() {
        return page;
    }

    @JsonProperty("size")
    public int getSize() {
        return size;
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
