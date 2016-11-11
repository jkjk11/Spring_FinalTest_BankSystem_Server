package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;

public class BankTransferService implements BankService {

	@Override
	public void execute(Model model) {
		DataSource dataSource=(DataSource) model.asMap().get("dataSource");
		HttpServletRequest request=(HttpServletRequest) model.asMap().get("request");
		String sendMemberId= request.getParameter("sendMemberId");
		String receiveMemberId= request.getParameter("receiveMemberId");
		String transferBalance= request.getParameter("transferBalance");
		Connection con;
		boolean result=false;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false); //transaction 시작 
	
			BankDAO dao=new BankDAO(con);
			
			boolean result1=dao.updateWithdraw(sendMemberId, transferBalance);
			boolean result2=dao.updateDeposit(receiveMemberId, transferBalance);
			if(result1 && result2){
				con.commit();
					boolean result3=dao.insertTransferHistory(sendMemberId, receiveMemberId, transferBalance);
					if(result3){
						con.commit();
						
					}else{
						con.rollback();
					}				
				result=true;
			}else{
				con.rollback();
				result=false;
			}
			
			model.addAttribute("RESULT", result);
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
