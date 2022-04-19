package com.fishyfriends.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.fishyfriends.util.ConnectionFactory;
import com.fishyfriends.util.ResourceCloser;
import com.fishyfriends.Model.Animal;
import com.fishyfriends.Model.User;

public class UserDAO {
	
	private static UserDAO userList;
	
	private UserDAO() {}
	
	private static synchronized UserDAO getUsers() {
		if(userList==null) {
			userList=new UserDAO();
		}
		return userList;
	}
	
	//To generate an arraylist from the database table for Users:
		public static ArrayList<User> findAllUsers(){
			
			ArrayList<User> users = new ArrayList<>();
			
			Connection conn = null;
			Statement stmt = null;
			ResultSet set = null;
			final String SQL = "select * from users";
			
			try {
				conn = ConnectionFactory.getConnection();
				stmt = conn.createStatement();
				set = stmt.executeQuery(SQL);
				//System.out.println(set);
				
				while(set.next()) {
					users.add(new User(
							set.getString(2),
							set.getInt(1),
							set.getString(3),
							set.getBoolean(4),
							set.getString(5),
							set.getString(6),
							set.getString(7),
							set.getInt(8),
							set.getString(9),
							set.getInt(10),
							set.getBoolean(11),
							set.getBoolean(12)
							));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					conn.close();
					stmt.close();
					set.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
				ResourceCloser.closeConnection(conn);
				ResourceCloser.closeResultSet(set);
				ResourceCloser.closeStatement(stmt);
			}
			return users;
		}
		
		public static ArrayList<User> findUsersByID(){
			
			int userID = 3;
			
			ArrayList<User> usersID = new ArrayList<>();
			
			Connection conn = null;
			Statement stmt = null;
			ResultSet set = null;
			final String SQL = "select * from users where user_account_id="+userID;
			
			try {
				conn = ConnectionFactory.getConnection();
				stmt = conn.createStatement();
				set = stmt.executeQuery(SQL);
				
				while(set.next()) {
					usersID.add(new User(
							set.getString(2),
							set.getInt(1),
							set.getString(3),
							set.getBoolean(4),
							set.getString(5),
							set.getString(6),
							set.getString(7),
							set.getInt(8),
							set.getString(9),
							set.getInt(10),
							set.getBoolean(11),
							set.getBoolean(12)
							));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					conn.close();
					stmt.close();
					set.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
				ResourceCloser.closeConnection(conn);
				ResourceCloser.closeResultSet(set);
				ResourceCloser.closeStatement(stmt);
			}
			return usersID;
		}
		
		public static void addUser(String name, int id, String password, boolean isPrimary, String street, String city, String state, int zip, String email, int birthday, boolean isEmployee, boolean isAdmin) {
			
			Connection conn = null;
			PreparedStatement stmt = null;
			final String SQL = "insert into users values(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			try {
				conn = ConnectionFactory.getConnection();
				stmt = conn.prepareStatement(SQL);
				stmt.setString(2,name);
				stmt.setInt(1,id);
				stmt.setString(3,password);
				stmt.setBoolean(4,isPrimary);
				stmt.setString(5,street);
				stmt.setString(6,city);
				stmt.setString(7,state);
				stmt.setInt(8,zip);
				stmt.setString(9,email);
				stmt.setInt(10,birthday);
				stmt.setBoolean(11,isEmployee);
				stmt.setBoolean(12,isAdmin);
				stmt.execute();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				ResourceCloser.closeConnection(conn);
				ResourceCloser.closeStatement(stmt);
			}
		}
		
		public static void addUser(String name, int id, String password, String email) {
			
			Connection conn = null;
			PreparedStatement stmt = null;
			final String SQL = "insert into users values(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			try {
				conn = ConnectionFactory.getConnection();
				stmt = conn.prepareStatement(SQL);
				stmt.setString(2,name);
				stmt.setInt(1,id);
				stmt.setString(3,password);
				stmt.setBoolean(4,false);
				stmt.setString(5,null);
				stmt.setString(6,null);
				stmt.setString(7,null);
				stmt.setInt(8,0);
				stmt.setString(9,email);
				stmt.setInt(10,0);
				stmt.setBoolean(11,false);
				stmt.setBoolean(12,false);
				stmt.execute();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				ResourceCloser.closeConnection(conn);
				ResourceCloser.closeStatement(stmt);
			}
		}
		
		public static void removeUser(int id) {
			
			Connection conn = null;
			PreparedStatement stmt = null;
			final String SQL = "delete from users where user_account_id=?";
			
			try {
				conn = ConnectionFactory.getConnection();
				stmt = conn.prepareStatement(SQL);
				stmt.setInt(1,id);
				stmt.execute();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				ResourceCloser.closeConnection(conn);
				ResourceCloser.closeStatement(stmt);
			}
		}
		
		public static void removeSingleUser(String userName) {
			
			Connection conn = null;
			PreparedStatement stmt = null;
			final String SQL = "delete from users where user_name=?";
			
			try {
				conn = ConnectionFactory.getConnection();
				stmt = conn.prepareStatement(SQL);
				stmt.setString(1,userName);
				stmt.execute();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				ResourceCloser.closeConnection(conn);
				ResourceCloser.closeStatement(stmt);
			}
		}
}