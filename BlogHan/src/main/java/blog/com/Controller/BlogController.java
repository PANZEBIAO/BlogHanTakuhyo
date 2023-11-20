package blog.com.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.Model.Entity.BlogEntity;
import blog.com.Model.Entity.CommentEntity;
import blog.com.Model.Entity.UserEntity;
import blog.com.Service.BlogService;
import blog.com.Service.CommentService;
import blog.com.Service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogController {
	// BlogServiceが使えるように宣言
	@Autowired
	private BlogService blogService;

	// Sessionが使えるように宣言
	@Autowired
	private HttpSession session;

	// UserServiceが使えるように宣言
	@Autowired
	private UserService userService;
	
	//CommentServiceが使えるように宣言
	@Autowired
	private CommentService commentService;
	
	// blog画面の表示
	@GetMapping("/blog/list")
	public String getBlogList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "2") int pageSize, Model model) {
		// セッションからログインしている人の情報を取得
		UserEntity user = (UserEntity) session.getAttribute("user");
		// ログイン情報がなかったらログインページに戻る
		if (user == null) {
			return "redirect:/login";
		} else {
			// 商品一覧の情報取得
			Page<BlogEntity> blogList = blogService.selectAll(page, pageSize);
			// 全部ブログの数
			Long blogCount = blogService.count();
			model.addAttribute("blogCount", blogCount);
			model.addAttribute("blogList", blogList);
			model.addAttribute("totalBlogs", blogList.getTotalElements());

			int totalPages = blogList.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
				model.addAttribute("pageSize", pageSize);
			}
			return "blog_list.html";
		}
	}
	
	//検索機能
	@GetMapping("/blog/search")
	public String searchBlogs(@RequestParam String keyword,Model model) {
			//"blogService.searchBlogs" メソッドを呼び出して、指定された検索用語に一致するブログを検索します。
			List<BlogEntity> searchResults = blogService.searchBlogs(keyword);
			//検索して何件表示したかを変数searchCountに入れる
			int searchCount = searchResults.size();
			model.addAttribute("searchResults",searchResults);
			model.addAttribute("searchCount",searchCount);
			model.addAttribute("keyword",keyword);
			return "blog_search.html";
	}
	
	// ブログコンテント画面表示
	@GetMapping("/blog/content/{blogId}")
	public String getBlogContentPage(@PathVariable Long blogId, Model model) {
		// blogIdで特定のブログ投稿を取得し、blogListに格納
		BlogEntity blogList = blogService.getEditBlog(blogId);

		// blogListがnullだったら、list画面に戻る
		if (blogList == null) {
			// ブログ見つからない場合はlist画面に戻ります
			return "redirect:/blog/list";
		} else {
			model.addAttribute("blog", blogList);
			// ブログ投稿の作成者を取得します。
			UserEntity aythor = userService.getUserById(blogList.getUserId());
			model.addAttribute("authorName", aythor.getUserName());
			
			//comment取得
			List<CommentEntity> comments = commentService.findByBlogId(blogId);
			model.addAttribute("comments", comments);
			return "blog_content.html";
		}
	}

	// MyPage画面を表示
	@GetMapping("/blog/myPage")
	public String getMyPage(Long userId, HttpSession session, Model model) {
		// セッションからログインしている人の情報を取得
		UserEntity userList = (UserEntity) session.getAttribute("user");
		if (userList == null) {
			// ログイン情報がなかったらログインページに戻る
			return "redirect:/login";
		} else {
			// userIdからこのユーザーが登録したブログを表示
			List<BlogEntity> blogList = blogService.findByUserId(userList.getUserId());
			Long blogCount = blogService.countByUserId(userList.getUserId());

			model.addAttribute("blogList", blogList);
			model.addAttribute("blogCount", blogCount);
			return "blog_MyPage.html";
		}
	}

	// ブログ登録画面を表示
	@GetMapping("/blog/create")
	public String getBlogCreatePage() {
		// セッションからログインしている人の情報を取得
		UserEntity userList = (UserEntity) session.getAttribute("user");

		if (userList == null) {
			// ログイン情報がなかったらログインページに戻る
			return "redirect:/login";
		} else {
			return "blog_create.html";
		}
	}

	// ブログ登録処理
	@PostMapping("/blog/create/process")
	public String blogCreate(@RequestParam String blogTitle, @RequestParam Long categoryId,
			@RequestParam MultipartFile blogImage, @RequestParam String blogDetail, Model model) {
		// セッションからログインしている人の情報を取得
		UserEntity userList = (UserEntity) session.getAttribute("user");
		// 現在の日時情報を元に、ファイル名を作成
		// blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
				+ blogImage.getOriginalFilename();
		try {
			Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog_img/" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 新規作成時間を登録
		LocalDate registerDate = LocalDate.now();
		// createBlogを呼び出し、ブログを作成
		if (blogService.createBlog(blogTitle, registerDate, fileName, blogDetail, categoryId, userList.getUserId())) {
			return "create_success.html";
		} else {
			return "redirect:/blog/create";
		}

	}

	// ブログ編集画面表示
	@GetMapping("/blog/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		// セッションからログインしている人の情報を取得
		UserEntity userList = (UserEntity) session.getAttribute("user");
		// blogIdからブログを検索メソッドを変数blogListに格納
		BlogEntity blogList = blogService.getEditBlog(blogId);
		// blogIdがなかったら/myPageに戻る
		if (blogList == null) {
			return "redirect:/blog/myPage";
		} else {
			// blogIdがあったらブログ編集画面を表示
			model.addAttribute("username", userList.getUserName());
			model.addAttribute("blogList", blogList);
			return "blog_edit.html";
		}
	}

	// ブログ編集画面処理
	@PostMapping("/blog/edit/process")
	public String editBlog(@RequestParam String blogTitle, @RequestParam Long categoryId,
			@RequestParam MultipartFile newBlogImage, @RequestParam String blogDetail, @RequestParam Long blogId) {

		// 現在の日時情報を元に、ファイル名を作成
		// blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
				+ newBlogImage.getOriginalFilename();

		try {
			// 新しい画像を指定したパスにコピー
			Path destinationPath = Paths.get("src/main/resources/static/blog_img/" + fileName);
			Files.createDirectories(destinationPath.getParent());
			Files.copy(newBlogImage.getInputStream(), destinationPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// blogServiceからeditBlogメソッドを使う
		if (blogService.editBlog(fileName, blogTitle, blogDetail, categoryId, blogId)) {
			return "edit_success.html";
		} else {
			return "redirect:/blog/edit/{blogId}";
		}
	}
	
	

	// 削除処理
	@PostMapping("/blog/delete")
	public String blogDelete(@RequestParam Long blogId) {
		if (blogService.deleteBlog(blogId)) {
			return "delete_success.html";
		} else {
			return "redirect:/blog/list";
		}
	}

}
