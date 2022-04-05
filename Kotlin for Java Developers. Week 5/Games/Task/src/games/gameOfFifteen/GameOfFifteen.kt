package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(val initializer: GameOfFifteenInitializer) : Game{
    private val board = createGameBoard<Int?>(4)
    override fun initialize() {
        val boardListOfPerm = initializer.initialPermutation
        val allCells = board.getAllCells().toList()
        for (i in boardListOfPerm.indices){
            board[allCells[i]] = boardListOfPerm[i]
        }
    }

    override fun canMove(): Boolean {
        return !hasWon()
    }

    override fun hasWon(): Boolean {
        val allCells = board.getAllCells().toList()
        val goal = (1..15).toList()
        val currentAllCellVal = allCells.mapNotNull { board[it] }
        return goal == currentAllCellVal
    }

    fun GameBoard<Int?>.getNeighbour(cell: Cell, direction: Direction): Cell? {
        return cell.getNeighbour(direction)
     }

    override fun processMove(direction: Direction) {
        val valueOfNullCell = board.find { it == null } as Cell
        val nb = board.getNeighbour(valueOfNullCell,direction.reversed())
        if (nb != null){
            val temp = board[nb]
            board[nb] = null
            board[valueOfNullCell] = temp
        }
    }

    override fun get(i: Int, j: Int): Int? {
        return board.run { get(getCell(i, j)) }
    }

}