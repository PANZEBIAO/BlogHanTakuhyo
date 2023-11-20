package blog.com.Model.Dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.Model.Entity.BlogEntity;
import jakarta.transaction.Transactional;

@Repository
//削除メソッドがある場合入れる
@Transactional
public interface BlogDao extends JpaRepository<BlogEntity, Long>,BlogDaoCustom {
	// userIdでブログを検索
	List<BlogEntity> findByUserId(Long userId);

	// 商品一覧の情報取得 SELECT * FROM blogs
	// List<BlogEntity> findAll();
	
	//titleかdetailでブログを検索
	List<BlogEntity> searchBlog(String keyword);
	
	// SELECT * FROM blogs WHERE blogId = ?
	BlogEntity findByBlogId(Long blogId);

	// SELECT * FROM blogs WHERE blogTitle=?
	BlogEntity findByBlogTitle(String blogTitle);

	// ログインしてるユーザーのブログの数をカウント
	long countByUserId(Long userId);

	// 全部ブログの数
	long count();

	// ログインしてるユーザーのブログでページ分けで検索
	//@Query("SELECT b FROM BlogEntity b WHERE userId = ?1")
	//List<BlogEntity> findBlogsByUser(Long userId, Pageable pageable);

	// ページ分けで検索
	Page<BlogEntity> findAll(Pageable pageable);

	// 削除メソッド idで削除を行う
	int deleteByBlogId(Long blogId);

}
