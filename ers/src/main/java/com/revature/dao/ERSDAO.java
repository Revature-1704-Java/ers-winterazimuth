package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.beans.Reimbursement;
import com.revature.util.ConnectionUtil;

public class ERSDAO {
	public List<Integer> getIDFromName(String firstName, String lastName) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Integer> ids = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT EmployeeID FROM Employees WHERE FirstName = ? AND LastName = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("EmployeeID");
				ids.add(id);
			}
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
		return ids;
	}
	
	public String getNameFromID(int nameThisID) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String idName = "There is no employee with that ID";
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT FirstName, LastName FROM Employees WHERE EmployeeID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, nameThisID);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				idName = rs.getString("FirstName") + " " + rs.getString("LastName");
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
		
		return idName;
	}
	
	public boolean employeeLogIn(int id, String password) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT FirstName FROM Employees WHERE EmployeeID = ? AND EmployeePassword = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				return true;
			else
				return false;
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			return false;
		}
		
	}
	
	public void submitReimbursement(int amount, String description, int employee) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO Reimbursement (RequestedBy, Amount, Description, Status) VALUES (?, ?, ?, 2)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, employee);
			ps.setInt(2, amount);
			ps.setString(3, description);
			
			ps.executeQuery();
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
	}
	
	public int retrieveAmountFromID(int rid) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT Amount FROM Reimbursement WHERE ReimbursementID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rid);
			
			rs = ps.executeQuery();
			
			if (rs.next())
				return rs.getInt("Amount");
			else
				return 0;
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
			return -1;
		}
	}
	
	public List<Reimbursement> employeeViewAll(int emp) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Reimbursement r = null;
		List<Reimbursement> yourRequests = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT ReimbursementID, Amount, Description, Status FROM Reimbursement WHERE RequestedBy = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, emp);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				int id_n = rs.getInt("ReimbursementID");
				int amt = rs.getInt("Amount");
				String desc = rs.getString("Description");
				int stat = rs.getInt("Status");
				r = new Reimbursement(id_n, emp, amt, desc, stat);
				yourRequests.add(r);
			}
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
		
		return yourRequests;
	}
	
	public List<Reimbursement> viewAllOfOneStatus(int stat) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Reimbursement r = null;
		List<Reimbursement> sameStatusRequests = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT ReimbursementID, RequestedBy, Amount, Description FROM Reimbursement WHERE Status = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, stat);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				int id_n = rs.getInt("ReimbursementID");
				int emp = rs.getInt("RequestedBy");
				int amt = rs.getInt("Amount");
				String desc = rs.getString("Description");
				r = new Reimbursement(id_n, emp, amt, desc, stat);
				sameStatusRequests.add(r);
			}
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
		
		return sameStatusRequests;
	}
	
	public void approve(int approveID) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE Reimbursement SET Status = 1 WHERE ReimbursementID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, approveID);
			
			ps.executeQuery();
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
	}
	
	public void deny(int denyID) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE Reimbursement SET Status = 0 WHERE ReimbursementID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, denyID);
			
			ps.executeQuery();
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
	}
	
	public void managerDelete(int deleteID) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM Reimbursement WHERE ReimbursementID = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deleteID);
			
			ps.executeQuery();
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
	}
	
	public void employeeDelete(int deleteID, int currentEmployee) {
		PreparedStatement ps = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM Reimbursement WHERE ReimbursementID = ? AND RequestedBy = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, deleteID);
			ps.setInt(2,  currentEmployee);
			
			ps.executeQuery();
		} catch (Exception ex) {
			ex.getMessage();
			ex.printStackTrace();
		}
	}
}
