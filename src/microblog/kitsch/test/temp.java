package microblog.kitsch.test;

import org.junit.Test;

import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.service.*;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;

public class temp {
	private PostingService postingService = new PostingServiceImpl();
	private BlogService blogService = new BlogServiceImpl();
	private MemberService memberService = new MemberServiceImpl();
	
	@Test
	public void temp() throws DataDuplicatedException, DataNotFoundException {
		blogService.createBlog(DomainObjectsForTest.members[0], "awesome_blog");
		blogService.createBlog(DomainObjectsForTest.members[2], "animation");
		blogService.createBlog(DomainObjectsForTest.members[3], "fashionable");
		
		for (Posting post : DomainObjectsForTest.audios) {
			postingService.writePosting("awesome_blog", post);
		}
		postingService.writePosting("awesome_blog", DomainObjectsForTest.texts[0]);
		postingService.writePosting("awesome_blog", DomainObjectsForTest.videos[0]);
		
		postingService.writePosting("animation", DomainObjectsForTest.images[0]);
		postingService.writePosting("animation", DomainObjectsForTest.images[4]);
		postingService.writePosting("animation", DomainObjectsForTest.texts[2]);
		
		postingService.writePosting("fashionable", DomainObjectsForTest.images[1]);
		postingService.writePosting("fashionable", DomainObjectsForTest.images[5]);
		postingService.writePosting("fashionable", DomainObjectsForTest.images[2]);
		postingService.writePosting("fashionable", DomainObjectsForTest.images[3]);
		postingService.writePosting("fashionable", DomainObjectsForTest.videos[1]);
		postingService.writePosting("fashionable", DomainObjectsForTest.texts[1]);
		postingService.writePosting("fashionable", DomainObjectsForTest.texts[3]);
		
	}
}
