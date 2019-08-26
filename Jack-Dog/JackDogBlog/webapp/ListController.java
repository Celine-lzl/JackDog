import redis.clients.jedis.Jedis;
import com.lzl.*;
import java.io.*;
import java.util.*;

public class ListController implements Controller {
	public void doGet(Request request, Response response) throws IOException {
		Jedis jedis = new Jedis("127.0.0.1");
		List<String> as = jedis.lrange("article_id_list", 0, -1);
		if (as.size() == 0) {
			response.println("空的博客");
			return;
		}
		response.println("<ol>");
		for (String line : as) {
			String[] f = line.split("@");
			String id = f[0];
			String title = f[1];
			String s = String.format("<li><a href='/a?id=%s'>%s</a></li>",
				id, title);
			response.println(s);
		}
		response.println("</ol>");
	}
}	