# -*- coding: utf-8 -*-
import numpy
import datetime
import matplotlib.pyplot as plt
import matplotlib.dates as mdates
from pylab import *  

mpl.rcParams['font.sans-serif'] = ['SimHei']
mpl.rcParams['axes.unicode_minus'] = False

def DrawChart(x, y, title, lab, xlabel, ylabel, savepath=None) :
    dates = []
    total_days = 0
    for v in x:
        dates.append(datetime.date(int(v[0:4]),int(v[4:6]),int(v[6:8])))
    if len(dates) == 0:
        return
    total_days = (dates[len(dates) - 1] - dates[0]).days
    print ("%d days" %(total_days))
    
    plt.figure(figsize=(12,8))
    plt.plot(dates, y, label=lab, color='blue')
    plt.title(title)
    plt.grid()
    plt.ylabel(ylabel)
    axis = plt.gca()
    if (total_days > 10 * 365) :
        axis.xaxis.set_major_locator(mdates.YearLocator())
    elif (total_days > 31) :
        months = mdates.MonthLocator(range(1, 13), bymonthday=1, interval=int((total_days + 300) / 300))
        axis.xaxis.set_major_locator(months)
    else :
        days = mdates.DayLocator(range(1, 32), interval=int((total_days + 10) / 10))
        axis.xaxis.set_major_locator(days)

    axis.xaxis.set_major_formatter(mdates.DateFormatter('%Y-%m-%d'))
    locs,labels = plt.xticks()
    plt.setp(plt.xticks()[1], rotation=45)
    plt.legend()
    if savepath != None:
        plt.savefig(savepath)
    else:
        plt.show()
    
#DrawChart(['20160101','20160110','20160130'],['2000.11','3000.22','4000.33'], 'aaa', 'bbb', 'ccc', 'ddd', 'a.png')
