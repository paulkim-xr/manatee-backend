package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.dto.PostDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.mapper.CommentMapper;
import com.rathon.manatee.community.mapper.PostMapper;
import com.rathon.manatee.community.model.Post;
import com.rathon.manatee.database.dto.PagedDtoList;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostMapper mapper;
    private final EmployeeMapper employeeMapper;
    private final CommentMapper commentMapper;

    public PostService(PostMapper mapper, EmployeeMapper employeeMapper, CommentMapper commentMapper) {
        this.mapper = mapper;
        this.employeeMapper = employeeMapper;
        this.commentMapper = commentMapper;
    }

    public PagedDtoList<PostSummaryDto> getPagedPosts(Integer page, Integer size, String sort) {
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<PostSummaryDto> list = mapper.getPagedPosts(page * size, size, sortColumn, sortDirection).stream().map(this::summarize).toList();
        return toPagedDto(list, page, size, mapper.getCount());
    }

    public List<CommentDto> getComments(Long id) {
        return commentMapper.findByPostId(id).stream().map(CommentService::toDto).toList();
    }

    public PostSummaryDto summarize(Post p) {
        PostSummaryDto d = new PostSummaryDto();
        d.setId(p.getId());
        d.setPosted(p.getPostedTime());
        d.setAuthor(employeeMapper.findById(p.getAuthorId()));
        d.setBoardId(p.getBoardId());
        d.setTitle(p.getTitle());
        d.setIsAnnouncement(p.getIsAnnouncement());
        d.setCommentCount(0);
        d.setLikeCount(0);
        d.setViewCount(0);

        return d;
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
        return toDto(mapper.findById(id));
    }

    public PostDto toDto(Post p) {
        PostDto d = new PostDto();
        d.setId(p.getId());
        d.setPosted(p.getPostedTime());
        d.setAuthor(employeeMapper.findById(p.getAuthorId()));
        d.setBoardId(p.getBoardId());
        d.setTitle(p.getTitle());
        d.setIsAnnouncement(p.getIsAnnouncement());
        d.setCommentCount(0);
        d.setLikeCount(0);
        d.setViewCount(0);
        d.setEdited(p.getEditedTime());
        d.setContent(p.getContent());

        return d;
    }
}
