package games.gameOfFifteen

import board.*
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteenImpl(initializer)


class GameOfFifteenImpl(private val initializer: GameOfFifteenInitializer) : Game {

    private val gameBoard = createGameBoard<Int>(4)

    override fun initialize() {
        (gameBoard.getCellsInOrder() zip initializer.initialPermutation).forEach{
            gameBoard[it.first] = it.second
        }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        val numbers = getNumbersInOrder()
        return numbers.take(15) == (1..15).toList()
    }

    override fun processMove(direction: Direction) {
        gameBoard.processMove(direction)
    }

    private fun <T> GameBoard<T>.processMove(direction: Direction) {
        val emptyCell = getEmptyCell()
        emptyCell.getNeighbour(direction.reversed())?.let {
            gameBoard[emptyCell] = gameBoard[it]
            gameBoard[it] = null
        }
    }

    override fun get(i: Int, j: Int): Int? {
        return gameBoard.getCellOrNull(i, j)?.let { gameBoard[it] }
    }

    private fun <T> GameBoard<T>.getCellsInOrder(): List<Cell> {
        val rows = 1..width
        val columns = 1..width
        return rows.map{ getRow(it, columns) }.flatten()
    }

    private fun getNumbersInOrder(): List<Int?> {
        return with(gameBoard) {
            getCellsInOrder().map{ get(it) }
        }
    }

    private fun getEmptyCell() : Cell {
        return gameBoard.find { it == null } ?: throw IllegalStateException("No empty square found!")
    }
}
