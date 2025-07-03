package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.dto.PostDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.mapper.CommentMapper;
import com.rathon.manatee.community.mapper.PostMapper;
import com.rathon.manatee.community.model.Comment;
import com.rathon.manatee.community.model.Post;
import com.rathon.manatee.community.service.mapper.CommentMapperService;
import com.rathon.manatee.community.service.mapper.PostMapperService;
import com.rathon.manatee.core.service.ObjectService;
import com.rathon.manatee.core.dto.PagedList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService extends ObjectService<Post, PostDto, PostMapper, PostMapperService> {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final CommentMapperService commentMapperService;

    public PostService(
            PostMapper mapper,
            PostMapperService service,
            CommentService commentService,
            CommentMapper commentMapper,
            CommentMapperService commentMapperService
    ) {
        super(mapper, service);
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.commentMapperService = commentMapperService;
    }

    public PagedList<PostSummaryDto> getPagedObjects(Integer page, Integer size, String sort) {
        String sortColumn = "id";
        String sortDirection = "asc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<PostSummaryDto> list = mapper.getPagedObjects(page * size, size, sortColumn, sortDirection).stream().map(service::summarize).toList();
        return PagedList.build(list, page, size, mapper.getCount());
    }

    public List<CommentDto> getComments(Long id) {
        return commentMapper.findByPostId(id).stream().map(commentMapperService::toDto).toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        List<Comment> comments = commentMapper.findByPostId(id).stream().filter(comment -> comment.getParentId() == null).toList();
        for (Comment comment : comments) {
            commentService.deepDelete(comment.getId());
        }

        mapper.delete(id);
    }

    public PagedList<PostSummaryDto> search(Integer page, Integer size, String sort, String query, Integer option) {
        String sortColumn = "postedTime";
        String sortDirection = "desc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        return PagedList.build(mapper.search(
                null,
                page * size,
                size, sortColumn,
                sortDirection,
                query,
                option
        ).stream().map(service::summarize).toList(),
                page,
                size,
                mapper.searchCount(null, query, option)
        );
    }
}
