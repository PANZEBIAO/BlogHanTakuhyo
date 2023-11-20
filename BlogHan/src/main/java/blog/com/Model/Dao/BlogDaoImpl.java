package blog.com.Model.Dao;

import java.util.List;

import blog.com.Model.Entity.BlogEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class BlogDaoImpl implements BlogDaoCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<BlogEntity> searchBlog(String keyword) {
		//クリエ文字列を構築し、JPQLを使用し、部分一致検索
		String queryString = "SELECT b FROM BlogEntity b WHERE lower(b.blogTitle) LIKE :keyword OR lower(b.blogDetail) LIKE :keyword";
		Query query = entityManager.createQuery(queryString,BlogEntity.class);
		//パラメータを設定して検索を実行
		query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
		//検索条件とマッチするListを返す
		return query.getResultList();
	}
	
}
