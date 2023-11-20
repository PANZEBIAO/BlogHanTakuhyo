package blog.com.Model.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.Model.Entity.CommentEntity;
import jakarta.transaction.Transactional;

@Repository
//削除メソッドがある場合入れる
@Transactional
public interface CommentDao extends JpaRepository<CommentEntity, Long> {
	// blogIdからコメントを検索
	List<CommentEntity> findByBlogId(Long blogId);

	// 削除メソッド idで削除を行う
	int deleteByCommentId(Long commentId);
}
