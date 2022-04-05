package taxipark

import kotlin.math.floor

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.filter {
            d -> trips.all { t -> d.name != t.driver.name }
        }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
    val passengersList = trips.map { tp -> tp.passengers.map { p -> p.name } }
    var countPassenger = allPassengers.associateWith {
        p -> passengersList.count { pl -> p.name in pl }
    }
    return countPassenger.filter { it.value >= minTrips }.map { it.key }.toSet()
}


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */

fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {

    var driverPassengerPairs = trips.flatMap {
            dp -> dp.passengers.map { p -> dp.driver.name to p.name }
    }
    var resultMap = driverPassengerPairs.associateWith {
            dpp -> driverPassengerPairs.count { c -> (c.first == dpp.first && c.second == dpp.second)  }
    }
    var resultMap2 = resultMap.filter { rf -> rf.value > 1 && rf.key.first == driver.name }

    var result = allPassengers.filter {
        f -> resultMap2.any { r -> r.key.second == f.name }
    }

    return result.toSet()
}


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    var (discountTrips,notDiscoutTrips) = trips.partition { it.discount != null && it.discount > 0.0 }

    var discountTripsCount = allPassengers.associateWith {
            x1 -> discountTrips.count { x2 -> x2.passengers.any { x3 -> x3.name == x1.name }} }

    var notDiscountTripsCount = allPassengers.associateWith {
            x1 -> notDiscoutTrips.count { x2 -> x2.passengers.any { x3 -> x3.name == x1.name }} }
    var resultPairs = discountTripsCount.filter {
            x1 -> notDiscountTripsCount.any {
            x2 -> x2.key.name == x1.key.name && x1.value > x2.value } }

    return resultPairs.map { it.key }.toSet()

}


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    var durationLists = trips.map { it.duration }
    var div = durationLists.max()?.plus(10)?.div(10)
    var durationRangeLists = mutableListOf<IntRange>()
    var start = 0
    if(div != null){
        for(i in 0 until div){
            durationRangeLists.add(start .. start + 9)
            start = start + 10
        }

       var durationRangeCount = durationRangeLists.associateWith { x1 -> durationLists.count { x2 -> x2 in x1 } }
       var freq = durationRangeCount.maxBy { it.value }

       return freq?.key
    }

    return null
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
  var driverl = floor(allDrivers.size.times(0.2)).toInt()
  if (!trips.isEmpty()){
      var driverLists =  allDrivers.filter {
              d -> trips.any { t -> d.name == t.driver.name }
      }
      var incomesMap = driverLists.associateWith {
              x1 -> trips.sumBy { x2 ->
                  if ( x1.name == x2.driver.name ) return@sumBy x2.cost.toInt()
                  else return@sumBy 0
      } }

      var sortedDrivers = incomesMap.toList().sortedByDescending { (_,v) -> v }
      var bestDrivers = sortedDrivers.subList(0,driverl)

      var income = bestDrivers.sumBy { it.second }
      var totalIncome = sortedDrivers.sumBy { it.second }
      var eightyIncome = totalIncome.times(0.8)

      return income >= eightyIncome
  }
    return false
}
