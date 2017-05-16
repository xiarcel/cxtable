package cxtable;

/*this is part of the Dice plugin...the dice plugin is standard in the current
xCommShell...it is an example of a global plugin... it is not a 1-to-1..but
rather a plugin that broadcasts to anyone who has a Dice plugin its
roll results...and posts the results sent to it from anyone who has a
Dice plugin running*/


public interface xDiceListener{

public int num();
public void roll(String mssg);
}
