package nicestring

var cond1 = false
var cond2 = false
var cond3 = false

fun String.checkCondition1() : Boolean{
    var pairStr = this.zipWithNext()
    var badcheck = pairStr.map { (it.first == 'b' && it.second == 'u') ||
                  (it.first == 'b' && it.second == 'a') ||
                  (it.first == 'b' && it.second == 'e')  }
    return true !in badcheck
}

fun String.checkCondition2() : Boolean{
    var vowels = "aeiou"
    var count  = this.sumBy { c -> vowels.count {it == c} }
    return count >= 3
}

fun String.checkCondition3() : Boolean{
    var doublePairLetter = this.zipWithNext()
    var doubleCheck = doublePairLetter.map { it.first == it.second }
    return true in doubleCheck
}
fun String.isNice(): Boolean {
    cond1 = this.checkCondition1()
    cond2 = this.checkCondition2()
    cond3 = this.checkCondition3()

    return  (cond1 && cond2) || (cond1 && cond3) || (cond2 && cond3)
}