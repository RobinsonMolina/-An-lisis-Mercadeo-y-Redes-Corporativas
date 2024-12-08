package co.edu.uptc.model.entities;

public class Edge {
    private Node source; 
    private Node target;
    private double weight; 
    private boolean isDeleted;


    public Edge(Node source, Node target, double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        this.isDeleted = isDeleted;
    }


    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public double getWeight() {
        return weight;
    }
    
    

    public void setWeight(double weight) {
		this.weight = weight;
	}
    
	public boolean isDeleted() {
		return isDeleted;
	}


	public void setDeleted(boolean isDelete) {
		this.isDeleted = isDelete;
	}


	@Override
    public String toString() {
        return "Edge{source=" + source.getName() + ", target=" + target.getName() +
               ", weight=" + weight + "}";
    }
}

