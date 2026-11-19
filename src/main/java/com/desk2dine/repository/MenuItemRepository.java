package com.desk2dine.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.desk2dine.entity.MenuItem;
import com.desk2dine.util.DBConnection;

@Repository
public class MenuItemRepository {
	
	private static final String INSERT = "INSERT INTO menu_item(item_name, price, category, quantity_available) VALUES(?,?,?,?)";
	private static final String UPDATE = "UPDATE menu_item SET item_name=?, price=?, category=?, quantity_available=? WHERE id=?";
	private static final String UPDATE_QUANTITY = "UPDATE menu_item SET quantity_available=? WHERE id=?";
	private static final String DELETE = "DELETE FROM menu_item WHERE id=?";
	private static final String FIND_BY_ID = "SELECT * FROM menu_item WHERE id=?";
	private static final String FIND_BY_NAME = "SELECT * FROM menu_item WHERE item_name=?";
	private static final String FIND_ALL = "SELECT * FROM menu_item";
	
	public int insert(MenuItem item) {
		int insert = 0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)){
			pstmt.setString(1, item.getItemName());
			pstmt.setDouble(2,item.getPrice());
			pstmt.setString(3, item.getCategory());
			pstmt.setInt(4,item.getQuantityAvailable());
			insert = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				item.setId(rs.getInt("id"));
			}
		} catch (SQLException sqle) {
			System.out.println("Menu Item is unable to insert " + sqle.getMessage());
		}
		return insert;
	}
	
	public int update(MenuItem item) {
		int update = 0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(UPDATE)){
			pstmt.setString(1, item.getItemName());
			pstmt.setDouble(2,item.getPrice());
			pstmt.setString(3, item.getCategory());
			pstmt.setInt(4,item.getQuantityAvailable());
			pstmt.setInt(5,item.getId());
			update = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Menu Item is unable to update " + sqle.getMessage());
		}
		return update;
	}
	
	public int updateQuantity(int id, int quantity) {
		int update = 0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(UPDATE_QUANTITY)){
			pstmt.setInt(1,quantity);
			pstmt.setInt(2,id);
			update = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Menu Item is unable to update " + sqle.getMessage());
		}
		return update;
	}
	
	public int delete(int id) {
		int delete = 0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(DELETE)){
			pstmt.setInt(1,id);
			delete = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Menu Item is unable to delete " + sqle.getMessage());
		}
		return delete;
	}
	
	public MenuItem findById(int id) {
		MenuItem item = null;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(FIND_BY_ID)){
			pstmt.setInt(1,id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				item = new MenuItem(rs.getInt("id"),rs.getString("item_name"),rs.getDouble("price"),rs.getInt("quantity_available"),rs.getString("category"));
			}
		} catch (SQLException sqle) {
			System.out.println("Menu Item is unable to find " + sqle.getMessage());
		}
		return item;
	}
	
	public MenuItem findByName(String itemName) {
		MenuItem item = null;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(FIND_BY_NAME)){
			pstmt.setString(1, itemName);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				item = new MenuItem(rs.getInt("id"),rs.getString("item_name"),rs.getDouble("price"),rs.getInt("quantity_available"),rs.getString("category"));
			}
		} catch (SQLException sqle) {
			System.out.println("Menu Item is unable to find " + sqle.getMessage());
		}
		return item;
	}
	
	public List<MenuItem> findAll() {
		List<MenuItem> list = new ArrayList<>();
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(FIND_ALL)){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuItem item = new MenuItem(rs.getInt("id"),rs.getString("item_name"),rs.getDouble("price"),rs.getInt("quantity_available"),rs.getString("category"));
				list.add(item);
			}
		} catch (SQLException sqle) {
			System.out.println("Menu Item list is unable to find " + sqle.getMessage());
		}
		return list;
	}

}
