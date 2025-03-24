package org.example.biologyera.model.chat.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/24$ 14:53$
 * @Params: $
 * @Return $
 */
@Data
@TableName("chat_room")
public class ChatRoomMegVO {
    /**
     * roomId
     */
    private Integer roomId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
