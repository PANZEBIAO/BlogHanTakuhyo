package blog.com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.Model.Entity.UserEntity;
import blog.com.Service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	// UserServiceとリンク
	@Autowired
	private UserService userService;

	// Sessionが使えるように宣言
	@Autowired
	private HttpSession session;

	// ログイン画面を表示
	@GetMapping("/login")
	public String getUserLoginPage() {
		return "login.html";
	}

	// ログイン処理
	@PostMapping("/login/process")
	public String login(@RequestParam String email, @RequestParam String password) {
		// Serviceクラスのメソッドを使ってログインしてる人の情報を獲得して変数を格納
		UserEntity userList = userService.loginCheck(email, password);
		//データベースに入力したメールとパスワードが見つからなかったら、ログイン画面に戻る
		if (userList == null) {
			return "redirect:/login";
		} else {
			//sessionを使ってログインしている人の情報を保存する
			session.setAttribute("user", userList);
			//ログイン成功画面に飛ぶ
			return "login_success.html";
		}
	}

	// ログアウト処理
	@GetMapping("/logout")
	public String logout() {
		// sessionの無効化
		session.invalidate();
		return "redirect:/login";
	}
}
