# Coroutine_Retrofit2
코루틴과 Retrofit2를 조합하여 보다 합리적인 로직을 구현했습니다.

MainViewModel.kt
<pre>
<code>
class MainViewModel : ViewModel() {

    var userData: MutableLiveData<UserResponse> = MutableLiveData()

    // 코루틴 scope 내부 Exception에 대한 처리 핸들러입니다.
    private val coroutineExceptionHanlder =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.printStackTrace()
        }

    //Dispatchers를 선언하고 위에서 만든 Exception 핸들러와 결합합니다.
    private val ioDispatchers = Dispatchers.IO + coroutineExceptionHanlder
    private val uiDispatchers = Dispatchers.Main + coroutineExceptionHanlder


    init {
    //viewModel이 생성됨과 동시에 user 데이터를 불러옵니다.
        getUser()
    }


    private fun getUser() {

        var tmpData: UserResponse? = null
        // IO 스레드에서 getUserData()를 실행하고 응답받은 데이터는 실제 liveData인 userData의 값은 Main 스레이드에서 처리합니다.
        viewModelScope.launch(ioDispatchers) {

            try {
                RetrofitObject.getApiService().getUserData().apply {

                    if (this.age.isNotEmpty() && this.name.isNotEmpty()) {
                        tmpData = this
                    }

                    withContext(uiDispatchers) {
                        userData.value = tmpData
                    }
                }
            } catch (e: Exception) {
              //예외처리 코드를 추가합니다.
            }
        }
    }
}
</code>
</pre>   
APIService.kt     

<pre>
<code>
interface APIService {

    // Retrofit2 2.6.0 이상버전부터는 Response<T>로 결과값을 받을 필요가 없습니다. 
    // 해서 data class인 UserResponse를 넣었습니다.
    @GET("/HostingRepository/mvvm_sample_data.json")
    suspend fun getUserData() : UserResponse
}
</code>
</pre>    

RetrofitObject.kt
<pre>
<code>
object RetrofitObject {

  //데이터 통신에 필요한 Retorfit 객체를 싱글톤으로 관리합니다.

    private fun getRetrofit(): Retrofit{

        val baseUrl = "https://mjjang94.github.io"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): APIService{
        return getRetrofit().create(APIService::class.java)
    }
}
</code>
</pre>

