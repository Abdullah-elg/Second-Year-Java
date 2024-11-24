import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
public class Maze1 {
    private Graph maze;
    private int scale;
    private int width;
    private int height;
    private int coins;
    private GraphNode start;
    private GraphNode end;

    public Maze1(String inputFile) throws MazeException{
        try {
            Scanner input = new Scanner(new File(inputFile));
            int count = 0;
            int mainLoopCount = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                count++;
                if (count == 1) {
                    scale = Integer.parseInt(line);
                }
                else if (count == 2) {
                    width = Integer.parseInt(line);
                }
                else if (count == 3) {
                    height = Integer.parseInt(line);
                    maze = new Graph(width * height);
                }
                else if (count == 4) {
                    coins = Integer.parseInt(line);
                } else {
                    try {
                        if (count % 2 == 1) {
                            int valueCount = (mainLoopCount * width);
                            mainLoopCount++;
                            for (int i = 0; i < line.length(); i++) {
                                if (i % 2 == 0) {
                                    if (line.charAt(i) == 's') {
                                        start = maze.getNode(valueCount);
                                    }
                                    if (line.charAt(i) == 'x') {
                                        end = maze.getNode(valueCount);
                                    }
                                    valueCount += 1;
                                }
                                if (i % 2 == 1) {
                                    if (line.charAt(i) == 'w') {
                                        // Do Nothing
                                    } else if (line.charAt(i) == 'c') {
                                        maze.insertEdge(maze.getNode(valueCount - 1), maze.getNode(valueCount), 0, "corridor");
                                    } else {
                                        maze.insertEdge(maze.getNode(valueCount - 1), maze.getNode(valueCount), Character.getNumericValue(line.charAt(i)), "door");
                                    }
                                }
                            }
                        } else if (count % 2 == 0) {
                            int valueCount = 0;
                            for (int i = 0; i < line.length(); i++) {
                                if (i % 2 == 0) {
                                    int lower = valueCount + ((mainLoopCount-1) * width);
                                    int upper = valueCount +  (mainLoopCount * width);
                                    valueCount += 1;
                                    if (line.charAt(i) == 'w') {
                                        //Do Nothing
                                    } else if (line.charAt(i) == 'c') {
                                        maze.insertEdge(maze.getNode(lower), maze.getNode(upper), 0, "corridor");
                                    } else {
                                        maze.insertEdge(maze.getNode(lower), maze.getNode(upper), Character.getNumericValue(line.charAt(i)), "door");
                                    }
                                }
                            }
                        }
                    } catch (GraphException e) {
                        throw new MazeException("Error creating maze");
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new MazeException("File not found");
        }
    }
    public Graph getGraph() throws MazeException {
        if (maze == null) {
            throw new MazeException("The Maze is Blank");
        }
        return maze;
    }

    public Iterator solve() {
        ArrayList<GraphNode> solution = solver(start, coins, start);
        if (solution == null) {
            return null;
        }
        return solution.iterator();
    }

    private ArrayList<GraphNode> solver(GraphNode node, int remainingCoins, GraphNode parent) {
        try {
            if (node.getName() == end.getName()) {
                ArrayList<GraphNode> output = new ArrayList<GraphNode>();
                output.add(node);
                return output;
            }
            Iterator edges = maze.incidentEdges(node);
            while (edges.hasNext()) {
                int tempCoins = remainingCoins;
                GraphEdge edge = (GraphEdge) edges.next();
                tempCoins -= edge.getType();
                if (tempCoins >= 0) {
                if (edge.firstEndpoint().getName() == node.getName() && edge.secondEndpoint().getName() != parent.getName()) {
                    edge.firstEndpoint().mark(true);
                    if (!edge.secondEndpoint().isMarked()) {
                        ArrayList<GraphNode> output = solver(edge.secondEndpoint(), tempCoins, node);
                        if (output != null) {
                            output.add(0, node);
                            return output;
                        }
                        edge.firstEndpoint().mark(false);
                    }
                    } else if (edge.secondEndpoint().getName() == node.getName() && edge.firstEndpoint().getName() != parent.getName()) {
                        edge.secondEndpoint().mark(true);
                        if (!edge.firstEndpoint().isMarked()) {
                            ArrayList<GraphNode> output = solver(edge.firstEndpoint(), tempCoins, node);
                            if (output != null) {
                                output.add(0, node);
                                return output;
                            }
                            edge.secondEndpoint().mark(false);
                        }
                    }
                }
            }
            return null;
        } catch (GraphException e) {
            return null;
        }
    }
}