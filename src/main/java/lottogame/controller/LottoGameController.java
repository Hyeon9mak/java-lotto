package lottogame.controller;

import java.util.List;
import lottogame.domain.Count;
import lottogame.domain.LottoGame;
import lottogame.domain.MatchResults;
import lottogame.domain.Money;
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

        MatchResults matchResults = lottoGame.getMatchingResult(lottoTickets, lottoWinningNumbers);
        OutputView.printLottoGameResult(matchResults.getRanks());
        OutputView.printLottoGameYield(lottoGame.getYield(matchResults));
    }

    private LottoTickets getLottoTickets(final Count manualTicketCount, final LottoGame lottoGame) {
        List<List<Integer>> manualTicketNumbers = InputView.inputManualTicketNumbers(manualTicketCount);
        LottoTickets manualTickets = lottoGame.issueManualTickets(manualTicketNumbers);
        LottoTickets autoTickets = lottoGame.issueAutoTickets();
        OutputView.printLottoTickets(manualTickets.getLottoTickets(), autoTickets.getLottoTickets());
        return manualTickets.joinLottoTickets(autoTickets);
    }

    private LottoWinningNumbers getWinningNumbers() {
        List<Integer> winningNumbers = InputView.inputWinningNumbers();
        int bonusNumber = InputView.inputBonusNumber();
        return new LottoWinningNumbers(winningNumbers, bonusNumber);
    }
}
