package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root = null;
    private int size = 0;

    public class Node {
        private K key;
        private V value;
        private Node lchild, rchild;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public int compareK(K k1, K k2) {
        return k1.compareTo(k2);
    }

    @Override
    public void clear() {
        if (this.root != null) {
            this.root = null;
            this.size = 0;
        }
    }

    private boolean nodeContainsKey(Node node, K key) {
        //这里原本逻辑是检查key是否为空，但是如果传入结点为空会导致空指针异常
        if (node == null) {
            return false;
        }
        int index = compareK(key, node.key);
        if(index == -999) {
            return false;
        }
        if (index > 0) {
            return nodeContainsKey(node.rchild, key);
        } else if (index < 0) {
            return nodeContainsKey(node.lchild, key);
        } else {
            return true;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return nodeContainsKey(root, key);
    }

    private V nodeGet(Node node, K key) {
        if (node == null) {
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
        //说明是叶结点或者单独一个的根节点，直接返回
        if (node.lchild == null && node.rchild == null) {
            return node;
        }
        // key > node.key
        if (index < 0) {
            if (node.rchild == null) {
                return node;
            }
            return findParent(node.rchild, key);
        } else {
            if (node.lchild == null) {
                return node;
            }
            return findParent(node.lchild, key);
        }
    }

    @Override
    public void put(K key, V value) {
        if (this.root == null) {
            //这里不应该实例化一个新的root结点，使得类的成员变量没有被赋值
            this.root = new Node(key, value);
            this.root.lchild = null;
            this.root.rchild = null;
            size = 1;
        } else {
            Node node = findParent(this.root, key);
            //这里没有对newnode实例化
            Node newNode = new Node(key, value);
            if (compareK(node.key, key) > 0) {
                node.lchild = newNode;
                size++;
            }
            // 在插入一个已经存在的键时，应更新旧键值并保持树大小不变
            else if (compareK(node.key, key) == 0) {
                node.value = value;
            }
            else {
                node.rchild = newNode;
                size++;
            }
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
            size--;
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
            size--;
            return changeTree(ptr, cmp, true);
        } else if (ptr.rchild.key == key) {
            Node cmp = ptr.rchild;
            size--;
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
            size--;
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
            size--;
            return changeTree(ptr, cmp, true);
        }
        if (ptr.rchild.key == key) {
            Node cmp = ptr.rchild;
            size--;
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
