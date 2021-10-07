package com.spring.ex04;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

// SqlMapConfig.xml 파일을 이용해 SqlMapper 객체를 생성합니다.
// 그런 다음 selectAllMemberList() 메서드를 호출하면서
// 인자로 mapper.member.selectAllMemberList를 전달해
// member.xml에서 해당 네임스페이스와 id에 해당하는 SQL문을 실행합니다.
public class MemberDAO {

	private static SqlSessionFactory sqlMapper = null;
	public static SqlSessionFactory getInstance() {
		if(sqlMapper == null) {
			// MemberDAO의 각 메서드 호출 시 src/mybatis/SqlMapConfig.xml
			// 에서 설정 정보를 읽은 후 데이터베이스와의 연동 준비를 합니다.
			String resource = "mybatis/SqlMapConfig.xml";
			try {
				Reader reader = Resources.getResourceAsReader(resource);
				// 마이바티스를 이용하는 sqlMapper 객체를 가져옵니다.
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sqlMapper;
	}
	
	public List<MemberVO> selectAllMemberList() {
		sqlMapper = getInstance();
		// 실제 member.xml의 SQL문을 호출하는데 사용되는
		// SqlSession 객체를 가져옵니다.
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memList=null;
		// 여러 개의 레코드를 조회하므로 selectList() 메서드에
		// 실행하고자 하는 SQL 문의 id를 인자로 전달합니다.
		memList=session.selectList("mapper.member.selectAllMemberList");
		return memList;
	}
	

	// selectOne() 메서드는 하나의 레코드를 조회할 때 사용합니다.
	// selectOne() 메서드의 두 번째 인자는 첫 번째 인자의 SQL문에서
	// 매개변수 이름 id로 조건 값을 전달합니다.
	public MemberVO selectMemberById(String id) {
		sqlMapper = getInstance();
		// 실제 member.xml의 SQL문을 호출하는데 사용되는
		// SqlSession 객체를 가져옵니다.
		SqlSession session = sqlMapper.openSession();		
		// id 매개변수는 서블릿에서 넘어온 id의 값을 selectOne()메서드 호출 시
		// 해당 SQL문의 조건 값으로 전달합니다.
		MemberVO memberVO = session.selectOne("mapper.member.selectMemberById", id);
		return memberVO;
	}
	
	public List<MemberVO> selectMemberByPwd(int pwd) {
		sqlMapper = getInstance();
		// 실제 member.xml의 SQL문을 호출하는데 사용되는
		// SqlSession 객체를 가져옵니다.
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> membersList = null;
		
		// selectList() 메서드는 비밀번호가 같은 회원은 여러 명이 있을 수 있으므로
		// selectList() 메서드로 조회합니다.
		// 여러 개의 레코드를 조회하므로 selectList() 메서드에
		// 실행하고자 하는 SQL 문의 id를 인자로 전달합니다.
		membersList=session.selectList("mapper.member.selectMemberByPwd", pwd);
		return membersList;
	}

	// MemberDAO 클래스에서 insert문을 사용하려면 SqlSession 클래스의
	// insert() 메서드를 이용해야 합니다. 다음과 같이 insert() 메서드의
	// 첫 번째 인자에는 실행하고자 하는 SQL문의 id를 입력하고
	// 두 번째 인자에는 SQL문으로 전달할 데이터를 지정합니다.
	// SQL문으로 전달할 데이터는 insert 태그의 parameterType 속성의
	// 데이터 타입인 MemberVO 클래스와 일치해야 합니다.
	public int insertMember(MemberVO memberVO) {

		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = 0;
		// 지정한 id의 SQL문에 memberVO의 값을 전달하여
		// 회원 정보를 테이블에 추가합니다.
		result = session.insert("mapper.member.insertMember", memberVO);
		// 수동 커밋이므로 반드시 commit() 메서드를 호출하여 영구 반영합니다.
		session.commit();
		return result;
	}

	public int insertMember2(Map<String, String> memberMap) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		// memberMap 매개변수는 insertMember2() 메서드로 전달된
		// HashMap을 다시 SQL문으로 전달합니다.
		// 즉, 여기서 SqlSession 클래스의 insert() 메서드 호출 시
		// 두 번째 인자로 HashMap을 전달합니다.
		int result = session.insert("mapper.member.insertMember2", memberMap);
				
		// 수동 커밋이므로 반드시 commit() 메서드를 호출하여 영구 반영합니다.
		session.commit();
		return result;
	}

	// MemberDAO에서 SqlSession 클래스의 update() 메서드를 이용해서
	// update문을 실행하도록 다음과 같이 설정합니다.
	// update() 메서드를 호출하면서 서블릿에서 전달된  memberVO를
	// update 문으로 전달합니다. update() 메서드로 SQL문을 실행한 후에
	// 반드시 commit() 메서드를 사용해서 커밋을 해주어야 합니다.
	public int updateMember(MemberVO memberVO) {
		
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
 	              	 // update문 호출 시 SqlSession의
					 // update() 메서드를 이용합니다.
		int result = session.update("mapper.member.updateMember", memberVO);
		session.commit();
		return result;
	}

	// MemberDAO에서 SqlSession 클래스의 delete() 메서드를 이용해서
	// delete문을 실행하고, 전달된 ID를 다시 delete() 메서드를 호출하면서
	// delete문으로 전달합니다. 
	public int deleteMember(String id) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = 0;
				// delete문을 실행하려면 SqlSession의
				// delete() 메서드를 이용해야 합니다.
		result = session.delete("mapper.member.deleteMember", id);
		// SQL문을 실행한 후 반드시 커밋합니다.
		session.commit();
		return result;
	}
}
