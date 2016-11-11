package com.cjon.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.dto.historyDTO;

public class BankDAO {

	private Connection con;
	
	public BankDAO(Connection con) {
		// TODO Auto-generated constructor stub
		this.con=con;
	}

	public ArrayList<BankDTO> selectAll() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<BankDTO> list=new ArrayList<BankDTO>();
		
		try{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
					
			String sql="select * from bank_member_tb";
			pstmt= con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				BankDTO dto=new BankDTO();
				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getInt("member_balance"));
				list.add(dto);
			}
			
		}catch(Exception e){
			
		}finally{
			try {
				rs.close();
				pstmt.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}

	public boolean updateDeposit(String memberID, String memberBalance) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		boolean result=false;
		
		String sql="update bank_member_tb set member_balance=member_balance+? where member_id=?";
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(memberBalance));
			pstmt.setString(2, memberID);
			
			int count = pstmt.executeUpdate();
			if(count==1){
				//정상처리되면 
				String sql1="select member_id, member_balance from bank_member_tb where member_id=?";
				PreparedStatement pstmt1=con.prepareStatement(sql1);
				pstmt1.setString(1, memberID);
				rs=pstmt1.executeQuery();
				
				if(rs.next()){
					result=true;
				}
				
				try{
					rs.close();
					pstmt1.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				result=false;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				pstmt.close();
			}catch(Exception e2){
				
			}
		}
		
		return result;
	}

	public boolean updateWithdraw(String memberID, String memberBalance) {
		PreparedStatement pstmt=null;
		boolean result=false;
		ResultSet rs=null;
		
		String sql="update bank_member_tb set member_balance=member_balance-? where member_id=?";
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(memberBalance));
			pstmt.setString(2, memberID);
			
			int count = pstmt.executeUpdate();
			if(count==1){
				//정상처리되면 
				String sql1="select member_id, member_balance from bank_member_tb where member_id=?";
				PreparedStatement pstmt1=con.prepareStatement(sql1);
				pstmt1.setString(1, memberID);
				rs=pstmt1.executeQuery();
				
				if(rs.next()){
					int member_balance=rs.getInt("member_balance");
					
					if(member_balance<0){
						System.out.println("예금 금액이 적어 출력할 수 없어요.");
						result=false;
					}else{
						result=true;
					}
				}
								
				try{
					rs.close();
					pstmt1.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				//result=true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				pstmt.close();
			}catch(Exception e2){
				
			}
		}
		
		return result;
	}

	public ArrayList<BankDTO> searchMember(String memberID) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<BankDTO> list=new ArrayList<BankDTO>();
		
		try{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
					
			String sql="select * from bank_member_tb where member_id=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, memberID);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				BankDTO dto=new BankDTO();
				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getInt("member_balance"));
				list.add(dto);
			}
			
		}catch(Exception e){
			
		}finally{
			try {
				rs.close();
				pstmt.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}

	public boolean insertHistory(String memberID, String status, String memberBalance) {
		PreparedStatement pstmt=null;
		boolean result=false;
		
		try{
			String sql="insert into bank_statement_tb(member_id, kind, money) values (?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, memberID);
			pstmt.setString(2, status);
			pstmt.setInt(3, Integer.parseInt(memberBalance));
			
			int count=pstmt.executeUpdate();
			if(count==1){
				result=true;
			}
			
		}catch(Exception e){
			System.out.println(e);
		}finally{
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
		
		
		
	}
	public boolean insertTransferHistory(String sendMemberId, String receiveMemberId, String transferBalance) {
		PreparedStatement pstmt=null;
		boolean result=false;
		
		try{
			String sql="insert into bank_transfer_history_tb(send_member_id, receive_member_id,transfer_money) values (?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, sendMemberId);
			pstmt.setString(2, receiveMemberId);
			pstmt.setInt(3, Integer.parseInt(transferBalance));
			
			int count=pstmt.executeUpdate();
			if(count==1){
				result=true;
				
				String sql2="insert into bank_statement_tb(member_id, kind, money) values (?,?,?)";
				PreparedStatement pstmt2=con.prepareStatement(sql2);
				pstmt2.setString(1, sendMemberId);
				pstmt2.setString(2, "transfer_withdraw");
				pstmt2.setInt(3, Integer.parseInt(transferBalance));
				int count2=pstmt2.executeUpdate();
				
				String sql3="insert into bank_statement_tb(member_id, kind, money) values (?,?,?)";
				PreparedStatement pstmt3=con.prepareStatement(sql3);
				pstmt3.setString(1, receiveMemberId);
				pstmt3.setString(2, "transfer_deposit");
				pstmt3.setInt(3, Integer.parseInt(transferBalance));
				int count3=pstmt3.executeUpdate();
			}
			
		}catch(Exception e){
			System.out.println(e);
		}finally{
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
		
		
		
	}

	public ArrayList<historyDTO> checkMember(String checkMemberId) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<historyDTO> list=new ArrayList<historyDTO>();
		
		try{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
					
			String sql="select * from bank_statement_tb where member_id=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, checkMemberId);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				historyDTO dto=new historyDTO();
				dto.setCheckId(rs.getString("member_id"));
				dto.setKind(rs.getString("kind"));
				dto.setCheckBalance(rs.getInt("money"));
				list.add(dto);
			}
			
		}catch(Exception e){
			
		}finally{
			try {
				rs.close();
				pstmt.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}

	
}
