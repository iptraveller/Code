import string
import time
import SQL
import LOG
import URL

__index_db = 'stock_index'
__index_table = 'code'
__index_url = 'http://quotes.money.163.com/trade/lsjysj_zhishu_{}.html'
__index_url_date = __index_url+'?year={}&season={}'
__prefix_index = [0, 399]
__begin_index=1
__end_index=999
__indexs=[]
    
def GetIndex(id, year_from, season_from, year_to, season_to):
    indexs=[]
    for year in range(year_from, year_to + 1) :
        if year == year_from :
            season_begin = season_from
            season_end = 4
        elif year == year_to :
            season_begin = 1
            season_end = season_to
        else :
            season_begin = 1
            season_end = 4
        for season in range(season_begin, season_end + 1):
            content = URL.request(__index_url_date.format(str(id),str(year),str(season)))
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
    indexs.sort(key = lambda x:x[0])
                                       
    return indexs

def UpdateIndexs():
    db = SQL.sql(__index_db)
    for index in __indexs:
        index_code = index[0]
        index_name = index[1]
        LOG.info ("Updating index %s %s" % (index_code, index_name))
        command = "CREATE TABLE IF NOT EXISTS `{}`(date char(8) unique, price double, volume double)".format(index_code)
        db.set(command)
        row = db.getone("SELECT MAX(date) from `{}`".format(index_code))
        if row[0] != None :
            begin_year = int(row[0][0:4])
            begin_season = int((int(row[0][4:6]) + 2) / 3)
        else :
            begin_year = 2000
            begin_season = 1
        end_year = int(time.localtime().tm_year)
        end_season = int(int(time.localtime().tm_mon + 2) / 3)            
        historys = GetIndex(index_code, begin_year, begin_season, end_year, end_season)
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
            command = "REPLACE INTO `{}` VALUES({},{},{})".format(index_code, date, str(price), str(volume))
            #print (command)
            db.set(command)
    db.close()

def UpdateCodes():
    db = SQL.sql(__index_db)
    db.set("CREATE TABLE IF NOT EXISTS {}(code char(6) unique, name char(36))".format(__index_table))
    for prefix in __prefix_index:
        for num in range(__begin_index, __end_index):
            index = prefix * 1000 + num
            index_str='{:0>6}'.format(index)
            content = URL.request(__index_url.format(index_str))
            index_begin = content.find('var STOCKSYMBOL = \'')
            if index_begin == -1:
                continue
            index_end = content.find('\'', index_begin + 19)
            index_code = content[index_begin + 19:index_end]
            index_begin = content.find('var STOCKNAME = \'')
            if index_begin == -1:
                continue
            index_end = content.find('\'', index_begin + 17)
            index_name = content[index_begin + 17:index_end]
            LOG.info ('%s %s' %(index_code, index_name))
            __indexs.append([index_code, index_name])
            db.set("REPLACE INTO {} VALUES(\'{}\',\'{}\')".format(__index_table, index_code, index_name))
    db.close()

UpdateCodes()
UpdateIndexs()
