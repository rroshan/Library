package com.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.bean.SearchFinesResultsBean;
import com.library.dao.FinesDAO;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Iterator;

public class FinesDAOMySQLImpl implements FinesDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public int calculateFine() {
		// TODO Auto-generated method stub
		conn = ConnectionFactory.getConnection();
		CallableStatement callableStatement = null;

		String calcFinesCall = "{call calculate_fines()}";

		try {
			callableStatement = conn.prepareCall(calcFinesCall);

			callableStatement.executeUpdate();

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
			return 0;
		} catch(Exception ex) {
			ex.printStackTrace();
			return 0;
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return 1; 
	}

	@Override
	public ArrayList<SearchFinesResultsBean> searchFinesByBorrowerId(int borrowerId) {
		// TODO Auto-generated method stub
		ArrayList<SearchFinesResultsBean> arrSearchResults = new ArrayList<SearchFinesResultsBean>();
		SearchFinesResultsBean searchResult;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT * FROM fines_vw WHERE card_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, borrowerId);

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				searchResult = new SearchFinesResultsBean();
				searchResult.setBookId(set.getString("book_id"));
				searchResult.setBranchId(set.getInt("branch_id"));
				searchResult.setDueDate(set.getDate("due_date"));
				searchResult.setFineAmt(set.getDouble("fine_amt"));
				searchResult.setLateDays(set.getInt("late_days"));
				
				set.getDate("date_in");
				
				if(set.wasNull())
				{
					searchResult.setStatus("Not Returned");
				}
				else
				{
					searchResult.setStatus("Returned");
				}

				arrSearchResults.add(searchResult);
			}
			
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
		
		return arrSearchResults;
	}

	@Override
	public int payFines(int borrowerId) {
		// TODO Auto-generated method stub
		int count = 0;
		int loan_id;
		ArrayList<Integer> arrLoanId = new ArrayList<Integer>();
		
		conn = ConnectionFactory.getConnection();
		try {
			String sql = "SELECT count(*) FROM fines_vw WHERE card_no = ? and date_in is null";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, borrowerId);

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				count = set.getInt(1);
			}
			
			if(count > 0)
			{
				return 0;
			}
			
			set.close();
			
			sql = "SELECT loan_id from fines_vw WHERE card_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, borrowerId);
			
			set = pstmt.executeQuery();
			
			while(set.next())
			{
				arrLoanId.add(set.getInt(1));
			}
			
			set.close();
			
			Iterator<Integer> it = arrLoanId.iterator();
			
			sql = "UPDATE fines SET paid = true WHERE loan_id = ?";
			
			while(it.hasNext())
			{
				loan_id = it.next();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, loan_id);
				
				pstmt.executeUpdate();
			}
			
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
		
		return 1;
	}
}
