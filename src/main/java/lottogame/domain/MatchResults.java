package lottogame.domain;

import java.util.EnumMap;
import java.util.Map;
import lottogame.domain.machine.LottoTicketIssueMachine;

public class MatchResults {

    private final Map<Rank, Integer> matchingResults;

    public MatchResults(Map<Rank, Integer> matchingResults) {
        this.matchingResults = new EnumMap<>(matchingResults);
    }

    public Map<Rank, Integer> getRanks() {
        Map<Rank, Integer> ranks = new EnumMap<>(this.matchingResults);
        ranks.remove(Rank.FAIL);
        return ranks;
    }

    public double totalInvestment() {
        return this.matchingResults.values()
            .stream()
            .reduce(0, Integer::sum)
            * LottoTicketIssueMachine.getTicketPrice();
    }

    public double totalWinningPrice() {
        return this.matchingResults.entrySet()
            .stream()
            .mapToDouble(rank -> multiplyPriceByCount(rank.getKey(), rank.getValue()))
            .sum();
    }

    private int multiplyPriceByCount(Rank rank, Integer count) {
        return rank.getPrice() * count;
    }
}
