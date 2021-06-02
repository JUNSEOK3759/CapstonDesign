#-*ccoding:utf-8-*-

from typing import List
from selenium import webdriver
from urllib.request import urlopen
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup
import datetime
import pandas as pd
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import pyrebase
import json
import time
from openpyxl import Workbook
import sched

def crawling():
    #search_word = input('검색할 단어를 입력하세요: ')
    #tag_n = input('가져올 태그의 숫자를 입력하세요 : ')
    search_word = 'nike_gangnam'
    tag_n = '5'
    driver = webdriver.Chrome('chromedriver.exe')
    driver.get('https://www.instagram.com')

    with open("auth.json") as f:
        config = json.load(f)


    #driver.maximize_window()

    time.sleep(3)
    ###로그인
    e = driver.find_elements_by_class_name('_2hvTZ.pexuQ.zyHYP')[0]
    e.send_keys('capstone_mj')
    e = driver.find_elements_by_class_name('_2hvTZ.pexuQ.zyHYP')[1]
    e.send_keys('capstone12!')
    e.send_keys(Keys.ENTER)
    time.sleep(3)



    ###해시태그나 아이디로 넘어감
    #url = 'https://www.instagram.com/explore/tags/' + search_word+'/'
    url = 'https://www.instagram.com/' + search_word+'/'
    driver.get(url)
    time.sleep(5)
    html = driver.page_source
    soup = BeautifulSoup(html)
    firebase = pyrebase.initialize_app(config)
    db = firebase.database()


    insta = soup.select('.v1Nh3.kIKUG._bz0w')

    n = 1
    nowTime = datetime.datetime.now().strftime("%y%m%d_%H%m%S")

    # ###########사진저장
    # for i in insta:
    #     print('https://www.instagram.com/'+i.a['href'])
    #     eventurl = i.select_one('.KL4Bh').img['src']
    #     with urlopen(eventurl) as f:
    #         with open('./event/' + nowTime + '_' + search_word + str(n) + '.jpg', 'wb') as h:
    #             img = f.read()
    #             h.write(img)
    #         n += 1
    #         #  print(eventurl)
    #         #  print()

    #해시태그에서 사진클릭
    driver.find_elements_by_class_name('_9AhH0')[0].click()
    time.sleep(2)

    ###게시물 태그
    tags = driver.find_elements_by_class_name('C4VMK')

    tag_list = []
    n = 1
    # for i in tags:
    #     print(n)
    #     if i in tags:
    #         print(i.text)
    #         for  in
    #         tag_list.append(i.text)
    #     n += 1

    while True:
        try:
            if int(tag_n) > n:
                for i in tags:
                    print(n)

                    print(i.text)
                    tag_list.append(i.text)
                driver.find_elements_by_class_name('_65Bje.coreSpriteRightPaginationArrow')[0].click()
                time.sleep(4)
                tags = driver.find_elements_by_class_name('C4VMK')
                n += 1

            else :
                if (n >= int(tag_n)):
                    break

                else :
                    driver.execute_script('window.scrollTo(0, document.body.scrollHeight)')

        except:
            if (n >= int(tag_n)):
                break

    print(len(tag_list))
    search = "NIKE GANGNAM - THE DRAW"

    word_list = List()
    for word in tag_list:
        if search in word:
            word_list.append(word)
            if len(word_list) == 1:
                break
    print(word_list)
    # print(tag_list)
    print(len(word_list))

    f = open('nike_gangnam.txt', 'w', encoding='utf-8')

    for c in word_list:

        f.write(c)
        f.write('\n')

    f.close()

    with open('nike_gangnam.txt', mode='r', encoding='utf-8') as rawtext :
        text=rawtext.read()
        list = []
        lastidx=text.find('\n[')
        list.append(text[:lastidx])
        firstidx=text.find('▶ 응모기간') # 다음 '>' 까지의 위치 찾기
        text=text[firstidx+1:] # 해당 위치까지 자르기 (태그 부분이 잘린다)
        lastidx1=text.find('\n▶ 당첨발표')# 다음 '<'의 위치 찾기
        list.append(text[:lastidx1])# 알맹이를 뽑아서 리스트에 추가하기
        list.append(url)
        dictionary = {"id" : list[0], "pw" : list[1], "linkk" : list[2]}

    db.child("Draw").child("Draw_28").set(dictionary)
sched.every().hours.do(crawling)
while 1:
    sched.run_pending()
    time.sleep(1)