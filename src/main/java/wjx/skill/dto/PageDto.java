package wjx.skill.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PageDto<T> implements Serializable {

    private Integer number; //当前页面-1
    private Integer totalElements; //总条数
    private Integer totalPages; //总页面
    private Integer size; //页面大小
    private Boolean first; //是否首页
    private Boolean last; //是否尾页
    private List<Map<String, Object>> list;

    private Integer from; //用于分页
    private Integer pageSize;//用于分页
    private String username;//用于分页查询用户

    private Integer total;
    public PageDto() {
    }

    public PageDto(Integer from, Integer pageSize, String username) {
        this.from = from;
        this.pageSize = pageSize;
        this.username = username;
    }

    public PageDto(Integer number, Integer totalElements, Integer totalPages, Integer size, Boolean first, Boolean last) {
        this.number = number;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.size = size;
        this.first = first;
        this.last = last;
    }
    public PageDto(List<Map<String, Object>> list, Integer number, Integer totalElements, Integer totalPages, Integer size, Boolean first, Boolean last) {
        this.number = number;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.size = size;
        this.first = first;
        this.last = last;
        this.list = list;
    }
    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }
}
