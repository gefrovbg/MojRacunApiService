package database.curs

import database.data.*

interface CursDatabase {

    fun getLatestCurs(): List<Curs>

    fun getLatestTwoWeekByBank(bankName: String): BankWithListCurses?

    fun getGroupedDollarLatestTwoWeekByBank(bankName: String): BankWithGroupedCurses.BankWithGroupedListCurses<CursDollar>?

    fun getGroupedEuroLatestTwoWeekByBank(bankName: String): BankWithGroupedCurses.BankWithGroupedListCurses<CursEuro>?

    fun saveCurs(bank: String, usd: Double, euro: Double, bankLabel: String)

}