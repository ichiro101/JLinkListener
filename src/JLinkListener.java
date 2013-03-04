/**
 * Created with IntelliJ IDEA.
 * User: kenli
 * Date: 3/2/13
 * Time: 4:03 午後
 * To change this template use File | Settings | File Templates.
 */

import com.sun.net.httpserver.*;
import com.wolfram.jlink.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.*;

public class JLinkListener {

    public static void main(String argv[]) throws MathLinkException, IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(57557), 0);
        server.createContext("/math", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    private static class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            // query parse
            InputStream requestStream = httpExchange.getRequestBody();
            java.util.Scanner s = new java.util.Scanner(requestStream).useDelimiter("\\A");
            String query = s.hasNext() ? s.next() : "";

            Map<String, String> queryMap = getQueryMap(query);
            String input = queryMap.get("input");

            // call mathematica
            Mathematica m = new Mathematica();

            // serve the webpage to the user
            String response = m.call(input);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }



    }

    public static Map<String, String> getQueryMap(String query) throws UnsupportedEncodingException {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String name = param.split("=")[0];
            String value = URLDecoder.decode(param.split("=")[1], "UTF-8");
            map.put(name, value);
        }
        return map;
    }


}
