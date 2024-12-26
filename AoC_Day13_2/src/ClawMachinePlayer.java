
public class ClawMachinePlayer extends Thread{

	private ClawMachine clawMachine;
	
	private long result;
	private boolean done;
	
	public ClawMachinePlayer (ClawMachine clawMachine) {
		this.clawMachine = clawMachine;
		this.result = 0l;
		this.done = false;
	}
	
	@Override
	public void run() {
		System.out.println(this.clawMachine);

		long targetX = this.clawMachine.getTarget().getX();
		long aButtonX = this.clawMachine.getButton('a').getMovement().getX();
		long bButtonX = this.clawMachine.getButton('b').getMovement().getX();

		long targetY = this.clawMachine.getTarget().getY();
		long aButtonY = this.clawMachine.getButton('a').getMovement().getY();
		long bButtonY = this.clawMachine.getButton('b').getMovement().getY();

		long aButtonCost = this.clawMachine.getButton('a').getCost();
		long bButtonCost = this.clawMachine.getButton('b').getCost();

		for (long aButtonValue = 0; aButtonValue <= targetX; aButtonValue += aButtonX) {
			long remainder = targetX - aButtonValue;
			if (remainder % bButtonX == 0) {
				long bCount = remainder / bButtonX;
				long aCount = aButtonValue / aButtonX;
				long permYValue = aCount * aButtonY + bCount * bButtonY;
				if (targetY == permYValue) {
					result = aCount * aButtonCost + bCount * bButtonCost;
					System.out.println(result);
					break;
				}
			}
		}
		
		this.done = true;
	}
	
	public long getResult() {
		return this.result;
	}
	
	public boolean isDone() {
		return this.done;
	}
	
	@Override
	public String toString() {
		return "%s -> %d".formatted(this.clawMachine.toString(), this.result);
	}

}
