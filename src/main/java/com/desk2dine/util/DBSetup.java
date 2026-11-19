package com.desk2dine.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBSetup {
	
	private static final String FACULTY = """
			CREATE TABLE IF NOT EXISTS faculty(
			id INTEGER PRIMARY KEY,
			password TEXT NOT NULL,
			name TEXT NOT NULL,
			gender TEXT)
			""" ;
	
	private static final String CANTEEN_STAFF = """
			CREATE TABLE IF NOT EXISTS canteen_staff(
			id INTEGER PRIMARY KEY,
			password TEXT NOT NULL,
			name TEXT NOT NULL,
			gender TEXT)
			""";
	
	private static final String MENU_ITEM = """
			CREATE TABLE IF NOT EXISTS menu_item(
			id INTEGER PRIMARY KEY AUTOINCREMENT,
			item_name TEXT NOT NULL,
			price REAL NOT NULL,
			category TEXT NOT NULL,
			quantity_available INTEGER NOT NULL)
			""";

	private static final String ORDER = """
			CREATE TABLE IF NOT EXISTS order_details(
			id INTEGER PRIMARY KEY AUTOINCREMENT,
			faculty_id INTEGER NOT NULL,
			order_status TEXT NOT NULL,
			location TEXT NOT NULL,
			bill REAL NOT NULL,
			order_date TEXT NOT NULL,
			estimated_delivery_time TEXT NOT NULL,
			FOREIGN KEY(faculty_id) REFERENCES faculty(id))
			""";
	
	private static final String ORDER_ITEM = """
			CREATE TABLE IF NOT EXISTS order_item(
			id INTEGER PRIMARY KEY AUTOINCREMENT,
			order_id INTEGER NOT NULL,
			menu_item_id INTEGER NOT NULL,
			price REAL NOT NULL,
			quantity INTEGER NOT NULL,
			FOREIGN KEY(order_id) REFERENCES order_details(id),
			FOREIGN KEY(menu_item_id) REFERENCES menu_item(id))
			""";
	
	private void createFaculty(){
		
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(FACULTY);){
			pstmt.executeUpdate();
			System.out.println("Faculty Table created.");
		}
		catch(SQLException sqle) {
			System.out.println("Faculty Table failed to create." + sqle.getMessage());
		}
	}
	
	private void createCanteenStaff(){
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(CANTEEN_STAFF);){
			pstmt.executeUpdate();
			System.out.println("CanteenStaff Table created.");
		}
		catch(SQLException sqle) {
			System.out.println("CanteenStaff Table failed to create." + sqle.getMessage());
		}
	}

	private void createOrder(){
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(ORDER);){
			pstmt.executeUpdate();
			System.out.println("Order Table created.");
		}
		catch(SQLException sqle) {
			System.out.println("Order Table failed to create." + sqle.getMessage());
		}
	}
	
	private void createOrderItem(){
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(ORDER_ITEM);){
			pstmt.executeUpdate();
			System.out.println("OrderItem Table created.");
		}
		catch(SQLException sqle) {
			System.out.println("OrderItem Table failed to create." + sqle.getMessage());
		}
	}
	
	private void createMenuItem(){
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(MENU_ITEM);){
			pstmt.executeUpdate();
			System.out.println("MenuItem Table created.");
		}
		catch(SQLException sqle) {
			System.out.println("MenuItem Table failed to create." + sqle.getMessage());
		}
	}
	
	public void createTables(){
		createFaculty();
		createCanteenStaff();
		createMenuItem();
		createOrder();
		createOrderItem();
		System.out.println("Tables created successfully.");
	}
}
