package core.implimintations.sony;

import core.intarfaces.Hand;

public class SonyHand implements Hand {
    @Override
    public void catchSomething() {
        System.out.println("Catching something for SONY...");
    }
}
