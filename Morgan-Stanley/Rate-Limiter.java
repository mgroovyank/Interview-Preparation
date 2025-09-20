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
