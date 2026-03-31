package models.dao.impl;

import db.DB;
import db.DbException;
import mapper.DepartmentMapper;
import mapper.SellerMapper;
import models.dao.SellerDao;
import models.entities.Department;
import models.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    private List<Seller> instantiateSellerList(ResultSet rs) throws SQLException {
        List<Seller> list = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>();

        while (rs.next()) {
            Integer depId = rs.getInt("DepartmentId");

            Department dep = map.get(depId);

            if (dep == null) {
                dep = DepartmentMapper.fromResultSet(rs);
                map.put(depId, dep);
            }

            Seller obj = SellerMapper.fromResultSet(rs, dep);
            list.add(obj);
        }
        return list;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO seller "
                            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE seller "
                            + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE Id = ?");

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

@Override
public void deleteById(Integer id) {
    PreparedStatement st = null;
    try {
        st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");

        st.setInt(1, id);

        int rowsAffected = st.executeUpdate();

        if (rowsAffected == 0){
            throw new RuntimeException("Id doesn't exist");
        }

    } catch (SQLException e) {
        throw new DbException(e.getMessage());
    }
}

@Override
public Seller findById(Integer id){
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
        st = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName "
                        + "FROM seller INNER JOIN department "
                        + "ON seller.DepartmentId = department.Id "
                        + "WHERE seller.Id = ?");
        st.setInt(1, id);
        rs = st.executeQuery();

        if (rs.next()){
            Department dep = DepartmentMapper.fromResultSet(rs);
            Seller obj = SellerMapper.fromResultSet(rs, dep);
            return obj;
        }
        return null;

    } catch (SQLException e) {
        throw new DbException(e.getMessage());
    }
    finally {
        DB.closeStatement(st);
        DB.closeResultSet(rs);
    }
}

@Override
public List<Seller> findAll() {
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
        st = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName\n" +
                        "FROM seller INNER JOIN department\n" +
                        "ON seller.DepartmentId = department.Id\n" +
                        "ORDER BY Name");

        rs = st.executeQuery();

        return instantiateSellerList(rs);

    } catch (SQLException e) {
        throw new DbException(e.getMessage());
    }
    finally {
        DB.closeStatement(st);
        DB.closeResultSet(rs);
    }
}

@Override
public List<Seller> findByDepartment(Department department) {
    PreparedStatement st = null;
    ResultSet rs = null;
    try {
        st = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName "
                        + "FROM seller INNER JOIN department "
                        + "ON seller.DepartmentId = department.Id "
                        + "WHERE DepartmentId = ? "
                        + "ORDER BY Name");

        st.setInt(1, department.getId());
        rs = st.executeQuery();

        return instantiateSellerList(rs);

    } catch (SQLException e) {
        throw new DbException(e.getMessage());
    }
    finally {
        DB.closeStatement(st);
        DB.closeResultSet(rs);
    }
}
}
