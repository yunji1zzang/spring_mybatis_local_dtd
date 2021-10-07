<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!-- search.jsp 파일은 검색창에 입력한 값과 셀렉트 박스의 검색 조건을 선택해
         서블릿으로 전송하는 역할을 합니다. -->
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>회원 검색창</title>
</head>
<body>
 <form action="${pageContext.request.contextPath}/mem3.do"> 
   입력 : <input  type="text" name="value" />
   <select name="action">
     <option value="listMembers" >전체회원정보검색</option>
     <option value="selectMemberById" >아이디로회원검색</option>
     <option  value="selectMemberByPwd">비밀번호로회원검색</option>
   </select> <br>
   <input type="submit" value="검색"  />
</form>   
</body>
</html>
