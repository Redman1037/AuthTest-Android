package rest.restutils

import rest.restmodels.CommonResponse
import java.io.IOException

class ApiException(val commonResponse: CommonResponse) : IOException(commonResponse.message)
class NoInternetException(message: String) : IOException(message)