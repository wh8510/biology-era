package org.example.biologyera.model;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.sql.Date;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/12$ 23:56$
 * @Params: $
 * @Return $
 */
@Data
public class ObjectPO {
    /**
     * 创建时间
     */
    public Date createTime;
    /**
     * 创建人
     */
    public Long createPerson;
    /**
     * 更新时间
     */
    public Date updateTime;
    /**
     * 更新人
     */
    public Long updatePerson;
    /**
     * 逻辑删除
     */
    public Integer delete;
}
