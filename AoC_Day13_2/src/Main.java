import java.util.ArrayList;
import java.util.List;

import org.jksoft.utils.inputreader.InputReader;

public class Main {

	private static long newValueBase = 10000000000000l;
//	private static long newValueBase = 0l;
	
	private static int MAX_PROCESSING = 14;

	public static void main(String[] args) {

		List<String> lines = InputReader.readInput();
//		List<String> lines = InputReader.readInput();
		// lines.forEach(System.out::println);

		List<ClawMachine> machines = new ArrayList<>();

		for (int i = 0; i <= lines.size() - 3; i += 4) {
			String values = lines.get(i).split(": ")[1];
			String[] splitValues = values.split(", ");

			long x = Long.parseLong(splitValues[0].split("\\+")[1]);
			long y = Long.parseLong(splitValues[1].split("\\+")[1]);

			Button a = new Button('A', 3, new GridVector(x, y));

			values = lines.get(i + 1).split(": ")[1];
			splitValues = values.split(", ");

			x = Long.parseLong(splitValues[0].split("\\+")[1]);
			y = Long.parseLong(splitValues[1].split("\\+")[1]);

			Button b = new Button('B', 1, new GridVector(x, y));

			values = lines.get(i + 2).split(": ")[1];
			splitValues = values.split(", ");

			x = newValueBase + Long.parseLong(splitValues[0].split("=")[1]);
			y = newValueBase + Long.parseLong(splitValues[1].split("=")[1]);

			GridVector target = new GridVector(x, y);

			ClawMachine machine = new ClawMachine(target);
			machine.addButton('a', a);
			machine.addButton('b', b);
			machines.add(machine);
		}

		ClawMachineController controller = new ClawMachineController(MAX_PROCESSING, machines);
		controller.start();
		
		while(!controller.isDone()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		long result = controller.getResult();
		
		System.err.println(result);
	}
}