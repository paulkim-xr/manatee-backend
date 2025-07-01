package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.dto.PostDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.mapper.CommentMapper;
import com.rathon.manatee.community.mapper.PostMapper;
import com.rathon.manatee.community.service.mapper.CommentMapperService;
import com.rathon.manatee.community.service.mapper.PostMapperService;
import com.rathon.manatee.database.dto.PagedDtoList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostMapper mapper;
    private final CommentMapper commentMapper;
    private final PostMapperService postMapperService;
    private final CommentMapperService commentMapperService;

    public PostService(PostMapper mapper, CommentMapper commentMapper, PostMapperService postMapperService, CommentMapperService commentMapperService) {
        this.mapper = mapper;
        this.commentMapper = commentMapper;
        this.postMapperService = postMapperService;
        this.commentMapperService = commentMapperService;
    }

    public PagedDtoList<PostSummaryDto> getPagedPosts(Integer page, Integer size, String sort) {
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<PostSummaryDto> list = mapper.getPagedPosts(page * size, size, sortColumn, sortDirection).stream().map(postMapperService::summarize).toList();
        return toPagedDto(list, page, size, mapper.getCount());
    }

    public List<CommentDto> getComments(Long id) {
        return commentMapper.findByPostId(id).stream().map(commentMapperService::toDto).toList();
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

    public PostDto getPostById(Long id) {
        return postMapperService.toDto(mapper.findById(id));
    }

    public void insert(PostDto d) {
        mapper.insert(postMapperService.toEntity(d));
    }

    public void update(PostDto d) {
        mapper.update(postMapperService.toEntity(d));
    }

    public void delete(Long id) {
        mapper.delete(id);
    }

    public PagedDtoList<PostSummaryDto> search(Integer page, Integer size, String sort, String query, Integer option) {
        String sortColumn = "posted_time";
        String sortDirection = "desc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        return toPagedDto(mapper.search(null, page * size, size, sortColumn, sortDirection, query, option).stream().map(postMapperService::summarize).toList(), page, size, mapper.countSearchResult(null, query, option));
    }
}
