package microblog.kitsch.test;

import org.junit.Before;
import org.junit.Test;

import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.service.MemberService;
import microblog.kitsch.business.service.MemberServiceImpl;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;

public class TestMemberServiceImpl {
	private MemberService memberService;
	private Member testMember;
	
	@Before
	public void init() throws DataDuplicatedException {
		System.out.println("******* init() *******");
		memberService = new MemberServiceImpl();
		testMember = new Member("testing@testing.com", "testing member", "test1234");
		memberService.registerMember(testMember);
	}
	
	@Test
	public void testLogin() {
		System.out.println("******* testLogin() *******");
		Member checkedMember = memberService.loginCheck("testing@testing.com", "test1234");
		Member checkedMember1 = memberService.loginCheck("testing@test.com", "test1234");
		Member checkedMember2 = memberService.loginCheck("testing@testing.com", "test");
		Member checkedMember3 = memberService.loginCheck("testing@test.com", "test");
		
		assertEquals(checkedMember.getCheck(), Member.VALID_MEMBER);
		assertEquals(checkedMember1.getCheck(), Member.INVALID_EMAIL);
		assertEquals(checkedMember2.getCheck(), Member.INVALID_PASSWORD);
		assertEquals(checkedMember3.getCheck(), Member.INVALID_EMAIL);
	}
	
	@Test
	public void testRegistMember() throws DataDuplicatedException, DataNotFoundException {
		System.out.println("******* testRegistMember() *******");
		Member member = new Member("Thomas@kitsch.com", "Tom", "tom123");
		memberService.registerMember(member);
		Member searchedMember = memberService.findMemberByName("Tom");
		
		assertEquals(searchedMember.getName(), member.getName());
		assertEquals(searchedMember.getEmail(), member.getEmail());
		assertEquals(searchedMember.getPassword(), member.getPassword());
		assertEquals(searchedMember.getRole(), Member.NORMAL_USER);
		
		memberService.removeMember(member);
	}
	
	@Test (expected=DataNotFoundException.class)
	public void testRemoveMember() throws DataNotFoundException, DataDuplicatedException {
		System.out.println("******* testRemoveMember() *******");
		Member member = new Member("Thomas@kitsch.com", "Tom", "tom123");
		memberService.registerMember(member);
		memberService.removeMember(member);
		Member selectedMember = memberService.findMemberByName(member.getName());
		assertSame(null, selectedMember);
	}
	
	@Test
	public void testUpdateMember() throws DataNotFoundException {
		System.out.println("******* testUpdateMember() *******");
		testMember.setPassword("123123123");
		testMember.setName("테스트1");
		testMember.setProfileImage("testProfile_image.jpg");
		memberService.updateMember(testMember);
		
		Member selectedMember = memberService.findMemberByName(testMember.getName());
		assertEquals(selectedMember.getEmail(), testMember.getEmail());
		assertEquals(selectedMember.getName(), testMember.getName());
		assertEquals(selectedMember.getPassword(), testMember.getPassword());
		assertEquals(selectedMember.getProfileImage(), testMember.getProfileImage());
	}
	
	@Test
	public void testSearchMember() throws DataDuplicatedException, DataNotFoundException {
		System.out.println("******* testSearchMember() *******");
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
		
		memberService.registerMember(member1);
		memberService.registerMember(member2);
		memberService.registerMember(member3);
		memberService.registerMember(member4);
		
		Member[] searchedMembers = memberService.getMemberList(searchInfo);
		System.out.println("################### selected rows : " + searchedMembers.length);
		for (int i = 0; i < searchedMembers.length; i++) {
			assertTrue(searchedMembers[i].getEmail().equals(member3.getEmail()) || searchedMembers[i].getEmail().equals(member4.getEmail()));
			assertTrue(searchedMembers[i].getName().equals(member3.getName()) || searchedMembers[i].getName().equals(member4.getName()));
			assertTrue(searchedMembers[i].getPassword().equals(member3.getPassword()) || searchedMembers[i].getPassword().equals(member4.getPassword()));
			assertTrue(searchedMembers[i].getRole() == member3.getRole() || searchedMembers[i].getRole() == member4.getRole());
		}
		
		int count = memberService.getMemberCount(searchInfo);
		assertEquals(2, count);
		
		memberService.removeMember(member1);
		memberService.removeMember(member2);
		memberService.removeMember(member3);
		memberService.removeMember(member4);
	}

	@Test
	public void testAvailableMemberName() {
		System.out.println("******* testAvailableMemberName() *******");
		System.out.println(memberService.checkName("admin"));
		System.out.println(memberService.checkName("new member"));
		assertFalse(memberService.checkName("Administrator"));
		assertTrue(memberService.checkName("new member"));
	}
	
	@After
	public void end() throws DataNotFoundException {
		System.out.println("******* end() *******");
		memberService.removeMember(testMember);
	}
}