package com.cf.study.ali;

import java.util.*;

/**
 * @author miaomiao
 * @date 2021/3/2 22:22
 */
public class Probleam1 {

    public static void main(String[] args) {
        System.out.println(solve("Welcome4(ToAlibaba(To3)2)2"));
    }
    public  static String solve(String str){
        HashMap<String,Integer> wordList = new HashMap<>();
        Stack<Pair> stack = new Stack<>();

        //对字符串的处理
        int index = 0;
        int len = str.length();
        while(index<len){
            if(str.charAt(index) == '('){
                Pair pair = new Pair("(",0);
                stack.push(pair);
                index++;
            }
            else if(str.charAt(index)>='A' && str.charAt(index)<='Z'){
                //获取该单词
                int end=index;
                do {
                    end++;
                }while(end<len && str.charAt(end)>='a' && str.charAt(end)<='z');
                String word = str.substring(index, end);
                index = end;
                int num = 0;
                if(index>=len || (str.charAt(index)>='A' && str.charAt(index)<='Z')){
                    num = 1;
                }
                else{
                    while(index<len && str.charAt(index)>='0' && str.charAt(index)<='9'){
                        num=num*10+(int)(str.charAt(index)-'0');
                        index++;
                    }
                }

                Pair pair = new Pair(word, num);
                stack.push(pair);
            }
            else{
                //处理右括号的情况
                index++;
                //获取右括号旁边的数字
                int num = 0;
                if(index>=len || (str.charAt(index)<'0' || str.charAt(index)>'9')){
                    num = 1;
                }
                else{
                    while(index<len && str.charAt(index)>='0' && str.charAt(index)<='9'){
                        num=num*10+(int)(str.charAt(index)-'0');
                        index++;
                    }
                }
                //出栈
                do{
                    Pair pair = stack.pop();
                    if(pair.count==0) break;
                    pair.count*=num;
                    if(wordList.containsKey(pair.word)){
                        wordList.put(pair.word, wordList.get(pair.word)+pair.count);
                    }
                    else{
                        wordList.put(pair.word, pair.count);
                    }
                }while(true);
            }
        }
        if(!stack.isEmpty()){
            Pair pair = stack.pop();
            if(wordList.containsKey(pair.word)){
                wordList.put(pair.word, wordList.get(pair.word)+pair.count);
            }
            else{
                wordList.put(pair.word, pair.count);
            }
        }
        //对hashmap进行统计
        Set<String> words = wordList.keySet();
        Set<String> sortSet = new TreeSet<String>((o1, o2) -> o2.compareTo(o1));
        sortSet.addAll(words);
        StringBuffer res =new StringBuffer();
        for(Iterator iter = sortSet.iterator(); iter.hasNext(); ) {
            String d = (String) iter.next();
            res.append(d);
            res.append(wordList.get(d));
        }
        return res.toString();
    }


}

class Pair{
    String word;
    int count;

    public Pair(String word, int count) {
        this.word = word;
        this.count = count;
    }
}