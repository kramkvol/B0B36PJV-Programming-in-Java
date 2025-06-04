package cz.cvut.fel.pjv;
class TreeImpl implements Tree {
    private Node root;

    public TreeImpl() {
    }

    @Override
    public void setTree(int[] values) {
        root = constructTree(values, 0, values.length - 1);
    }

    // Recursive method to construct the tree
    private Node constructTree(int[] values, int start, int end) {
        if (start > end) {
            return null;
        }
        int middleIndex = (start + end + 1) / 2;
        Node leftChild = constructTree(values, start, middleIndex - 1);
        Node rightChild = constructTree(values, middleIndex + 1, end);

        return new NodeImpl(values[middleIndex], leftChild, rightChild);
    }


    @Override
    public Node getRoot() {
        return root;
    }

    // The depth parameter represents the level of the node in the tree
    @Override
    public String toString() {
        return toString(root, 0);
    }

    // generate indentation string based on the depth of the node in the tree
    private String getIndentation(int depth) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            str.append(" ");
        }
        return str.toString();
    }

    // Recursive method to generate string representation of the subtree rooted at the given node
    private String toString(Node node, int depth) {
        StringBuilder str = new StringBuilder();
        // If the node is not null, append its value to the string representation
        if (node != null) {
            str.append(getIndentation(depth)).append("- ").append(node.getValue()).append("\n");
            // Recursively generate string representations for left and right subtrees
            str.append(toString(node.getLeft(), depth + 1));
            str.append(toString(node.getRight(), depth + 1));
        }
        return str.toString();
    }
}
