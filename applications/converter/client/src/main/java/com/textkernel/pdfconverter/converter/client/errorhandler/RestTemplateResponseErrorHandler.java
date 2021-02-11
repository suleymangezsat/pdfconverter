package com.textkernel.pdfconverter.converter.client.errorhandler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import com.textkernel.pdfconverter.converter.client.exception.ApiClientException;

/**
 * Handles errors received by {@link org.springframework.web.client.RestTemplate}
 */
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	/**
	 * @param httpResponse
	 * 		reponse object to check if it contains error
	 * @return if the response has error or not
	 *
	 * @throws IOException
	 * 		in case of an unknown HTTP status code
	 */
	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
		HttpStatus.Series series = httpResponse.getStatusCode().series();

		return series == HttpStatus.Series.CLIENT_ERROR || series == HttpStatus.Series.SERVER_ERROR;
	}

	/**
	 * @param httpResponse
	 * 		http response which is used to handle error
	 * @throws IOException
	 * 		in case of an unknown HTTP status code
	 */
	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {
		HttpStatus.Series series = httpResponse.getStatusCode().series();

		if (series == HttpStatus.Series.SERVER_ERROR) {
			throw new HttpClientErrorException(httpResponse.getStatusCode());
		} else if (series == HttpStatus.Series.CLIENT_ERROR) {
			throw new ApiClientException(httpResponse.getStatusText());
		}
	}
}