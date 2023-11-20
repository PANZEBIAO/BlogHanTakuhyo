package blog.com.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import blog.com.Model.Dao.BlogDao;
import blog.com.Model.Entity.BlogEntity;

@Service
public class BlogService {
	// BlogDaoを使えるようにする
	@Autowired
	private BlogDao blogDao;

	// 商品一覧の部分のメソッド
	public Page<BlogEntity> selectAll(Integer page, Integer pageSize) {
		Integer start = (page - 1) * pageSize;
		// 商品一覧を表示する
		return blogDao.findAll(PageRequest.of(start, pageSize));
	}
	
	//検索機能
	public List<BlogEntity> searchBlogs(String keyword) {
        return blogDao.searchBlog(keyword);
    }

	// ログインしてるユーザーの編集できるブログを表示するメソッド
	public List<BlogEntity> findByUserId(Long userId) {
		// ユーザーIDからこのユーザーが登録したブログを表示
		return blogDao.findByUserId(userId);
	}

	// ログインしてるユーザーの全部ブログの数
	public long countByUserId(Long userId) {
		return blogDao.countByUserId(userId);
	}

	// 全部ブログの数
	public long count() {
		return blogDao.count();
	}

	// Blog登録のメソッド
	public boolean createBlog(String blogTitle, LocalDate registerDate, String blogImage, String blogDetail,
			Long categoryId, Long userId) {
		// BLogの名前がテーブルに存在しない場合
		if (blogDao.findByBlogTitle(blogTitle) == null) {
			blogDao.save(new BlogEntity(blogTitle, registerDate, blogImage, blogDetail, categoryId, userId));
			return true;
		} else {
			return false;
		}
	}

	// blogIdからブログを検索
	public BlogEntity getEditBlog(Long blogId) {
		// blogIdがなかったらnullを返す
		if (blogId == null) {
			return null;
		} else {
			// あったら、Daoから検索をかける
			return blogDao.findByBlogId(blogId);
		}
	}

	// ブログ編集処理
	public boolean editBlog(String fileName, String blogTitle, String blogDetail, Long categoryId, Long blogId) {
		// blogIdからブログ詳細を検索し、変数blogListに格納
		BlogEntity blogList = blogDao.findByBlogId(blogId);
		if (blogTitle == null) {
			return false;
		} else {
			// setで更新をかける
			blogList.setBlogImage(fileName);
			blogList.setBlogTitle(blogTitle);
			blogList.setCategoryId(categoryId);
			blogList.setBlogDetail(blogDetail);
			// 更新したデータを保存
			blogDao.save(blogList);
			return true;
		}
	}

	// 削除するメソッド
	public boolean deleteBlog(Long blogId) {
		if (blogId == null) {
			return false;
		} else {
			blogDao.deleteByBlogId(blogId);
			return true;
		}
	}

}
