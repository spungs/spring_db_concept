package com.care.db.basic.repository;

import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import com.care.db.basic.dto.MemberDTO;

@Repository
public interface IMemberDAO {
	public int insert(MemberDTO member);
	public MemberDTO selectId(String id);
	public ArrayList<MemberDTO> list();
	public int update(MemberDTO member);
	public int delete(String id);
}
