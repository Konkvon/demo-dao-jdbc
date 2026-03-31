package mapper;

import models.entities.Department;
import models.entities.Seller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerMapper {

    public static Seller fromResultSet(ResultSet rs, Department dep) throws SQLException {

        Seller obj = new Seller();

        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(dep);

        return obj;
    }
}
