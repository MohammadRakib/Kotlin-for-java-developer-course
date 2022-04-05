package board

import board.Direction.*
import java.lang.IllegalArgumentException

fun createSquareBoard(width: Int): SquareBoard = SqrBoardImp(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImp(width)

open class SqrBoardImp(width: Int) : SquareBoard{

    override val width = width
    val listOfCells = mutableListOf<Cell>()

    init {
        for (i in 1..width){
            for (j in 1..width){
                listOfCells.add(Cell(i,j))
            }
        }
    }
    
    override fun getCellOrNull(i: Int, j: Int): Cell? {
       return listOfCells.find { it.i == i && it.j == j }
    }

    override fun getCell(i: Int, j: Int): Cell {
        return listOfCells.find { it.i == i && it.j == j } ?: throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> {
        return listOfCells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        if(jRange.first > jRange.last){
           return listOfCells.filter { it.i == i && it.j in jRange }.reversed()
        }
        return listOfCells.filter { it.i == i && it.j in jRange }

    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        if(iRange.first > iRange.last){
            return listOfCells.filter { it.i in iRange && it.j == j }.reversed()
        }
        return listOfCells.filter { it.i in iRange && it.j == j }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
           return when(direction){
                UP -> listOfCells.find { l -> l.i == (i-1) && l.j == j }
                DOWN -> listOfCells.find { l -> l.i == (i+1) && l.j == j }
                RIGHT -> listOfCells.find { l -> l.i == i && l.j == (j+1) }
                else -> listOfCells.find { l -> l.i == i && l.j == (j-1) }
            }
    }

}

class GameBoardImp<T>(width: Int) : SqrBoardImp(width), GameBoard<T>{

    override val width = width
    private val cellsValueMaps = mutableMapOf<Cell,T?>()

    init {
        for(x in listOfCells){
            cellsValueMaps[x] = null
        }
    }

    override fun get(cell: Cell): T? {
        return cellsValueMaps[cell]
    }

    override fun set(cell: Cell, value: T?) {
        cellsValueMaps[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellsValueMaps.filter { predicate(it.value) }.map { it.key }
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return cellsValueMaps.filter { predicate(it.value) }.keys.firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cellsValueMaps.any { predicate(it.value) }
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return cellsValueMaps.all { predicate(it.value) }
    }

}