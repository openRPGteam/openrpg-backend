package info.openrpg.image.processing;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

public class HTTPRequestSender implements RequestSender {
    @Override
    public String ping() {
        return null;
    }

    @Override
    public Optional<InputStream> sendMove(long id, int x, int y) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            CloseableHttpResponse get = client.execute(
                    new HttpHost("localhost", 8080),
                    new BasicHttpRequest(
                            "GET",
                            String.format("/move/%d/%d/%d", id, x, -y)
                    )
            );
            String response = IOUtils.toString(get.getEntity().getContent(), Charset.defaultCharset());
            return Optional.of(new URL(response).openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
