package org.example.biologyera.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 基础查询类
 *
 * @author zwh
 * @date 2025/03/10
 */
@Getter
@Setter
@ToString
public class BaseQuery {
    /**
     * 页码
     */
    Long pageNumber = 1L;

    /**
     * 页大小
     */
    Long pageSize = 50L;

    /**
     * 查询词
     */
    String searchKey;
}