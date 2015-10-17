package microblog.kitsch.business.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 */
public class PostingContent implements Serializable{
	// Variables
	// Instance Variables
	private static final long serialVersionUID = -3440708136003859673L;
	
	private String blogName;
	private int postingNum;
	private String textContent;
	private String[] filePaths;
	
	// Class Variables
	// available content types
	public static final int SINGLE_TYPE_CONTENT = 1;
	public static final int MIXED_TYPE_CONTENT = 2;
	public static final int TEXT_CONTENT = 310;
	public static final int SINGLE_IMAGE_FILE_CONTENT = 120;
	public static final int SINGLE_IMAGE_LINK_CONTENT = 121;
	public static final int MIXED_IMAGE_FILE_CONTENT = 220;
	public static final int MIXED_IMAGE_LINK_CONTENT = 221;
	public static final int SINGLE_AUDIO_FILE_CONTENT = 130;
	public static final int SINGLE_AUDIO_LINK_CONTENT = 131;
	public static final int MIXED_AUDIO_FILE_CONTENT = 230;
	public static final int MIXED_AUDIO_LINK_CONTENT = 231;
	public static final int SINGLE_VIDEO_FILE_CONTENT = 140;
	public static final int SINGLE_VIDEO_LINK_CONTENT = 141;
	public static final int MIXED_VIDEO_FILE_CONTENT = 240;
	public static final int MIXED_VIDEO_LINK_CONTENT = 241;
	
	// Constructors
	public PostingContent(String blogName, int postingNum) {
		this.blogName = blogName;
		this.postingNum = postingNum;
	}
	
	// for Text Contents
	public PostingContent(String blogName, int postingNum, String textContent) {
		this(blogName, postingNum);
		this.textContent = textContent;
	}
	
	// for Single Contents (Media content)
	public PostingContent(String blogName, int postingNum, String[] filePaths) {
		this(blogName, postingNum);
		this.filePaths = filePaths;
	}
	
	// for Mixed Contents
	public PostingContent(String blogName, int postingNum, String textContent, String[] filePath) {
		this(blogName, postingNum, filePath);
		this.textContent = textContent;
	}
	
	// for Text Contents when create
	/*public PostingContent(String blogName, String textContent) {
		this.blogName = blogName;
		this.textContent = textContent;
	}*/
	public PostingContent(String textContent) {
		this.textContent = textContent;
	}
	
	// for Mixed Contents when create
	/*public PostingContent(String blogName, String textContent, String... filePath) {
		this.blogName = blogName;
		this.textContent = textContent;
		this.filePaths = filePath;
	}*/
	public PostingContent(String textContent, String... filePath) {
		this.textContent = textContent;
		this.filePaths = filePath;
	}
	
	// Methods
	@Override
	public String toString() {
		return "PostingContent [blogName=" + blogName + ", postingNum=" + postingNum + ", textContent=" + textContent
				+ ", filePaths=" + Arrays.toString(filePaths) + "]";
	}

	public String getBlogName() {
		return blogName;
	}

	public int getPostingNum() {
		return postingNum;
	}

	public String getTextContent() {
		return textContent;
	}

	public String[] getFilePaths() {
		return filePaths;
	}

	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	public void setPostingNum(int postingNum) {
		this.postingNum = postingNum;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setFilePaths(String[] filePaths) {
		this.filePaths = filePaths;
	}
	
}