package org.example.biologyera.mapper.Masterpiece;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 张文化
 * @Description: zwh$
 * @DateTime: 2025/3/24$ 23:26$
 * @Params: $
 * @Return $
 */
@Mapper
public interface MasterpieceMapper {
    void saveMasterpieceInfo(@Param("personId") Long personId,@Param("outputUrl") String outputUrl);

    String get3DModelById(@Param("personId") Long personId);
}
