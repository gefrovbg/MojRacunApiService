package tools

//import com.google.gson.Gson
//import data.Curs
//import data.CursOfBanks
//import me.gefro.FinanceBotWebAppBackend.data.CursOfBank
//
//internal fun convertToCursOfBank(
//    curs2: CursOfBanks
//): CursOfBank{
//    val hip = Gson().fromJson(curs2.hip, Curs::class.java)
//    val ckb = Gson().fromJson(curs2.ckb, Curs::class.java)
//    val lov = Gson().fromJson(curs2.lovcen, Curs::class.java)
//    val nlb = Gson().fromJson(curs2.nlb, Curs::class.java)
//    val prva = Gson().fromJson(curs2.prva, Curs::class.java)
//    val erste = Gson().fromJson(curs2.erste, Curs::class.java)
//    val ziraat = Gson().fromJson(curs2.ziraat, Curs::class.java)
//    val curs = CursOfBank(
//        date = curs2.date,
//        hip = hip,
//        lovcen = lov,
//        ckb = ckb,
//        nlb = nlb,
//        prva = prva,
//        erste = erste,
//        ziraat = ziraat,
//        time = curs2.time
//    )
//    return curs
//}