import SQL
import urllib.request
import string
import time

__index_db = 'stock_index'
__index_url = 'http://quotes.money.163.com/trade/lsjysj_zhishu_{}.html'
__index_url_date = __index_url+'?year={}&season={}'
__begin_index=1
__end_index=200
__begin_year = 2000
__end_year = 2016
__indexs=[]
    
def GetIndex(id, year_from, year_to):
    indexs=[]
    for year in range(year_from, year_to + 1):
        for season in range(1, 5):
            #print (__index_url_date.format(str(id),str(year),str(season)))
            s = urllib.request.urlopen(__index_url_date.format(str(id),str(year),str(season)))
            #print (s)
            content = str(s.read().decode('utf-8'))
            search_begin = content.find('<table class=')
            if search_begin == -1 :
                continue
            search_begin = content.find('<tr class=\'\'>', search_begin)
            search_end = content.find('</table>', search_begin)
            content = content[search_begin:search_end]
            search_end = 0
            index = 0
            while True :
                search_begin = content.find('<tr class=', search_end)
                if search_begin == -1 :
                    break
                record=[]
                for i in range(1, 10):
                    search_begin = content.find('<td>', search_end)
                    search_end = content.find('</td>', search_begin + 4)
                    value = content[search_begin + 4:search_end]
                    value = value.replace(',', '')
                    record.append(value)
                indexs.append(record)
    indexs.sort(key=lambda x:x[0])
                                       
    return indexs

def UpdateIndexs():
    db = SQL.sql(__index_db)
    for index in __indexs:
        index_code = index[0]
        index_name = index[1]
        print ("Updating index %s %s" % (index_code, index_name))
        command = "create table if not exists `{}`(date char(8) unique, price double, volume double)".format(index_code)
        print (command)
        db.set(command)
        historys = GetIndex(index_code, __begin_year, __end_year)
        for record in historys:
            date = record[0]
            try :
                price = float(record[4])
            except :
                price = 0
            try :
                volume = float(record[7])
            except :
                volume = 0
            #print ("%s %s %s" % (record[0], record[4], record[7]))
            command = "replace into `{}` values({},{},{})".format(index_code, date, str(price), str(volume))
            #print (command)
            db.set(command)
    db.close()

def UpdateCodes():
    db = SQL.sql(__index_db)
    db.set("create table if not exists code(code char(6) unique, name char(36))")
    for index in range(__begin_index, __end_index):
        index_str='{:0>6}'.format(index)
        try:
            s = urllib.request.urlopen(__index_url.format(index_str))
        except:
            print ('No index %s' %(index_str))
            continue
        content = str(s.read().decode('utf-8'))
        index_begin = content.find('var STOCKSYMBOL = \'')
        if index_begin == -1:
            break
        index_end = content.find('\'', index_begin + 19)
        index_code = content[index_begin + 19:index_end]
        index_begin = content.find('var STOCKNAME = \'')
        if index_begin == -1:
            break
        index_end = content.find('\'', index_begin + 17)
        index_name = content[index_begin + 17:index_end]
        print ('%s %s' %(index_code, index_name))
        __indexs.append([index_code, index_name])
        db.set("replace into code values(\'{}\',\'{}\')".format(index_code, index_name))
        s.close()
    db.close()
    #print (__indexs)

UpdateCodes()
UpdateIndexs()
