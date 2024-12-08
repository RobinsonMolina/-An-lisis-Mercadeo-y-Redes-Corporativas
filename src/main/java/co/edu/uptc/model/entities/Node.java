package co.edu.uptc.model.entities;

public class Node {

    private String id;
    private String name;
    private String type;

    public Node(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Node{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", type='" + type + '\'' + '}';
    }
}
