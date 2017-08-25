from urllib2 import *
import urllib
import json
import sys

def pushNotification(messageTitle, messageBody):

	MY_API_KEY="AIzaSyCgSjnjxtYBGMOq7jNgnE_tbhpOJjU5nOo"

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
