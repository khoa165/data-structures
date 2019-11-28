package avl_tree;

import static org.junit.Assert.fail;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AvlTreeTest {
  AvlTree<String, String> tree1;
  AvlTree<Integer, String> tree2;

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    tree1 = createInstance();
    tree2 = createInstance2();
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {
    tree1 = null;
    tree2 = null;
  }

  protected AvlTree<String, String> createInstance() {
    return new AvlTree<String, String>();
  }

  protected AvlTree<Integer, String> createInstance2() {
    return new AvlTree<Integer, String>();
  }

  /**
   * Insert three values in sorted order and then check the root, left, and
   * right keys to see if re-balancing occurred.
   */
  @Test
  void testBALST_001_insert_sorted_order_simple() {
    try {
      tree2.insert(10, "10");
      if (!tree2.getKeyAtRoot().equals(10))
        fail("Avl insert at root does not work.");

      tree2.insert(20, "20");
      if (!tree2.getKeyOfRightChildOf(10).equals(20))
        fail("Avl insert to right child of root does not work.");

      tree2.insert(30, "30");
      Integer k = tree2.getKeyAtRoot();
      if (!k.equals(20))
        fail("Avl rotate does not work.");

      // If re-balancing is working, the tree should have 20 at the root and 10
      // as its left child and 30 as its right child.

      Assert.assertEquals(Integer.valueOf(20), tree2.getKeyAtRoot());
      Assert.assertEquals(Integer.valueOf(10), tree2.getKeyOfLeftChildOf(20));
      Assert.assertEquals(Integer.valueOf(30), tree2.getKeyOfRightChildOf(20));

      tree2.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 001: " + e.getMessage());
    }
  }

  /**
   * Insert three values in reverse sorted order and then check the root, left,
   * and right keys to see if re-balancing occurred in the other direction.
   */
  @Test
  void testBALST_002_insert_reversed_sorted_order_simple() {
    try {
      tree2.insert(999, "nine nine nine");
      if (!tree2.getKeyAtRoot().equals(999))
        fail("Avl insert at root does not work.");

      tree2.insert(88, "eight eight");
      if (!tree2.getKeyOfLeftChildOf(999).equals(88))
        fail("Avl insert to left child of root does not work.");

      tree2.insert(7, "seven");
      Integer k = tree2.getKeyAtRoot();
      if (!k.equals(88))
        fail("Avl rotate does not work.");

      // If re-balancing is working, the tree should have 88 at the root and 7
      // as its left child and 999 as its right child.

      Assert.assertEquals(Integer.valueOf(88), tree2.getKeyAtRoot());
      Assert.assertEquals(Integer.valueOf(7), tree2.getKeyOfLeftChildOf(88));
      Assert.assertEquals(Integer.valueOf(999),
          tree2.getKeyOfRightChildOf(88));

      tree2.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 002: " + e.getMessage());
    }
  }

  /**
   * Insert three values so that a right-left rotation is needed to fix the
   * balance.
   * 
   * Example: 10-30-20
   * 
   * Then check the root, left, and right keys to see if re-balancing occurred
   * in the other direction.
   */
  @Test
  void testBALST_003_insert_smallest_largest_middle_order_simple() {
    try {
      tree2.insert(123, "1-2-3");
      if (!tree2.getKeyAtRoot().equals(123))
        fail("Avl insert at root does not work.");

      tree2.insert(789, "7-8-9");
      if (!tree2.getKeyOfRightChildOf(123).equals(789))
        fail("Avl insert to right child of root does not work.");

      tree2.insert(456, "4-5-6");
      Integer k = tree2.getKeyAtRoot();
      if (!k.equals(456))
        fail("Avl rotate does not work.");

      // If re-balancing is working, the tree should have 456 at the root and
      // 123 as its left child and 789 as its right child.

      Assert.assertEquals(Integer.valueOf(456), tree2.getKeyAtRoot());
      Assert.assertEquals(Integer.valueOf(123),
          tree2.getKeyOfLeftChildOf(456));
      Assert.assertEquals(Integer.valueOf(789),
          tree2.getKeyOfRightChildOf(456));

      tree2.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 003: " + e.getMessage());
    }
  }

  /**
   * Insert three values so that a left-right rotation is needed to fix the
   * balance.
   * 
   * Example: 30-10-20
   * 
   * Then check the root, left, and right keys to see if re-balancing occurred
   * in the other direction.
   */
  @Test
  void testBALST_004_insert_largest_smallest_middle_order_simple() {
    try {
      tree2.insert(4444, "|4|4|4|4|");
      if (!tree2.getKeyAtRoot().equals(4444))
        fail("Avl insert at root does not work.");

      tree2.insert(1111, "|1|1|1|1|");
      if (!tree2.getKeyOfLeftChildOf(4444).equals(1111))
        fail("Avl insert to left child of root does not work.");

      tree2.insert(2222, "|2|2|2|2|");
      Integer k = tree2.getKeyAtRoot();
      if (!k.equals(2222))
        fail("Avl rotate does not work.");

      // If re-balancing is working, the tree should have 2222 at the root and
      // 1111 as its left child and 4444 as its right child.

      Assert.assertEquals(Integer.valueOf(2222), tree2.getKeyAtRoot());
      Assert.assertEquals(Integer.valueOf(1111),
          tree2.getKeyOfLeftChildOf(2222));
      Assert.assertEquals(Integer.valueOf(4444),
          tree2.getKeyOfRightChildOf(2222));

      tree2.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 004: " + e.getMessage());
    }
  }

  /**
   * Insert 1 key-value pair, check all methods.
   */
  @Test
  void testBALST_005_insert_one_check_everything() {
    try {
      tree2.insert(2000, "My birth year.");

      Assert.assertEquals(1, tree2.numKeys()); // Expect 1 after insert.
      // Expect node with key 2000 to be the root of the tree.
      Assert.assertEquals(Integer.valueOf(2000), tree2.getKeyAtRoot());
      // Expect left child of key 2000 to be null.
      Assert.assertEquals(null, tree2.getKeyOfLeftChildOf(2000));
      // Expect right child of key 2000 to be null.
      Assert.assertEquals(null, tree2.getKeyOfRightChildOf(2000));
      // Expect height of tree to be 1.
      Assert.assertEquals(1, tree2.getHeight());

      // Traversal
      // In-order traversal.
      List<Integer> inOrderList = tree2.getInOrderTraversal();
      // Expect list of keys to be size of 1.
      Assert.assertEquals(1, inOrderList.size());
      // Expect the only key in list is 2000.
      Assert.assertEquals(Integer.valueOf(2000), inOrderList.get(0));
      // Pre-order traversal.
      List<Integer> preOrderList = tree2.getPreOrderTraversal();
      // Expect list of keys to be size of 1.
      Assert.assertEquals(1, preOrderList.size());
      // Expect the only key in list is 2000.
      Assert.assertEquals(Integer.valueOf(2000), preOrderList.get(0));
      // Post-order traversal.
      List<Integer> postOrderList = tree2.getPostOrderTraversal();
      // Expect list of keys to be size of 1.
      Assert.assertEquals(1, postOrderList.size());
      // Expect the only key in list is 2000.
      Assert.assertEquals(Integer.valueOf(2000), postOrderList.get(0));
      // Level-order traversal.
      List<Integer> levelOrderList = tree2.getLevelOrderTraversal();
      // Expect list of keys to be size of 1.
      Assert.assertEquals(1, levelOrderList.size());
      // Expect the only key in list is 2000.
      Assert.assertEquals(Integer.valueOf(2000), levelOrderList.get(0));

      // Expect the value of key 2000 is "My birth year.".
      Assert.assertEquals("My birth year.", tree2.get(2000));
      // Expect key 2000 exists in the tree.
      Assert.assertEquals(true, tree2.contains(2000));
      // Expect key 1999 does not exist in the tree.
      Assert.assertEquals(false, tree2.contains(1999));

      // Expect true when removing key 2000.
      Assert.assertEquals(true, tree2.remove(2000));
      Assert.assertEquals(0, tree2.numKeys()); // Expect 0 after remove.

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 005: " + e.getMessage());
    }
  }

  /**
   * Insert 2 key-value pairs, check all methods.
   */
  @Test
  void testBALST_006_insert_two_check_everything() {
    try {
      tree2.insert(2000, "My birth year.");
      tree2.insert(16, "My birth day.");

      Assert.assertEquals(2, tree2.numKeys()); // Expect 2 after insert those.
      // Expect node with key 2000 to be the root of the tree.
      Assert.assertEquals(Integer.valueOf(2000), tree2.getKeyAtRoot());
      // Expect left child of key 2000 to be 16.
      Assert.assertEquals(Integer.valueOf(16),
          tree2.getKeyOfLeftChildOf(2000));
      // Expect right child of key 2000 to be null.
      Assert.assertEquals(null, tree2.getKeyOfRightChildOf(2000));
      // Expect left child of key 16 to be null.
      Assert.assertEquals(null, tree2.getKeyOfLeftChildOf(16));
      // Expect right child of key 16 to be null.
      Assert.assertEquals(null, tree2.getKeyOfRightChildOf(16));
      // Expect height of tree to be 2.
      Assert.assertEquals(2, tree2.getHeight());

      // Traversal
      // In-order traversal.
      List<Integer> inOrderList = tree2.getInOrderTraversal();
      // Expect list of keys to be size of 2.
      Assert.assertEquals(2, inOrderList.size());
      // Expect the key at index 0 in list is 16.
      Assert.assertEquals(Integer.valueOf(16), inOrderList.get(0));
      // Expect the key at index 1 in list is 2000.
      Assert.assertEquals(Integer.valueOf(2000), inOrderList.get(1));
      // Pre-order traversal.
      List<Integer> preOrderList = tree2.getPreOrderTraversal();
      // Expect list of keys to be size of 2.
      Assert.assertEquals(2, preOrderList.size());
      // Expect the key at index 0 in list is 2000.
      Assert.assertEquals(Integer.valueOf(2000), preOrderList.get(0));
      // Expect the key at index 1 in list is 16.
      Assert.assertEquals(Integer.valueOf(16), preOrderList.get(1));
      // Post-order traversal.
      List<Integer> postOrderList = tree2.getPostOrderTraversal();
      // Expect list of keys to be size of 2.
      Assert.assertEquals(2, postOrderList.size());
      // Expect the key at index 0 in list is 16.
      Assert.assertEquals(Integer.valueOf(16), postOrderList.get(0));
      // Expect the key at index 1 in list is 2000.
      Assert.assertEquals(Integer.valueOf(2000), postOrderList.get(1));
      // Level-order traversal.
      List<Integer> levelOrderList = tree2.getLevelOrderTraversal();
      // Expect list of keys to be size of 2.
      Assert.assertEquals(2, levelOrderList.size());
      // Expect the key at index 0 in list is 2000.
      Assert.assertEquals(Integer.valueOf(2000), levelOrderList.get(0));
      // Expect the key at index 1 in list is 16.
      Assert.assertEquals(Integer.valueOf(16), levelOrderList.get(1));

      // Expect the value of key 16 is "My birth day.".
      Assert.assertEquals("My birth day.", tree2.get(16));
      // Expect key 2000 and 16 exists in the tree.
      Assert.assertEquals(true, tree2.contains(2000));
      Assert.assertEquals(true, tree2.contains(16));
      // Expect key 15 does not exist in the tree.
      Assert.assertEquals(false, tree2.contains(15));

      // Expect true when removing key 2000.
      Assert.assertEquals(true, tree2.remove(2000));
      Assert.assertEquals(1, tree2.numKeys()); // Expect 1 after remove.
      // Expect node with key 16 to be the root of the tree.
      Assert.assertEquals(Integer.valueOf(16), tree2.getKeyAtRoot());
      // Expect left child of key 16 to be null.
      Assert.assertEquals(null, tree2.getKeyOfLeftChildOf(16));
      // Expect right child of key 16 to be null.
      Assert.assertEquals(null, tree2.getKeyOfRightChildOf(16));
      // Expect height of tree to be 1.
      Assert.assertEquals(1, tree2.getHeight());

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 006: " + e.getMessage());
    }
  }

  /**
   * Insert 9 key-value pairs then remove 1, check if the tree is structured
   * correctly before and after remove.
   */
  @Test
  void testBALST_007_insert_nine_remove_one() {
    try {
      tree2.insert(111, "111");
      tree2.insert(222, "222");
      tree2.insert(333, "333"); // Re-balancing expected.
      tree2.insert(444, "444");
      tree2.insert(555, "555"); // Re-balancing expected.
      tree2.insert(666, "666"); // Re-balancing expected.
      tree2.insert(777, "777"); // Re-balancing expected.
      tree2.insert(888, "888");
      tree2.insert(999, "999"); // Re-balancing expected.
      // Expected current tree:
      // ---------------------444---------------------
      // ---------222---------------------666---------
      // ---111---------333---------555---------888---
      // AAA---AAA---AAA---AAA---AAA---AAA---777---999
      List<Integer> list1 = tree2.getLevelOrderTraversal();
      Assert.assertEquals(9, list1.size());
      Assert.assertEquals(9, tree2.numKeys());
      String sequence1 = ""; // String that concatenate the numbers in list.
      for (int i = 0; i < list1.size(); ++i) {
        sequence1 += (list1.get(i) / 100); // Only get the first digit.
      }
      Assert.assertEquals("426135879", sequence1);

      tree2.remove(444); // Remove 444.
      // Before re-balancing:
      // ---------------------555---------------------
      // ---------222---------------------666---------
      // ---111---------333---------AAA---------888---
      // AAA---AAA---AAA---AAA---AAA---AAA---777---999
      // After re-balancing:
      // ---------------------555---------------------
      // ---------222---------------------888---------
      // ---111---------333---------666---------999---
      // AAA---AAA---AAA---AAA---AAA---777---AAA---AAA

      // Expect node with key 555 to be the root of the tree.
      Assert.assertEquals(Integer.valueOf(555), tree2.getKeyAtRoot());
      List<Integer> list2 = tree2.getLevelOrderTraversal();
      Assert.assertEquals(8, list2.size());
      Assert.assertEquals(8, tree2.numKeys());
      String sequence2 = ""; // String that concatenate the letters in list.
      for (int i = 0; i < list2.size(); ++i) {
        sequence2 += (list2.get(i) / 100); // Only get the first digit.
      }
      Assert.assertEquals("52813697", sequence2);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 007: " + e.getMessage());
    }
  }

  /**
   * Insert some key-value pairs, then check if all traversal methods functions
   * correctly.
   */
  @Test
  void testBALST_008_insert_string_no_rotation_traversal() {
    try {
      tree1.insert(":) M :)", "M with smiley faces.");
      tree1.insert(":) G :)", "G with smiley face.");
      tree1.insert(":) S :)", "S with smiley faces.");
      tree1.insert(":) W :)", "W with smiley face.");
      tree1.insert(":) P :)", "P with smiley faces.");
      tree1.insert(":) J :)", "J with smiley face.");
      tree1.insert(":) C :)", "C with smiley face.");
      tree1.insert(":) Q :)", "Q with smiley face.");
      // Expected current tree:
      // --------------M--------------
      // ------G---------------S------
      // --C-------J-------P-------W--
      // *---*---*---*---*---Q---*---*

      // Pre-order traversal.
      List<String> poList = tree1.getPreOrderTraversal();
      Assert.assertEquals(8, poList.size());
      Assert.assertEquals(8, tree1.numKeys());
      String poStr = ""; // String that concatenate the letters in list.
      for (int i = 0; i < poList.size(); ++i) {
        poStr += poList.get(i).charAt(3); // Only get the alphabetical letter.
      }
      Assert.assertEquals("MGCJSPQW", poStr);

      // In-order traversal.
      List<String> ioList = tree1.getInOrderTraversal();
      Assert.assertEquals(8, ioList.size());
      Assert.assertEquals(8, tree1.numKeys());
      String ioStr = ""; // String that concatenate the letters in list.
      for (int i = 0; i < ioList.size(); ++i) {
        ioStr += ioList.get(i).charAt(3); // Only get the alphabetical letter.
      }
      Assert.assertEquals("CGJMPQSW", ioStr);

      // Post-order traversal.
      List<String> ptoList = tree1.getPostOrderTraversal();
      Assert.assertEquals(8, ptoList.size());
      Assert.assertEquals(8, tree1.numKeys());
      String ptoStr = ""; // String that concatenate the letters in list.
      for (int i = 0; i < ptoList.size(); ++i) {
        ptoStr += ptoList.get(i).charAt(3); // Only get the alphabetical letter.
      }
      Assert.assertEquals("CJGQPWSM", ptoStr);

      // Level order traversal.
      List<String> loList = tree1.getLevelOrderTraversal();
      Assert.assertEquals(8, loList.size());
      Assert.assertEquals(8, tree1.numKeys());
      String loStr = ""; // String that concatenate the letters in list.
      for (int i = 0; i < loList.size(); ++i) {
        loStr += loList.get(i).charAt(3); // Only get the alphabetical letter.
      }
      Assert.assertEquals("MGSCJPWQ", loStr);

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 008: " + e.getMessage());
    }
  }

  /**
   * Insert some key-value pairs, then check if all traversal methods function
   * correctly.
   */
  @Test
  void testBALST_009_insert_integer_no_rotation_traversal() {
    try {
      tree2.insert(500, "005");
      tree2.insert(250, "052");
      tree2.insert(750, "057");
      tree2.insert(125, "521");
      tree2.insert(375, "573");
      tree2.insert(625, "526");
      tree2.insert(875, "578");
      // Expected current tree:
      // ---------500---------
      // ---250---------750---
      // 125---375---625---875
      tree2.insert(100, "001");
      tree2.insert(400, "004");
      tree2.insert(700, "007");
      tree2.insert(800, "008");
      // Expected current tree:
      // ---------------------500---------------------
      // ---------250---------------------750---------
      // ---125---------375---------625---------875---
      // 100---AAA---AAA---400---AAA---700---800---AAA

      // Pre-order traversal.
      List<Integer> poList = tree2.getPreOrderTraversal();
      Assert.assertEquals(11, poList.size());
      Assert.assertEquals(11, tree2.numKeys());
      String poStr = ""; // String that concatenate the numbers in list.
      for (int i = 0; i < poList.size(); ++i) {
        poStr += poList.get(i) + " > "; // Separated by >.
      }
      Assert.assertEquals(poStr,
          "500 > 250 > 125 > 100 > 375 > 400 > 750 > 625 > 700 > 875 > 800 > ");

      // In-order traversal.
      List<Integer> ioList = tree2.getInOrderTraversal();
      Assert.assertEquals(11, ioList.size());
      Assert.assertEquals(11, tree2.numKeys());
      String ioStr = ""; // String that concatenate the numbers in list.
      for (int i = 0; i < ioList.size(); ++i) {
        ioStr += ioList.get(i) + " > "; // Separated by >.
      }
      Assert.assertEquals(ioStr,
          "100 > 125 > 250 > 375 > 400 > 500 > 625 > 700 > 750 > 800 > 875 > ");

      // Post-order traversal.
      List<Integer> ptoList = tree2.getPostOrderTraversal();
      Assert.assertEquals(11, ptoList.size());
      Assert.assertEquals(11, tree2.numKeys());
      String ptoStr = ""; // String that concatenate the numbers in list.
      for (int i = 0; i < ptoList.size(); ++i) {
        ptoStr += ptoList.get(i) + " > "; // Separated by >.
      }
      Assert.assertEquals(ptoStr,
          "100 > 125 > 400 > 375 > 250 > 700 > 625 > 800 > 875 > 750 > 500 > ");

      // Level order traversal.
      List<Integer> loList = tree2.getLevelOrderTraversal();
      Assert.assertEquals(11, loList.size());
      Assert.assertEquals(11, tree2.numKeys());
      String loStr = ""; // String that concatenate the numbers in list.
      for (int i = 0; i < loList.size(); ++i) {
        loStr += loList.get(i) + " > "; // Separated by >.
      }
      Assert.assertEquals(loStr,
          "500 > 250 > 750 > 125 > 375 > 625 > 875 > 100 > 400 > 700 > 800 > ");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 009: " + e.getMessage());
    }
  }

  /**
   * Insert some key-value pairs, then call get() and contains(), expect the
   * tree does not change anything.
   */
  @Test
  void testBALST_010_get_contains_not_affect_the_tree() {
    try {
      tree1.insert(":) M :)", "M with smiley faces.");
      tree1.insert(":) G :)", "G with smiley face.");
      tree1.insert(":) S :)", "S with smiley faces.");
      tree1.insert(":) W :)", "W with smiley face.");
      tree1.insert(":) P :)", "P with smiley faces.");
      tree1.insert(":) J :)", "J with smiley face.");
      tree1.insert(":) C :)", "C with smiley face.");
      tree1.insert(":) Q :)", "Q with smiley face.");
      // Expected current tree:
      // --------------M--------------
      // ------G---------------S------
      // --C-------J-------P-------W--
      // *---*---*---*---*---Q---*---*

      String value = tree1.get(":) J :)");
      Assert.assertEquals("J with smiley face.", value);
      Assert.assertEquals(true, tree1.contains(":) P :)"));
      Assert.assertEquals(false, tree1.contains(":) Z :)"));
      Assert.assertEquals(false, tree1.contains(""));
      List<String> list = tree1.getLevelOrderTraversal();
      String sequence = ""; // String that concatenate the letters in list.
      for (int i = 0; i < list.size(); ++i) {
        sequence += list.get(i).charAt(3); // Only get the alphabetical letter.
      }

      Assert.assertEquals("MGSCJPWQ", sequence);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 010: " + e.getMessage());
    }
  }

  /**
   * Insert multiple keys and ensure height is still computed correctly after
   * multiple re-balancing.
   */
  @Test
  void testBALST_011_heights_with_multiple_rebalancing() {
    try {
      tree2.insert(111, "111");
      Assert.assertEquals(1, tree2.getHeight());
      tree2.insert(222, "222");
      Assert.assertEquals(2, tree2.getHeight());
      tree2.insert(333, "333"); // Re-balancing expected.
      Assert.assertEquals(2, tree2.getHeight());
      tree2.insert(444, "444");
      Assert.assertEquals(3, tree2.getHeight());
      tree2.insert(555, "555"); // Re-balancing expected.
      Assert.assertEquals(3, tree2.getHeight());
      tree2.insert(666, "666"); // Re-balancing expected.
      Assert.assertEquals(3, tree2.getHeight());
      tree2.insert(777, "777"); // Re-balancing expected.
      Assert.assertEquals(3, tree2.getHeight());
      tree2.insert(888, "888");
      Assert.assertEquals(4, tree2.getHeight());
      tree2.insert(999, "999"); // Re-balancing expected.
      Assert.assertEquals(4, tree2.getHeight());
      // Expected current tree:
      // ---------------------444---------------------
      // ---------222---------------------666---------
      // ---111---------333---------555---------888---
      // AAA---AAA---AAA---AAA---AAA---AAA---777---999

      tree2.remove(333);
      Assert.assertEquals(4, tree2.getHeight());
      tree2.remove(555); // Re-balancing expected.
      Assert.assertEquals(4, tree2.getHeight());
      tree2.remove(999); // Re-balancing expected.
      Assert.assertEquals(3, tree2.getHeight());
      // Expected current tree:
      // ---------------------444---------------------
      // ---------222---------------------777---------
      // ---111---------AAA---------666---------888---
      tree2.remove(444);
      Assert.assertEquals(3, tree2.getHeight());
      tree2.remove(666);
      Assert.assertEquals(3, tree2.getHeight());
      tree2.remove(777); // Re-balancing expected.
      Assert.assertEquals(2, tree2.getHeight());
      // Expected current tree:
      // ---------------------222---------------------
      // ---------111---------------------888---------
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 011: " + e.getMessage());
    }
  }

  /**
   * Insert multiple keys and ensure numKeys() is still computed correctly after
   * multiple re-balancing.
   */
  @Test
  void testBALST_012_num_keys_with_multiple_rebalancing() {
    try {
      tree2.insert(111, "111");
      Assert.assertEquals(1, tree2.numKeys());
      tree2.insert(222, "222");
      Assert.assertEquals(2, tree2.numKeys());
      tree2.insert(333, "333"); // Re-balancing expected.
      Assert.assertEquals(3, tree2.numKeys());
      tree2.insert(444, "444");
      Assert.assertEquals(4, tree2.numKeys());
      tree2.insert(555, "555"); // Re-balancing expected.
      Assert.assertEquals(5, tree2.numKeys());
      tree2.insert(666, "666"); // Re-balancing expected.
      Assert.assertEquals(6, tree2.numKeys());
      tree2.insert(777, "777"); // Re-balancing expected.
      Assert.assertEquals(7, tree2.numKeys());
      tree2.insert(888, "888");
      Assert.assertEquals(8, tree2.numKeys());
      tree2.insert(999, "999"); // Re-balancing expected.
      Assert.assertEquals(9, tree2.numKeys());
      // Expected current tree:
      // ---------------------444---------------------
      // ---------222---------------------666---------
      // ---111---------333---------555---------888---
      // AAA---AAA---AAA---AAA---AAA---AAA---777---999

      tree2.remove(333);
      Assert.assertEquals(8, tree2.numKeys());
      tree2.remove(555); // Re-balancing expected.
      Assert.assertEquals(7, tree2.numKeys());
      tree2.remove(999); // Re-balancing expected.
      Assert.assertEquals(6, tree2.numKeys());
      // Expected current tree:
      // ---------------------444---------------------
      // ---------222---------------------777---------
      // ---111---------AAA---------666---------888---
      tree2.remove(444);
      Assert.assertEquals(5, tree2.numKeys());
      tree2.remove(666);
      Assert.assertEquals(4, tree2.numKeys());
      tree2.remove(777); // Re-balancing expected.
      Assert.assertEquals(3, tree2.numKeys());
      // Expected current tree:
      // ---------------------222---------------------
      // ---------111---------------------888---------
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 012: " + e.getMessage());
    }
  }

  /**
   * Check exceptions handling and edge cases of all methods.
   */
  @Test
  void testBALST_013_check_exception_handling_and_edge_cases() {
    try {
      Assert.assertEquals(null, tree1.getKeyAtRoot());
      Assert.assertEquals(null, tree2.getKeyAtRoot());
      Assert.assertEquals(0, tree1.getHeight());
      Assert.assertEquals(0, tree2.getHeight());
      Assert.assertEquals(0, tree1.numKeys());
      Assert.assertEquals(0, tree2.numKeys());

      Assert.assertEquals(0, tree1.getPreOrderTraversal().size());
      Assert.assertEquals(0, tree1.getPostOrderTraversal().size());
      Assert.assertEquals(0, tree1.getInOrderTraversal().size());
      Assert.assertEquals(0, tree1.getLevelOrderTraversal().size());
      Assert.assertEquals(0, tree2.getPreOrderTraversal().size());
      Assert.assertEquals(0, tree2.getPostOrderTraversal().size());
      Assert.assertEquals(0, tree2.getInOrderTraversal().size());
      Assert.assertEquals(0, tree2.getLevelOrderTraversal().size());

      Assert.assertEquals(false, tree1.contains("boring"));
      Assert.assertEquals(false, tree2.contains(1206));

      tree1.insert("a key", "a value");
      tree2.insert(123456789, "987654321");
      Assert.assertEquals(null, tree1.getKeyOfLeftChildOf("a key"));
      Assert.assertEquals(null, tree2.getKeyOfLeftChildOf(123456789));
      Assert.assertEquals(null, tree1.getKeyOfRightChildOf("a key"));
      Assert.assertEquals(null, tree2.getKeyOfRightChildOf(123456789));

      Assert.assertEquals(1, tree1.getPreOrderTraversal().size());
      Assert.assertEquals(1, tree1.getPostOrderTraversal().size());
      Assert.assertEquals(1, tree1.getInOrderTraversal().size());
      Assert.assertEquals(1, tree1.getLevelOrderTraversal().size());
      Assert.assertEquals(1, tree2.getPreOrderTraversal().size());
      Assert.assertEquals(1, tree2.getPostOrderTraversal().size());
      Assert.assertEquals(1, tree2.getInOrderTraversal().size());
      Assert.assertEquals(1, tree2.getLevelOrderTraversal().size());

      Assert.assertEquals(true, tree1.contains("a key"));
      Assert.assertEquals(true, tree2.contains(123456789));
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 013: " + e.getMessage());
    }
    try {
      tree1.getKeyOfLeftChildOf(null);
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree2.getKeyOfLeftChildOf(null);
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree1.getKeyOfLeftChildOf("ABC");
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree2.getKeyOfLeftChildOf(123);
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree1.getKeyOfRightChildOf(null);
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree2.getKeyOfRightChildOf(null);
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree1.getKeyOfRightChildOf("ABC");
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree2.getKeyOfRightChildOf(123);
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree1.get(null);
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree2.get(null);
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree1.get("XYZ");
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree2.get(789);
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree1.contains(null);
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
    try {
      tree2.contains(null);
      fail("Not supposed to reach here. Should have thrown exception.");
    } catch (Exception e) {
    }
  }

}
