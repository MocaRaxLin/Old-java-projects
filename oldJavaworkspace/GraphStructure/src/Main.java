import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws FileNotFoundException{
		Scanner file = new Scanner(new File("connected.txt"));
		for(int i = 0;i<3;i++){
			file.next();
		}
		int totalVector = Integer.parseInt(file.next());
		Graph graph = new Graph(totalVector);
		System.out.println(totalVector);
		while(file.hasNext()){
			String s = file.next();
			char s1 = s.charAt(1);
			char s2 = s.charAt(3);
			Vertex v1 = new Vertex(s1);
			Vertex v2 = new Vertex(s2);
			
			String c = file.next();
			char cArray[] = c.toCharArray();
			char ccArray[] = new char[cArray.length-2];
			for(int i = 1;i<cArray.length-1;i++){
				ccArray[i-1] = cArray[i];
			}
			int cost = Integer.parseInt(String.valueOf(ccArray));
			graph.add(v1, v2, cost);
		}
		file.close();
		graph.printData();
		graph.DFTraversal(graph, graph.headvertex);
		graph.miniumSpanningTree(graph);
	}
}
