package info.openrpg.image.processing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.openrpg.image.processing.dto.ChunkDto;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
            return Optional.empty();
        }
    }

    @Override
    public Optional<InputStream> spawnPlayer(long id) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            CloseableHttpResponse get = client.execute(
                    new HttpHost("localhost", 8080),
                    new BasicHttpRequest("GET", "/spawn/".concat(String.valueOf(id)))
            );
            HttpEntity entity = get.getEntity();
            String response = IOUtils.toString(entity.getContent(), Charset.defaultCharset());
            return Optional.of(new URL(response).openConnection().getInputStream());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<InputStream> createImage(ChunkDto chunkDto) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPost postRequest = new HttpPost(
                    "http://localhost:8080/");
            StringEntity input = new StringEntity(createJsonByChunk(chunkDto));
            input.setContentType("application/json");
            postRequest.setEntity(input);
            HttpResponse postResponse = client.execute(postRequest);

            HttpEntity entity = postResponse.getEntity();
            String response = IOUtils.toString(entity.getContent(), Charset.defaultCharset());
            return Optional.of(new URL(response).openConnection().getInputStream());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private String createJsonByChunk(ChunkDto chunkDto) {
        try {
            return new ObjectMapper().writeValueAsString(chunkDto);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException();
        }
    }
}
