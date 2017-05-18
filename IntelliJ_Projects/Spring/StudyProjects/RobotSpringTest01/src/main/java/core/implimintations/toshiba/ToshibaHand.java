package core.implimintations.toshiba;

import core.intarfaces.Hand;

public class ToshibaHand implements Hand{
    @Override
    public void catchSomething() {
        System.out.println("Catching something from TOSHIBA");
    }
}
