package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.dto.historyDTO;

public class BankCheckMemberService implements BankService {

	@Override
	public void execute(Model model) {
		DataSource dataSource=(DataSource) model.asMap().get("dataSource");
		HttpServletRequest request=(HttpServletRequest) model.asMap().get("request");
		String checkMemberId= request.getParameter("checkMemberId");
		Connection con;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false); //transaction 시작 
	
			BankDAO dao=new BankDAO(con);
			ArrayList<historyDTO> list=dao.checkMember(checkMemberId);
			if(list!=null){
				con.commit();
			}else{
				con.rollback();
			}
			
			model.addAttribute("RESULT", list);
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
