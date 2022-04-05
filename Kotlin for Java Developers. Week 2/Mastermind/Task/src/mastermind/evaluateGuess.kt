package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

@OptIn(ExperimentalStdlibApi::class)
fun evaluateGuess(secret: String, guess: String): Evaluation {
    var scr = secret.toCharArray()
    var gs = guess.toCharArray()
    var rightPosition: Int = 0
    var wrongPosition: Int = 0

    fun calcRightPosition(){
        for (i in 0 .. 3) {
            if(secret[i] == guess[i]){
                rightPosition += 1
                scr[i] = ' '
                gs[i] = ' '
            }
        }
    }

    fun calcWrongPosition(){
        var scr2 : String = scr.concatToString()
        var gs2 : String = gs.concatToString()

        for(x in gs2){
            if(x in scr2 && x != ' '){
                wrongPosition += 1
                scr2 = scr2.replaceFirst(x,' ')
            }
        }
    }

    calcRightPosition()
    calcWrongPosition()
    return Evaluation(rightPosition,wrongPosition)
}
