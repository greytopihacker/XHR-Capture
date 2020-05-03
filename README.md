[![License](https://img.shields.io/github/license/greytopihacker/XHR-Capture)](https://raw.githubusercontent.com/greytopihacker/XHR-Capture/master/LICENSE)

# XHR-Capture
`:  Scans through network calls and gets XHR request/response from HAR file  :`

This little repo was born after I got curious about capturing `XHR` requests made by a webpage in the background. 
Initially I was using the **`requests`** with **`beautifulSoup4`** package in Python for making HTTP requests and then parsing the webpages for some content.

But I found that some of the content was loaded dynamically and after a sleepless night found out that **`selenium`** + **`browsermob-proxy`** was what I was looking for.

Sharing my learnings and findings on the same topic. :yellow_heart:
