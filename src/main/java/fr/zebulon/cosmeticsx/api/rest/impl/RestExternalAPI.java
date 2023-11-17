package fr.zebulon.cosmeticsx.api.rest.impl;

import com.google.gson.Gson;
import fr.zebulon.cosmeticsx.api.rest.RestAPI;
import fr.zebulon.cosmeticsx.configuration.Config;
import fr.zebulon.cosmeticsx.models.rank.PlayerRank;
import fr.zebulon.cosmeticsx.models.requests.CosmeticEquipRequest;
import fr.zebulon.cosmeticsx.models.requests.CosmeticModifyQuantityRequest;
import fr.zebulon.cosmeticsx.models.requests.CosmeticUnlockRequest;
import fr.zebulon.cosmeticsx.models.responses.CosmeticProfileResponse;
import fr.zebulon.cosmeticsx.models.responses.SimpleResponse;
import kong.unirest.GenericType;
import kong.unirest.HttpMethod;
import kong.unirest.HttpResponse;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RestExternalAPI extends RestAPI {

    public RestExternalAPI(Config config, Gson gson) {
        super(config, gson);
    }

    @Override
    public CompletableFuture<HttpResponse<CosmeticProfileResponse>> getProfile(UUID uuid) {
        return request(HttpMethod.GET, "/cosmetics/profile/{uuid}").routeParam("uuid", uuid.toString()).asObjectAsync(CosmeticProfileResponse.class);
    }

    @Override
    public CompletableFuture<HttpResponse<PlayerRank[]>> getRanks() {
        return request(HttpMethod.GET, "/network/ranks").asObjectAsync(new GenericType<>() {
        });
    }

    /*
     *  Stubbed out methods because they do not have external access
     */
    @Override
    public CompletableFuture<HttpResponse<SimpleResponse>> equipCosmetic(CosmeticEquipRequest req) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<HttpResponse<Void>> unlockCosmetic(CosmeticUnlockRequest req) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<HttpResponse<Void>> modifyCosmeticQuantity(CosmeticModifyQuantityRequest req) {
        return CompletableFuture.completedFuture(null);
    }
}
