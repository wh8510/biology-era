package org.example.biologyera.common.enums;

import java.io.File;

/**
 * 全局常量
 *
 * @author zwh
 * @date 2025/3/10
 */
public interface Constants {

    /**
     * 菜单根id
     */
    Long RESOURCE_ROOT_ID = 1001L;

    /**
     * 日期格式化
     */
    String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    String IMAGE = "image";

    String DOCUMENT = "document";

    Integer CITATION_COUNT_ADD = 1;

    Integer CITATION_COUNT_REDUCE = -1;

    String SAMPLE_ROOT_DIR = "examples";
}
