package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val perList = permutation.toMutableList()
    var swapCount = 0
    for(i in perList.indices){
        for (j in 0 until perList.size-i-1){
            val temp = perList[j]
            if (temp > perList[j+1]){
                perList[j] = perList[j+1]
                perList[j+1] = temp
                swapCount++
            }
        }
    }
    return swapCount%2 == 0
}
