package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.mapper.BoardMapper;
import com.rathon.manatee.community.mapper.PostMapper;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.Post;
import com.rathon.manatee.community.service.mapper.BoardMapperService;
import com.rathon.manatee.community.service.mapper.PostMapperService;
import com.rathon.manatee.core.service.ObjectService;
import com.rathon.manatee.core.dto.PagedList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService extends ObjectService<Board, BoardDto, BoardMapper, BoardMapperService> {
    private final PostMapper postMapper;
    private final PostService postService;
    private final PostMapperService postMapperService;

    public BoardService(BoardMapper mapper, PostMapper postMapper, PostMapperService postMapperService, BoardMapperService service, PostService postService) {
        super(mapper, service);
        this.postMapper = postMapper;
        this.postMapperService = postMapperService;
        this.postService = postService;
    }

    public PagedList<PostSummaryDto> getBoardPosts(Long id, Integer page, Integer size, String sort) {
        String sortColumn = "postedTime";
        String sortDirection = "desc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<PostSummaryDto> posts = postMapper.findPostsByBoardId(id, page * size, size, sortColumn, sortDirection).stream().map(postMapperService::summarize).toList();

        return PagedList.build(posts, page, size, postMapper.countPostsByBoardId(id));
    }

    // Maybe fix post search and hand over?
    public PagedList<PostSummaryDto> searchBoardPosts(Long id, Integer page, Integer size, String sort, String query, Integer option) {
        String sortColumn = "posted_time";
        String sortDirection = "desc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<PostSummaryDto> posts = postMapper.search(id, page * size, size, sortColumn, sortDirection, query, option).stream().map(postMapperService::summarize).toList();

        return PagedList.build(posts, page, size, postMapper.searchCount(id, query, option));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        System.out.printf("Delete board id: %d%n", id);
        List<Post> posts = postMapper.findPostsByBoardId(
                id,
                0,
                Integer.MAX_VALUE,
                null,
                null
        );
        System.out.println(posts.size());
        posts.forEach(post -> postService.delete(post.getId()));
        mapper.delete(id);
    }
}
