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
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author fireshoes
 */
public class SaprazzanLegate extends CardImpl {

    private static final FilterPermanent filterMountain = new FilterPermanent();
    private static final FilterPermanent filterIsland = new FilterPermanent();

    static {
        filterMountain.add(new SubtypePredicate(("Mountain")));
        filterIsland.add(new SubtypePredicate(("Island")));
    }

    public SaprazzanLegate(UUID ownerId) {
        super(ownerId, 100, "Saprazzan Legate", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "MMQ";
        this.subtype.add("Merfolk");
        this.subtype.add("Soldier");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If an opponent controls a Mountain and you control an Island, you may cast Saprazzan Legate without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls a Mountain and you control an Island",
                new OpponentControlsPermanentCondition(filterMountain),
                new PermanentsOnTheBattlefieldCondition(filterIsland));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));
    }

    public SaprazzanLegate(final SaprazzanLegate card) {
        super(card);
    }

    @Override
    public SaprazzanLegate copy() {
        return new SaprazzanLegate(this);
    }
}
