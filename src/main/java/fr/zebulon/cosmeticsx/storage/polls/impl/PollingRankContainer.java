package fr.zebulon.cosmeticsx.storage.polls.impl;

import fr.zebulon.cosmeticsx.CosmeticsX;
import fr.zebulon.cosmeticsx.models.rank.PlayerRank;
import fr.zebulon.cosmeticsx.storage.polls.PollingContainer;
import kong.unirest.HttpResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PollingRankContainer extends PollingContainer<List<PlayerRank>> {

    private boolean success = false;
    private final CosmeticsX plugin;

    public PollingRankContainer(CosmeticsX plugin) {
        super(plugin, List.of(), 600); // Every 30 seconds poll the data
        this.plugin = plugin;
    }

    @Override
    public Optional<List<PlayerRank>> poll() {
        if (success) return Optional.empty();

        final HttpResponse<PlayerRank[]> res = plugin.getRestApi().getRanks().join();
        if (!res.isSuccess()) return Optional.empty();

        final PlayerRank[] ranks = res.getBody();
        if (ranks.length == 0) return Optional.empty();

        this.success = true;
        return Optional.of(Arrays.asList(ranks));
    }

    @Override
    public void process(List<PlayerRank> state) {
        PlayerRank.registerRanks(state);
        cancel();
    }
}
