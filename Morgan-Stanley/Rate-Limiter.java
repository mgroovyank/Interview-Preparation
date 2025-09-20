/**
Rate Limiter
IP based
10 requests/per min

Map<IP, Integer> ipToRequestsMap;

every one minute, I'll update the map

Bucket
- ip
- counter
- lastUpdateTimestamp

while(true){
1. Find buckets where currentTime - lastUpdateTimetstamp > 1 min
2. Update the counter = maxLimit for those buckets
}

1. When you receive an API requests, updated the counter in the bucket for that IP.
2. if counter exceeds maxLimit, drop the request or maybe store it somewhere to execute when counter resets.

Problem with this approach - its not truly rate limiting. For example 10 requests in last 30 seconds
from previous window + 10 requests in next 30 seconds of start of new window = 20 requests in one minute.

Also if requests are handled on multiple threads, counter should be thread safe. So we need to make counter thread safe via Atomic Integer or use Redis to store counter and use inc() for thread safe or maybe a lua script.

so we need to actually, look at number of requests in past 1 minute, whenever we get a request to see if that request is not exceeding limit.

Bucket
- IP
- List<Timestamp>  of size 10

when I get a request, I remove all the timestamps older than past 1 minute. if there is space, I accept the current request and add it to list as well.

if you have multiple instances of application, counter needs to be externalized in a redis cache.
**/

package com.mayank.playground;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {
    static void main() {
        class RequestCounter {
            Integer count;
            LocalDateTime windowStartTime;

            public RequestCounter(Integer count, LocalDateTime windowStartTime){
                this.count = count;
                this.windowStartTime = windowStartTime;
            }

            public Integer getCount() {
                return count;
            }

            public LocalDateTime getWindowStartTime() {
                return windowStartTime;
            }

            public void setCount(Integer count) {
                this.count = count;
            }

            public void setWindowStartTime(LocalDateTime windowStartTime) {
                this.windowStartTime = windowStartTime;
            }
        }
        Map<String, RequestCounter> ipToRequestCount = new ConcurrentHashMap<>();
        String inputIP = "12.34.56.78";
        /**
         * When Thread A exits a synchronized block:
         * All changes it made to shared variables (like counter.count and counter.startTime) are flushed to main memory.
         * This ensures that other threads can see the updated values.
         * When Thread B enters the same synchronized block:
         * It invalidates its local cache for that object.
         * It reloads the latest values from main memory.
         * This is called a happens-before relationship:
         *
         * Threads don’t cache objects or references in the way you might think of copying them. Instead, they cache fields
         * (values) of objects — specifically, the primitive values and object references stored in fields — in CPU registers
         * or local caches.
         */
        ipToRequestCount.putIfAbsent(inputIP, new RequestCounter(0, LocalDateTime.now()));
        RequestCounter counter = ipToRequestCount.get(inputIP);
        synchronized (counter){
            Integer count = counter.getCount();
            LocalDateTime windowStartTime = counter.getWindowStartTime();
            if(Duration.between(windowStartTime, LocalDateTime.now()).toMinutes() > 1L){
                // new window started
                counter.setCount(count + 1);
                counter.setWindowStartTime(LocalDateTime.now());
            }else{
                // same window
                if(count + 1 > 10){
                    // don't accept request
                }else{
                    counter.setCount(count + 1);
                }
            }
        }
    }
}
