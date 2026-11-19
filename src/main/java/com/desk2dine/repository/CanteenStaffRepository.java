package com.desk2dine.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.desk2dine.entity.CanteenStaff;
import com.desk2dine.util.DBConnection;

@Repository
public class CanteenStaffRepository {
	private static final String INSERT = "INSERT INTO canteen_staff VALUES(?,?,?,?)"; 
	private static final String UPDATE = "UPDATE canteen_staff SET name=?,password=?,gender=? where id=?";
	private static final String DELETE = "DELETE FROM canteen_staff WHERE id=?";
	private static final String FIND_BY_ID = "SELECT * FROM canteen_staff WHERE id=?";
	private static final String FIND_ALL = "SELECT * FROM canteen_staff";
	private static final String VALIDATE = "SELECT * FROM canteen_staff WHERE id=? AND password=?";
	
	public int insert(CanteenStaff staff) {
		int insert=0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(INSERT);){
			pstmt.setInt(1,staff.getId());
			pstmt.setString(2,staff.getPassword());
			pstmt.setString(3,staff.getName());
			pstmt.setString(4,staff.getGender());
			insert = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Insertion of the Canteen Staff Failed " + sqle.getMessage());
		}
		return insert;
	}
	
	public int update(CanteenStaff staff) {
		int update=0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(UPDATE);){
			pstmt.setString(1,staff.getName());
			pstmt.setString(2,staff.getPassword());
			pstmt.setString(3,staff.getGender());
			pstmt.setInt(4,staff.getId());
			update = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Updation of the Canteen Staff Failed " + sqle.getMessage());
		}
		return update;
	}
	
	public int delete(int id) {
		int delete=0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(DELETE);){
			pstmt.setInt(1,id);
			delete = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Deletion of the Canteen Staff Failed " + sqle.getMessage());
		}
		return delete;
	}
	
	public CanteenStaff findById(int id) {
		CanteenStaff staff = null;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(FIND_BY_ID);){
			pstmt.setInt(1,id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				staff = new CanteenStaff(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("gender"));
			}
		} catch (SQLException sqle) {
			System.out.println("Canteen Staff unable to find " + sqle.getMessage());
		}
		return staff;
	}
	
	public List<CanteenStaff> findAll() {
		CanteenStaff staff = null;
		List<CanteenStaff> list = new ArrayList<>();
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(FIND_ALL);){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				staff = new CanteenStaff(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("gender"));
				list.add(staff);
			}
		} catch (SQLException sqle) {
			System.out.println("Canteen Staff List unable to find " + sqle.getMessage());
		}
		return list;
	}
	
	public CanteenStaff validate(int id, String password) {
		CanteenStaff staff = null;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(VALIDATE);){
			pstmt.setInt(1,id);
			pstmt.setString(2,password);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				staff = new CanteenStaff(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("gender"));
			}
		} catch (SQLException sqle) {
			System.out.println("Canteen Staff unable to Login " + sqle.getMessage());
		}
		return staff;
	}
	
}
