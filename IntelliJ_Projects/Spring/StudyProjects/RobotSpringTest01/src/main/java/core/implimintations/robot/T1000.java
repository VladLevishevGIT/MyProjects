package core.implimintations.robot;

import core.intarfaces.Hand;
import core.intarfaces.Head;
import core.intarfaces.Leg;
import core.intarfaces.Robot;

public class T1000 implements Robot{

    public Hand hand;
    public Head head;
    public Leg leg;

    public T1000(){

    }

    public T1000(Head head, Hand hand, Leg leg){
        this.head = head;
        this.hand = hand;
        this.leg = leg;
    }

    @Override
    public void action() {
        hand.catchSomething();
        head.thinkAboutSomething();
        leg.goToSomePlace();
    }

    @Override
    public void dance() {
        System.out.println("The ROBOT is dancing...");
    }
}
