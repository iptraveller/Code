import pymysql
import warnings

warnings.filterwarnings('ignore')

class sql:
    __host = ''
    __port = 0,
    __user = ''
    __passwd = ''
    __cursor = None
    __conn = None
    db = ''
    
    def __init__(self, db, user='root', passwd='11111111') :
        self.__host = 'localhost'
        self.__port = 3306
        self.__user = user
        self.__passwd = passwd
        self.db = db
        self.__conn = pymysql.connect(
            host = self.__host,
            port = self.__port,
            user = self.__user,
            passwd = self.__passwd,
            db = self.db,
            charset = 'utf8');
        self.__cursor = self.__conn.cursor()

    def set(self, command) :
        self.__cursor.execute(command)
        self.__conn.commit()

    def get(self, command) :
        nr_rows = self.__cursor.execute(command)
        rows = self.__cursor.fetchall()
        return rows
    
    def getone(self, command) :
        nr_rows = self.__cursor.execute(command)
        row = self.__cursor.fetchone()
        return row
    
    def close(self) :
        if self.__cursor != None :
            self.__cursor.close()
        if self.__conn != None :
            self.__conn.close()
