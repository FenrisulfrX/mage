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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class DecreeOfPain extends CardImpl {

    public DecreeOfPain(UUID ownerId) {
        super(ownerId, 72, "Decree of Pain", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{6}{B}{B}");
        this.expansionSetCode = "C13";


        // Destroy all creatures. They can't be regenerated. Draw a card for each creature destroyed this way.
        this.getSpellAbility().addEffect(new DecreeOfPainEffect());
        // Cycling {3}{B}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{3}{B}{B}")));
        // When you cycle Decree of Pain, all creatures get -2/-2 until end of turn.
        Ability ability = new CycleTriggeredAbility(new BoostAllEffect(-2,-2, Duration.EndOfTurn));
        this.addAbility(ability);
    }

    public DecreeOfPain(final DecreeOfPain card) {
        super(card);
    }

    @Override
    public DecreeOfPain copy() {
        return new DecreeOfPain(this);
    }
}

class DecreeOfPainEffect extends OneShotEffect {

    public DecreeOfPainEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures. They can't be regenerated. Draw a card for each creature destroyed this way";
    }

    public DecreeOfPainEffect(final DecreeOfPainEffect effect) {
        super(effect);
    }

    @Override
    public DecreeOfPainEffect copy() {
        return new DecreeOfPainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int destroyedCreature = 0;
            for(Permanent creature: game.getState().getBattlefield().getActivePermanents(new FilterCreaturePermanent(), controller.getId(), game)) {
                if (creature.destroy(source.getSourceId(), game, true)) {
                    destroyedCreature++;
                }
            }
            if (destroyedCreature > 0) {
                controller.drawCards(destroyedCreature, game);
            }
            return true;
        }
        return false;
    }
}
