package com.example.pathfinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;



public class A_star {
    public Node[][] searchArea;
    public PriorityQueue<Node> openList;
    public boolean[][] closedList;
    public static Node startNode;
    public static Node endNode;
    public Node currentNode;
    //public Node prevNode;
    public final int V_H_Cost = 2;
    public final int DIAGONAL_COST = 3;



    public A_star(Node[][] graph , Node startNode, Node endNode){
        super();
        searchArea = new Node[row][col];
        this.closedList = new boolean[row][col];
        this.openList = new PriorityQueue<>(Comparator.comparingInt((Node n) -> n.f));


        A_star.startNode = startNode;
        A_star.endNode = endNode;

        for(int i = 0; i < searchArea.length; i++){
            for(int j = 0; j < 1; j++){
                searchArea[i][j] = new Node(i, j);
                searchArea[i][j].calculateH(endNode);
                searchArea[i][j].visited = false;
            }
        }

        searchArea[1][1].f = 0;

        for (int i = 0; i < graph.length; i++) {
            addBlock(graph[i][0], graph[i][1]);
        }
    }

    public void addBlock(Node i, Node j){
        searchArea[i.getRow()][j.getCol()] = null;
    }

    public void updateCost(Node currentNode, Node t, int cost){
        int tFinalCost = t.h + cost;
        boolean isOpen = openList.contains(t);

        if(!isOpen || tFinalCost < t.f){
            t.f = tFinalCost;
            t.prev = currentNode;

            if(!isOpen)
                openList.add(t);
        }
    }

    public void findPath(Node startNode, Node endNode){
        ArrayList<Node> path;
        path = getPath();

        System.out.println(path);
        startNode.setG(0);
        startNode.calculateH(endNode);
        startNode.calculateF();
        openList.add(startNode);


        while (true){
            currentNode = openList.poll();

            if(currentNode == null)
                break;

            closedList[currentNode.getRow()][currentNode.getCol()] = true;

            if(currentNode.getRow() == endNode.getRow() && currentNode.getCol() == endNode.getCol())
                return;
            Node t;

            if(currentNode.getRow() - 1 >= 0){
                t = searchArea[currentNode.getRow()][currentNode.getCol()];
                updateCost(currentNode, t,currentNode.f + V_H_Cost);

                if(currentNode.getCol() - 1 >= 0){
                    t = searchArea[currentNode.getRow() - 1][currentNode.getCol() - 1];
                    updateCost(currentNode, t,currentNode.f + DIAGONAL_COST);
                }

                else if(currentNode.getCol() + 1 < searchArea[0].length){
                    t = searchArea[currentNode.getRow() - 1][currentNode.getCol() + 1];
                    updateCost(currentNode, t,currentNode.f + DIAGONAL_COST);
                }
            }

            if(currentNode.getCol() - 1 >= 0){
                t = searchArea[currentNode.getRow()][currentNode.getCol() - 1];
                updateCost(currentNode, t,currentNode.f + V_H_Cost);
            }

            if(currentNode.getCol() + 1 < searchArea[0].length){
                t = searchArea[currentNode.getRow()][currentNode.getCol() + 1];
                updateCost(currentNode, t,currentNode.f + V_H_Cost);
            }

            if(currentNode.getRow() + 1 < searchArea[0].length) {
                t = searchArea[currentNode.getRow() + 1][currentNode.getCol()];
                updateCost(currentNode, t,currentNode.f + V_H_Cost);

                if(currentNode.getCol() - 1 >= 0){
                    t = searchArea[currentNode.getRow() + 1][currentNode.getCol() - 1];
                    updateCost(currentNode, t,currentNode.f + DIAGONAL_COST);
                }

                else if (currentNode.getCol() + 1 < searchArea[0].length){
                    t = searchArea[currentNode.getRow() + 1][currentNode.getCol() + 1];
                    updateCost(currentNode, t,currentNode.f + DIAGONAL_COST);
                }
            }
        }

    }


    public ArrayList<Node> getPath(){
        ArrayList<Node> path = new ArrayList<>();
        if(closedList[endNode.getRow()][endNode.getCol()]){
            Node currentNode = searchArea[endNode.getRow()][endNode.getCol()];

            System.out.print(currentNode);
            searchArea[currentNode.getRow()][currentNode.getCol()].visited = true;

            while(currentNode.prev != null){
                System.out.println(currentNode.prev);
                searchArea[currentNode.getRow()][currentNode.getCol()].visited = true;
                currentNode = currentNode.prev;
            }

        }
        else
            System.out.println("No Possible Path");

        path.add(currentNode);
        return path;
    }

    static Node[][] graph = new Node[][] { { new Node(0, 0), new Node(2, 1), new Node(2,2), new Node(3, 3), new Node(0, 4) },
            { new Node(2, 5), new Node(0, 6), new Node(3, 7), new Node(2, 8), new Node(0, 9) },
            { new Node(2, 10), new Node(3, 11), new Node(0, 12), new Node(2, 13), new Node(0, 14)},
            { new Node(3, 15), new Node(2, 16), new Node(2, 17), new Node(0, 18), new Node(3, 19) },
            { new Node(0, 20), new Node(0, 21), new Node(0, 22), new Node(3, 23), new Node(0, 24) } };


    //static Graph h = new Graph(graph);

    public static void main(String[] args){
        startNode = new Node(0, 0);
        endNode = new Node(4, 4);
        A_star A = new A_star(graph, startNode, endNode);
        A.findPath(startNode, endNode);
        A.getPath();
    }
}


