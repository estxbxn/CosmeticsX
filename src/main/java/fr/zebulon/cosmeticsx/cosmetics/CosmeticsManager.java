package fr.zebulon.cosmeticsx.cosmetics;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CosmeticsManager {

    private final Cache<UUID, CosmeticProfileResponse> cache = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).build();
}
