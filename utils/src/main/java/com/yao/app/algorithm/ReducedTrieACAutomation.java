package com.yao.app.algorithm;

import java.util.List;

/**
 * 描述
 *
 * @auther allen@xiaohongshu.com 2019-08-26
 */
public class ReducedTrieACAutomation {

    private static int DEFAULT_SIZE = 65536;

    private static int DEFAULT_TAIL_SIZE = 65536 * 5;

    private int[] base;

    private int[] check;

    private char[] tail;

    private List<Integer>[] lists;


    // base字符的失败跳转
    private int[] baseFail;

    // tail字符的失败跳转
    private int[] tailFail;

    private int position;

    public ReducedTrieACAutomation() {
        this.base = new int[DEFAULT_SIZE];
        this.check = new int[DEFAULT_SIZE];
        this.tail = new char[DEFAULT_TAIL_SIZE];
        this.lists = new List[DEFAULT_SIZE];

        this.baseFail = new int[DEFAULT_SIZE];
        this.tailFail = new int[DEFAULT_TAIL_SIZE];

        this.base[1] = 1;
        this.check[1] = 1;
        this.position = 1;
    }

    public static ReducedTrieACAutomation build(List<String> keys){

        for (int i = 0; i < keys.size(); i++) {

        }
        return null;
    }



    // 描述Trie树的节点
    private static class Node {
        int code;
        int depth;
        int left;
        int right;
    };



    /*public int build(List<String> key) {
        int _keySize = key.size();
        if (_keySize > _key.size() || _key == null)
            return 0;

        // progress_func_ = progress_func;
        key = _key;
        length = _length;
        keySize = _keySize;
        value = _value;
        progress = 0;

        resize(65536 * 32);

        base[0] = 1;
        nextCheckPos = 0;

        DartDoubleArrayTrie.Node root_node = new DartDoubleArrayTrie.Node();
        root_node.left = 0;
        root_node.right = keySize;
        root_node.depth = 0;

        List<DartDoubleArrayTrie.Node> siblings = new ArrayList<DartDoubleArrayTrie.Node>();
        fetch(root_node, siblings);
        insert(siblings);

        // size += (1 << 8 * 2) + 1; // ???
        // if (size >= allocSize) resize (size);

        used = null;
        key = null;

        return error_;
    }*/
}
