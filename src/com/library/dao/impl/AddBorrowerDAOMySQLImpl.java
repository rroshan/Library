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

}
