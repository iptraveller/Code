import logging
import sys

logging.basicConfig(level = logging.DEBUG,
                    format='[%(asctime)s] %(levelname)-8s: %(message)s',
                    datefmt = '%Y-%m-%d %H:%M:%S',
                    filename = 'stock.log',
                    filemode='a')

console = logging.StreamHandler(sys.stdout)
console.setLevel(logging.DEBUG)
formatter = logging.Formatter('[%(asctime)s] %(levelname)-8s: %(message)s')
console.setFormatter(formatter)
logging.getLogger('').addHandler(console)

def debug(message) :
    logging.debug(message)

def info(message) :
    logging.info(message)

def warn(message) :
    logging.warning(message)
                    
def error(message) :
    logging.error(message)
