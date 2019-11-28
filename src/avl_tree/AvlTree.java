package avl_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to implement a BalanceSearchTree.
 * 
 * @param <K> is the generic type of key.
 * @param <V> is the generic type of value.
 * 
 * @author Khoa Thien Le (Harry).
 */
public class AvlTree<K extends Comparable<K>, V> implements AvlTreeADT<K, V> {
  /**
   * Class to represent node on a BalanceSearchTree. A node stores a key-value
   * pair as well as reference to its left child node and its right child node.
   * A node also has balanceFactor and height.
   * 
   * @param <K> is the generic type of key.
   * @param <V> is the generic type of value.
   * 
   * @author Debra Deppeler.
   */
  class Node<K, V> {
    K key; // Key of node.
    V value; // Value of node.
    Node<K, V> left; // Reference to its left child node.
    Node<K, V> right; // Reference to its right child node.
    int balanceFactor; // Balance factor of node, difference between
                       // left-subtree's height and right-subtree's height.
    int height; // Height of this node.

    /**
     * BSTNode constructor that takes key-value pair as well as left and right
     * child node reference.
     * 
     * @param key        key of node.
     * @param value      value of node.
     * @param leftChild  reference to its left child node.
     * @param rightChild reference to its right child node.
     */
    Node(K key, V value, Node<K, V> leftChild, Node<K, V> rightChild) {
      this.key = key;
      this.value = value;
      this.left = leftChild;
      this.right = rightChild;
      this.height = 1; // Default height of new node is 1.
      this.balanceFactor = 0; // Zero as left and right subtrees's height are 0.
    }

    /**
     * BSTNode constructor that takes only a key-value pair.
     * 
     * @param key   key of node.
     * @param value value of node.
     */
    Node(K key, V value) {
      this(key, value, null, null);
    }
  }

  private Node<K, V> root; // Root of the tree.
  private int numKeys; // Number of key-value pairs in BALST data structure.

  /**
   * Constructor of this balanced search tree data structure. Initialize root as
   * null and numKeys as 0.
   */
  public AvlTree() {
    this.root = null;
    this.numKeys = 0;
  }

  /**
   * Private helper method to check if the BALST is empty.
   * 
   * @return true if root is null and there is no keys, false otherwise.
   */
  private boolean isEmpty() {
    // Empty when root is null and there is no keys.
    return this.numKeys == 0 && this.root == null;
  }

  /**
   * Returns the key that is in the root node of this BST. If root is null,
   * returns null.
   * 
   * @return key found at root node, or null.
   */
  @Override
  public K getKeyAtRoot() {
    if (this.isEmpty())
      return null;
    else
      return this.root.key;
  }

  /**
   * Tries to find a node with a key that matches the specified key. If a
   * matching node is found, it returns the returns the key that is in the left
   * child. If the left child of the found node is null, returns null.
   * 
   * @param key A key to search for.
   * @return The key that is in the left child of the found key.
   * 
   * @throws IllegalNullKeyException if key argument is null.
   * @throws KeyNotFoundException    if key is not found in this BST.
   */
  @Override
  public K getKeyOfLeftChildOf(K key)
      throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) // Throw IllegalNullKeyException when null key.
      throw new IllegalNullKeyException();
    if (getNodeHelper(key, this.root) == null) // Key not found.
      throw new KeyNotFoundException(); // Throw KeyNotFoundException.
    else if (getNodeHelper(key, this.root).left == null)
      return null; // Key found has no left child.
    else // Key found and it has left child.
      return getNodeHelper(key, this.root).left.key;
  }

  /**
   * Tries to find a node with a key that matches the specified key. If a
   * matching node is found, it returns the returns the key that is in the right
   * child. If the right child of the found node is null, returns null.
   * 
   * @param key A key to search for.
   * @return The key that is in the right child of the found key.
   * 
   * @throws IllegalNullKeyException if key is null.
   * @throws KeyNotFoundException    if key is not found in this BST.
   */
  @Override
  public K getKeyOfRightChildOf(K key)
      throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) // Throw IllegalNullKeyException when null key.
      throw new IllegalNullKeyException();
    if (getNodeHelper(key, this.root) == null) // Key not found.
      throw new KeyNotFoundException(); // Throw KeyNotFoundException.
    else if (getNodeHelper(key, this.root).right == null)
      return null; // Key found has no right child.
    else // Key found and it has right child.
      return getNodeHelper(key, this.root).right.key;
  }

  /**
   * Returns the height of this BST. H is defined as the number of levels in the
   * tree.
   * 
   * If root is null, return 0. If root is a leaf, return 1 Else return 1 +
   * max(height(root.left), height(root.right)).
   * 
   * Examples: A BST with no keys, has a height of zero (0). A BST with one key,
   * has a height of one (1). A BST with two keys, has a height of two (2). A
   * BST with three keys, can be balanced with a height of two(2) or it may be
   * linear with a height of three (3) ... and so on for tree with other heights
   * 
   * @return the number of levels that contain keys in this BINARY SEARCH TREE
   */
  @Override
  public int getHeight() {
    return getHeight(this.root);
  }

  /**
   * Get the height of a specific node.
   * 
   * @param current current node to calculate height.
   * @return height of a specific node.
   */
  private int getHeight(Node<K, V> current) {
    if (current == null)
      return 0; // Node passed in is null.
    if (current.left == null && current.right == null)
      return 1; // Both children are null, height is 1.
    if (current.left == null) // Left child is null, right child is not null.
      return 1 + current.right.height; // Height is 1 + right child's size.
    if (current.right == null) // Left child is not null, right child is null.
      return 1 + current.left.height; // Height is 1 + left child's size.
    // Else, it's 1 + max of left child's size and right child's size.
    return 1 + Math.max(current.left.height, current.right.height);
  }

  /**
   * Get the balance factor of a specific node.
   * 
   * @param current current node to calculate height.
   * @return balance factor of a specific node.
   */
  private int getBalanceFactor(Node<K, V> current) {
    if (current == null)
      return 0;
    // Balance factor is difference of left and right sub-trees's height.
    return getHeight(current.left) - getHeight(current.right);
  }

  /**
   * Returns the keys of the data structure in sorted order. In the case of
   * binary search trees, the visit order is: L V R.
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in-order.
   */
  @Override
  public List<K> getInOrderTraversal() {
    return getInOrderHelper(this.root); // Call recursive helper method.
  }

  /**
   * Recursive method to traverse tree in-order.
   * 
   * @param current the current node.
   * @return the list of keys in-order.
   */
  private List<K> getInOrderHelper(Node<K, V> current) {
    // Create ArrayList to store keys.
    List<K> keys = new ArrayList<K>();
    if (current == null) // Base case: empty tree.
      return keys;
    // Recursive case: add left, add current, add right.
    keys.addAll(getInOrderHelper(current.left)); // Add keys left sub-tree.
    keys.add(current.key); // Add current node's key.
    keys.addAll(getInOrderHelper(current.right)); // Add keys right sub-tree.
    return keys;
  }

  /**
   * Returns the keys of the data structure in pre-order traversal order. In the
   * case of binary search trees, the order is: V L R.
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in pre-order.
   */
  @Override
  public List<K> getPreOrderTraversal() {
    return getPreOrderHelper(this.root); // Call recursive helper method.
  }

  /**
   * Recursive method to traverse tree pre-order.
   * 
   * @param current the current node.
   * @return the list of keys pre-order.
   */
  private List<K> getPreOrderHelper(Node<K, V> current) {
    // Create ArrayList to store keys.
    List<K> keys = new ArrayList<K>();
    if (current == null) // Base case: empty tree.
      return keys;
    // Recursive case: add current, add left, add right.
    keys.add(current.key); // Add current node's key.
    keys.addAll(getPreOrderHelper(current.left)); // Add keys left sub-tree.
    keys.addAll(getPreOrderHelper(current.right)); // Add keys right sub-tree.
    return keys;
  }


  /**
   * Returns the keys of the data structure in post-order traversal order. In
   * the case of binary search trees, the order is: L R V.
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in post-order.
   */
  @Override
  public List<K> getPostOrderTraversal() {
    return getPostOrderHelper(this.root); // Call recursive helper method.
  }

  /**
   * Recursive method to traverse tree post-order.
   * 
   * @param current the current node.
   * @return the list of keys post-order.
   */
  private List<K> getPostOrderHelper(Node<K, V> current) {
    // Create ArrayList to store keys.
    List<K> keys = new ArrayList<K>();
    if (current == null) // Base case: empty tree.
      return keys;
    // Recursive case: add left, add right, add current.
    keys.addAll(getPostOrderHelper(current.left)); // Add keys left sub-tree.
    keys.addAll(getPostOrderHelper(current.right)); // Add keys right sub-tree.
    keys.add(current.key); // Add current node's key.
    return keys;
  }

  /**
   * Returns the keys of the data structure in level-order traversal order.
   * 
   * The root is first in the list, then the keys found in the next level down,
   * and so on.
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in level-order.
   */
  @Override
  public List<K> getLevelOrderTraversal() {
    // Create ArrayList to store keys.
    List<K> keys = new ArrayList<K>();
    if (isEmpty())
      return keys;
    for (int i = 1; i <= this.root.height; ++i) { // Loop through each level.
      keys.addAll(getLevelOrderHelper(this.root, i)); // Add keys on that level.
    }
    return keys;
  }

  /**
   * Recursive method to traverse tree level-order.
   * 
   * @param current the current node.
   * @param level   level of tree.
   * @return the list of keys in-order.
   */
  private List<K> getLevelOrderHelper(Node<K, V> current, int level) {
    // Create ArrayList to store keys for this level.
    List<K> keys = new ArrayList<K>();
    if (current == null) // Base case: empty tree.
      return keys;
    if (level == 1) // Add current node's key if level reaches 1.
      keys.add(current.key);
    else if (level > 1) { // Recursive case: add children's key of current node.
      keys.addAll(getLevelOrderHelper(current.left, level - 1));
      keys.addAll(getLevelOrderHelper(current.right, level - 1));
    }
    return keys;
  }

  /**
   * Add the key-value pair to the data structure and increase the number of
   * keys. If key is null, throw IllegalNullKeyException. If key is already in
   * data structure, throw DuplicateKeyException. Do not increase the number of
   * keys in the structure, if key-value pair is not added.
   * 
   * @param key   A key to insert.
   * @param value A value to insert.
   */
  @Override
  public void insert(K key, V value)
      throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (contains(key))
      throw new DuplicateKeyException();
    Node<K, V> newNode = new Node<K, V>(key, value); // Construct a new node.

    // Call insert helper method.
    this.root = insertHelper(newNode, this.root);
    this.numKeys++; // Increment number of keys after inserting.
  }

  /**
   * Insert helper method.
   * 
   * @param newNode new node to be inserted.
   * @param current root of subtree where newNode will be inserted into.
   * @return root of the tree after new node has been inserted.
   */
  private Node<K, V> insertHelper(Node<K, V> newNode, Node<K, V> current) {
    if (current == null) { // Check to prevent null pointer exception.
      return newNode;
    }
    int compare = newNode.key.compareTo(current.key); // Compare keys.
    if (compare < 0) { // Key added is smaller than current key.
      // Recursively traverse left to add key.
      current.left = insertHelper(newNode, current.left);
      int balance = getBalanceFactor(current); // Get balance of current factor.
      current.balanceFactor = balance; // Re-assign to current's balance factor.
      // Compare key added to key of left child of current.
      int compareLeft = newNode.key.compareTo(current.left.key);
      // Left left case.
      if (balance > 1 && compareLeft < 0)
        return rightRotate(current);
      // Left right case.
      if (balance > 1 && compareLeft > 0) {
        current.left = leftRotate(current.left); // First rotate left.
        return rightRotate(current); // Then rotate right.
      }
    } else if (compare > 0) { // Key added is greater than current key.
      // Recursively traverse right to add key.
      current.right = insertHelper(newNode, current.right);
      int balance = getBalanceFactor(current); // Get balance of current factor.
      current.balanceFactor = balance; // Re-assign to current's balance factor.
      // Compare key added to key of right child of current.
      int compareRight = newNode.key.compareTo(current.right.key);
      // Right right case.
      if (balance < -1 && compareRight > 0)
        return leftRotate(current);
      // Right left case.
      if (balance < -1 && compareRight < 0) {
        current.right = rightRotate(current.right); // First rotate right.
        return leftRotate(current); // Then rotate left.
      }
    }

    // Update height of current node.
    current.height = getHeight(current);

    return current;
  }

  /**
   * If key is found, remove the key-value pair from the data structure and
   * decrease number keys. If key is not found, do not decrease the number of
   * keys in the data structure. If key is null, throw IllegalNullKeyException.
   * If key is not found, throw KeyNotFoundException().
   * 
   * @param key A key to remove.
   * @return true always, indicating node removed successfully.
   */
  @Override
  public boolean remove(K key)
      throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (!contains(key))
      throw new KeyNotFoundException();

    // Call remove helper method.
    this.root = removeHelper(key, this.root);
    this.numKeys--; // Decrement number of keys after inserting.
    return true;
  }

  /**
   * Remove helper method.
   * 
   * @param target  key of node to be removed.
   * @param current pointer of current node.
   * @return root of the tree after node successfully removed.
   */
  private Node<K, V> removeHelper(K target, Node<K, V> current) {
    if (current == null)
      return current;

    int compare = target.compareTo(current.key); // Compare keys.
    if (compare < 0) // Recursively traverse to the left sub-tree.
      current.left = removeHelper(target, current.left);
    else if (compare > 0) // Recursively traverse to the left sub-tree.
      current.right = removeHelper(target, current.right);
    else { // Found node.
      if (current.left == null) // Case left child is null.
        current = current.right;
      else if (current.right == null) // Case right child is null.
        current = current.left;
      else { // Current node to be removed has both childs.
        // Temporarily store current (to be removed) so that we can still have
        // access to its left and right child.
        Node<K, V> temp = current;
        current = findMinNode(current.right); // Find in-order successor.
        // Remove the in-order successor from the right sub-tree of current.
        current.right = removeHelper(current.key, temp.right);
        current.left = temp.left; // Reconnect the left child.
      }
    }
    if (current == null)
      return current;

    // Update height of current node.
    current.height = getHeight(current);

    int balance = getBalanceFactor(current); // Get balance of current factor.
    current.balanceFactor = balance; // Re-assign to current's balance factor.

    // Left left case.
    if (balance > 1 && getBalanceFactor(current.left) >= 0)
      return rightRotate(current); // Rotate right.

    // Left right case.
    if (balance > 1 && getBalanceFactor(current.left) < 0) {
      current.left = leftRotate(current.left); // Rotate left.
      return rightRotate(current); // Then rotate right.
    }

    // Right right case.
    if (balance < -1 && getBalanceFactor(current.right) <= 0)
      return leftRotate(current); // Rotate left.

    // Right left case.
    if (balance < -1 && getBalanceFactor(current.right) > 0) {
      current.right = rightRotate(current.right); // Rotate right.
      return leftRotate(current); // Then rotate left.
    }

    return current;
  }

  /**
   * Private helper method to find the node with the smallest key.
   * 
   * @param current root of the tree/sub-tree to find min node.
   * @return the node with smallest key.
   */
  private Node<K, V> findMinNode(Node<K, V> current) {
    if (current == null || current.left == null)
      return current; // Return null or current if current's left child is null.
    return findMinNode(current.left);
  }

  /**
   * Returns the value associated with the specified key.
   *
   * Does not remove key or decrease number of keys. If key is null, throw
   * IllegalNullKeyException. If key is not found, throw KeyNotFoundException().
   * 
   * @param key A key to get the value associated with it.
   * @return the value associated with the give key if found.
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (getNodeHelper(key, this.root) == null)
      throw new KeyNotFoundException();
    else // Call get node helper to retrieve the node with that key.
      return getNodeHelper(key, this.root).value;
  }

  /**
   * Get node helper method.
   * 
   * @param key     key to be looked for.
   * @param current root of sub-tree.
   * @return node that contains key given if found, otherwise null.
   */
  private Node<K, V> getNodeHelper(K key, Node<K, V> current) {
    while (current != null) { // Only if current is not null.
      int compare = key.compareTo(current.key);
      if (compare < 0) // Traverse to the left sub-tree.
        current = current.left;
      else if (compare > 0) // Traverse to the right sub-tree.
        current = current.right;
      else // Node found.
        return current;
    }
    return current;

  }

  /**
   * Returns true if the key is in the data structure. If key is null, throw
   * IllegalNullKeyException. Returns false if key is not null and is not
   * present.
   * 
   * @param key A key to check if it exists in data structure.
   * @return true if key is found, false otherwise.
   */
  @Override
  public boolean contains(K key) throws IllegalNullKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (getNodeHelper(key, this.root) == null) // Node not found.
      return false;
    else
      return true;
  }

  /**
   * Returns the number of key-value pairs in the data structure.
   * 
   * @return the number of key-value pairs in the data structure.
   */
  @Override
  public int numKeys() {
    return this.numKeys;
  }

  /**
   * Print the tree.
   *
   * For our testing purposes: all keys that we insert in the tree will have a
   * string length of exactly 2 characters. example: numbers 10-99, or strings
   * aa - zz, or AA to ZZ
   *
   * This makes it easier for you to not worry about spacing issues.
   *
   * You can display in any of a variety of ways, but we should see a tree that
   * we can identify left and right children of each node
   *
   * For example:
   * 
   * | |-------50 |-------40 | |-------35 30 |-------20 | |-------10
   * 
   * Look from bottom to top. In-order traversal of above tree
   * (10,20,30,35,40,50)
   * 
   * Or, you can display a tree of this kind.
   * 
   * 30 /\ / \ 20 40 / /\ / / \ 10 35 50
   * 
   * Or, you can come up with your own orientation pattern, like this.
   * 
   * 10 20 30 35 40 50
   * 
   * The connecting lines are not required if we can interpret your tree.
   * 
   */
  @Override
  public void print() {
    System.out.println("##################################################"
        + "###################################");
    System.out.println("Tree structure:");
    System.out.println("--------------------------------------------------"
        + "-----------------------------------");
    List<K> keys = new ArrayList<K>(); // Create ArrayList to store keys.
    for (int i = 1; i <= this.root.height; ++i) { // Loop through each level.
      keys.addAll(printHelper(this.root, i)); // Add keys on that level.
    }
    int num = 1; // Number of nodes on a level.
    int index = 0; // Index of the list.
    // Number of spaces before each level.
    int spaceBegin = (int) (Math.round(Math.pow(2, this.root.height - 1)) - 1);
    // Number of spaces between nodes on each level.
    int spaceBetween = (int) (Math.round(Math.pow(2, this.root.height)));
    for (int i = 0; i < this.root.height; i++) { // Loop number of levels.
      printSpace(spaceBegin); // Print space to align middle.
      for (int j = 0; j < num; j++) { // Loop number of nodes on this level.
        if (i < keys.size() && keys.get(index) != null) { // Print valid key.
          System.out.print(keys.get(index));
        } else { // Print placeholder.
          System.out.print("X");
        }
        printSpace(spaceBetween); // Print space between nodes.
        index++; // Increment index.
      }
      spaceBegin /= 2; // As tree moves down, align left decreases.
      spaceBetween = spaceBetween / 2; // And space between also decreases.
      num *= 2; // Double number of nodes as tree moves down 1 level.
      System.out.println(); // New line after finishing a level.
    }
    System.out.println("--------------------------------------------------"
        + "-----------------------------------");
  }

  /**
   * Print spaces (tabs) needed to format the tree to look nice.
   * 
   * @param numSpaces number of tabs needed printing.
   */
  private void printSpace(int numSpaces) {
    for (int i = 0; i < numSpaces; i++) {
      System.out.print("\t");
    }
  }

  /**
   * Recursive method to traverse tree level-order. Similar to level order
   * traversal, except add null to list if current node is null;
   * 
   * @param current the current node.
   * @param level   level of tree.
   * @return the list of keys in-order.
   */
  private List<K> printHelper(Node<K, V> current, int level) {
    List<K> keys = new ArrayList<K>();
    if (current == null) { // Base case: empty tree.
      K nullKey = null;
      keys.add(nullKey);
      return keys;
    }
    if (level == 1) // Add current node's key if level reaches 1.
      keys.add(current.key);
    else if (level > 1) { // Recursive case: add children's key of current node.
      keys.addAll(printHelper(current.left, level - 1));
      keys.addAll(printHelper(current.right, level - 1));
    }
    return keys;
  }

  /**
   * Rotate the tree right, return new root.
   * 
   * @param root the root that has balance factor of 2 or bigger that needs to
   *             be rotated.
   * @return the new root of this sub-tree after re-balancing.
   */
  private Node<K, V> rightRotate(Node<K, V> root) {
    // New root is current root's left child for right rotation.
    Node<K, V> newRoot = root.left;
    Node<K, V> newRootRight = newRoot.right; // Store the right child of new
                                             // root.
    newRoot.right = root; // Make current root as right child of new root.
    // Set right child of new root as left child of current root.
    root.left = newRootRight;

    // Update height of current root.
    root.height = getHeight(root);
    // Update height of new root.
    newRoot.height = getHeight(newRoot);

    // Update balance factors.
    root.balanceFactor = getBalanceFactor(root);
    newRoot.balanceFactor = getBalanceFactor(newRoot);

    return newRoot; // Return reference of new root.
  }

  /**
   * Rotate the tree left, return new root.
   * 
   * @param root the root that has balance factor of -2 or smaller that needs to
   *             be rotated.
   * @return the new root of this sub-tree after re-balancing.
   */
  private Node<K, V> leftRotate(Node<K, V> root) {
    // New root is current root's right child for left rotation.
    Node<K, V> newRoot = root.right;
    // Store the left child of new root.
    Node<K, V> newRootLeft = newRoot.left;
    newRoot.left = root; // Make current root as left child of new root.
    // Set left child of new root as right child of current root.
    root.right = newRootLeft;

    // Update height of current root.
    root.height = getHeight(root);
    // Update height of new root.
    newRoot.height = getHeight(newRoot);

    // Update balance factors.
    root.balanceFactor = getBalanceFactor(root);
    newRoot.balanceFactor = getBalanceFactor(newRoot);

    return newRoot; // Return reference of new root.
  }
}
