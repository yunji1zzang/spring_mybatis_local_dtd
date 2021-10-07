package com.spring.ex02;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

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

	public String selectName() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();

		// member.xml의 select 태그의 id로 각각의 SQL문을 호출한 후
		// 각 select 태그의 resultType에 설정한 같은 타입의 변수로
		// 한개의 데이터를 반환합니다.
		String name = session.selectOne("mapper.member.selectName");
		return name;
	}

	public int selectPwd() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();

		// member.xml의 select 태그의 id로 각각의 SQL문을 호출한 후
		// 각 select 태그의 resultType에 설정한 같은 타입의 변수로
		// 한개의 데이터를 반환합니다.
		int pwd = session.selectOne("mapper.member.selectPwd");
		return pwd;
	}
}
