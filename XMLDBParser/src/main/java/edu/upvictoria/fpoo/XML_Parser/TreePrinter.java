package edu.upvictoria.fpoo.XML_Parser;

/**
 * TreePrinter class
 */
public class TreePrinter {
    /**
     * Print the tree structure
     * 
     * @param node  the node to print
     * @param level the level of the node
     */
    public static void printTree(TagNode node, int level) {
        for (int i = 0; i < level; i++)
            System.out.print("  ");
        System.out.println(node.getName());

        if (node.getContent() != null) {
            for (int i = 0; i < level; i++)
                System.out.print("  ");
            System.out.println("Content: " + node.getContent());
        }

        if (!node.getAttributes().isEmpty()) {
            for (Attribute attribute : node.getAttributes()) {
                for (int i = 0; i < level; i++)
                    System.out.print("  ");
                System.out.println(attribute.getName() + " = " + attribute.getValue());
            }
        }
        for (TagNode child : node.getChildren())
            printTree(child, level + 1);
    }
}
