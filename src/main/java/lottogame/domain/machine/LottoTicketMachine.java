package lottogame.domain.machine;

import lottogame.domain.Money;
import lottogame.domain.ticket.LottoTicket;
import lottogame.domain.ticket.LottoTickets;

public class LottoTicketMachine {

    public static final int TICKET_PRICE = 1000;

    public LottoTickets buyTickets(final Money money) {
        LottoTickets lottoTickets = new LottoTickets();
        while (isCanBuyTicket(money)) {
            lottoTickets.add(new LottoTicket());
            money.spent(TICKET_PRICE);
        }
        return lottoTickets;
    }

    private boolean isCanBuyTicket(final Money money) {
        return money.getValue() >= TICKET_PRICE;
    }
}
