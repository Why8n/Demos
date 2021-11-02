package com.yn.mybatisintegrationdemo.handler;

import com.yn.mybatisintegrationdemo.enums.Gender;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class GenderTypeHandler extends BaseTypeHandler<Gender> {

    private Gender selectGender(String gender) {
        return Arrays.stream(Gender.values())
                .filter(item -> item.toString().equals(gender))
                .findFirst()
                .orElse(Gender.MALE);
    }

    // 把 Java 类型参数（paramter）转换为 jdbc 类型
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Gender parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    // 通过字段名 columnName 从结果集 rs 中获取到数据，将该数据转换为对应的 Java 类型
    @Override
    public Gender getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String gender = rs.getString(columnName);
        return this.selectGender(gender);
    }

    // 通过字段索引 columnIndex 从结果集 rs 中获取到数据，将该数据转换为对应的 Java 类型
    @Override
    public Gender getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String gender = rs.getString(columnIndex);
        return this.selectGender(gender);
    }

    // 通过字段索引 columnIndex 从存储过程中获取到数据，将该数据转换为对应的 Java 类型
    @Override
    public Gender getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String gender = cs.getString(columnIndex);
        return this.selectGender(gender);
    }
}
