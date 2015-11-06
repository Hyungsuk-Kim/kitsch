package microblog.kitsch.test;

import java.util.Date;

import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.domain.PostingContent;

public class DomainObjectsForTest {
	public static Posting[] postings = new Posting[20];
	public static Posting[] texts = new Posting[4];
	public static Posting[] audios = new Posting[8];
	public static Posting[] videos = new Posting[2];
	public static Posting[] images = new Posting[6];
	
	public static Blog[] blogs = new Blog[6];
	public static Blog[] memberBlogs = new Blog[2];
	public static Blog defaultBlog;
	
	public static Member[] members = new Member[4];
	public static Member logonMember;
	
	static {
		Member admin1 = new Member("admin@kitsch.com", "administrator", "admin1234", -1, "Sample_files/images/profile/911.jpg", new Date(), 0);
		Member admin2 = new Member("kitsch@kitsch.com", "kitsch", "kitsch1234", -1, "Sample_files/images/profile/totoro.jpg", new Date(), 3);
		Member member1 = new Member("test@kitsch.com", "test1", "test1234", 0, "Sample_files/images/profile/fingers.jpg", new Date(), 0);
		Member member2 = new Member("test2@kitsch.com", "test2", "test1234", 0, "Sample_files/images/profile/bonobono.jpg", new Date(), 0);
		
		members[0] = admin1;
		members[1] = admin2;
		members[2] = member1;
		members[3] = member2;
		
		Blog blog1 = new Blog("blog_id_1", "test@kitsch.com", "animation", 0, 0, 0, "Sample_files/images/header/4.png", 0, new Date());
		Blog blog2 = new Blog("blog_id_2", "admin@kitsch.com", "personal", 0, 0, 0, "Sample_files/images/header/4.png", 0, new Date());
		Blog blog3 = new Blog("blog_id_3", "admin@kitsch.com", "awsome_blog", 0, 0, 0, "Sample_files/images/header/4.png", 0, new Date());
		Blog blog4 = new Blog("blog_id_4", "test2@kitsch.com", "fashionable", 0, 0, 0, "Sample_files/images/header/5.png", 0, new Date());
		Blog blog5 = new Blog("blog_id_5", "test2@kitsch.com", "secondary", 0, 0, 0, "Sample_files/images/header/5.png", 0, new Date());
		Blog blog6 = new Blog("blog_id_6", "admin@kitsch.com", "notify", 0, 0, 0, "Sample_files/images/header/4.png", 0, new Date());
		
		blogs[0] = blog1;
		blogs[1] = blog2;
		blogs[2] = blog3;
		blogs[3] = blog4;
		blogs[4] = blog5;
		blogs[5] = blog6;
		
		// 1
		PostingContent textCon1 = new PostingContent(1, "Thank you for using our service.");
		Posting textPost1 = new Posting(1, "Welcome to kitsch.", "administrator", textCon1, PostingContent.TEXT_CONTENT,
				4, new Date(), 2, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#kitsch", 1, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 2
		PostingContent singleImg1 = new PostingContent(1, new String[] {"Sample_files/images/character/bleach.jpg"});
		Posting singleImgPost1 = new Posting(1, "Characters of Bleach", "test1", singleImg1, PostingContent.SINGLE_IMAGE_FILE_CONTENT,
				1, new Date(), 0, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#bleach", 1, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 3
		PostingContent mixedImg1 = new PostingContent(2, "I love Naruto animation!!", new String[] {"Sample_files/images/character/naruto.jpg"});
		Posting mixedImgPost1 = new Posting(2, "Characters of Naruto", "test1", mixedImg1, PostingContent.MIXED_IMAGE_FILE_CONTENT,
				3, new Date(), 1, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#Naruto", 2, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 4
		PostingContent singleImg2 = new PostingContent(1, new String[] {"Sample_files/images/fashion/221014BW4664Web.jpg", "Sample_files/images/fashion/121212Stripe5336Web.jpg", "Sample_files/images/fashion/91514Somerset6B9481Web.jpg"});
		Posting singleImgPost2 = new Posting(2, "Women who fascinating.", "test2", singleImg2, PostingContent.SINGLE_IMAGE_FILE_CONTENT,
				0, new Date(), 0, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#women #fashion #style #street", 2, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 5
		PostingContent mixedAudio1 = new PostingContent(1, "I love his voice and songs.", new String[] {"Sample_files/audios/can_we.mp3", "Sample_files/audios/the_simple_things.mp3"});
		Posting mixedAudioPost1 = new Posting(1, "Michael Carreon`s songs.", "administrator", mixedAudio1, PostingContent.MIXED_AUDIO_FILE_CONTENT,
				1, new Date(), 0, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#michael carreon", 1, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 6
		PostingContent singleAudio2 = new PostingContent(2, new String[] {"Sample_files/audios/american_boy.mp3"});
		Posting singleAudioPost2 = new Posting(2, "American Boy (feat. Kanye West) - Estelle", "administrator", singleAudio2, PostingContent.SINGLE_AUDIO_FILE_CONTENT,
				3, new Date(), 1, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#women #fashion #style #street", 2, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 7
		PostingContent mixedAudio2 = new PostingContent(3, "This song`s lyrics contains lascivious contents.", new String[] {"Sample_files/audios/blow.mp3"});
		Posting mixedAudioPost3 = new Posting(3, "Blow - Beyonce", "administrator", mixedAudio2, PostingContent.MIXED_AUDIO_FILE_CONTENT,
				30, new Date(), 3, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "", 3, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 8
		PostingContent singleAudio3 = new PostingContent(4, new String[] {"Sample_files/audios/Do_it.mp3"});
		Posting singleAudioPost3 = new Posting(4, "Do it - Tuxedo", "administrator", singleAudio3, PostingContent.SINGLE_AUDIO_FILE_CONTENT,
				22, new Date(), 0, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "", 4, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 9
		PostingContent singleAudio4 = new PostingContent(5, new String[] {"Sample_files/audios/inside_and_out.mp3"});
		Posting singleAudioPost4 = new Posting(5, "Inside And Out - Feist", "administrator", singleAudio4, PostingContent.SINGLE_AUDIO_FILE_CONTENT,
				3, new Date(), 1, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#song #pop", 5, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 10
		PostingContent singleAudio5 = new PostingContent(6, new String[] {"Sample_files/audios/Night_birds.mp3"});
		Posting singleAudioPost5 = new Posting(6, "Night Birds - Shakatak", "administrator", singleAudio5, PostingContent.SINGLE_AUDIO_FILE_CONTENT,
				10, new Date(), 2, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#song #pop", 6, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 11
		PostingContent singleAudio6 = new PostingContent(7, new String[] {"Sample_files/audios/running.mp3"});
		Posting singleAudioPost6 = new Posting(7, "Running - Jessie Ware", "administrator", singleAudio6, PostingContent.SINGLE_AUDIO_FILE_CONTENT,
				3, new Date(), 0, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#song #pop", 7, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 12
		PostingContent singleAudio7 = new PostingContent(8, new String[] {"Sample_files/audios/stop_the_train.mp3"});
		Posting singleAudioPost7 = new Posting(8, "Stop the train - oshi", "administrator", singleAudio7, PostingContent.SINGLE_AUDIO_FILE_CONTENT,
				4, new Date(), 0, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#song #pop", 8, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 13
		PostingContent mixedVideo1 = new PostingContent(9, "Go Youtube channel. https://www.youtube.com/channel/UCj0ElpMSiesbvyed1SZ-brw", new String[] {"Sample_files/videos/sample_video2.mp4"});
		Posting mixedVideoPost1 = new Posting(9, "Funny video", "administrator", mixedVideo1, PostingContent.MIXED_VIDEO_FILE_CONTENT,
				5, new Date(), 2, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "", 9, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 14
		PostingContent singleVideo1 = new PostingContent(1, new String[] {"Sample_files/videos/sample_video2.mp4"});
		Posting singleVideoPost1 = new Posting(1, "Funny video", "test2", singleVideo1, PostingContent.SINGLE_VIDEO_FILE_CONTENT,
				11, new Date(), 1, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#fun #video", 1, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 15
		PostingContent mixedImg2 = new PostingContent(2, "Cool! Awesome! Fantastic!", new String[] {"Sample_files/images/fashion/vintage-sat.jpg"});
		Posting mixedImgPost2 = new Posting(2, "Vintage Photo", "test2", mixedImg2, PostingContent.MIXED_IMAGE_FILE_CONTENT,
				8, new Date(), 1, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#photo #vintage", 2, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 16
		PostingContent singleImg3 = new PostingContent(3, new String[] {"Sample_files/images/fashion/10913Denimsuit7104Web.jpg", "Sample_files/images/fashion/ManMilan7528web.jpg", "Sample_files/images/fashion/72015MRA04431.jpg"});
		Posting singleImgPost3 = new Posting(3, "Gentle Man", "test2", singleImg3, PostingContent.SINGLE_IMAGE_FILE_CONTENT,
				14, new Date(), 3, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#suit #male", 3, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 17
		PostingContent singleImg4 = new PostingContent(4, new String[] {"Sample_files/images/fashion/Sf1_0866Web.jpg"});
		Posting singleImgPost4 = new Posting(4, "A Dress.", "test2", singleImg4, PostingContent.SINGLE_IMAGE_FILE_CONTENT,
				6, new Date(), 2, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "", 4, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 18
		PostingContent textCon2 = new PostingContent(2, "<h2>\"All things are not shining. But All the shining things are.\"</h2> \n\t\t [<h3>All Things Shining</h3> <em>written by</em> <strong>Hubert Dreyfus & Sean Dorrance Kelly</strong>]");
		Posting textPost2 = new Posting(2, "Quot", "test2", textCon2, PostingContent.TEXT_CONTENT,
				13, new Date(), 2, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#book", 2, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 19
		PostingContent textCon3 = new PostingContent(2, "This is test posting. fdsgihsoire;hgs;oierhpoitsrhoihisghioshrguavkajhevfuaygfailewgfjhawvefyukagfluagvbefhjvauyfdgasdk fvakjhvef alegflia gflef. ailrfglakjbfeawlgfluyaewfvcawevcf;lagegfawljvf,agjkshdlgh ajkghe;ghoesr gs sdfasdfaf");
		Posting textPost3 = new Posting(2, "Test post.", "test1", textCon3, PostingContent.TEXT_CONTENT,
				1, new Date(), 0, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#test", 2, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		// 20
		PostingContent textCon4 = new PostingContent(3, "<em>...nothing.</em>");
		Posting textPost4 = new Posting(3, "untitled", "test2", textCon4, PostingContent.TEXT_CONTENT,
				6, new Date(), 1, Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "", 3, 0, 
				0, 0, Posting.NORMAL_TYPE_POSTING, 0, Posting.NOTHING);
		
		postings[0] = singleImgPost1;
		postings[1] = singleAudioPost2;
		postings[2] = singleAudioPost6;
		postings[3] = textPost1;
		postings[4] = mixedVideoPost1;
		postings[5] = singleAudioPost3;
		postings[6] = mixedAudioPost3;
		postings[7] = textPost3;
		postings[8] = singleAudioPost7;
		postings[9] = singleAudioPost5;
		postings[10] = mixedImgPost1;
		postings[11] = singleImgPost2;
		postings[12] = singleVideoPost1;
		postings[13] = textPost4;
		postings[14] = mixedAudioPost1;
		postings[15] = textPost2;
		postings[16] = singleAudioPost4;
		postings[17] = mixedImgPost2;
		postings[18] = singleImgPost3;
		postings[19] = singleImgPost4;
		
		texts[0] = textPost1;
		texts[1] = textPost2;
		texts[2] = textPost3;
		texts[3] = textPost4;
		
		audios[0] = singleAudioPost2;
		audios[1] = singleAudioPost3;
		audios[2] = singleAudioPost4;
		audios[3] = singleAudioPost5;
		audios[4] = singleAudioPost6;
		audios[5] = singleAudioPost7;
		audios[6] = mixedAudioPost1;
		audios[7] = mixedAudioPost3;
		
		videos[0] = singleVideoPost1;
		videos[1] = mixedVideoPost1;
		
		images[0] = singleImgPost1;
		images[1] = singleImgPost2;
		images[2] = singleImgPost3;
		images[3] = singleImgPost4;
		images[4] = mixedImgPost1;
		images[5] = mixedImgPost2;
		
		logonMember = member2;
		memberBlogs[0] = blog4;
		memberBlogs[1] = blog5;
		defaultBlog = blog4;
	}
}
