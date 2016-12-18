# -*- coding: utf-8 -*-

import SQL
import numpy
import Chart

__index_db = 'stock_index'
__indexs = {}

def ReadIndexs(code):
    dates = []
    prices = []
    
    db = SQL.sql(__index_db)
    rows = db.get("select name from code where code={}".format(code))
    for row in rows:
        name = row[0]
        
    rows = db.get('select date,price from `{}` order by date asc'.format(code))
    for row in rows:
        dates.append(row[0])
        prices.append(row[1])
    db.close()
    
    __indexs[code] = [name, dates, prices]
    print ("Read Indexs %s %s" %(code, name))

def DrawIndexs(code, save=0) :
    if save != 0 :
        savepath= "indexs/{}_{}.png".format(code, __indexs[code][0])
    else :
        savepath=None
    Chart.DrawChart(__indexs[code][1], __indexs[code][2], __indexs[code][0], code, '日期', '指数', savepath)
    
'''
days = []
prices = []
pay_monthly = 10000

def DaySplit(orig) :
    year=orig[0:4]
    month=orig[4:6]
    day=orig[6:8]
    return int(year), int(month), int(day)

def DayCombine(year, month, day) :
    return str(year * 10000 + month * 100 + day)

def ChooseValidDay(orig_day, days) :
    year, month, day = DaySplit(orig_day)
    new_day = day
    while not (DayCombine(year, month, new_day) in days) :
        while (new_day <= 31) :
            new_day = new_day + 1
            if DayCombine(year, month, new_day) in days:
                break
        if new_day <= 31:
            break;
        new_day = day
        while (new_day > 0) :
            new_day = new_day - 1
            if DayCombine(year, month, new_day) in days:
                break
        if new_day > 0:
            break
        
    return DayCombine(year, month, new_day)    

    
def MonthIncrease(orig_day) :
    year, month, day = DaySplit(orig_day)
    month = month + 1
    if month > 12 :
        year = year + 1
        month = 1

    return str(year * 10000 + month * 100 + day)
    
def MonthlyStrategy(begin, end):
    invest_days = []
    begin_day = begin
    end_day = end
    day = begin_day

    if begin >= end:
        return 0
    
    while (int(day) < int(end_day)) :
        day = ChooseValidDay(day, days)
        invest_days.append(day)
        day = MonthIncrease(day)
        
    total = 0.0
    irr = []
    for day in invest_days:
        idx = days.index(str(day))
        irr.append(-1 * pay_monthly)
        total = total + pay_monthly / float(prices[idx])
#        print ('%d %s %.2lf %.2lf' % (idx, days[idx], float(prices[idx]), pay_monthly / float(prices[idx])))
    gain = total * prices[len(prices) - 1]
#    print ('Total month %d, from %s to %s' % (len(invest_days), begin, end))
#    print ('Total pay  %.2lf' % (pay_monthly * len(invest_days)))
#    print ('Total gain %.2lf' % (gain))
    irr.append(gain)    
    monthly_rate = numpy.irr(irr)
    yearly_rate = ((1 + monthly_rate) ** 12 - 1)
#    print ('Monthly IRR %.2lf %%' % (monthly_rate * 100))
#    print ('Yearly  IRR %.2lf %%' % (yearly_rate * 100))

    return monthly_rate
'''
'''
rates = []
for year in range(2006, 2017) :
    for month in range(1,13) :
        rates.append(MonthlyStrategy(DayCombine(year, month, 1),'20161201'))

for rate in rates:
    print ('%.4lf' % (rate * 100))
'''

db = SQL.sql(__index_db)
rows = db.get("select code from code")
for row in rows:
    ReadIndexs(row[0])
    DrawIndexs(row[0], 1)

