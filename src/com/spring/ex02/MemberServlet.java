package com.spring.ex02;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 브라우저에서 요청 시 MemberDAO 객체를 생성한 후
// selectAllMemberList()를 호출하는 서블릿 역할 함.
@WebServlet("/mem2.do")
public class MemberServlet  extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException   {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		MemberDAO dao = new MemberDAO();
		// 서블릿에서는 MemberDAO의 selectName()과 selectPwd()메서드를 
		// 호출한 후 각 데이터를 브라우저에 알림창으로 출력합니다.

		// MemberDAO의 selectName()메서드를 호출합니다.
		// String name = dao.selectName();
		
		// MemberDAO의 selectPwd()메서드를 호출합니다.
		   int pwd = dao.selectPwd();
		PrintWriter pw = response.getWriter();
		pw.write("<script>");

		// 조회한 이름을 브라우저로 출력합니다.
		// pw.write("alert(' 이름 : " + name + " ');");

		pw.write("alert(' 비밀번호 : " + pwd + " ');");
		
		pw.write("</script>");
	}
}
