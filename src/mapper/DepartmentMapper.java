package mapper;

import models.entities.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper {

    public static Department fromResultSet(ResultSet rs) throws SQLException {
        Department dep = new Department();

        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));

        return dep;
    }

}
