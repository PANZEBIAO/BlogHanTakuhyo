package blog.com.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.Model.Dao.CommentDao;
import blog.com.Model.Entity.CommentEntity;

@Service
public class CommentService {
	// commentDaoを使えるようにする
	@Autowired
	private CommentDao commentDao;

	// blogIdからコメントを検索メソッド
	public List<CommentEntity> findByBlogId(Long blogId) {
		return commentDao.findByBlogId(blogId);
	}

	// comment登録メソッド
	public boolean saveComment(LocalDate registerDate, String comment, Long blogId, Long userId) {
		commentDao.save(new CommentEntity(registerDate, comment, blogId, userId));
		return true; // Assuming save is always successful
	}

	// 削除するメソッド
	public boolean deleteComment(Long commentId) {
		if (commentId == null) {
			return false;
		} else {
			commentDao.deleteByCommentId(commentId);
			return true;
		}
	}

}
