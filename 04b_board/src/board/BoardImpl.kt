package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)


class SquareBoardImpl(override val width: Int) : SquareBoard {

    val cells: List<Cell> = (0 until width*width).map {
        val quotient = it / width
        val remainder = it % width
        Cell(remainder + 1, quotient + 1)
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i !in 1..width || j !in 1..width) return null
        val index = coordinatesToIndex(i, j)
        return cells[index]
    }

    override fun getCell(i: Int, j: Int): Cell =
            getCellOrNull(i, j) ?: throw IllegalArgumentException("Both arguments (i, j) should be between 1 and $width. getCell was called with ($i, $j)")

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
            jRange.filter{ it in 1..width }.map{ getCell(i, it) }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
            iRange.filter{ it in 1..width }.map{ getCell(it, j) }

    override fun Cell.getNeighbour(direction: Direction): Cell? = when(direction) {
        UP -> getCellOrNull(i - 1, j)
        DOWN -> getCellOrNull(i + 1, j)
        LEFT -> getCellOrNull(i, j - 1)
        RIGHT -> getCellOrNull(i, j + 1)
    }

    private fun coordinatesToIndex(i: Int, j: Int): Int {
        return (i - 1) + (j - 1) * width
    }
}


class GameBoardImpl<T> private constructor(private val squareBoard: SquareBoard) : GameBoard<T>, SquareBoard by squareBoard {

    constructor(width: Int) : this(SquareBoardImpl(width))

    private val cellValues: MutableMap<Cell, T?> = getAllCells().map{ it to null }.toMap().toMutableMap()

    override operator fun get(cell: Cell): T? = cellValues.get(cell)
    override operator fun set(cell: Cell, value: T?) {
        cellValues[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = cellValues.filterValues{ predicate(it) }.map{ it.key }
    override fun find(predicate: (T?) -> Boolean): Cell? = cellValues.filterValues{ predicate(it) }.map{ it.key }.first()
    override fun any(predicate: (T?) -> Boolean): Boolean = cellValues.any{ predicate(it.value) }
    override fun all(predicate: (T?) -> Boolean): Boolean = cellValues.all{ predicate(it.value) }
}