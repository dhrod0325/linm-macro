package lm.macro.auto.object.instance;

import java.util.ArrayList;
import java.util.List;

public class LmParty {
    private boolean isOwner = false;

    private LmPcInstance pcInstance;

    private List<LmPcInstance> partyList = new ArrayList<>();

    public void setPartyList(List<LmPcInstance> partyList) {
        this.partyList = partyList;
    }

    public List<LmPcInstance> getMyPartyList() {
        List<LmPcInstance> result = new ArrayList<>();

        for (LmPcInstance pcInstance : partyList) {
            if (!pcInstance.equals(this.pcInstance)) {
                result.add(pcInstance);
            }
        }

        return result;
    }

    public LmPcInstance getPcInstance() {
        return pcInstance;
    }

    public void setPcInstance(LmPcInstance pcInstance) {
        this.pcInstance = pcInstance;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }
}
