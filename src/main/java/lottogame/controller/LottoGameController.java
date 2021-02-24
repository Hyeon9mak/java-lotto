package lottogame.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import lottogame.domain.Count;
import lottogame.domain.LottoGame;
import lottogame.domain.Money;
import lottogame.domain.Rank;
import lottogame.domain.machine.LottoTicketIssueMachine;
import lottogame.domain.number.LottoWinningNumbers;
import lottogame.domain.ticket.LottoTickets;
import lottogame.view.InputView;
import lottogame.view.OutputView;

public class LottoGameController {

    public LottoGameController() {
    }

    public void play() {
        Money money = new Money(InputView.inputMoney());
        Count manualTicketCount = new Count(InputView.inputManualTicketCount());
        LottoGame lottoGame = new LottoGame(new LottoTicketIssueMachine(money, manualTicketCount));
        LottoTickets lottoTickets = getLottoTickets(manualTicketCount, lottoGame);
        LottoWinningNumbers lottoWinningNumbers = getWinningNumbers();
        Map<Rank, Integer> ranks = lottoGame.getMatchingResult(lottoTickets, lottoWinningNumbers);
        printLottoGameResult(lottoGame.getMatchingResult(lottoTickets, lottoWinningNumbers), lottoGame.getYield(ranks));
    }

    private LottoTickets getLottoTickets(final Count manualTicketCount, final LottoGame lottoGame) {
        List<Set<Integer>> manualTicketNumbers = InputView.inputManualTicketNumbers(manualTicketCount);
        LottoTickets manualTickets = lottoGame.issueManualTickets(manualTicketNumbers);
        LottoTickets autoTickets = lottoGame.issueAutoTickets();
        OutputView.printLottoTickets(manualTickets.getLottoTickets(), autoTickets.getLottoTickets());
        return manualTickets.joinLottoTickets(autoTickets);
    }

    private LottoWinningNumbers getWinningNumbers() {
        Set<Integer> winningNumbers = InputView.inputWinningNumbers();
        int bonusNumber = InputView.inputBonusNumber();
        return new LottoWinningNumbers(winningNumbers, bonusNumber);
    }

    private void printLottoGameResult(final Map<Rank, Integer> ranks, final double yield) {
        OutputView.printLottoGameResult(ranks);
        OutputView.printLottoGameYield(yield);
    }
}
