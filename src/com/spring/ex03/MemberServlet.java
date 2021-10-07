package com.spring.ex03;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 브라우저에서 요청 시 MemberDAO 객체를 생성한 후
// selectAllMemberList()를 호출하는 서블릿 역할 함.
@WebServlet("/mem3.do")
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
		MemberVO memberVO = new MemberVO();
		String action=request.getParameter("action");
		String nextPage="";

		// 전체회원정보검색 선택할 경우 실행되는 로직
		if(action == null || action.equals("listMembers")) {
			List<MemberVO> membersList = dao.selectAllMemberList();
			request.setAttribute("membersList", membersList);
			nextPage="test02/listMembers.jsp";
			
			// 아이디로회원검색 선택할 경우 실행되는 로직
			// 검색 조건이 selectMemberById이면 전송된 값을
			// getParameter()로 가져온 후 SQL문의 조건식에서
			// id의 조건 값으로 전달합니다.
		} else if (action.equals("selectMemberById")) {
			String id = request.getParameter("value");
			memberVO = dao.selectMemberById(id);
			request.setAttribute("member", memberVO);
			nextPage="test02/memberInfo.jsp";
			
			// 비밀번호로회원검색 선택할 경우 실행되는 로직
			// 검색 조건이 selectMemberByPwd이면 전송된 값을
			// getParameter()로 가져온 후 SQL문의 조건식에서
			// pwd의 조건값으로 전달합니다.
		} else if (action.equals("selectMemberByPwd")) {
			int pwd = Integer.parseInt(request.getParameter("value"));
			List<MemberVO> membersList = dao.selectMemberByPwd(pwd);
			request.setAttribute("membersList", membersList);
			nextPage="test02/listMembers.jsp";
	}
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
}
