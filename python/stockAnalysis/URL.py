import urllib.request
import socket
import LOG

def request(url, timeout = 60) :
    socket.setdefaulttimeout(timeout)
    try :
        s = urllib.request.urlopen(url)
    except urllib.error.URLError as ex:
        LOG.error ("Failed to request URL %s, %s" %(url, ex))
        return ""
    content = str(s.read().decode('utf-8'))
    s.close()
    
    return content
