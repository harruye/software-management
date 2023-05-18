import os

import torch
from transformers import AutoTokenizer, AutoModelForQuestionAnswering, BertTokenizer

def answer_question(question,context):
    model_path=os.path.join(os.getcwd(),"model","chinese_pretrain_mrc_roberta_wwm_ext_large")
    # 加载模型和tokenizer
    tokenizer = BertTokenizer.from_pretrained(model_path)
    model = AutoModelForQuestionAnswering.from_pretrained(model_path)
    # 准备输入数据

    # 对输入进行编码
    inputs = tokenizer.encode_plus(question, context, add_special_tokens=True, return_tensors='pt')

    # 输入模型并得到答案
    start_positions, end_positions = model(**inputs).values()
    start_index = torch.argmax(start_positions)
    end_index = torch.argmax(end_positions) + 1
    answer = tokenizer.convert_tokens_to_string(tokenizer.convert_ids_to_tokens(inputs['input_ids'][0][start_index:end_index]))
    return answer
