package com.desk2dine.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.desk2dine.entity.Faculty;
import com.desk2dine.util.DBConnection;

@Repository
public class FacultyRepository {
	private static final String INSERT = "INSERT INTO faculty VALUES(?,?,?,?)";
	private static final String UPDATE = "UPDATE faculty SET password=?,name=?, gender=? WHERE id=?";
	private static final String DELETE = "DELETE FROM faculty WHERE id=?";
	private static final String SELECT_ID = "SELECT * FROM faculty WHERE id=?";
	private static final String SELECT_ALL = "SELECT * FROM faculty";
	private static final String VALIDATE = "SELECT * FROM faculty WHERE id=? AND password=?";


	public int insertFaculty(Faculty faculty) {
		int insert = 0;
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(INSERT);){
			pstmt.setInt(1, faculty.getId());
			pstmt.setString(2, faculty.getPassword());
			pstmt.setString(3, faculty.getName());
			pstmt.setString(4, faculty.getGender());
			insert = pstmt.executeUpdate();
		}
		catch(SQLException sqle) {
			System.out.println("Faculty is unable to insert."+sqle.getMessage());
		}
		return insert;
	}
	
	public int updateFaculty(Faculty faculty) {
		int update = 0;
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(UPDATE);){
			pstmt.setString(1, faculty.getPassword());
			pstmt.setString(2, faculty.getName());
			pstmt.setString(3, faculty.getGender());
			pstmt.setInt(4, faculty.getId());
			update = pstmt.executeUpdate();
		}
		catch(SQLException sqle) {
			System.out.println("Faculty is unable to update."+sqle.getMessage());
		}
		return update;
	}
	
	public int deleteFaculty(int id) {
		int delete = 0;
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(DELETE);){
			pstmt.setInt(1, id);
			delete = pstmt.executeUpdate();
		}
		catch(SQLException sqle) {
			System.out.println("Faculty is unable to delete."+sqle.getMessage());
		}
		return delete;
	}
	
	public Faculty findFacultyById(int id) {
		Faculty faculty = null;
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(SELECT_ID);){
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				faculty = new Faculty(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("gender"));
			}
		}
		catch(SQLException sqle) {
			System.out.println("Faculty is unable to find."+sqle.getMessage());
		}
		return faculty;
	}
	
	public List<Faculty> findAllFaculty() {
		List<Faculty> list = new ArrayList<>();
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(SELECT_ALL);){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Faculty faculty = new Faculty(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("gender"));
				list.add(faculty);
			}
		}
		catch(SQLException sqle) {
			System.out.println("Faculties are unable to find."+sqle.getMessage());
		}
		return list;
	}
	
	public Faculty validateFaculty(int id , String password) {
		Faculty faculty = null;
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(VALIDATE);){
			pstmt.setInt(1, id);
			pstmt.setString(2, password);	
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				faculty = new Faculty(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("gender"));
			}
		}
		catch(SQLException sqle) {
			System.out.println("Faculty is invalid."+sqle.getMessage());
		}
		return faculty;
	}
	
}
