package lm.macro.auto.object.manager;

import lm.macro.auto.object.LmSlot;
import lm.macro.auto.object.instance.LmParty;
import lm.macro.auto.object.instance.LmPcInstance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LmPartyManager {
    private List<LmParty> partyList = new ArrayList<>();

    private List<LmParty> nonOwnerPartyList() {
        List<LmParty> result = new ArrayList<>();

        for (LmParty party : partyList) {
            if (!party.isOwner()) {
                result.add(party);
            }
        }

        return result;
    }

    public LmParty partyOwner() {
        for (LmParty party : partyList) {
            if (party.isOwner()) {
                return party;
            }
        }

        return null;
    }

    public void addParty(LmPcInstance instance) {
        LmParty p = new LmParty();
        p.setPcInstance(instance);
        if (partyList.size() == 0) {
            p.setOwner(true);
        }
        partyList.add(p);
    }

    public void initialize() throws Exception {
        LmParty owner = partyOwner();
        List<LmParty> nonOwnerPartyList = nonOwnerPartyList();
        for (LmParty instance : nonOwnerPartyList) {
            if (owner.getPcInstance().getHp() < 70) {
                instance.getPcInstance().useSlot(0, LmSlot.SlotType.SLOT2);
            }
        }
    }
}
