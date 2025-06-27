package com.rathon.manatee.community.dto;

import com.rathon.manatee.database.dto.EmployeeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostDto extends PostSummaryDto{
    private Date edited;
    private String content;
}
