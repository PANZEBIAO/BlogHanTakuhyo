package blog.com.Model.Entity;


public class CommentDTO {
	private CommentEntity comment;
    private String userName;
    
	public CommentDTO(CommentEntity comment, String userName) {
		this.comment = comment;
		this.userName = userName;
	}

	public CommentEntity getComment() {
		return comment;
	}

	public void setComment(CommentEntity comment) {
		this.comment = comment;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
