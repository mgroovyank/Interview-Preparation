// https://leetcode.com/problems/cheapest-flights-within-k-stops/

// Time Complexity: O(ElogV)
// Space Complexity: O(n*n-1)
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<List<Flight>> connectivity = new ArrayList<>();
        for(int i=0;i<n;i++){
            connectivity.add(new ArrayList<>());
        }
        for(int[] flight: flights){
            int from = flight[0];
            int to = flight[1];
            int price = flight[2];
            connectivity.get(from).add(new Flight(to, price));
        }

        int[] cheapest = new int[n];
        Arrays.fill(cheapest, Integer.MAX_VALUE);
        PriorityQueue<Flight> pq = new PriorityQueue<>();
        pq.add(new Flight(src, cheapest[src]=0, k));

        while(!pq.isEmpty()){
            Flight curr = pq.poll();
            int currCity = curr.to;
            int currPrice = curr.price;
            int availStops = curr.stops;

            List<Flight> nextFlights = connectivity.get(currCity);
            for(Flight nextFlight: nextFlights){
                int nextCity = nextFlight.to;
                int nextPrice = currPrice + nextFlight.price;
                // if here I had a cheapest price for next City on a path with more stops
                // I can still explore a path with less stops if it is cheaper
                // but for ordering by price, I might explore a path which consumes more stops
                // and prevent me from reaching my destination. When I explore another path with less stops,
                // that is costlier but because it is costlier, I don't explore it and miss.
                if(availStops>=0 && nextPrice<=cheapest[nextCity]){
                    cheapest[nextCity] = nextPrice;
                    pq.add(new Flight(nextCity, nextPrice, availStops-1));
                }
            }
        }

        return cheapest[dst] == Integer.MAX_VALUE ? - 1 : cheapest[dst];

    }

    class Flight implements Comparable<Flight>{
        int to;
        int price;
        int stops;

        Flight(int to, int price){
            this.to = to;
            this.price = price;
        }

        Flight(int to, int price, int stops){
            this.to = to;
            this.price = price;
            this.stops = stops;
        }

        public int compareTo(Flight f){
            return Integer.compare(f.stops, this.stops); // process paths where I have more stops available first
            // if I process paths with more available stops, first, I put the cheapest price to 
            // reach each city on the path
            // Now when i explore paths with less stops available, I only take those paths, only
            // if they are cheaper.
            // With ordering via cheaper price, I might spend more stops and not explore paths
            // from that node which are costlier but still take me to my destination within the stops
            // available
        }
    }
}

// We don't even need a priority queue here as from one level to another stops are always decreased by 1 for all next cities, so they
// all are equal candidates for exploration

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<List<Flight>> connectivity = new ArrayList<>();
        for(int i=0;i<n;i++){
            connectivity.add(new ArrayList<>());
        }
        for(int[] flight: flights){
            int from = flight[0];
            int to = flight[1];
            int price = flight[2];
            connectivity.get(from).add(new Flight(to, price));
        }

        int[] cheapest = new int[n];
        Arrays.fill(cheapest, Integer.MAX_VALUE);
        Deque<Flight> pq = new ArrayDeque<>();
        pq.add(new Flight(src, cheapest[src]=0, k));

        while(!pq.isEmpty()){
            Flight curr = pq.remove();
            int currCity = curr.to;
            int currPrice = curr.price;
            int availStops = curr.stops;

            List<Flight> nextFlights = connectivity.get(currCity);
            for(Flight nextFlight: nextFlights){
                int nextCity = nextFlight.to;
                int nextPrice = currPrice + nextFlight.price;
                if(availStops>=0 && nextPrice<=cheapest[nextCity]){
                    cheapest[nextCity] = nextPrice;
                    pq.add(new Flight(nextCity, nextPrice, availStops-1));
                }
            }
        }

        return cheapest[dst] == Integer.MAX_VALUE ? - 1 : cheapest[dst];

    }

    class Flight{
        int to;
        int price;
        int stops;

        Flight(int to, int price){
            this.to = to;
            this.price = price;
        }

        Flight(int to, int price, int stops){
            this.to = to;
            this.price = price;
            this.stops = stops;
        }
    }
}
