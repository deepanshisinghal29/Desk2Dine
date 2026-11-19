package com.desk2dine.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desk2dine.entity.Faculty;
import com.desk2dine.entity.Order;
import com.desk2dine.entity.OrderStatus;
import com.desk2dine.util.DBConnection;

@Repository
public class OrderRepository {
	
	@Autowired
	private FacultyRepository facultyRepository;
	
	private static final String INSERT ="INSERT INTO order_details(faculty_id, order_status, location, bill, order_date, estimated_delivery_time) VALUES(?,?,?,?,?,?)";
	private static final String UPDATE ="UPDATE order_details SET faculty_id=?,order_status=?,location=?,bill=?,order_date=?,estimated_delivery_time=? WHERE id=?";
	private static final String UPDATE_BILL ="UPDATE order_details SET bill=? WHERE id=?";
	private static final String UPDATE_STATUS ="UPDATE order_details SET order_status=? WHERE id=?";
	private static final String DELETE ="DELETE FROM order_details WHERE id=?";
	private static final String FIND_BY_CART ="SELECT * FROM order_details WHERE faculty_id=? AND order_status=?";
	private static final String FIND_BY_ID ="SELECT * FROM order_details WHERE id=?";
	private static final String FIND_BY_FACULTY ="SELECT * FROM order_details WHERE faculty_id=?";
	private static final String FIND_ALL ="SELECT * FROM order_details";
	
	public int insert(Order order) {
		int insert = 0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)){
			pstmt.setInt(1, order.getFaculty().getId());
			pstmt.setString(2,order.getOrderStatus().name());
			pstmt.setString(3, order.getLocation());
			pstmt.setDouble(4,order.getOrderBill());
			pstmt.setString(5,order.getOrderDate().toString());
			pstmt.setString(6,order.getEstimatedDeliveryTime().toString());
			insert = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				order.setId(rs.getInt("id"));
			}
		} catch (SQLException sqle) {
			System.out.println("Order is unable to insert " + sqle.getMessage());
		}
		return insert;
	}
	
	public int update(Order order) {
		int update = 0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(UPDATE)){
			pstmt.setInt(1, order.getFaculty().getId());
			pstmt.setString(2,order.getOrderStatus().name());
			pstmt.setString(3, order.getLocation());
			pstmt.setDouble(4,order.getOrderBill());
			pstmt.setString(5,order.getOrderDate().toString());
			pstmt.setString(6,order.getEstimatedDeliveryTime().toString());
			pstmt.setInt(7,order.getId());
			update = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Order is unable to update " + sqle.getMessage());
		}
		return update;
	}
	
	public int updateStatus(int id, OrderStatus status) {
		int updateStatus = 0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(UPDATE_STATUS)){
			pstmt.setString(1, status.name());
			pstmt.setInt(2,id);
			updateStatus = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Order status is unable to update " + sqle.getMessage());
		}
		return updateStatus;
	}
	
	public int updateBill(int id, double bill) {
		int updateStatus = 0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(UPDATE_BILL)){
			pstmt.setDouble(1,bill);
			pstmt.setInt(2,id);
			updateStatus = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Order status is unable to update " + sqle.getMessage());
		}
		return updateStatus;
	}
	
	public int delete(int id) {
		int delete = 0;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(DELETE)){
			pstmt.setInt(1,id);
			delete = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Order is unable to delete " + sqle.getMessage());
		}
		return delete;
	}
	
	public Order find(int id) {
		Order order = null;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(FIND_BY_ID)){
			pstmt.setInt(1,id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				order = new Order();
				order.setId(rs.getInt("id"));
				order.setLocation(rs.getString("location"));
				order.setOrderBill(rs.getDouble("bill"));
				order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
				order.setOrderDate(LocalDateTime.parse(rs.getString("order_date")));
				order.setEstimatedDeliveryTime(LocalDateTime.parse(rs.getString("estimated_delivery_time")));
				Faculty faculty = facultyRepository.findFacultyById(rs.getInt("faculty_id"));
				order.setFaculty(faculty);
			}
		} catch (SQLException sqle) {
			System.out.println("Unable to find order: " + sqle.getMessage());
		}
		return order;
	}
	
	public Order findCart(int facultyId) {
		Order order = null;
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(FIND_BY_CART)){
			pstmt.setInt(1,facultyId);
			pstmt.setString(2, OrderStatus.CART.name());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				order = new Order();
				order.setId(rs.getInt("id"));
				order.setLocation(rs.getString("location"));
				order.setOrderBill(rs.getDouble("bill"));
				order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
				order.setOrderDate(LocalDateTime.parse(rs.getString("order_date")));
				order.setEstimatedDeliveryTime(LocalDateTime.parse(rs.getString("estimated_delivery_time")));
				Faculty faculty = facultyRepository.findFacultyById(rs.getInt("faculty_id"));
				order.setFaculty(faculty);
			}
		} catch (SQLException sqle) {
			System.out.println("Unable to find order: " + sqle.getMessage());
		}
		return order;
	}
	
	public List<Order> findByFaculty(int facultyId){
		Order order = null;
		List<Order> list = new ArrayList<>();
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(FIND_BY_FACULTY)){
			pstmt.setInt(1,facultyId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				order = new Order();
				order.setId(rs.getInt("id"));
				order.setLocation(rs.getString("location"));
				order.setOrderBill(rs.getDouble("bill"));
				order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
				order.setOrderDate(LocalDateTime.parse(rs.getString("order_date")));
				order.setEstimatedDeliveryTime(LocalDateTime.parse(rs.getString("estimated_delivery_time")));
				Faculty faculty = facultyRepository.findFacultyById(rs.getInt("faculty_id"));
				order.setFaculty(faculty);
				list.add(order);
			}
		} catch (SQLException sqle) {
			System.out.println("Unable to find Orders of: "+ facultyId +" " + sqle.getMessage());
		}
		return list;
	}
	
	public List<Order> findAll(){
		Order order = null;
		List<Order> list = new ArrayList<>();
		try(Connection con = DBConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(FIND_ALL)){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				order = new Order();
				order.setId(rs.getInt("id"));
				order.setLocation(rs.getString("location"));
				order.setOrderBill(rs.getDouble("bill"));
				order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
				order.setOrderDate(LocalDateTime.parse(rs.getString("order_date")));
				order.setEstimatedDeliveryTime(LocalDateTime.parse(rs.getString("estimated_delivery_time")));
				Faculty faculty = facultyRepository.findFacultyById(rs.getInt("faculty_id"));
				order.setFaculty(faculty);
				list.add(order);
			}
		} catch (SQLException sqle) {
			System.out.println("Unable to find Orders: " + sqle.getMessage());
		}
		return list;
	}
}
