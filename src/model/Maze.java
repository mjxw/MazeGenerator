package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class Maze {	
	
	private Node[][] G; 
	private int width;
	private int depth; 
	private boolean debug; 
	private Node entrance; 
	private Node exit;
	private char[][] x;  // displaying the maze using x 
	
	public Maze(int width, int depth, boolean debug) {
		this.width = width; 
		this.depth = depth;
		this.debug = debug; 
		G = new Node[depth][width];
		x = new char[ 2 * depth + 1][2 * width + 1];
		
		setArea(depth, width);
		buildMaze();
	}
	
	private void setArea(int depth, int width) {
		for (int i = 0; i < depth; i++) {
			for (int j = 0; j < width; j++) {
				G[i][j] = new Node(i,j);
			}
		}
		for ( int i = 0; i < x.length; i++) {
			for (int j = 0; j < x[0].length; j++) {
				if (i % 2 != 0 && j % 2 != 0) {
					x[i][j] = ' ';
					System.out.print("" + x[i][j]);
				} else {
					x[i][j] = 'o';
					System.out.print("" + x[i][j]);
				}
			}
			System.out.println();
		}
		entrance = G[0][0];
		entrance.setNorthWall(false);
		exit = G[depth - 1][width - 1];
		exit.setSouthWall(false);
	}
	
	private void buildMaze() {
		Stack<Node> stack = new Stack<>();
		stack.push(entrance);
		//entrance.setVisited(true);
		Node current = entrance;
		Node last;
		ArrayList<int[]> neighborLocation = new ArrayList<int[]>();
		while(!stack.isEmpty()) {
			current.setVisited(true);
			
			// Finds all the location of the neighbor nodes to the current
			neighborLocation = neighbors(current);
			System.out.println("Size: " + neighborLocation.size());
			
			// Gets a location of one of the neighbor node
			int[] temp = neighborLocation.get(0);
			System.out.println("New coord: " + temp[0] + " , " + temp[1]);
			
			// Sets last to the current node
			last = current;
			
			// Sets current to the new neighbor node
			current = G[temp[0]][temp[1]];
			
			// Checks to see if the current node has been visited
			if(!current.visit()) {
				stack.push(current);
				current.setVisited(true);
				int[] lastN = current.getPosition();
//				if(temp[0] == lastN[0] && temp[1] > lastN[1]) {
//					// R/L False
//					x[temp[0]][temp[1] + 1] = ' ';
//				} else if(temp[0] > lastN[0] && temp[1] == lastN[1]) {
//					// B/T False
//					x[temp[0]][temp[1]] = ' ';
//				} else if(temp[0] == lastN[0] && temp[1] < lastN[1]) {
//					x[temp[0]][temp[1] + 1] = ' ';
//				} else if(temp[0] < lastN[0] && temp[1] == lastN[1]){
//					x[temp[0]][temp[1] + 1] = ' ';
//				}
			}
			
			// set neighbor node as visited
			// add to stack
			// set wall to false between last node and current node
			// if the neighboring wall have all been visited pop it off the stack
			stack.pop();
		}
		
	}
	
	private ArrayList<int[]> neighbors(Node current) {
		ArrayList<int[]> location = new ArrayList<int[]>();
		int[] coordinates = current.getPosition();
		int[] temp = new int[2];
		int[] temp2 = new int[2];
		int[] temp3 = new int[2];
		int[] temp4 = new int[2];
		// Starting corner piece
		if(coordinates[0] == 0 && coordinates[1] == 0) {
			System.out.println("Top corner");
			temp[0] = coordinates[0] + 1;
			temp[1] = coordinates[1];
			System.out.println(" " + temp[0] + " , " + temp[1]);
			location.add(temp);
			temp2[0] = coordinates[0];
			temp2[1] = coordinates[1] + 1;
			System.out.println(" " + temp2[0] + " , " + temp2[1]);
			location.add(temp2);
		// Top Row	
		} else if(coordinates[0] == 0 && coordinates[1] == depth - 1) {
			System.out.println("Top");
			temp[0] = coordinates[0] + 1;
			temp[1] = coordinates[1];
			System.out.println(" " + temp[0] + " , " + temp[1]);
			location.add(temp);
			if(coordinates[1] + 1 <= depth - 1) {
				temp2[0] = coordinates[0];
				temp2[1] = coordinates[1] + 1;
				System.out.println(" " + temp2[0] + " , " + temp2[1]);
				location.add(temp2);
			}
			temp3[0] = coordinates[0];
			temp3[1] = coordinates[1] - 1;
			System.out.println(" " + temp3[0] + " , " + temp3[1]);
			location.add(temp3);
		}
		// Bottom row
		else if(coordinates[0] == depth - 1 && coordinates[1] == 0) {
			System.out.println("Bottom");
			temp[0] = coordinates[0];
			temp[1] = coordinates[1] + 1;
			System.out.println(" " + temp[0] + " , " + temp[1]);
			location.add(temp);
			if(coordinates[0] + 1 < depth - 1) {
				temp2[0] = coordinates[0] + 1;
				temp2[1] = coordinates[1];
				System.out.println(" " + temp2[0] + " , " + temp2[1]);
				location.add(temp2);
			}
			temp3[0] = coordinates[0] - 1;
			temp3[1] = coordinates[1];
			System.out.println(" " + temp3[0] + " , " + temp3[1]);
			location.add(temp3);
		}
		// Bottom corner piece
		else if(coordinates[0] == depth - 1 && coordinates[1] == depth - 1) {
			System.out.println("Bottom Corner");
			temp[0] = coordinates[0] - 1;
			temp[1] = coordinates[1];
			System.out.println(" " + temp[0] + " , " + temp[1]);
			location.add(temp);
			temp2[0] = coordinates[0];
			temp2[1] = coordinates[1] - 1;
			System.out.println(" " + temp2[0] + " , " + temp2[1]);
			location.add(temp2);
		// Non-border pieces	
		} else {
			temp[0] = coordinates[0] + 1;
			temp[1] = coordinates[1];
			System.out.println(" " + temp[0] + " , " + temp[1]);
			location.add(temp);
			if(coordinates[1] + 1 <= width - 1) {
				temp2[0] = coordinates[0];
				temp2[1] = coordinates[1] + 1;
				System.out.println(" " + temp2[0] + " , " + temp2[1]);
				location.add(temp2);
			} 
			if(coordinates[0] - 1 <= depth - 1) {
				temp3[0] = coordinates[0] - 1;
				temp3[1] = coordinates[1];
				System.out.println(" " + temp3[0] + " , " + temp3[1]);
				location.add(temp3);
			}
			if(coordinates[1] - 1 >= 0) {
				temp4[0] = coordinates[0];
				temp4[1] = coordinates[1] - 1;
				System.out.println(" " + temp4[0] + " , " + temp4[1]);
				location.add(temp4);
			}
		}
		Collections.shuffle(location);
		return location;
	}

	public void display() {
		for ( int i = 0; i < x.length; i++) {
			for (int j = 0; j < x[0].length; j++) {
				if (i % 2 != 0 && j % 2 != 0) {
					System.out.print("" + x[i][j]);
				} else {
					System.out.print("" + x[i][j]);
				}
			}
			System.out.println();
		}
	}
	

	
	class Node {
		private int position[];// position of node in maze

		private boolean walls[] = new boolean[] { true, true, true, true };

		private boolean visited = false;
		private boolean isPath = false;		

		public Node(int x, int y) {
			position = new int[] { x, y };
		}

		public void setPath(boolean path) {
			this.isPath = path;
		}

		public boolean getPath() {
			return isPath;
		}

		public void setNorthWall(boolean north) {
			walls[0] = north;
		}
		
		public void setSouthWall(boolean south) {
			walls[1] = south;
		}
		
		public void setEastWall(boolean east) {
			walls[2] = east;
		}
		
		public void setWestWall(boolean west) {
			walls[3] = west;
		}
		
		public boolean getNorthWall() {
			return walls[0];
		}

		public boolean getSouthWall() {
			return walls[1];
		}

		public boolean getEastWall() {
			return walls[2];
		}

		public boolean getWestWall() {
			return walls[3];
		}

		public void setVisited(boolean value) {
			visited = value;
		}
		
		public boolean visit() {
			return visited;
		}

		public boolean getVisited() {
			return visited;
		}

		public int[] getPosition() {
			return position;
		}
		

		public String toString() {
			String result = "";
			result += String.valueOf(walls[0]);
			for (int i = 1; i < walls.length; i++) {
				result += ", "; 
				result += String.valueOf(walls[i]);
			}
			
			return "position: (" + position[0] + "," + position[1]
					+ "), wall:[" + result + "]";
		}

		public void print() {
			System.out.println(this.toString());
		}
	}
	
}