from jieba import posseg,setLogLevel
import logging
import numpy as np
import pandas as pd
from gensim import corpora, models, similarities
import tornado.ioloop
import tornado.web
from tornado.options import define, options
import json
setLogLevel(logging.INFO)

class AI:
    ###创建停用词表
    def stopwordslist(self):
        stopwords = [line.strip() for line in open('./stopwords1893.txt', encoding='UTF-8').readlines()]
        return stopwords

    ###将问询文档分词、去停用词
    def seg_test(self, text):
        stopwords = self.stopwordslist()
        stop_flag = ['x', 'c', 'u', 'd', 'p', 't', 'uj', 'm', 'f', 'r']
        result = []
        words = posseg.cut(text)  # 文档分词
        for word, flag in words:
            if flag not in stop_flag and word not in stopwords:  # 删除停用词和停用词性
                result.append(word)
        return result

    def preprocess(self):
        self.df = pd.read_csv('health1.csv')  # 打开健康文档
        self.dictionary = corpora.Dictionary.load('sym_dict.txt')  # 加载字典文件
        corpus_array = np.load('sym_corpus.npy')  # 加载词袋语料库文件
        corpus = corpus_array.tolist()

        self.tfidf = models.TfidfModel(corpus)  # 计算tfidf模型
        featurenum = len(self.dictionary.token2id.keys())  # 通过token2id得到特征数
        self.index = similarities.SparseMatrixSimilarity(self.tfidf[corpus], num_features=featurenum)  # 稀疏矩阵相似度，建立索引
        return True

    def Process(self,txt):
        test_seg_list = self.seg_test(txt)  # 预处理问询文档
        test_vec = self.dictionary.doc2bow(test_seg_list)  # 把问询文档也转换为词袋模型
        sim = self.index[self.tfidf[test_vec]]  # 得到相似度
        sim_result = list(sim)
        max_index = sim_result.index(max(sim_result))  # 找到相似度最大的结果序号
        return json.dumps({'name':self.df.name[max_index],'treatment':self.df.treatment[max_index]},ensure_ascii=False)


def main():
    ai = AI()
    ai.preprocess()
    define('ai',default=ai)
    options.parse_command_line()
    print('准备就绪！')
    app = tornado.web.Application([
        (r'/', MainHandler),
    ])
    app.listen(8085)
    tornado.ioloop.IOLoop.current().start()

class MainHandler(tornado.web.RequestHandler):
    def get(self):
        ai = options.ai
        content = self.get_argument('content')
        print('---------->接收到的信息：'+content)
        result = ai.Process(content)
        print('---------->返回的结果:\n'+result)
        self.write(result)

if __name__ == '__main__':
    main()
