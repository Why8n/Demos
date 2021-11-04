package com.yn.mybatisintegrationdemo.handler;

import com.yn.mybatisintegrationdemo.enums.IEnum2StringConverter;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Enum2StringHandler<E extends Enum<E> & IEnum2StringConverter> extends BaseTypeHandler<E> {
    private Class<E> type;

    // type：enum 类型
    public Enum2StringHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument can not be null!");
        }
        this.type = type;
    }

    /**
     * @param typeStr Enum 的字符串描述
     * @return 返回 typeStr 对应的 Enum 类型
     */
    private E getCorrespondingType(String typeStr) {
        return Arrays.stream(this.type.getEnumConstants())
                .filter(item -> item.stringify().equals(typeStr))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.stringify());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.getCorrespondingType(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.getCorrespondingType(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.getCorrespondingType(cs.getString(columnIndex));
    }
}
