import java.util.ArrayList;
import java.util.List;

public class ClawMachineController extends Thread{

	private List<ClawMachinePlayer> queue;
	private List<ClawMachinePlayer> processingList;
	private List<ClawMachinePlayer> doneList;

	private int maxProcessing;
	private boolean done;
	private long result;

	public ClawMachineController(int maxProcessing, List<ClawMachine> machines) {
		this.maxProcessing = maxProcessing;
		this.queue = new ArrayList<>();
		this.processingList = new ArrayList<>();
		this.doneList = new ArrayList<>();
		
		for (ClawMachine clawMachine : machines) {
			ClawMachinePlayer player = new ClawMachinePlayer(clawMachine);
			this.queue.add(player);
		}
	}

	@Override
	public void run() {
		while(this.queue.size() > 0 || this.processingList.size() > 0) {
			
			List<ClawMachinePlayer> freshlyDone = this.processingList.stream().filter(player -> player.isDone()).toList();
			this.processingList.removeAll(freshlyDone);
			this.doneList.addAll(freshlyDone);
			
			if(this.processingList.size() < this.maxProcessing && this.queue.size() > 0) {
				ClawMachinePlayer player = this.queue.get(0);
				this.queue.remove(player);
				this.processingList.add(player);
				player.start();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.result = this.doneList.stream().mapToLong(ClawMachinePlayer::getResult).sum();
		this.done = true;
	}
	
	public long getResult() {
		return this.result;
	}
	
	public boolean isDone() {
		return this.done;
	}

}
