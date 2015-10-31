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
	
	private int num;
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
	public static final int COMMON_IMAGE_CONTENT = 929;
	public static final int COMMON_AUDIO_CONTENT = 939;
	public static final int COMMON_VIDEO_CONTENT = 949;
	
	// Constructors
	// for Text Contents
	public PostingContent(int num, String textContent) {
		this.num = num;
		this.textContent = textContent;
	}
	
	// for Single Contents (Media content)
	public PostingContent(int num, String[] filePaths) {
		this.num = num;
		this.filePaths = filePaths;
	}
	
	// for Mixed Contents
	public PostingContent(int num, String textContent, String[] filePaths) {
		this.num = num;
		this.textContent = textContent;
		this.filePaths = filePaths;
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
	public PostingContent(String textContent, String[] filePaths) {
		this.textContent = textContent;
		this.filePaths = filePaths;
	}
	
	public PostingContent(String[] filePaths) {
		this.filePaths = filePaths;
	}
	
	public PostingContent() {
		
	}

	// Methods
	@Override
	public String toString() {
		return "PostingContent [num=" + num + ", textContent=" + textContent + ", filePaths="
				+ Arrays.toString(filePaths) + "]";
	}

	// Getters
	public int getNum() {
		return num;
	}

	public String getTextContent() {
		return textContent;
	}

	public String[] getFilePaths() {
		return filePaths;
	}

	// Setters
	public void setNum(int num) {
		this.num = num;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setFilePaths(String[] filePaths) {
		this.filePaths = filePaths;
	}
	
}