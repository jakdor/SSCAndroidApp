from urllib2 import *
import urllib
import json
import sys

MY_API_KEY="AIzaSyCgSjnjxtYBGMOq7jNgnE_tbhpOJjU5nOo"

messageTitle = sys.argv[1]
messageBody = sys.argv[2]

data={
    "to" : "/topics/sscapp",
    "data" : {
        "body" : messageBody,
        "title" : messageTitle,
        "icon" : "favicon",
	"vibration": "true",
	"led": "true"
    }
}
	
dataAsJSON = json.dumps(data)

request = Request(
    "https://gcm-http.googleapis.com/gcm/send",
    dataAsJSON,
    { "Authorization" : "key="+MY_API_KEY,
      "Content-type" : "application/json"
    }
)

print urlopen(request).read()
