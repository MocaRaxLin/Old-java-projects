import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class main {
	public static void main(String[] args) throws IOException {
		Scanner file = new Scanner(new File("input.txt"));
		Stack<Integer> nnstack = new Stack<Integer>();
		Queue<LinkedList<Integer>> nlognQueue = new LinkedList<LinkedList<Integer>>();
		while (file.hasNext()) {
			int t = file.nextInt();
			nnstack.push(t);
			LinkedList<Integer> list = new LinkedList<Integer>();
			list.add(t);
			nlognQueue.offer(list);
		}
		file.close();

		// n^2 sorting
		long nnStart = System.nanoTime();
		for (int i = 0; i < nnstack.size(); i++) {
			for (int j = i + 1; j < nnstack.size(); j++) {
				if (nnstack.get(i) > nnstack.get(j)) {
					int x = nnstack.get(i);
					nnstack.set(i, nnstack.get(j));
					nnstack.set(j, x);
				}
			}
			System.out.println(i+"/"+nnstack.size());
		}
		long nnEnd = System.nanoTime();
		long nncost = nnEnd - nnStart;

		// output n^2 algorithm file
		FileWriter nnOutput = new FileWriter("n2out.txt");
		for (int i = 0; i < nnstack.size(); i++) {
			nnOutput.write(nnstack.get(i) + " ");
		}
		nnOutput.write("\nAlgorithm: Bubble sort");
		nnOutput.write("\nCost time:" + TimeUnit.NANOSECONDS.toMillis(nncost)
				+ "(ms)");
		nnOutput.close();

		
		// nlogn sorting
		long nlognStart = System.nanoTime();
		while (nlognQueue.size() > 1) {
			LinkedList<Integer> a = nlognQueue.poll();
			LinkedList<Integer> b = nlognQueue.poll();
			LinkedList<Integer> s = merge(a, b);
			nlognQueue.offer(s);
			System.out.println("nlogn©|¥¼§¹¦¨"+nlognQueue.size());
		}
		long nlognEnd = System.nanoTime();
		long nlognCost = nlognEnd - nlognStart;
		
		// output nlogn algorithm file
		FileWriter nlognOutput = new FileWriter("nlognout.txt");
		LinkedList<Integer> output = nlognQueue.poll();
		for (int i = 0; i < output.size(); i++) {
			nlognOutput.write(output.get(i)+" ");
		}
		nlognOutput.write("\nAlgorithm: Merge sort");
		nlognOutput.write("\nCost time:"
				+ TimeUnit.NANOSECONDS.toMillis(nlognCost) + "(ms)");
		nlognOutput.close();
	}

	private static LinkedList<Integer> merge(LinkedList<Integer> a,
			LinkedList<Integer> b) {
		LinkedList<Integer> s = new LinkedList<Integer>();
		while (!a.isEmpty() && !b.isEmpty()) {
			if (a.peekFirst() < b.peekFirst()) {
				s.add(a.pollFirst());
			} else {
				s.add(b.pollFirst());
			}
		}
		while (!a.isEmpty()) {
			s.add(a.pollFirst());
		}
		while (!b.isEmpty()) {
			s.add(b.pollFirst());
		}
		return s;
	}
}
