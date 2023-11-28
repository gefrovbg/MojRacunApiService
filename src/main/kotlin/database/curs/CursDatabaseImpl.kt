package database.curs

import database.data.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import tools.printCustom
import tools.transactionCustom
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit


@Service
class CursDatabaseImpl: CursDatabase {

    override fun getLatestCurs(): List<Curs> {
        return transactionCustom {
            val cursList = mutableListOf<Curs>()
            BankEntity.all().forEach { bankEntity ->
                bankEntity.curses
                    .maxByOrNull { it.dateTime }
                    .let {
                        if (it?.toCurs() != null)
                            cursList.add(it.toCurs())
                    }

            }

            cursList
        }
    }

    override fun getLatestTwoWeekByBank(bankName: String): BankWithListCurses? {
        return transactionCustom {
            val cursList = mutableListOf<CursForList>()
            val twoWeeksAgo = LocalDateTime.now(ZoneOffset.UTC).minus(2, ChronoUnit.WEEKS)

            val bank = BankEntity.find {
                Banks.name eq bankName
            }
            if (!bank.empty()){
                val bankForReturn = bank.first().toBank()

                bank.first().curses
                    .filter { cursEntityList ->
                        cursEntityList.dateTime > twoWeeksAgo
                    }.forEach {
                        cursList.add(it.toCursForList())
                    }

                BankWithListCurses(bank = bankForReturn, curses = cursList.sortedBy { it.dateTime })
            }else
                null

        }
    }

    override fun saveCurs(bank: String, usd: Double, euro: Double, bankLabel: String) {
        transactionCustom {
            val dbBank = BankEntity.find {
                Banks.name eq bank
            }.firstOrNull() ?: BankEntity.new {
                name = bank
                labelName = bankLabel
                imageUrl = ""
            }
            CursEntity.new {
                dateTime = LocalDateTime.now()
                this.usd = usd
                this.euro = euro
                this.bank = dbBank
            }
        }
    }



    override fun getGroupedDollarLatestTwoWeekByBank(bankName: String): BankWithGroupedCurses.BankWithGroupedListCurses<CursDollar>? {
        return transactionCustom {
            val cursList = mutableListOf<CursDollar>()
            val twoWeeksAgo = LocalDateTime.now(ZoneOffset.UTC).minus(2, ChronoUnit.WEEKS)

            val bank = BankEntity.find {
                Banks.name eq bankName
            }
            if (!bank.empty()){
                val bankForReturn = bank.first().toBank()

                bank.first().curses
                    .filter { cursEntityList ->
                        cursEntityList.dateTime > twoWeeksAgo
                    }.forEach {
                        cursList.add(it.toCursDollar())
                    }
                BankWithGroupedCurses.BankWithGroupedListCurses(
                    bank = bankForReturn,
                    curses = groupByCurrencyDollar1(cursList.sortedBy { it.dateTimeStart })
                )
            }else
                null

        }
    }

    override fun getGroupedEuroLatestTwoWeekByBank(bankName: String): BankWithGroupedCurses.BankWithGroupedListCurses<CursEuro>? {
        return transactionCustom {
            val cursList = mutableListOf<CursEuro>()
            val twoWeeksAgo = LocalDateTime.now(ZoneOffset.UTC).minus(2, ChronoUnit.WEEKS)

            val bank = BankEntity.find {
                Banks.name eq bankName
            }
            if (!bank.empty()){
                val bankForReturn = bank.first().toBank()

                bank.first().curses
                    .filter { cursEntityList ->
                        cursEntityList.dateTime > twoWeeksAgo
                    }.forEach {
                        cursList.add(it.toCursEuro())
                    }
                BankWithGroupedCurses.BankWithGroupedListCurses(
                    bank = bankForReturn,
                    curses = groupByCurrencyEuro(cursList.sortedBy { it.dateTimeStart })
                )
            }else
                null

        }
    }

    private fun groupByCurrencyDollar1(dataList: List<CursDollar>): List<CursDollar> {
        val groupedData = mutableListOf<CursDollar>()
        var countGroupedData = 0

        dataList.forEachIndexed { index, item ->
            try {
                if (index != 0){
                    val prevValue = dataList[index-1]
                    if (prevValue.value == item.value){
                        groupedData[countGroupedData] = groupedData[countGroupedData].copy(dateTimeStop = item.dateTimeStart)
                    }else{
                        groupedData[countGroupedData] = groupedData[countGroupedData].copy(dateTimeStop = item.dateTimeStart)
                        countGroupedData += 1
                        groupedData.add(item)
                    }
                }else{
                    groupedData.add(item)
                }
            }catch (e: Exception){
                printCustom("groupByCurrency: ${e.message}")
            }

        }

        return groupedData
    }

    private fun groupByCurrencyEuro(dataList: List<CursEuro>): List<CursEuro> {
        val groupedData = mutableListOf<CursEuro>()
        var countGroupedData = 0

        dataList.forEachIndexed { index, item ->
            try {
                if (index != 0){
                    val prevValue = dataList[index-1]
                    if (prevValue.value == item.value){
                        groupedData[countGroupedData] = groupedData[countGroupedData].copy(dateTimeStop = item.dateTimeStart)
                    }else{
                        groupedData[countGroupedData] = groupedData[countGroupedData].copy(dateTimeStop = item.dateTimeStart)
                        countGroupedData += 1
                        groupedData.add(item)
                    }
                }else{
                    groupedData.add(item)
                }
            }catch (e: Exception){
                printCustom("groupByCurrency: ${e.message}")
            }

        }

        return groupedData
    }

}