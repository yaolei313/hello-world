package com.yao.app.algorithm;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie.Hit;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

/**
 * 描述
 *
 * @auther allen@xiaohongshu.com 2019-08-26
 */
public class ACStudy {

    public static void main(String[] args) {
        Trie trie = Trie.builder()
            .addKeyword("hers")
            .addKeyword("his")
            .addKeyword("she")
            .addKeyword("he")
            .build();
        Collection<Emit> emits = trie.parseText("ushers");

        // Collect test data set
        TreeMap<String, String> map = new TreeMap<String, String>();
        String[] keyArray = new String[]
            {
                "hers",
                "his",
                "she",
                "he"
            };
        for (String key : keyArray)
        {
            map.put(key, key);
        }
        // Build an AhoCorasickDoubleArrayTrie
        AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<String>();
        acdat.build(map);
        // Test it
        final String text = "uhers";
        List<Hit<String>> wordList = acdat.parseText(text);
    }
}
