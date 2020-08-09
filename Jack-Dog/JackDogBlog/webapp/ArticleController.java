import redis.clients.jedis.Jedis;
import com.lzl.*;
import java.io.*;

public class ArticleController implements Controller {
	public void doGet(Request request, Response response) throws IOException {
		String id = request.params.get("id");
		if (id == null) {
			response.stauts = "404 Not Found";
			response.println("没有该文章");
			return;
		}
		Jedis jedis = new Jedis("127.0.0.1");
		String title = jedis.hget("article_" + id, "title");
		String content = jedis.hget("article_" + id, "content");
		if (title == null) {
			response.stauts = "404 Not Found";
			response.println("没有该文章");
			return;
		}
		
		response.println("<h1>" + title + "</h1>");
		response.println("<p>" + content + "</p>");
	}
}