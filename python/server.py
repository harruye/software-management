# -*- coding: UTF-8 -*-
import json
from xmlrpc.server import SimpleXMLRPCServer
from xmlrpc.server import SimpleXMLRPCRequestHandler
import time

from MRC import answer_question
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
    res={"text":article,"summary":summary(article)}
    resjson=json.dumps(res,ensure_ascii=False)
    print(resjson)
    return resjson


server.register_function(trans, 'trans')

def text2rank(article):

    res={"article":article,"summary":getSummary(article,3)}
    resjson=json.dumps(res,ensure_ascii=False)
    print(resjson)
    return resjson

server.register_function(text2rank, 'text2rank')

def MRC(question,context):
    res={"question":question,"context":context,"answer":answer_question(question, context)}
    resjson=json.dumps(res,ensure_ascii=False)
    return resjson

server.register_function(MRC, 'MRC')
# Run the server's main loop
server.serve_forever()
