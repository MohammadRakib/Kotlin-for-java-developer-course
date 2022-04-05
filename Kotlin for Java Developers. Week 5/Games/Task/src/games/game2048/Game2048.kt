package games.game2048

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game =
    Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) {
    val (cell, value) = initializer.nextValue(this) ?: (null to null)
    if (cell != null && value != null) {
        this[cell] = value
    }
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
    val rowOrColumnVal = rowOrColumn.map { this[it] }
    val temp = rowOrColumnVal.moveAndMergeEqual { it + it }

    if (temp == rowOrColumnVal){
        return false
    }else if (rowOrColumnVal.filterNotNull().isEmpty()){
        return false
    }
    var i = 0
    while (i < rowOrColumn.size) {
        if (i < temp.size) {
            this[rowOrColumn[i]] = temp[i]
        } else {
            this[rowOrColumn[i]] = null
        }
        i++
    }
    return true
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
    if (Direction.UP == direction) {
        val col1 = moveValuesInRowOrColumn(getColumn(1..4, 1))
        val col2 = moveValuesInRowOrColumn(getColumn(1..4, 2))
        val col3 = moveValuesInRowOrColumn(getColumn(1..4, 3))
        val col4 = moveValuesInRowOrColumn(getColumn(1..4, 4))
        return col1 || col2 || col3 || col4
    } else if (Direction.DOWN == direction) {
        val col1 = moveValuesInRowOrColumn(getColumn(4 downTo 1, 1))
        val col2 = moveValuesInRowOrColumn(getColumn(4 downTo 1, 2))
        val col3 = moveValuesInRowOrColumn(getColumn(4 downTo 1, 3))
        val col4 = moveValuesInRowOrColumn(getColumn(4 downTo 1, 4))
        return col1 || col2 || col3 || col4
    } else if (Direction.RIGHT == direction) {
        val col1 = moveValuesInRowOrColumn(getRow(1, 4 downTo 1))
        val col2 = moveValuesInRowOrColumn(getRow(2, 4 downTo 1))
        val col3 = moveValuesInRowOrColumn(getRow(3, 4 downTo 1))
        val col4 = moveValuesInRowOrColumn(getRow(4, 4 downTo 1))
        return col1 || col2 || col3 || col4
    } else {
        val col1 = moveValuesInRowOrColumn(getRow(1, 1..4))
        val col2 = moveValuesInRowOrColumn(getRow(2, 1..4))
        val col3 = moveValuesInRowOrColumn(getRow(3, 1..4))
        val col4 = moveValuesInRowOrColumn(getRow(4, 1..4))
        return col1 || col2 || col3 || col4
    }
}
