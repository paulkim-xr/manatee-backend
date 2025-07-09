package com.rathon.manatee.auth.dto;

import com.rathon.manatee.auth.model.UIComponent;
import com.rathon.manatee.core.dto.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UIComponentDto extends Dto<UIComponent> {
    private String name;
    private String description;
}
