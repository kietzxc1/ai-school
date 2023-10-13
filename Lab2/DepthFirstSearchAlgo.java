package Lab2;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearchAlgo implements  ISearchAlgo{

    @Override
    public Node execute(Node root, String goal) {
        Stack<Node> frontier = new Stack<>();
        List<Node> expandList = new ArrayList<>();
        frontier.push(root);
        while(!frontier.isEmpty()){
            Node current = frontier.pop(); //get the element have been push
            if(current.getLabel().equals(goal)){
                return current;
            }
            else {
                expandList.add(current);
                List<Node> childList = new ArrayList<>(); //store the child of the current node
                childList = current.getChildrenNodes(); //set the element
                childList.sort(new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o2.getLabel().compareTo(o1.getLabel()); // do stack thêm vào theo thứ a->z, lấy ra z->a
                    }
                });
                for (Node child:childList) {
                    if(!frontier.contains(child) && !expandList.contains(child) ){ //kiểm tra node con này đã mở rộng và chọn hay chưa
                        frontier.push(child);
                        child.setParent(current);
                    //    child.setPathCost(current.getPathCost() + current.getEdgeWeigth(child));
                    }
                }

            }
        }
        return null;
    }

    @Override
    public Node execute(Node root, String start, String goal) {
        Stack<Node> frontier = new Stack<>();
        List<Node> expandList = new ArrayList<>();
        frontier.push(root);
        boolean startF = false;
        while (!frontier.isEmpty()){
            Node current = frontier.pop(); //get first element
            if(current.getLabel().equals(start)){
                startF =true;
                frontier.clear();
                expandList.clear();
            }
            if(startF && current.getLabel().equals(goal)){
                return current;
            }
            else {
                expandList.add(current);
                List<Node> childList = current.getChildrenNodes();
                childList.sort(new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o2.getLabel().compareTo(o1.getLabel()); // do stack thêm vào theo thứ a->z, lấy ra z->a
                    }
                });
                for (Node child :childList) {
                    if(!frontier.contains(child) && !expandList.contains(child)){
                        frontier.push(child);
                        if(startF ==true){
                            child.setParent(current);
                    //        child.setPathCost(current.getPathCost() + current.getEdgeWeigth(child));
                        }
                    }
                }
            }
        }
        return null;
    }
}