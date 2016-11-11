package com.cjon.bank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.dto.historyDTO;
import com.cjon.bank.service.BankCheckMemberService;
import com.cjon.bank.service.BankDepositService;
import com.cjon.bank.service.BankSearchMemberService;
import com.cjon.bank.service.BankSelectAllMemberService;
import com.cjon.bank.service.BankService;
import com.cjon.bank.service.BankTransferService;
import com.cjon.bank.service.BankWithdrawService;

@Controller
public class BankController {
	
	private DataSource dataSource;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private BankService service;
	//우리는 view로 JSP를 이용하지 않음 
	//만약 JSP를 이용할거면 String이 return되야하고 
	//JSON을 결과값으로 사용하려면 void로 사용 
	//우리는 클라이언트로부터 callback값을 받아야함
	//출력이 JSP가 아니라 Stream을 열어서 클라이언트에게 JSON을 전송해야함 
	
	@RequestMapping(value="/selectAllMember", method=RequestMethod.GET)
	public void selectAllMember(HttpServletRequest request, HttpServletResponse response, Model model){
		
		//입력처리
		String callback=request.getParameter("callback");
		
		//로직처리 
		service= new BankSelectAllMemberService();
		model.addAttribute("dataSource", dataSource);
		service.execute(model);
		
		//결과처리 
		//model에서 결과를 꺼냄 '
		ArrayList<BankDTO> list=(ArrayList<BankDTO>) model.asMap().get("RESULT");
		
		//ArrayList<BankDTO> 이놈을 JSON으로 바꾸어서 클라이언트에게 전송
		
		String json=null;		
		ObjectMapper om=new ObjectMapper();
		
		try{
			json=om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json+ ")");
			
		}catch(Exception e){
			
		}		
	}
	
	@RequestMapping(value="/searchMember", method=RequestMethod.GET)
	public void searchMember(HttpServletRequest request, HttpServletResponse response, Model model){
		
		//입력처리
		String callback=request.getParameter("callback");
		String memberId=request.getParameter("memberId");
		
		//로직처리 
		service= new BankSearchMemberService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		//결과처리 
		//model에서 결과를 꺼냄 '
		ArrayList<BankDTO> list=(ArrayList<BankDTO>) model.asMap().get("RESULT");
		
		//ArrayList<BankDTO> 이놈을 JSON으로 바꾸어서 클라이언트에게 전송
		
		String json=null;		
		ObjectMapper om=new ObjectMapper();
		
		try{
			json=om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json+ ")");
			
		}catch(Exception e){
			
		}		
	}
	
	@RequestMapping("/deposit")
	public void deposit(HttpServletRequest request, HttpServletResponse response, Model model){
		String callback=request.getParameter("callback");
		
		//서비스 객체 생성해서 로직처리 
		service=new BankDepositService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request); //ajax에서 받아온 data들 다 request에 저장되어 있음 
		service.execute(model);
		
		//결과처리 
		boolean result= (Boolean) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result+ ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	@RequestMapping("/withdraw")
	public void withdraw(HttpServletRequest request, HttpServletResponse response, Model model){
		String callback=request.getParameter("callback");
		
		//서비스 객체 생성해서 로직처리 
		service=new BankWithdrawService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request); //ajax에서 받아온 data들 다 request에 저장되어 있음 
		service.execute(model);
		
		//결과처리 
		boolean result= (Boolean) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result+ ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@RequestMapping("/transfer")
	public void transfer(HttpServletRequest request, HttpServletResponse response, Model model){
		String callback=request.getParameter("callback");
		
		//서비스 객체 생성해서 로직처리 
		service=new BankTransferService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request); //ajax에서 받아온 data들 다 request에 저장되어 있음 
		service.execute(model);
		
		//결과처리 
		boolean result= (Boolean) model.asMap().get("RESULT");
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result+ ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	@RequestMapping(value="/checkMember", method=RequestMethod.GET)
	public void checkMember(HttpServletRequest request, HttpServletResponse response, Model model){
		
		//입력처리
		String callback=request.getParameter("callback");
		String checkMemberId=request.getParameter("checkMemberId");
		
		//로직처리 
		service= new BankCheckMemberService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		//결과처리 
		//model에서 결과를 꺼냄 '
		ArrayList<historyDTO> list=(ArrayList<historyDTO>) model.asMap().get("RESULT");
		
		//ArrayList<BankDTO> 이놈을 JSON으로 바꾸어서 클라이언트에게 전송
		
		String json=null;		
		ObjectMapper om=new ObjectMapper();
		
		try{
			json=om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json+ ")");
			
		}catch(Exception e){
			
		}		
	}
}
