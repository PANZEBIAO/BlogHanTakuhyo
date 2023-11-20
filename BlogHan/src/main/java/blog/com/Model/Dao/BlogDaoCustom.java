package blog.com.Model.Dao;

import java.util.List;

import blog.com.Model.Entity.BlogEntity;

public interface BlogDaoCustom {
	// 引数でkeywordブログを検索
	List<BlogEntity> searchBlog(String keyword);
}
