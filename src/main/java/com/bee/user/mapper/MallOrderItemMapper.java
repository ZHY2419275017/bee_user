package com.bee.user.mapper;

import com.bee.user.pojo.MallOrderItem;
import java.util.List;

public interface MallOrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallOrderItem record);

    MallOrderItem selectByPrimaryKey(Integer id);

    List<MallOrderItem> selectAll();

    int updateByPrimaryKey(MallOrderItem record);
}