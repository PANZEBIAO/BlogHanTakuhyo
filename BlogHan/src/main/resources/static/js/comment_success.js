// 5秒後にログインページにリダイレクト
        setTimeout(function () {
            window.location.href = "/blog/content/{blogId}(blogId=${blog.blogId})";
        }, 5000);