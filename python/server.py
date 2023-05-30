# -*- coding: UTF-8 -*-
import json
from xmlrpc.server import SimpleXMLRPCServer
from xmlrpc.server import SimpleXMLRPCRequestHandler
import time

from MRC import answer_question
from ocr.ocr import pic_to_text
from textrank2 import getSummary
from trans import summary


# Restrict to a particular path.
class RequestHandler(SimpleXMLRPCRequestHandler):
    rpc_paths = ('/RPC2',)


# Create server
server = SimpleXMLRPCServer(("127.0.0.1", 8000),
                            requestHandler=RequestHandler)
server.register_introspection_functions()

# Register pow() function; this will use the value of
# pow.__name__ as the name, which is just 'pow'.
server.register_function(pow)


# Register a function under a different name
def adder_function(x, y):
    # time.sleep(30)
    return x + y


server.register_function(adder_function, 'add')


# Register an instance; all the methods of the instance are
# published as XML-RPC methods (in this case, just 'div').
class MyFuncs:
    def div(self, x, y):
        return x // y


server.register_instance(MyFuncs())


def trans(article):
    res = {"text": article, "summary": summary(article)}
    resjson = json.dumps(res, ensure_ascii=False)
    print(resjson)
    return resjson


server.register_function(trans, 'trans')


def text2rank(article):
    res = {"article": article, "summary": getSummary(article)}
    resjson = json.dumps(res, ensure_ascii=False)
    print(resjson)
    return resjson


server.register_function(text2rank, 'text2rank')


def MRC(question, context):
    answer = answer_question(question, context)
    res = {}
    if answer == "[CLS]":
        res = {"question": question, "context": context, "answer": "对不起，我无法回答"}
    else:
        res = {"question": question, "context": context, "answer": answer}
    resjson = json.dumps(res, ensure_ascii=False)
    print(res)
    return resjson


server.register_function(MRC, 'MRC')


def OCR(pic_path):
    print(pic_path)
    text = pic_to_text(pic_path, "thresh")
    res = {"file_path": pic_path, "article": text}
    resjson = json.dumps(res, ensure_ascii=False)
    return resjson


server.register_function(OCR, 'OCR')
# Run the server's main loop
server.serve_forever()
