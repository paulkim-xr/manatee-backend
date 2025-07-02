package com.rathon.manatee.community.mapper;

import com.rathon.manatee.community.model.Post;
import com.rathon.manatee.core.mapper.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper extends ObjectMapper<Post> {
    List<Post> findPostsByBoardId(Long id, Integer offset, Integer size, String sortColumn, String sortDirection);
    int countPostsByBoardId(Long id);

    List<Post> search(Long id, Integer offset, Integer size, String sortColumn, String sortDirection, String query, Integer option);
    int searchCount(Long id, String query, Integer option);
}
