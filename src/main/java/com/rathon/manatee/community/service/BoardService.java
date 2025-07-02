package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.mapper.BoardMapper;
import com.rathon.manatee.community.mapper.PostMapper;
import com.rathon.manatee.community.model.Post;
import com.rathon.manatee.community.service.mapper.BoardMapperService;
import com.rathon.manatee.community.service.mapper.PostMapperService;
import com.rathon.manatee.database.dto.PagedDtoList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardMapper mapper;
    private final PostMapper postMapper;
    private final PostMapperService postMapperService;
    private final BoardMapperService boardMapperService;
    private final PostService postService;

    public BoardService(BoardMapper mapper, PostMapper postMapper, PostMapperService postMapperService, BoardMapperService boardMapperService, PostService postService) {
        this.mapper = mapper;
        this.postMapper = postMapper;
        this.postMapperService = postMapperService;
        this.boardMapperService = boardMapperService;
        this.postService = postService;
    }

    public BoardDto findById(Long id) {
        return boardMapperService.toDto(mapper.findById(id));
    }

    public PagedDtoList<PostSummaryDto> getBoardPosts(Long id, Integer page, Integer size, String sort) {
        String sortColumn = "posted_time";
        String sortDirection = "desc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<PostSummaryDto> posts = postMapper.findPostsByBoardId(id, page * size, size, sortColumn, sortDirection).stream().map(postMapperService::summarize).toList();

        return toPagedDto(posts, page, size, postMapper.countPostsByBoardId(id));
    }

    private PagedDtoList<PostSummaryDto> toPagedDto(List<PostSummaryDto> list, int page, int size, int totalCount) {
        PagedDtoList<PostSummaryDto> pagedList = new PagedDtoList<>();
        pagedList.setContent(list);
        pagedList.setPage(page);
        pagedList.setSize(size);
        pagedList.setTotalCount(totalCount);
        pagedList.setTotalPages(Math.ceilDiv(totalCount, size));
        pagedList.setFirst(page == 0);
        pagedList.setLast(page == (pagedList.getTotalPages() - 1));

        return pagedList;
    }

    public BoardDto getBoard(Long id) {
        return boardMapperService.toDto(mapper.findById(id));
    }

    public void insert(BoardDto d) {
        mapper.insert(boardMapperService.toEntity(d));
    }

    public void update(BoardDto d) {
        mapper.update(boardMapperService.toEntity(d));
    }

    public void delete(Long id) {
        List<Post> posts = postMapper.findPostsByBoardId(id, Integer.MAX_VALUE, Integer.MAX_VALUE, null, null);
        for(Post post : posts) {
            postService.delete(post.getId());
        }

        mapper.delete(id);
    }

    public List<BoardDto> getAll() {
        return mapper.findAll().stream().map(boardMapperService::toDto).toList();
    }

    public PagedDtoList<PostSummaryDto> getBoardPosts(Long id, Integer page, Integer size, String sort, String query, Integer option) {
        String sortColumn = "posted_time";
        String sortDirection = "desc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<PostSummaryDto> posts = postMapper.search(id, page * size, size, sortColumn, sortDirection, query, option).stream().map(postMapperService::summarize).toList();

        return toPagedDto(posts, page, size, postMapper.countSearchResult(id, query, option));
    }
}
