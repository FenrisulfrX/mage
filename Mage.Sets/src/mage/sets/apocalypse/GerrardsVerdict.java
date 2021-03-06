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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class GerrardsVerdict extends CardImpl {

    public GerrardsVerdict(UUID ownerId) {
        super(ownerId, 102, "Gerrard's Verdict", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{W}{B}");
        this.expansionSetCode = "APC";


        // Target player discards two cards. You gain 3 life for each land card discarded this way.
        this.getSpellAbility().addEffect(new GerrardsVerdictEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public GerrardsVerdict(final GerrardsVerdict card) {
        super(card);
    }

    @Override
    public GerrardsVerdict copy() {
        return new GerrardsVerdict(this);
    }
}

class GerrardsVerdictEffect extends OneShotEffect {

    public GerrardsVerdictEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player discards two cards. You gain 3 life for each land card discarded this way";
    }

    public GerrardsVerdictEffect(final GerrardsVerdictEffect effect) {
        super(effect);
    }

    @Override
    public GerrardsVerdictEffect copy() {
        return new GerrardsVerdictEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            controller.gainLife(targetPlayer.discard(2, false, source, game).count(new FilterLandCard(), game) * 3, game);
            return true;
        }
        return false;
    }
}
