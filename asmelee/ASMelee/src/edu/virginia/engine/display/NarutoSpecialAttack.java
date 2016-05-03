package edu.virginia.engine.display;

public class NarutoSpecialAttack extends RangedAttack{

	public NarutoSpecialAttack(Character c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void destroy(DisplayObject o)
	{
		super.destroy(o);
		this.myCharacter.specialCount--;
		if (this.myCharacter.specialCount < 3){
			this.myCharacter.stopSpecial = false;
		}
	}
}
