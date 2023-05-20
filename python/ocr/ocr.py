# %%

# pip install easyocr
import base64
import re
import uuid
from datetime import time

import cv2
from fontTools.misc.eexec import encrypt
from matplotlib import pyplot as plt
import numpy as np
import os
import pytesseract
from PIL import Image


class OCR():
    def ocr(self, img_path, preprocess):
        #img_path = os.path.join(os.getcwd(), "origin/010.png")

        # img_path = "images/receipt.png"
        # preprocess = "blur"

        image = cv2.imread(img_path)
        gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

        # apply thresholding to segment the foreground from the background
        # 这种阈值方法对于读取覆盖在灰色形状上的暗文本非常有用
        if preprocess == "thresh":
            gray = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY | cv2.THRESH_OTSU)[1]

        # median blurring to remove salt and pepper noise
        elif preprocess == "blur":
            gray = cv2.medianBlur(gray, 3)

        # save the grayscale image as a temporary file so we can apply OCR to it
        folder_path=os.path.join(os.getcwd(), "images")
        filename = os.path.join(folder_path, "postprocess.png")
        if not os.path.exists(folder_path):
            os.makedirs(folder_path)
        cv2.imwrite(filename, gray)

        # load the image as a PIL/Pillow image, apply OCR, then delete the temporary file
        # convert the contents of the image into string
        text = pytesseract.image_to_string(Image.open(filename), lang="chi_sim")
        os.remove(filename)
        #print(text)
        return text
        # # show the output images
        # cv2.imshow("Image", image)
        # cv2.imshow("Output", gray)
        # cv2.waitKey(0)

    def ocr_files(self, ocr_model=None, text_result=None, tk=None):
        if ocr_model.img_paths:
            ocr_result = ocr_model.ocr_files()
            text_result.insert(tk.END, ocr_result)
        else:
            tk.messagebox.showinfo("提示", "无文件")
        # 其中btn_sure的绑定事件ocr_files()将文件路径和识别类型传入ocrprocesser:

    def ocr_files(self):
        for img_path in self.img_paths:
            img_file_name = os.path.basename(img_path).split('.')[0]
            # print('==========='+img_file_name+'===========')
            f = open(img_path, 'rb')
            img_code = base64.b64encode(f.read()).decode('utf-8')
            f.close()
            print(img_code)
            ocr_result = self.ocr_by_netease(img_code, self.img_type)
            print(ocr_result)
            return ocr_result

    def get_ocr_result(img_code, img_type):
        if img_type == 0 or img_type == 1:
            return img_code.ocr_common(img_code)
        elif img_type == 2 or img_type == 3:
            return img_code.ocr_card(img_code, img_type)
        elif img_type == 4:
            return img_code.ocr_table(img_code)
        elif img_type == 5:
            return img_code.ocr_problem(img_code)
        else:
            return "error:undefined type!"

    def ocr_common(img_code):
        YOUDAO_URL = 'https://openapi.youdao.com/ocrapi'
        data = {}
        data['detectType'] = '10012'
        data['imageType'] = '1'
        data['langType'] = 'auto'
        data['img'] = img_code
        data['docType'] = 'json'
        data = img_code.get_sign_and_salt(data, img_code)
        response = img_code.get_ocr_result(YOUDAO_URL, data)['regions']
        result = []
        for r in response:
            for line in r['lines']:
                result.append(line['text'])
        return result

    def ocr_card(img_code, img_type):
        YOUDAO_URL = 'https://openapi.youdao.com/ocr_structure'
        data = {}
        if img_type == 2:
            data['structureType'] = 'idcard'
        elif img_type == 3:
            data['structureType'] = 'namecard'
        data['q'] = img_code
        data['docType'] = 'json'
        data = img_code.get_sign_and_salt(data, img_code)
        return img_code.get_ocr_result(YOUDAO_URL, data)

    def ocr_table(img_code):
        YOUDAO_URL = 'https://openapi.youdao.com/ocr_table'
        data = {}
        data['type'] = '1'
        data['q'] = img_code
        data['docType'] = 'json'
        data = img_code.get_sign_and_salt(data, img_code)
        return img_code.get_ocr_result(YOUDAO_URL, data)

    def ocr_problem(img_code):
        YOUDAO_URL = 'https://openapi.youdao.com/ocr_formula'
        data = {}
        data['detectType'] = '10011'
        data['imageType'] = '1'
        data['img'] = img_code
        data['docType'] = 'json'
        data = img_code.get_sign_and_salt(data, img_code)
        response = img_code.get_ocr_result(YOUDAO_URL, data)['regions']
        result = []
        for r in response:
            for line in r['lines']:
                for l in line:
                    result.append(l['text'])
        return result

    def get_sign_and_salt(data, img_code, APP_KEY=None, APP_SECRET=None):
        data['signType'] = 'v3'
        curtime = str(int(time.time()))
        data['curtime'] = curtime
        salt = str(uuid.uuid1())
        signStr = APP_KEY + os.truncate(img_code) + salt + curtime + APP_SECRET
        sign = encrypt(signStr)
        data['appKey'] = APP_KEY
        data['salt'] = salt
        data['sign'] = sign
        return data

def pic_to_text(img_path, preprocess):
    text=OCR().ocr(img_path,preprocess)
    text=re.sub(r'[\s+]','',text)
    print(text)
    return text


if __name__ == '__main__':

    text=pic_to_text(img_path=os.path.join(os.getcwd(), "origin/010.png"),preprocess="thresh")

    # text = pytesseract.image_to_string(Image.open(os.path.join(os.getcwd(), "origin/010.png")), lang="chi_sim")
    # 如果你想试试Tesseract识别中文，只需要将代码中的eng改为chi_sim即可
    # print(text)
