package fr.zebulon.cosmeticsx.api.rest;

import com.google.gson.Gson;
import fr.zebulon.cosmeticsx.configuration.Config;
import fr.zebulon.cosmeticsx.configuration.PluginMode;
import fr.zebulon.cosmeticsx.models.rank.PlayerRank;
import fr.zebulon.cosmeticsx.models.requests.CosmeticEquipRequest;
import fr.zebulon.cosmeticsx.models.requests.CosmeticModifyQuantityRequest;
import fr.zebulon.cosmeticsx.models.requests.CosmeticUnlockRequest;
import fr.zebulon.cosmeticsx.models.responses.CosmeticProfileResponse;
import fr.zebulon.cosmeticsx.models.responses.SimpleResponse;
import kong.unirest.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class RestAPI {

    private final Gson gson;
    private final Config config;

    public RestAPI(Config config, Gson gson) {
        this.config = config;
        this.gson = gson;

        Unirest.config().setObjectMapper(new ObjectMapper() {
            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return gson.fromJson(value, valueType);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public <T> T readValue(String value, GenericType<T> genericType) {
                try {
                    return gson.fromJson(value, genericType.getType());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String writeValue(Object value) {
                try {
                    return gson.toJson(value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    protected HttpRequestWithBody request(HttpMethod method, String endPoint) {
        final HttpRequestWithBody req = Unirest.request(method.name(), config.getApiUrl() + endPoint);
        if (PluginMode.LOBBY == config.getMode()) return req.headers(Map.of("X-Access-Token", config.getApiSecret()));

        // Send the request
        return req;
    }

    protected HttpRequestWithBody postJson(String endPoint) {
        return request(HttpMethod.POST, endPoint).contentType("application/json");
    }

    public abstract CompletableFuture<HttpResponse<CosmeticProfileResponse>> getProfile(UUID uuid);

    public abstract CompletableFuture<HttpResponse<SimpleResponse>> equipCosmetic(CosmeticEquipRequest req);

    public abstract CompletableFuture<HttpResponse<Void>> unlockCosmetic(CosmeticUnlockRequest req);

    public abstract CompletableFuture<HttpResponse<Void>> modifyCosmeticQuantity(CosmeticModifyQuantityRequest req);

    public abstract CompletableFuture<HttpResponse<PlayerRank[]>> getRanks();

    public Gson getGson() {
        return gson;
    }

    public Config getConfig() {
        return config;
    }
}
