package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;

    public class Node {
        private K key;
        private V value;
        private Node lchild, rchild;

        public Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
        }
    }

    public int compareK(K k1, K k2) {
        if (k1 == null || k2 == null) {
            return -999;
        }
        return k1.compareTo(k2);
    }

    @Override
    public void clear() {
        if(this.root != null) {
            this.root = null;
            this.size = 0;
        }
    }

    private boolean nodeContainsKey(Node node, K key) {
        if (node.key == null) {
            return false;
        }
        int index = compareK(key, node.key);
        if(index == -999) {
            return false;
        }
        if (index < 0) {
            return nodeContainsKey(node.rchild, key);
        } else if (index > 0) {
            return nodeContainsKey(node.lchild, key);
        } else {
            return true;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return nodeContainsKey(root, key);
    }

    public V nodeGet(Node node, K key) {
        if (node.key == null) {
            return null;
        }
        int index = compareK(key, node.key);
        if (index == -999) {
            return null;
        }
        if (index < 0) {
            return nodeGet(node.lchild, key);
        } else if (index > 0) {
            return nodeGet(node.rchild, key);
        } else {
            return node.value;
        }
    }

    @Override
    public V get(K key) {
        return nodeGet(root, key);
    }

    @Override
    public int size() {
        return this.size;
    }

    private Node findParent(Node node, K key) {
        int index = compareK(node.key, key);
        int lIndex = compareK(node.lchild.key, key);
        int rIndex = compareK(node.rchild.key, key);
        if (index < 0) {
            if (rIndex == -999) {
                return node;
            } else {
                return findParent(node.rchild, key);
            }
        } else {
            if (lIndex == -999) {
                return node;
            } else{
                return findParent(node.lchild, key);
            }
        }
    }

    @Override
    public void put(K key, V value) {
        if (root == null) {
            root.key = key;
            root.value = value;
            root.lchild = null;
            root.rchild = null;
            size = 1;
        } else {
            Node node = findParent(root, key);
            Node newNode = null;
            if (compareK(node.key, key) >= 0) {
                node.lchild = newNode;
            } else {
                node.rchild = newNode;
            }
            newNode.key = key;
            newNode.value = value;
            newNode.lchild = newNode.rchild = null;
            size++;
        }
    }

    public void printInOrder(Node node) {
        if (node == null) {
            return;
        }
        printInOrder(node.lchild);
        System.out.println(node.key);
        printInOrder(node.rchild);
    }

    private Set<K> visit(Node node, Set<K> set) {
        visit(node.rchild, set);
        visit(node, set);
        visit(node.rchild, set);
        if (node == null) {
            return null;
        } else {
            set.add(node.key);
        }
        return set;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        return visit(root, set);
    }

    @Override
    public V remove(K key) {
        Node ptr = root;
        if (root.key == key) {
            clear();
            return root.value;
        } else {
            while(ptr != null) {
                if(ptr.lchild.key == key || ptr.rchild.key == key) {
                    break;
                } else if(compareK(ptr.key, key) > 0) {
                    ptr = ptr.lchild;
                } else if (compareK(ptr.key, key) < 0) {
                    ptr = ptr.rchild;
                }
            }
        }
        if (ptr.lchild.key == key) {
            Node cmp = ptr.lchild;
            return changeTree(ptr, cmp, true);
        } else if (ptr.rchild.key == key) {
            Node cmp = ptr.rchild;
            return changeTree(ptr, cmp, true);
        }
        return null;
    }

    private V changeTree(Node ptr, Node cmp, boolean tr) {
        V value = cmp.value;
        if (cmp.lchild == null && cmp.rchild == null) {
            if (tr) {
                ptr.lchild = null;
            } else {
                ptr.rchild = null;
            }
        } else if (cmp.lchild == null ^ cmp.rchild == null) {
            if (cmp.lchild == null) {
                if (tr) {
                    ptr.lchild = cmp.rchild;
                } else {
                    ptr.rchild = cmp.rchild;
                }
            } else {
                if (tr) {
                    ptr.lchild = cmp.lchild;
                } else {
                    ptr.rchild = cmp.lchild;
                }
            }
        } else {
            Node lMin = findMin(cmp.rchild);
            K mKey = lMin.key;
            V mValue = lMin.value;
            remove(lMin.key);
            cmp.value = mValue;
            cmp.key = mKey;
        }
        return value;
    }

    private Node findMin(Node node) {
        Node ptr = node;
        if (ptr.lchild != null) {
            return findMin(ptr.lchild);
        } else {
            return ptr;
        }
    }

    @Override
    public V remove(K key, V value) {
        Node ptr = root;
        if (root.key == key && root.value == value) {
            V rootValue = root.value;
            clear();
            return rootValue;
        } else {
            while (ptr != null) {
                if ((ptr.lchild.key == key && ptr.lchild.value == value)
                        || (ptr.rchild.key == key && ptr.rchild.value == value)) {
                    break;
                } else if(compareK(ptr.key, key) > 0) {
                    ptr = ptr.lchild;
                } else if (compareK(ptr.key, key) < 0) {
                    ptr = ptr.rchild;
                }
            }
        }
        if (ptr.lchild.key == key) {
            Node cmp = ptr.lchild;
            return changeTree(ptr, cmp, true);
        }
        if (ptr.rchild.key == key) {
            Node cmp = ptr.rchild;
            return changeTree(ptr, cmp, true);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class  BSTMapIterator implements Iterator<K> {
        private int currentIndex = 0;

        Queue<Node> queue = new ArrayDeque<>();

        @Override
        public boolean hasNext() {
            return currentIndex < size();
        }

        private void visit(Node node) {
            if (node.lchild != null) {
                visit(node.lchild);
            } else {
                queue.offer(node);
            }
            if (node.rchild != null) {
                visit(node.rchild);
            }
        }

        @Override
        public K next() {
           return queue.poll().key;
        }
    }
}
