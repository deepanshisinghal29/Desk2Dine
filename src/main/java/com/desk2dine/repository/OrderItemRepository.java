package com.desk2dine.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desk2dine.entity.OrderItem;
import com.desk2dine.util.DBConnection;

@Repository
public class OrderItemRepository {
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	private static final String INSERT = "INSERT INTO order_item(order_id, menu_item_id, price, quantity) VALUES(?,?,?,?)";
	private static final String UPDATE = "UPDATE order_item SET order_id=?,menu_item_id=?,price=?,quantity=? WHERE id=?";
	private static final String UPDATE_QUANTITY = "UPDATE order_item SET quantity=? WHERE order_id=? AND menu_item_id=?";
	private static final String DELETE = "DELETE FROM order_item WHERE id=?";
	private static final String DELETE_ORDER_ITEMS = "DELETE FROM order_item WHERE order_id=?";
	private static final String FIND = "SELECT * FROM order_item WHERE id=?";
	private static final String FIND_ORDER_MENU = "SELECT * FROM order_item WHERE order_id=? AND menu_item_id=?";
	private static final String FIND_BY_ORDER = "SELECT * FROM order_item WHERE order_id=?";
	private static final String FIND_ALL = "SELECT * FROM order_item";
	
	private OrderItem mapOrderItem(ResultSet rs) throws SQLException{
		OrderItem item = new OrderItem();
		item.setId(rs.getInt("id"));
		item.setMenuItem(menuItemRepository.findById(rs.getInt("menu_item_id")));
		item.setOrder(orderRepository.find(rs.getInt("order_id")));
		item.setPrice(rs.getDouble("price"));
		item.setQuantity(rs.getInt("quantity"));
		return item;

	}
	
	public int insert(OrderItem item) {
		int insert = 0;
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, item.getOrder().getId());
			pstmt.setInt(2, item.getMenuItem().getId());
			pstmt.setDouble(3, item.getPrice());
			pstmt.setInt(4, item.getQuantity());
			insert = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				item.setId(rs.getInt("id"));
			}
		} catch (SQLException sqle) {
			System.out.println("Order item cannot be added: " + sqle.getMessage());
		}
		return insert;
	}
	
	public int update(OrderItem item) {
		int update = 0;
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(UPDATE)) {
			pstmt.setInt(1, item.getOrder().getId());
			pstmt.setInt(2, item.getMenuItem().getId());
			pstmt.setDouble(3, item.getPrice());
			pstmt.setInt(4, item.getQuantity());
			pstmt.setInt(5, item.getId());
			update = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Order item couldn't update: " + sqle.getMessage());
		}
		return update;
	}
	
	public int updateQuantity(int orderId, int menuId, int quantity) {
		int update = 0;
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(UPDATE_QUANTITY)) {
			pstmt.setInt(1, quantity);
			pstmt.setInt(2, orderId);
			pstmt.setInt(3, menuId);
			update = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Order item couldn't update: " + sqle.getMessage());
		}
		return update;
	}
	
	public int delete(int id) {
		int delete = 0;
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(DELETE)) {
			pstmt.setInt(1, id);
			delete = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Order item couldn't delete: " + sqle.getMessage());
		}
		return delete;
	}
	
	public int deleteOrderItems(int orderId) {
		int delete = 0;
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(DELETE_ORDER_ITEMS)) {
			pstmt.setInt(1, orderId);
			delete = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("Order items of this order couldn't delete: " + sqle.getMessage());
		}
		return delete;
	}

	
	public OrderItem find(int id) {
		OrderItem item = null;
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				item = mapOrderItem(rs);
			}
		} catch (SQLException sqle) {
			System.out.println("Order item not found: " + sqle.getMessage());
		}
		return item;
	}
	
	public OrderItem findOrderAndMenu(int orderId, int menuId) {
		OrderItem item = null;
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ORDER_MENU)) {
			pstmt.setInt(1, orderId);
			pstmt.setInt(2, menuId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				item = mapOrderItem(rs);
			}
		} catch (SQLException sqle) {
			System.out.println("Order item not found: " + sqle.getMessage());
		}
		return item;
	}
	
	public List<OrderItem> findByOrder(int orderId){
		List<OrderItem> list = new ArrayList<>();
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_BY_ORDER)) {
			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(mapOrderItem(rs));
			}
		} catch (SQLException sqle) {
			System.out.println("Order item not found: " + sqle.getMessage());
		}
		return list;
	}
	
	public List<OrderItem> findAll(){
		List<OrderItem> list = new ArrayList<>();
		try(Connection con = DBConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(FIND_ALL)) {
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(mapOrderItem(rs));
			}
		} catch (SQLException sqle) {
			System.out.println("Order item not found: " + sqle.getMessage());
		}
		return list;
	}
}
