package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.filter {
            it !in trips.map { trip -> trip.driver }.toSet()
        }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        // get map of Passenger to List<Trip>
        allPassengers.map { p ->
            p to trips.filter { t ->
                p in t.passengers
            }
        }
                .filter { it.second.size >= minTrips }
                .map { it.first }
                .toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        trips.filter { it.driver == driver }
                .flatMap { it.passengers.toList() }
                .groupingBy { it }
                .eachCount()
                .filter { it.value > 1 }
                .map{ it.key }
                .toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        // get map of Passenger to Trips
        allPassengers.map { p ->
            p to trips.filter { t ->
                p in t.passengers
            }
        }
                .toMap()
                .filter { it.value.isNotEmpty() }
                .mapValues { (_, value) ->
                    value.count {
                        t -> (t.discount ?: 0.0) > 0.0
                    } / value.size.toDouble()
                }.filter { it.value > 0.5}.keys

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val lowerBound = trips.map { it.duration }
            .groupBy { (it / 10) * 10 }
            .maxBy { it.value.size }?.key
    return lowerBound?.rangeTo(lowerBound + 9)
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false
    val driversToIncome = allDrivers.map { d ->
        d to trips.filter { t -> t.driver == d }.map{ it.cost }.sum()
    }.sortedByDescending { it.second }
    val take = driversToIncome.size / 5
    val topFifthIncome = driversToIncome.take(take).map { it.second }.sum()
    val totalIncome = driversToIncome.map { it.second }.sum()
    return (topFifthIncome / totalIncome) >= 0.8
}
