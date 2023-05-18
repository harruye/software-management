# -*- coding: UTF-8 -*-
import jieba.posseg as pseg
import re
import numpy as np
import networkx as nx

def preprocess(text):
    # 分割成句子
    sentences = re.split(r'[!?。\n]', text)
    sentences = [s.strip() for s in sentences if s.strip()]
    sentencesWords = []
    for sentence in sentences:
        # 分词和词性标注
        words = pseg.cut(sentence)
        # 去除无关词汇
        filteredWords = [w.word for w in words if w.flag.startswith(('n', 'v', 'a')) and len(w.word) > 1]
        sentencesWords.append(filteredWords)
    return sentencesWords,sentences

def buildCoOccurrenceMatrix(sentencesWords):
    vocab = {}
    for words in sentencesWords:
        for word in words:
            if word not in vocab:
                vocab[word] = len(vocab)
    coMat = np.zeros((len(vocab), len(vocab)))
    for words in sentencesWords:
        for i, w1 in enumerate(words):
            for j, w2 in enumerate(words):
                if w1 == w2:
                    continue
                ix1, ix2 = vocab[w1], vocab[w2]
                coMat[ix1][ix2] += 1
    coMat /= np.sum(coMat, axis=0)
    return coMat, vocab

def calculateWeightedScore(coMat):
    graph = nx.from_numpy_array(coMat)
    score = nx.pagerank(graph, alpha=0.85, max_iter=100)
    return score

def getimportantsentence(score,vocab,sentenceWords):
    sentenceimportance={}
    for i,words in enumerate(sentenceWords):
        sentenceScore=0
        for word in words:
            wordid=vocab[word]
            sentenceScore+=score[wordid]
        sentenceimportance[i]=sentenceScore

    return sentenceimportance

def getSummary(text, n):

    sentencesWords,sentences= preprocess(text)
    coMat, vocab = buildCoOccurrenceMatrix(sentencesWords)
    score = calculateWeightedScore(coMat)
    #print(score[1])
    sentenceimportance=getimportantsentence(score,vocab,sentencesWords)
    sortedSentences = sorted(sentenceimportance.items(), key=lambda x: x[1], reverse=True)

    #print(sortedSentences)
    summary = []
    for (i, score) in sortedSentences[:n]:
        summary.append(sentences[i])
    #print(summary)
    return '。'.join(summary)+"。"


if __name__=="__main__":


    text=r"该研究主持者之一、波士顿大学地球与环境科学系博士陈池（音）表示，“尽管中国和印度国土面积仅占全球陆地的9%，但两国为这一绿化过程贡献超过三分之一。考虑到人口过多的国家一般存在对土地过度利用的问题，这个发现令人吃惊。” NASA埃姆斯研究中心的科学家拉玛·内曼尼（Rama Nemani）说，“这一长期数据能让我们深入分析地表绿化背后的影响因素。我们一开始以为，植被增加是由于更多二氧化碳排放，导致气候更加温暖、潮湿，适宜生长。”“MODIS的数据让我们能在非常小的尺度上理解这一现象，我们发现人类活动也作出了贡献。”NASA文章介绍，在中国为全球绿化进程做出的贡献中，有42%来源于植树造林工程，对于减少土壤侵蚀、空气污染与气候变化发挥了作用。 据观察者网过往报道，2017年我国全国共完成造林736.2万公顷、森林抚育830.2万公顷。其中，天然林资源保护工程完成造林26万公顷，退耕还林工程完成造林91.2万公顷。京津风沙源治理工程完成造林18.5万公顷。三北及长江流域等重点防护林体系工程完成造林99.1万公顷。完成国家储备林建设任务68万公顷"
    #print(text)
    summary = getSummary(text, 3)
    print(summary)