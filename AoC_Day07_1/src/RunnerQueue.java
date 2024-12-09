import java.util.ArrayList;
import java.util.List;

public class RunnerQueue extends Thread{
	private int maxRunners;
	
	private List<EquationRunner> runnerList;
	private List<EquationRunner> queue;
	private List<EquationRunner> doneList;
	
	private boolean done;
	
	public RunnerQueue (int maxRunners, List<EquationRunner> runnerList) {
		this.done = false;
		this.maxRunners = maxRunners;
		this.runnerList = runnerList;
		this.queue = new ArrayList<>();
		this.doneList = new ArrayList<>();
	}
	
	@Override
	public void run() {
		while(this.runnerList.size() > 0 || this.queue.size() > 0) {
			while(this.queue.size() <= this.maxRunners) {
				EquationRunner runner = this.runnerList.remove(0);
				this.queue.add(runner);
				runner.start();
				List<EquationRunner> completed = this.queue.stream().filter(r -> r.isDone()).toList();
				this.queue.removeAll(completed);
				this.doneList.addAll(completed);
				
				System.out.println(this.queue.size());
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		this.done = true;
	}
	
	public List<EquationRunner> getDoneList(){
		return this.doneList;
	}
	
	public boolean isDone() {
		return this.done;
	}
	
}
