package com.rathon.manatee.database.mapper;

import com.rathon.manatee.core.mapper.ObjectMapper;
import com.rathon.manatee.database.dto.UnitDto;
import com.rathon.manatee.database.model.Unit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnitMapper extends ObjectMapper<Unit, UnitDto> {
    List<Unit> findAll(Boolean root);

    List<UnitDto> findAllDto(Boolean root);
    List<UnitDto> searchDto(
            String name,
            String company,
            String type,
            String code,
            String parent,
            String sortColumn,
            String sortDirection,
            Integer offset,
            Integer size
    );
    Integer searchCountDto(String name, String company, String type, String code, String parent);

    UnitDto findParent(Long id);
    List<UnitDto> findChildren(Long id);

    Boolean checkUniqueCode(String code);

    List<UnitDto> findRootUnitsDto();
    UnitDto findCompanyRootUnitDto(Long id);

    List<UnitDto> findCompanyUnitsDto(Long id, Boolean root);

    Long[] getAncestry(Long id);
}
