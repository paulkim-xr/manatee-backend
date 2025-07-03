package com.rathon.manatee.core.typeHandler;

import com.rathon.manatee.community.model.BoardType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(BoardType.class)
public class BoardTypeHandler extends BaseTypeHandler<BoardType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BoardType parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.name(), java.sql.Types.OTHER);
    }

    @Override
    public BoardType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String name = rs.getString(columnName);
        return name == null ? null : BoardType.valueOf(name);
    }

    @Override
    public BoardType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String name = rs.getString(columnIndex);
        return name == null ? null : BoardType.valueOf(name);
    }

    @Override
    public BoardType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String name = cs.getString(columnIndex);
        return name == null ? null : BoardType.valueOf(name);
    }
}
