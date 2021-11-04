package com.yn.mybatisintegrationdemo.handler;

import com.yn.mybatisintegrationdemo.enums.IEnum2StringConverter;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoSwitchTypeHandler<E extends Enum<E> & IEnum2StringConverter> extends BaseTypeHandler<E> {
    private BaseTypeHandler<E> handler;

    // type：Enum 类型
    public AutoSwitchTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument can not be null!");
        }
        if (IEnum2StringConverter.class.isAssignableFrom(type)) {
            // 如果是 IEnum2StringConverter 类型，那么就使用 Enum2StringHandler 转换器
            this.handler = new Enum2StringHandler<>(type);
        } else {
            // 其他类型降级为使用 EnumTypeHandler
            this.handler = new EnumTypeHandler<>(type);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        this.handler.setNonNullParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.handler.getNullableResult(rs, columnName);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.handler.getNullableResult(rs, columnIndex);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.handler.getNullableResult(cs, columnIndex);
    }
}
