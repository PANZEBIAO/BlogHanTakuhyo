package blog.com.Controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.Model.Entity.UserEntity;
import blog.com.Service.CommentService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CommentController {
	// CommentServiceが使えるように宣言
	@Autowired
	private CommentService commentService;

	// Sessionが使えるように宣言
	@Autowired
	private HttpSession session;

	// comment登録処理
	@PostMapping("/blog/comment/process")
	public String blogComment(@RequestParam String comment,
			@RequestParam Long blogId, @RequestParam Long userId) {
		// セッションからログインしている人の情報を取得
		UserEntity userList = (UserEntity) session.getAttribute("user");
		// 新規作成時間を登録
		LocalDate registerDate = LocalDate.now();
		// UserIdがnullだったら、ログインページに飛ぶ
		if (userList.getUserId() == null) {
			return "redirect:/login";
		} else {
			// saveCommentを呼び出し、コメントを保存
			if (commentService.saveComment(registerDate, comment, blogId, userId)) {
				return "redirect:/blog/content/" + blogId;
			}
			return "redirect:/blog/content/" + blogId;
		}
	}
}
