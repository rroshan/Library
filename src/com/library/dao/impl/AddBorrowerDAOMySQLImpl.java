package com.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.library.bo.Borrower;
import com.library.dao.AddBorrowerDAO;

public class AddBorrowerDAOMySQLImpl implements AddBorrowerDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public void insertBorrower(Borrower b) {
		// TODO Auto-generated method stub
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "insert into borrower(fname, lname, email, address, city, state, phone) values (?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getFname());
			pstmt.setString(2, b.getLname());
			pstmt.setString(3, b.getEmail());
			pstmt.setString(4, b.getAddress());
			pstmt.setString(5, b.getCity());
			pstmt.setString(6, b.getState());
			pstmt.setString(7, b.getPhone());

			pstmt.executeUpdate();
			
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
	}

	@Override
	public boolean checkBorrowerExists(Borrower b) {
		// TODO Auto-generated method stub
		int count = 0;
		
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select count(*) from borrower where lower(fname) = lower(?) and lower(lname) = lower(?) and lower(address) = lower(?) and lower(city) = lower(?) and lower(state) = lower(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getFname());
			pstmt.setString(2, b.getLname());
			pstmt.setString(3, b.getAddress());
			pstmt.setString(4, b.getCity());
			pstmt.setString(5, b.getState());

			set = pstmt.executeQuery();
			
			while(set.next()) {
				count = set.getInt(1);
			}
			
			if(count > 0) {
				return false;
			}
			
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
		
		return true;
	}

}
