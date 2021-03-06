/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.watchers.common;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Quercitron
 * @author LevelX2
 * @author jeffwadsworth
 */
public class CardsAmountDrawnThisTurnWatcher extends Watcher {

    public final static String BASIC_KEY = "CardsAmountDrawnThisTurnWatcher";

    private final Map<UUID, Integer> amountOfCardsDrawnThisTurn = new HashMap<>();

    public CardsAmountDrawnThisTurnWatcher() {
        super(BASIC_KEY, WatcherScope.GAME);
    }

    public CardsAmountDrawnThisTurnWatcher(final CardsAmountDrawnThisTurnWatcher watcher) {
        super(watcher);
        amountOfCardsDrawnThisTurn.putAll(watcher.amountOfCardsDrawnThisTurn);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            if (amountOfCardsDrawnThisTurn.containsKey(event.getPlayerId())) {
                amountOfCardsDrawnThisTurn.put(event.getPlayerId(), amountOfCardsDrawnThisTurn.get(event.getPlayerId()) + 1);
            } else {
                amountOfCardsDrawnThisTurn.put(event.getPlayerId(), 1);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        amountOfCardsDrawnThisTurn.clear();
    }

    public boolean opponentDrewXOrMoreCards(UUID playerId, int x, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            for (Map.Entry<UUID, Integer> entry : amountOfCardsDrawnThisTurn.entrySet()) {
                if (game.isOpponent(player, entry.getKey())) {
                    if (entry.getValue() >= x) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getAmountCardsDrawn(UUID playerId) {
        Integer amount = amountOfCardsDrawnThisTurn.get(playerId);
        if (amount != null) {
            return amount;
        }
        return 0;
    }

    @Override
    public CardsAmountDrawnThisTurnWatcher copy() {
        return new CardsAmountDrawnThisTurnWatcher(this);
    }
}
