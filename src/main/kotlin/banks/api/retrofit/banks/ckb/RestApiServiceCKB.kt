package banks.api.retrofit.banks.ckb

import banks.api.retrofit.banks.ckb.data.CKBResponse
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tools.printCustom

@Service
class RestApiServiceCKB {

    fun getCurs(id: Int, datumOd: String, datumDo: String, onResult: (CKBResponse?) -> Unit){
        val retrofit = ServiceBuilderCKB.buildService(RetrofitApiCKB::class.java)
        retrofit.getInfo(id = id, datumOd = datumOd, datumDo = datumDo).enqueue(
            object : Callback<CKBResponse> {
                override fun onFailure(call: Call<CKBResponse>, t: Throwable) {
                    printCustom(t.toString())
                    onResult(null)
                }
                override fun onResponse(call: Call<CKBResponse>, response: Response<CKBResponse>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }

}