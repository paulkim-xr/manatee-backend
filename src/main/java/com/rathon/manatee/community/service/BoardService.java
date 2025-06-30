package com.rathon.manatee.community.service;

import com.rathon.manatee.community.dto.BoardDto;
import com.rathon.manatee.community.dto.CommentDto;
import com.rathon.manatee.community.dto.PostSummaryDto;
import com.rathon.manatee.community.mapper.BoardMapper;
import com.rathon.manatee.community.mapper.PostMapper;
import com.rathon.manatee.community.model.Board;
import com.rathon.manatee.community.model.Post;
import com.rathon.manatee.database.dto.PagedDtoList;
import com.rathon.manatee.database.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardMapper mapper;
    private final PostMapper postMapper;
    private final EmployeeMapper employeeMapper;

    public BoardService(BoardMapper mapper, PostMapper postMapper, EmployeeMapper employeeMapper) {
        this.mapper = mapper;
        this.postMapper = postMapper;
        this.employeeMapper = employeeMapper;
    }

    public BoardDto findById(Long id) {
        return toDto(mapper.findById(id));
    }

    public PagedDtoList<PostSummaryDto> getBoardPosts(Long id, Integer page, Integer size, String sort) {
        String sortColumn = "posted_time";
        String sortDirection = "desc";
        if (sort != null && sort.contains(",")) {
            sortColumn = sort.split(",")[0];
            sortDirection = sort.split(",")[1];
        }

        List<PostSummaryDto> posts = postMapper.findPostsByBoardId(id, page * size, size, sortColumn, sortDirection).stream().map(this::summarize).toList();

        return toPagedDto(posts, page, size, postMapper.countPostsByBoardId(id));
    }

    private BoardDto toDto(Board b) {
        BoardDto d = new BoardDto();
        d.setId(b.getId());
        d.setName(b.getName());
        d.setDescription(b.getDescription());
        d.setType(b.getType());

        return d;
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

    public BoardDto getBoard(Long id) {
        return toDto(mapper.findById(id));
    }
}
