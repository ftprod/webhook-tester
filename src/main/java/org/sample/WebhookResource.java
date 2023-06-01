package org.sample;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Path("/webhook-handler")
public class WebhookResource {

    CircularFifoQueue<String> messages = new CircularFifoQueue<>(5);

    @GET
    @Produces("text/plain")
    public String readLastPush(){
        return messages.stream().map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    @POST
    @ResponseStatus(204)
    public void processHook(@RestHeader("x-esante-api-hmac-sha256") String hmac,
                            InputStream body
    ) throws IOException {
        hmac = hmac.split("=")[1];
        try (var is = new InputStreamReader(body, StandardCharsets.UTF_8)) {
            var text = new BufferedReader(is).lines().collect(Collectors.joining("\n"));
            var signature = new HmacUtils("HmacSHA256", "123456").hmacHex(text);
            messages.add("Notification received. Signature verified: "+ signature.equals(hmac) + ". Date: " + new Date());
        }
    }
}
