package com.bee.user.mapper;

import com.bee.user.pojo.MallComment;
import java.util.List;

public interface MallCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallComment record);

    MallComment selectByPrimaryKey(Integer id);

    List<MallComment> selectAll();

    int updateByPrimaryKey(MallComment record);
}