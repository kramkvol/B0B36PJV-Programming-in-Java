package cz.cvut.fel.pjv;

class NodeImpl implements Node {
    private final int value;
    private Node leftChild;
    private Node rightChild;

    public NodeImpl(int value, Node leftChild, Node rightChild) {
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public NodeImpl(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public Node getLeft() {
        return leftChild;
    }

    @Override
    public Node getRight() {
        return rightChild;
    }

    public void setLeft(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setRight(Node rightChild) {
        this.rightChild = rightChild;
    }
}
