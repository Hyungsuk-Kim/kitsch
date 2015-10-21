package microblog.kitsch.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import microblog.kitsch.business.dataaccess.MemberDaoImpl;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.service.MemberDao;

import static org.junit.Assert.*;

public class TestMemberDaoImpl {
	private MemberDao memberDao;
	private Member testMember;
	
	@Before
	public void init() {
		memberDao = new MemberDaoImpl();
		testMember = new Member("testing@testing.com", "testing member", "test123"); 
	}
	
	@Test
	public void testInsertMember() {
		Member newMember = new Member("first_user@kitsch.com", "first_user", "Abc123");
		memberDao.insertMember(newMember);
		
		/*Member[] allMembers = memberDao.selectAllMembers();
		for (Member member : allMembers) {
			System.out.println(member.toString());
		}*/
		Member selectedMember = memberDao.selectMemberAsName(newMember.getName());
		assertEquals(selectedMember.getName(), newMember.getName());
		assertEquals(selectedMember.getEmail(), newMember.getEmail());
		assertEquals(selectedMember.getPassword(), newMember.getPassword());
		
		memberDao.deleteMember(newMember);
	}

	@Test
	public void testSelectMember() {
		memberDao.insertMember(testMember);
		Member selectedMember = memberDao.selectMemberAsEmail("testing@testing.com");
		assertEquals("testing@testing.com", selectedMember.getEmail());
		//System.out.println(selectedMember.toString());
		
		selectedMember = memberDao.selectMemberAsName("testing member");
		assertEquals("testing member", selectedMember.getName());
		//System.out.println(selectedMember.toString());
		
		Member[] selectedMembers = memberDao.selectMemberAsRole(Member.ADMINISTRATOR);
		
		assertEquals("admin@kitsch.com", selectedMembers[0].getEmail());
		assertEquals("Administrator", selectedMembers[0].getName());
		assertEquals("admin1234", selectedMembers[0].getPassword());
		assertEquals(Member.ADMINISTRATOR, selectedMembers[0].getRole());
		
		/*for (Member member : selectedMembers) {
			System.out.print("===== ADMINISTRATORS : ");
			System.out.println(member.toString());
		}*/
		/*selectedMembers = memberDao.selectMemberAsRole(Member.NORMAL_USER);
		for (Member member : selectedMembers) {
			System.out.print("===== NORMAL USERS : ");
			System.out.println(member.toString());
		}*/
		memberDao.deleteMember(testMember);
	}
	
	@Test
	public void testUpdateMember() {
		memberDao.insertMember(testMember);
		Member tempMember = memberDao.selectMemberAsName(testMember.getName());
		tempMember.setPassword("1234");
		tempMember.setRole(Member.SUPER_USER);
		memberDao.updateMember(tempMember);
		
		assertEquals(testMember.getEmail(), tempMember.getEmail());
		assertEquals(testMember.getName(), tempMember.getName());
		assertEquals("1234", tempMember.getPassword());
		assertEquals(Member.SUPER_USER, tempMember.getRole());
		//System.out.println(memberDao.memberNameExists(tempMember.getName()));
		memberDao.deleteMember(testMember);
	}
	
	@Test
	public void testSearchMember() {
		Member member1 = new Member("john123@kitsch.com", "John", "1234");
		Member member2 = new Member("susan123@kitsch.com", "Susan", "1234");
		Member member3 = new Member("samuel@kitsch.com", "Samuel", "1234");
		Member member4 = new Member("sam@kitsch.com", "Sam", "1234");
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "all"); // Available values : "member", "all"
		searchInfo.put("searchType", "memberEmail"); // Available values : "memberEmail", "memberName", "all"
		searchInfo.put("searchText", "sam");
		searchInfo.put("startRow", 1);
		searchInfo.put("endRow", 6);
		
		memberDao.insertMember(member1);
		memberDao.insertMember(member2);
		memberDao.insertMember(member3);
		memberDao.insertMember(member4);
		
		List<Member> mList = memberDao.getMemberList(searchInfo);
		int mCount = memberDao.getMemberCount(searchInfo);
		
		System.out.println("=========== Searched Count : " + mCount);
		for (Member member : mList) {
			System.out.println("=========== Searched Member : " + member.toString());
		}
		
		memberDao.deleteMember(member1);
		memberDao.deleteMember(member2);
		memberDao.deleteMember(member3);
		memberDao.deleteMember(member4);
	}
	
	@Test
	public void TestExisting() {
		memberDao.insertMember(testMember);
		
		assertTrue(memberDao.memberEmailExists("testing@testing.com"));
		assertFalse(memberDao.memberEmailExists("testing@test.com"));
		assertTrue(memberDao.memberNameExists("testing member"));
		assertFalse(memberDao.memberNameExists("testing"));
		
		memberDao.deleteMember(testMember);
	}
	@Test
	public void TestcheckMember() {
		memberDao.insertMember(testMember);
		
		Member validMember = memberDao.checkMember("testing@testing.com", "test123");
		Member PWInvalidMember = memberDao.checkMember("testing@testing.com", "test");
		Member EmailInvalidMember = memberDao.checkMember("testing@test.com", "test123");
		Member BothInvalidMember = memberDao.checkMember("testing@test.com", "test");
		
		assertEquals(validMember.getCheck(), Member.VALID_MEMBER);
		assertEquals(PWInvalidMember.getCheck(), Member.INVALID_PASSWORD);
		assertEquals(EmailInvalidMember.getCheck(), Member.INVALID_EMAIL);
		assertEquals(BothInvalidMember.getCheck(), Member.INVALID_EMAIL);
		
		memberDao.deleteMember(testMember);
	}
	/*@After
	public void end() {
		Member[] allMembers = memberDao.selectAllMembers();
		for (Member member : allMembers) {
			System.out.println(member.toString());
		}
	}*/
}
