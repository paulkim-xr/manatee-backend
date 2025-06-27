package com.rathon.manatee.community.mapper;

import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.model.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> getPagedPosts(int offset, Integer size, String sortColumn, String sortDirection);
    List<Post> findPostsByBoardId(Long id, Integer offset, Integer size, String sortColumn, String sortDirection);

    int countPostsByBoardId(Long id);

    int getCount();
}
